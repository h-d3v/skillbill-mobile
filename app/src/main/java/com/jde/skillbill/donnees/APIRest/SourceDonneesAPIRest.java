package com.jde.skillbill.donnees.APIRest;

import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jde.skillbill.domaine.entites.Facture;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.ISourceDonnee;
import com.jde.skillbill.donnees.APIRest.entites.UtilisateurRestAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class SourceDonneesAPIRest implements ISourceDonnee {
    private String URI_BASE = "http://192.168.0.16:8080/restAPI-1.0-SNAPSHOT/api/v0/";
    private String POINT_ENTREE_UTILISATEUR = "utilisateurs/";
    private String POINT_ENTREE_GROUPE = "groupes/";
    private String POINT_ENTREE_INSCRIPTION = "register/";


    @Override
    public List<Facture> lireFacturesParGroupe(Groupe groupe) {
        return null;
    }

    @Override
    public boolean ajouterFacture(double montantTotal, Utilisateur utilisateurPayeur, LocalDate localDate, Groupe groupe, String titre) {
        return false;
    }

    @Override
    public Utilisateur lireUtilisateur(String email) {
        URL url = null;
        Utilisateur utilisateur = null;
        try {
            url = new URL(URI_BASE + POINT_ENTREE_GROUPE);
        } catch (MalformedURLException e) {
            Log.e("SOurceDonneAPI: ", e.toString());
        }
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            String json = "{\"courriel\": \"" + email + "}";
            byte[] input = json.getBytes("utf-8");
            outputStream.write(input, 0, input.length);
            if (httpURLConnection.getResponseCode() == 200) {
                httpURLConnection.getInputStream();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public Utilisateur creerUtilisateur(Utilisateur utilisateur)  {
        Utilisateur utilisateurRetour = null;
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n    'Nom': '"+utilisateur.getNom()+"',\r\n    'Courriel': '"+utilisateur.getCourriel()+"',\r\n    'MotDePasse': '"+utilisateur.getMotPasse()+"'\r\n}");
        Request request = new Request.Builder()
                .url("http://192.168.1.32:51360/api/Register")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();

            if(response.code()==200) {
                //peut seulement etre consommer une seule fois, regarder documentation okhttp
                utilisateurRetour = decoderUtilisateur(Objects.requireNonNull(response.body()).byteStream());
            }
        }//si la connection a l'api est impossible, on retourne un user null
        catch(ConnectException e) {
            Log.e("erreur connection api", "message:" + Objects.requireNonNull(e.getMessage()) + " \n cause: " + e.getCause());

        } //si l'email entrer est deja pris, on retourne un user invalide
        catch (IOException e) {
            utilisateurRetour=new Utilisateur("-1", "-1", "-1", Monnaie.CAD);
            e.printStackTrace();
        }
        return utilisateurRetour;
    }

    @Override
    public Utilisateur tenterConnexion(String email, String mdp) {
        URL url = null;
        UtilisateurRestAPI utilisateur = null;
        try {
            url = new URL(URI_BASE + POINT_ENTREE_UTILISATEUR + "login");
        } catch (MalformedURLException e) {
            Log.e("SOurceDonneAPI: ", e.toString());
        }
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            String json = "{\"courriel\": \"" + email + "\"," +
                    "\"mot_de_passe\": \"" + mdp + "\" }";
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            outputStream.write(input, 0, input.length);
            if (httpURLConnection.getResponseCode() == 200) {
                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8);
                Gson gson = new GsonBuilder().create();
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuffer stringBuffer = new StringBuffer();
                String str;
                while ((str = bufferedReader.readLine()) != null) {
                    stringBuffer.append(str);
                }
                utilisateur = gson.fromJson(stringBuffer.toString(), UtilisateurRestAPI.class);
                if (utilisateur == null || utilisateur.getId() == 0) return null;


            }

        } catch (IOException e) {
            Log.e("SOurceDonneAPIRest: ", e.toString());
        }

        return utilisateur;
    }

    @Override
    public boolean creerGroupeParUtilisateur(Utilisateur utilisateur, Groupe groupe) {
        return false;
    }

    @Override
    public List<Groupe> lireTousLesGroupesAbonnes(Utilisateur utilisateur) {
        return null;
    }

    @Override
    public List<Utilisateur> lireUTilisateurParGroupe(Groupe groupe) {
        return null;
    }

    @Override
    public boolean ajouterMembre(Groupe groupe, Utilisateur utilisateur) {
        return false;
    }

    private Utilisateur decoderUtilisateur( InputStream utilisateurEncode ) throws IOException {
        InputStreamReader responseBodyReader =
                new InputStreamReader(utilisateurEncode, "UTF-8");

        JsonReader jsonReader = new JsonReader(responseBodyReader);
        jsonReader.beginObject();

        String email="";
        String nom="";
        int id=-1;
        while (jsonReader.hasNext()) {
            String key = jsonReader.nextName();

            if (key.equals("Courriel")) {
                email = jsonReader.nextString();
            }
            else if (key.equals("Nom")) {
                nom= jsonReader.nextString();
            }
            else if (key.equals("Id")) {
                id= jsonReader.nextInt();
            }
            else {
                jsonReader.skipValue();
            }
        }

        return new UtilisateurRestAPI( nom, email, "", Monnaie.CAD, id);
    }
}
