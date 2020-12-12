package com.jde.skillbill.donnees.APIRest;

import android.util.Base64;
import android.util.JsonReader;
import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jde.skillbill.domaine.entites.Facture;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.entites.UtilisateurException;
import com.jde.skillbill.domaine.interacteurs.ISourceDonnee;
import com.jde.skillbill.domaine.interacteurs.interfaces.SourceDonneeException;
import com.jde.skillbill.donnees.APIRest.entites.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class SourceDonneesAPIRest implements ISourceDonnee {
    private final String URI_BASE = "https://skillbillweb.azurewebsites.net/api/";
    private final String POINT_ENTREE_UTILISATEUR = "utilisateurs/";
    private final String POINT_ENTREE_GROUPE = "groupes/";
    private final String POINT_ENTREE_LOGIN = "login";
    private static final int READ_TIME_OUT = 8000;
    private static final int CONNECT_TIME_OUT = 4000;
    private final String POINT_ENTREE_FACTURE = "factures/";
    private final String POINT_ENTREE_PHOTO = "photos/";
    private static String apiKey = "";

    //region Factures
    @Override
    public List<Facture> lireFacturesParGroupe(Groupe groupe) throws SourceDonneeException {
        URL url = null;
        Utilisateur utilisateur = null;
        try {
            url = new URL(URI_BASE + "utilisateurs/0/groupes/" + ((GroupeRestApi) groupe).getId() + "/factures");
        } catch (MalformedURLException e) {
            Log.e("SOurceDonneAPI: ", e.toString());
            throw new SourceDonneeException("Connection non disponible");
        }

        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            Gson gson = new Gson();
            reglerTimeout(httpURLConnection);
            reglerHeader(httpURLConnection);
            if (httpURLConnection.getResponseCode() == 200) {
                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8);

                FactureRestAPI[] factureRestAPIS = gson.fromJson(inputStreamReader, FactureRestAPI[].class);
                List<Facture> factureRestAPIS1 = new ArrayList<>();
                if (factureRestAPIS != null) {

                    for (FactureRestAPI factureRestAPI : factureRestAPIS) {
                        HashMap<Utilisateur, Double> utilisateurMontantMap = new HashMap<>();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

                        factureRestAPI.setDateFacture(LocalDate.parse(factureRestAPI.getDate().substring(0,10), formatter));

                        for (PayeursEtMontant payeursEtMontant : factureRestAPI.getPayeursEtMontantsListe()) {

                            utilisateurMontantMap.put(new UtilisateurRestAPI(payeursEtMontant.getIdPayeur()), payeursEtMontant.getMontantPaye());
                        }
                        for (UtilisateurRestAPI utilisateurRestAPI : ((GroupeRestApi) groupe).getUtilisateursRestApi()) {

                            utilisateurMontantMap.putIfAbsent(utilisateurRestAPI, 0.0);
                        }
                        factureRestAPI.setMontantPayeParParUtilisateur(utilisateurMontantMap);

                    }
                    factureRestAPIS1.addAll(Arrays.asList(factureRestAPIS));

                    return factureRestAPIS1;
                }
            }


        } catch (java.net.SocketTimeoutException e) {
            throw new SourceDonneeException("Connection non disponible");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean modifierFacture(Facture facture) throws SourceDonneeException {
        URL url = null;
        try {

            url = new URL(URI_BASE + POINT_ENTREE_FACTURE);
        } catch (MalformedURLException e) {
            Log.e("SOurceDonneAPI : ", e.toString());
            throw new SourceDonneeException("Connection non disponible");
        }

        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("Content-Type", "application/json ; utf-8 ");
            httpURLConnection.setRequestMethod("PUT");
            reglerHeader(httpURLConnection);
            reglerTimeout(httpURLConnection);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            List<PayeursEtMontant> payeursEtMontant = new ArrayList<>();
            for (Utilisateur utilisateur : facture.getMontantPayeParParUtilisateur().keySet()) {
                payeursEtMontant.add(new PayeursEtMontant(((UtilisateurRestAPI) utilisateur).getId(), facture.getMontantPayeParParUtilisateur().get(utilisateur)));
            }


            ((FactureRestAPI) facture).setPayeursEtMontantsListe(payeursEtMontant);
            facture.setMontantTotal(facture.getMontantTotal());
            if (Objects.requireNonNull(facture.getPhotos()).size() != 0) { // Ne fonctionne que pour une photo
                for (byte[] bytes : facture.getPhotos()) {
                    if (((FactureRestAPI) facture).getPhotosRestAPI().size() != 0)
                        ((FactureRestAPI) facture).getPhotosRestAPI().get(0).setPhotoEncodee(Base64.encodeToString(bytes, Base64.DEFAULT));
                    else
                        ((FactureRestAPI) facture).getPhotosRestAPI().add(new PhotoRestApi(Base64.encodeToString(bytes, Base64.DEFAULT)));
                }
            }

            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(facture);


            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            outputStream.write(input, 0, input.length);
            if (httpURLConnection.getResponseCode() == 200) {
                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String sortie;
                while ((sortie = bufferedReader.readLine()) != null) {
                    stringBuilder.append(sortie);
                }

                return "true".equals(stringBuilder.toString());
            }

        } catch (java.net.SocketTimeoutException e) {
            throw new SourceDonneeException("Connection non disponible");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean creerFacture(Facture facture) throws SourceDonneeException {
        URL url = null;
        try {
            url = new URL(URI_BASE + "Factures");
        } catch (MalformedURLException e) {
            Log.e("SOurceDonneAPI: ", e.toString());
            throw new SourceDonneeException("Connection non disponible");
        }
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            reglerTimeout(httpURLConnection);
            reglerHeader(httpURLConnection);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json ; utf-8 ");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            Gson gson = new GsonBuilder().create();

            List<PayeursEtMontant> payeursEtMontant = new ArrayList<>();
            for (Utilisateur utilisateur : facture.getMontantPayeParParUtilisateur().keySet()) {
                payeursEtMontant.add(new PayeursEtMontant(((UtilisateurRestAPI) utilisateur).getId(), facture.getMontantPayeParParUtilisateur().get(utilisateur)));
            }

            if (!(facture instanceof FactureRestAPI)) {
                FactureRestAPI factureRestAPI = new FactureRestAPI(facture.getDateFacture().toString(),
                        ((GroupeRestApi) facture.getGroupe()).getId(),
                        facture.getMontantTotal(),
                        ((UtilisateurRestAPI) facture.getUtilisateurCreateur()).getId());
                factureRestAPI.setLibelle(facture.getLibelle());
                factureRestAPI.setPayeursEtMontantsListe(payeursEtMontant);
                if (!Objects.requireNonNull(facture.getPhotos()).isEmpty()) {
                    for (byte[] bytes : facture.getPhotos()) {
                        factureRestAPI.getPhotosRestAPI().add(new PhotoRestApi(Base64.encodeToString(bytes, Base64.DEFAULT)));
                    }
                }
                facture = factureRestAPI;
            } else {
                ((FactureRestAPI) facture).setPayeursEtMontantsListe(payeursEtMontant);
            }

            String json = gson.toJson(facture);

            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            outputStream.write(input, 0, input.length);

            if (httpURLConnection.getResponseCode() == 200) {
                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String sortie;
                while ((sortie = bufferedReader.readLine()) != null) {
                    stringBuilder.append(sortie);
                }

                return "true".equals(stringBuilder.toString());
            }

        } catch (java.net.SocketTimeoutException e) {
            throw new SourceDonneeException("Connection non disponible");
        } catch (IOException e) {
            e.printStackTrace();
        }


        return false;
    }


    public FactureRestAPI rechargerFacture(Facture facture) throws SourceDonneeException {
        URL url = null;
        FactureRestAPI factureRestAPI;
        try {
            factureRestAPI = (FactureRestAPI) facture;

        } catch (ClassCastException e) {
            Log.e("Source donnée API Rest :", e.getStackTrace().toString());
            throw new SourceDonneeException("entité invalide pour la source de donnée");
        }
        try {
            url = new URL(URI_BASE + POINT_ENTREE_FACTURE + factureRestAPI.getId());
        } catch (MalformedURLException e) {
            Log.e("SOurceDonneAPI: ", e.toString());
        }
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            reglerTimeout(httpURLConnection);
            reglerHeader(httpURLConnection);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Content-Type", "application/json ; utf-8 ");

            if (httpURLConnection.getResponseCode() == 200) {
                Gson gson = new Gson();
                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8);
                factureRestAPI = gson.fromJson(inputStreamReader, FactureRestAPI.class);
                factureRestAPI.setPhotos(new ArrayList<>());
                for (PhotoRestApi photoRestApi : factureRestAPI.getPhotosRestAPI()) {
                    String base64Photo = photoRestApi.getPhotoEncodee();
                    factureRestAPI.getPhotos().add(Base64.decode(base64Photo.toString(), Base64.DEFAULT));
                }
            }
        } catch (java.net.SocketTimeoutException e) {
            throw new SourceDonneeException("Connection non disponible");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return factureRestAPI;
    }


    @Override
    public List<byte[]> chargerPhotos(Facture factureEnCours) throws SourceDonneeException {
        URL url = null;
        List<PhotoRestApi> photoRestApiList = null;
        List<byte[]> photosBytes = new ArrayList<>();
        try {
            photoRestApiList = ((FactureRestAPI) factureEnCours).getPhotosRestAPI();
        } catch (ClassCastException e) {
            Log.e("Source donnée API Rest :", e.getStackTrace().toString());
            throw new SourceDonneeException("entité invalide pour la source de donnée");
        }
        if (photoRestApiList == null || photoRestApiList.size() == 0) {
            return photosBytes;
        } else {
            for (PhotoRestApi photoRestApi : photoRestApiList) {
                try {
                    url = new URL(URI_BASE + POINT_ENTREE_FACTURE + POINT_ENTREE_PHOTO + photoRestApi.getId());
                } catch (MalformedURLException e) {
                    Log.e("SOurceDonneAPI: ", e.toString());
                }

                HttpURLConnection httpURLConnection = null;
                try {
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    reglerTimeout(httpURLConnection);
                    reglerHeader(httpURLConnection);
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("Content-Type", "application/json ; utf-8 ");

                    if (httpURLConnection.getResponseCode() == 200) {
                        InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        StringBuilder stringBuilder = new StringBuilder();
                        String sortie;
                        while ((sortie = bufferedReader.readLine()) != null) {
                            stringBuilder.append(sortie);
                        }
                        byte[] photoByte = Base64.decode(stringBuilder.toString(), Base64.DEFAULT);
                        photosBytes.add(photoByte);
                    }
                } catch (java.net.SocketTimeoutException e) {
                    throw new SourceDonneeException("Connection non disponible");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return photosBytes;
    }


//endregion

//region Groupe

    @Override
    public Groupe creerGroupeParUtilisateur(Utilisateur utilisateur, Groupe groupe) throws SourceDonneeException {
        URL url = null;
        try {

            url = new URL(URI_BASE + POINT_ENTREE_UTILISATEUR + ((UtilisateurRestAPI) utilisateur).getId() + "/groupes");
        } catch (MalformedURLException e) {
            Log.e("SOurceDonneAPI : ", e.toString());
            throw new SourceDonneeException("Connection non disponible");
        }
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            reglerTimeout(httpURLConnection);
            reglerHeader(httpURLConnection);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json ; utf-8 ");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(new GroupeRestApi(groupe.getNomGroupe(), utilisateur, groupe.getMonnaieDuGroupe(), 0));
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            outputStream.write(input, 0, input.length);

            if (httpURLConnection.getResponseCode() == 200) {
                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8);
                GroupeRestApi groupeRestApi = gson.fromJson(inputStreamReader, GroupeRestApi.class);
                return groupeRestApi;
            }

        } catch (java.net.SocketTimeoutException e) {
            throw new SourceDonneeException("Connection non disponible");
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public List<Groupe> lireTousLesGroupesAbonnes(Utilisateur utilisateur) throws SourceDonneeException {
        URL url = null;
        try {

            url = new URL(URI_BASE + POINT_ENTREE_UTILISATEUR + ((UtilisateurRestAPI) utilisateur).getId() + "/groupes");
        } catch (MalformedURLException e) {
            Log.e("SOurceDonneAPI : ", e.toString());
            throw new SourceDonneeException("Connection non disponible");
        }
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            reglerTimeout(httpURLConnection);
            reglerHeader(httpURLConnection);
            Gson gson = new Gson();
            if (httpURLConnection.getResponseCode() == 200) {
                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8);
                GroupeRestApi[] groupeRestApis = gson.fromJson(inputStreamReader, GroupeRestApi[].class);
                List<Groupe> groupeRestApis1 = new ArrayList<>();
                if (groupeRestApis != null) {
                    groupeRestApis1.addAll(Arrays.asList(groupeRestApis));
                    return groupeRestApis1;
                }

            }
        } catch (java.net.SocketTimeoutException e) {
            throw new SourceDonneeException("Connection non disponible");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Utilisateur> lireUTilisateurParGroupe(Groupe groupe) throws SourceDonneeException {
        URL url = null;
        try {

            url = new URL(URI_BASE + POINT_ENTREE_UTILISATEUR + "/0/groupes/" + ((GroupeRestApi) groupe).getId());
        } catch (MalformedURLException e) {
            Log.e("SOurceDonneAPI : ", e.toString());
            throw new SourceDonneeException("Connection non disponible");
        }
        List<Utilisateur> utilisateursMembres = new ArrayList<Utilisateur>();
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            reglerTimeout(httpURLConnection);
            reglerHeader(httpURLConnection);
            Gson gson = new Gson();
            if (httpURLConnection.getResponseCode() == 200) {
                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8);
                GroupeRestApi groupeRestApi = gson.fromJson(inputStreamReader, GroupeRestApi.class);
                for (UtilisateurRestAPI utilisateur : groupeRestApi.getUtilisateursRestApi()) {
                    UtilisateurRestAPI utilisateurRestAPI = new UtilisateurRestAPI(utilisateur.getNom(), "", "", ((UtilisateurRestAPI) utilisateur).getMonnaieUsuelle(), ((UtilisateurRestAPI) utilisateur).getId());
                    utilisateursMembres.add(utilisateurRestAPI);

                }
            }

        } catch (java.net.SocketTimeoutException e) {
            throw new SourceDonneeException("Connection non disponible");
        } catch (IOException e) {
            e.printStackTrace();
        }


        return utilisateursMembres;
    }

    @Override
    public boolean ajouterMembre(Groupe groupe, Utilisateur utilisateur) throws SourceDonneeException {

        URL url = null;
        try {

            url = new URL(URI_BASE + POINT_ENTREE_GROUPE + ((GroupeRestApi) groupe).getId() + "?courriel=" + utilisateur.getCourriel());
        } catch (MalformedURLException e) {
            Log.e("SOurceDonneAPI : ", e.toString());
            throw new SourceDonneeException("Connection non disponible");
        }

        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            reglerHeader(httpURLConnection);
            reglerTimeout(httpURLConnection);

            if (httpURLConnection.getResponseCode() == 200) {
                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String sortie;
                while ((sortie = bufferedReader.readLine()) != null) {
                    stringBuilder.append(sortie);
                }

                return "true".equals(stringBuilder.toString());
            }
        } catch (java.net.SocketTimeoutException e) {
            throw new SourceDonneeException("Connection non disponible");
        } catch (IOException e) {
            e.printStackTrace();
        }


        return false;
    }


