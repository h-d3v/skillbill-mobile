package com.jde.skillbill.donnees.APIRest;

import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jde.skillbill.domaine.entites.Facture;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.ISourceDonnee;
import com.jde.skillbill.donnees.APIRest.entites.GroupeRestApi;
import com.jde.skillbill.donnees.APIRest.entites.UtilisateurRestAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SourceDonneesAPIRest implements ISourceDonnee {
    private String URI_BASE = "http://192.168.0.23:44302/api/";
    private String POINT_ENTREE_UTILISATEUR ="utilisateurs/";
    private String POINT_ENTREE_GROUPE = "groupes/";
    private String POINT_ENTREE_LOGIN ="login";


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
        Utilisateur utilisateur=null;
        try {
             url = new URL(URI_BASE+POINT_ENTREE_GROUPE);
        } catch (MalformedURLException e) {
            Log.e("SOurceDonneAPI: ", e.toString());
        }
        try {
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Accept", "application/json; utf-8");
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            String json = "{\"courriel\": \""+email+"}";
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            outputStream.write(input,0, input.length);
            Log.e("code reponse", String.valueOf(httpURLConnection.getResponseCode()));
            if(httpURLConnection.getResponseCode()==200){
                httpURLConnection.getInputStream();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public Utilisateur creerUtilisateur(Utilisateur utilisateur) {
        return null;
    }

    @Override
    public Utilisateur tenterConnexion(String email, String mdp) {
        URL url = null;
        UtilisateurRestAPI utilisateur=null;
        try {
            url = new URL(URI_BASE+POINT_ENTREE_LOGIN);
        } catch (MalformedURLException e) {
            Log.e("SOurceDonneAPI : ", e.toString());
        }
        try {
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json ; utf-8 ");
            httpURLConnection.setRequestProperty("User-agent", "Skillbill android");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            Gson gson = new GsonBuilder().create();
            String json =  gson.toJson(new UtilisateurRestAPI("",email,mdp, null , 0));
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            outputStream.write(input, 0,input.length);

            if(httpURLConnection.getResponseCode()==200){
              InputStreamReader inputStreamReader = new InputStreamReader( httpURLConnection.getInputStream(), StandardCharsets.UTF_8);
              utilisateur = gson.fromJson(inputStreamReader, UtilisateurRestAPI.class);

              if(utilisateur==null || utilisateur.getId()==0) return null;


            }

        } catch (IOException e) {
            Log.e("SOurceDonneAPIRest: ", e.toString());
        }

        return utilisateur;
    }

    @Override
    public boolean creerGroupeParUtilisateur(Utilisateur utilisateur, Groupe groupe) {
        URL url = null;



        try {

            url = new URL(URI_BASE+POINT_ENTREE_UTILISATEUR+((UtilisateurRestAPI) utilisateur).getId()+"/groupes");
        } catch (MalformedURLException e) {
            Log.e("SOurceDonneAPI : ", e.toString());
        }
        try {
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json ; utf-8 ");
            httpURLConnection.setRequestProperty("User-agent", "Skillbill android");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(new GroupeRestApi(groupe.getNomGroupe(), null, groupe.getMonnaieDuGroupe(), 0));
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            outputStream.write(input, 0,input.length);

            if(httpURLConnection.getResponseCode()==200){

                return httpURLConnection.getResponseMessage().equals("true");
            }

        }
        catch (IOException e) {
                e.printStackTrace();
            }


        return false;
    }

    @Override
    public List<Groupe> lireTousLesGroupesAbonnes(Utilisateur utilisateur) {
        URL url = null;
        try {

            url = new URL(URI_BASE+POINT_ENTREE_UTILISATEUR+((UtilisateurRestAPI) utilisateur).getId()+"/groupes");
        } catch (MalformedURLException e) {
            Log.e("SOurceDonneAPI : ", e.toString());
        }
        try {
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            Gson gson = new Gson();
            if(httpURLConnection.getResponseCode()==200){
                InputStreamReader inputStreamReader = new InputStreamReader( httpURLConnection.getInputStream(), StandardCharsets.UTF_8);
                GroupeRestApi[] groupeRestApis = gson.fromJson(inputStreamReader, GroupeRestApi[].class);
                List<Groupe> groupeRestApis1 = new ArrayList<>();
                if(groupeRestApis!=null){
                    groupeRestApis1.addAll(Arrays.asList(groupeRestApis));
                    return groupeRestApis1;
                }

            }



        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