//endregion

//region Utilisateurs

    public boolean utilisateurExiste(String email) throws SourceDonneeException {
        URL url = null;

        try {
            url = new URL(URI_BASE + "register" + "?courriel=" + email);
        } catch (MalformedURLException e) {
            Log.e("SOurceDonneAPI: ", e.toString());
        }

        try {

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            reglerTimeout(httpURLConnection);
            reglerHeader(httpURLConnection);
            httpURLConnection.setRequestMethod("HEAD");
            httpURLConnection.addRequestProperty("Accept-Encoding", "identity");

            if (httpURLConnection.getResponseCode() == 200) {
                return false;
            } else if (httpURLConnection.getResponseCode() == 409) {
                return true;
            }

        } catch (java.net.SocketTimeoutException e) {
            throw new SourceDonneeException("Connection non disponible");
        } catch (IOException e) {
            e.printStackTrace();
        }


        return false;
    }

    @Override
    public Utilisateur creerUtilisateur(Utilisateur utilisateur) throws SourceDonneeException {
        //todo appeler la methode dans le presenteur, pour verifier l'existance de l'utilisateur
        boolean existeDeja = utilisateurExiste(utilisateur.getCourriel());
        if (existeDeja) return null;
        Utilisateur utilisateurRetour = null;
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n    'Nom': '" + utilisateur.getNom() + "',\r\n    'Courriel': '" + utilisateur.getCourriel() + "',\r\n    'MotDePasse': '" + utilisateur.getMotPasse() + "'\r\n, 'Monnaie': '" + utilisateur.getMonnaieUsuelle().name() + "'\r\n}");
        Request request = new Request.Builder()
                .url(URI_BASE + "register")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("api-key", apiKey)
                .build();
        try {
            Response response = client.newCall(request).execute();

            if (response.code() == 201) {
                //peut seulement etre consommer une seule fois, regarder documentation okhttp
                utilisateurRetour = decoderUtilisateur(Objects.requireNonNull(response.body()).byteStream());
            } else if (response.code() == 409) {
                return null;
            }
        }//si la connection a l'api est impossible, on retourne un user null
        catch (ConnectException e) {

            Log.e("erreur connection api", "message:" + Objects.requireNonNull(e.getMessage()) + " \n cause: " + e.getCause());
            throw new SourceDonneeException("Connexion non disponnible");
        } //si l'email entrer est deja pris, on retourne un user invalide
        catch (IOException e) {
            Log.e("IOException creerUtilisateur", "Cause: " + e.getMessage() + "\n Message: " + e.getMessage());
        }
        return utilisateurRetour;
    }

    @Override
    public Utilisateur tenterConnexion(String email, String mdp) throws SourceDonneeException {
        URL url = null;
        UtilisateurRestAPI utilisateur = null;
        try {
            url = new URL(URI_BASE + POINT_ENTREE_LOGIN);
        } catch (MalformedURLException e) {
            Log.e("SOurceDonneAPI : ", e.toString());
            throw new SourceDonneeException("Connection non disponible");
        }
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            reglerTimeout(httpURLConnection);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json ; utf-8 ");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);


            OutputStream outputStream = httpURLConnection.getOutputStream();
            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(new UtilisateurRestAPI("", email, mdp, null, 0));
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            outputStream.write(input, 0, input.length);

            if (httpURLConnection.getResponseCode() == 200) {
                InputStream inputStream = httpURLConnection.getInputStream();
                utilisateur = decoderUtilisateur(inputStream);
                if (utilisateur == null || utilisateur.getId() == 0)
                    return null;
            }


        } catch (java.net.SocketTimeoutException e) {
            throw new SourceDonneeException("Connection non disponible");
        } catch (IOException e) {

            Log.e("SOurceDonneAPIRest: ", e.toString());
            throw new SourceDonneeException("Connection non disponible");
        }


        return utilisateur;
    }

//endregion

    //region fonctions utiles
    private UtilisateurRestAPI decoderUtilisateur(InputStream utilisateurEncode) throws SourceDonneeException {

        try {
            InputStreamReader responseBodyReader =
                    new InputStreamReader(utilisateurEncode, "UTF-8");
            String apiKey = "";

            JsonReader jsonReader = new JsonReader(responseBodyReader);
            jsonReader.beginObject();

            String email = "";
            String nom = "";
            int id = -1;
            String monnaieAPI = "";
            while (jsonReader.hasNext()) {
                String key = jsonReader.nextName();

                if (key.equals("Courriel")) {
                    email = jsonReader.nextString();
                } else if (key.equals("Nom")) {
                    nom = jsonReader.nextString();
                } else if (key.equals("Id")) {
                    id = jsonReader.nextInt();
                } else if (key.equals("Monnaie")) {
                    monnaieAPI = jsonReader.nextString();
                } else if (key.equals("ApiKey")) {
                    apiKey = jsonReader.nextString();
                } else {
                    jsonReader.skipValue();
                }
            }

            if ("".equals(apiKey)) {
                throw new SourceDonneeException("La clé d'api est introuvable");
            } else {
                SourceDonneesAPIRest.apiKey = apiKey;
                return new UtilisateurRestAPI(nom, email, "", Monnaie.valueOf(monnaieAPI), id);
            }

        } catch (IOException e) {
            Log.e("SourceAPI", "erreur de deserialisation JSON utilisateur ");
        }

        return null;
    }

    private static void reglerTimeout(HttpURLConnection httpURLConnection) {
        httpURLConnection.setReadTimeout(READ_TIME_OUT);
        httpURLConnection.setConnectTimeout(CONNECT_TIME_OUT);
    }

    private static void reglerHeader(HttpURLConnection httpURLConnection) {

        httpURLConnection.setRequestProperty("api-key", apiKey);
    }

    //endregion
    @Override
    public Utilisateur modifierUtilisateur(Utilisateur utilisateurModifier, Utilisateur utilisateurCourrant) throws SourceDonneeException, UtilisateurException {
        Utilisateur utilisateurRetour=null;
        URL url = null;
        try {
            url=new URL(URI_BASE + POINT_ENTREE_UTILISATEUR + "update/"+((UtilisateurRestAPI) utilisateurCourrant).getId());
        } catch (MalformedURLException e) {
            Log.e("SOurceDonneAPI url malformed: ", e.toString());
        }
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        String requestUserJson="{\r\n    'Nom': '"+utilisateurModifier.getNom()+"',\r\n    'Courriel': '"+utilisateurModifier.getCourriel()+"',\r\n    " +
                "'MotDePasse': '"+utilisateurModifier.getMotPasse()+"'\r\n, 'Monnaie': '"+utilisateurModifier.getMonnaieUsuelle().name()+"'\r\n";
        if(utilisateurModifier.getNouveauMotDePasse()!=null&&utilisateurModifier.getNouveauMotDePasse().length()>=8){
            requestUserJson+=", 'MotDePasseMod': '"+utilisateurModifier.getNouveauMotDePasse()+"'\r\n";
        }
        RequestBody body = RequestBody.create(mediaType, requestUserJson+"}");
        assert url != null;
        Request request = new Request.Builder()
                .url(url)
                .method("PUT", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("api-key", "")
                .build();

        try {
            Gson gson = new GsonBuilder().create();
            String json =  gson.toJson(new UtilisateurRestAPI("","","", null , 0));
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                InputStreamReader inputStreamReader= new InputStreamReader(Objects.requireNonNull(response.body()).byteStream(), StandardCharsets.UTF_8);
                utilisateurRetour = gson.fromJson(inputStreamReader, UtilisateurRestAPI.class);
            } else if (response.code() == 409) {
                //le 409 est retourner si l'email est deja pris
                throw new UtilisateurException("Courriel déjà pris, choisissez en un autre SVP.");
            } else if(response.code()==403){
                //le 403 est retourner si la combinaison id/mot de passe actuel est mauvaise.
                throw new UtilisateurException("Le mot de passe actuel entré n'est pas le bon.");
            }
        } catch(ConnectException e) {
            Log.e("erreur connection api", "message:" + Objects.requireNonNull(e.getMessage()) + " \n cause: " + e.getCause());
            throw new SourceDonneeException("Connexion non disponnible.");
        } //si l'email entrer est deja pris, on retourne un user invalide
        catch (IOException e) {
            Log.e("IOException creerUtilisateur","Cause: "+e.getMessage()+"\n Message: "+e.getMessage());
        }
        return utilisateurRetour;
    }
}