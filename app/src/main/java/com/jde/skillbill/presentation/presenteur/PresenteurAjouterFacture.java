package com.jde.skillbill.presentation.presenteur;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.jde.skillbill.R;
import com.jde.skillbill.domaine.entites.Facture;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionFacture;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionGroupes;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionUtilisateur;
import com.jde.skillbill.domaine.interacteurs.interfaces.SourceDonneeException;
import com.jde.skillbill.presentation.IContratVPAjouterFacture;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueAjouterFacture;

import com.jde.skillbill.ui.activity.ActivityVoirUnGroupe;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;

public class PresenteurAjouterFacture implements IContratVPAjouterFacture.IPresenteurAjouterFacture {
    Activity activityAjouterFacture;
    VueAjouterFacture vueAjouterFacture;
    Modele modele;
    private static final String EXTRA_ID_UTILISATEUR = "com.jde.skillbill.utlisateur_identifiant";
    private static final String EXTRA_GROUPE_POSITION = "com.jde.skillbill.groupe_identifiant";
    private static final String IMAGE = "com.jde.skillbill.BitmapImage";
    private static final String EXTRA_FACTURE = "com.jde.skillbill.facture_identifiant";
    private final IGestionFacture iGestionFacture;
    private final IGestionUtilisateur iGestionUtilisateur;
    private final IGestionGroupes iGestionGroupes;
    private static final int REQUETE_PRENDRE_PHOTO = 2;
    private String[] nomsMembres;
    private Thread filEsclave = null;
    private final Handler handlerReponse;
    private static final int MSG_AJOUT_FACTURE_REUSSI = 0;
    private static final int MSG_ERREUR = 1;
    private static final int MSG_ERREUR_CNX = 4;
    private static final int  MSG_ERREUR_AJOUT_FACTURE =2424;
    private static final int MSG_TROUVER_MEMBRES = 333;
    private static final int MSG_MODIF_FACTURE_FAIT=75;
    private static final int MSG_RECHARGER_FACTURE = 4444;
    private boolean photoChangee;

    @SuppressLint("HandlerLeak")
    public PresenteurAjouterFacture(Activity activityAjouterFacture, boolean estFactureExistante,  VueAjouterFacture vueAjouterFacture, Modele modele, IGestionFacture gestionFacture, IGestionUtilisateur gestionUtilisateur, IGestionGroupes gestionGroupes) {
        this.activityAjouterFacture = activityAjouterFacture;
        this.vueAjouterFacture = vueAjouterFacture;
        this.modele = modele;
        modele.setUtilisateurConnecte((Utilisateur) activityAjouterFacture.getIntent().getSerializableExtra(EXTRA_ID_UTILISATEUR));
        this.iGestionFacture = gestionFacture;
        this.iGestionUtilisateur = gestionUtilisateur;
        this.iGestionGroupes = gestionGroupes;
        modele.setGroupeEnCours((Groupe) activityAjouterFacture.getIntent().getSerializableExtra(EXTRA_GROUPE_POSITION));
        modele.setFactureEnCours((Facture) activityAjouterFacture.getIntent().getSerializableExtra(EXTRA_FACTURE));



        handlerReponse = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                filEsclave = null;
                vueAjouterFacture.fermerProgressBar();
                if (msg.what == MSG_AJOUT_FACTURE_REUSSI) {

                    redirigerVersListeFactures();

                } else if (msg.what == MSG_ERREUR) {
                    vueAjouterFacture.afficherMessageErreurAlertDialog(
                            activityAjouterFacture.getResources().getString(R.string.txt_message_erreur)
                            , activityAjouterFacture.getResources().getString(R.string.titre_erreur_generique)
                    );
                }
                else if (msg.what == MSG_TROUVER_MEMBRES) {
                    nomsMembres = (String[]) msg.obj;
                }
                else if (msg.what == MSG_ERREUR_CNX) {
                    Toast.makeText(activityAjouterFacture, R.string.pas_de_connection_internet, Toast.LENGTH_LONG).show();
                }
                else if(msg.what==MSG_MODIF_FACTURE_FAIT){
                    Toast.makeText(activityAjouterFacture, R.string.modif_effectuee, Toast.LENGTH_LONG).show();
                    redirigerVersListeFactures();
                }
                else if(msg.what==MSG_ERREUR_AJOUT_FACTURE){
                    Toast.makeText(activityAjouterFacture, R.string.erreur_modification, Toast.LENGTH_LONG).show();
                    redirigerVersListeFactures();
                }
                else if(msg.what == MSG_RECHARGER_FACTURE){
                    if(msg.obj!=null){
                        modele.setFactureEnCours((Facture) msg.obj);
                        //ne prend en charge qu'une seule photo car l'ui ne prend en charge qu'une photo pour le moment
                        if(!(modele.getFactureEnCours().getPhotos()==null || modele.getFactureEnCours().getPhotos().size()==0)){
                            byte[] photoByte = modele.getFactureEnCours().getPhotos().get(0);
                            vueAjouterFacture.setImageFacture(BitmapFactory.decodeByteArray(photoByte, 0, photoByte.length ));
                        }
                    }
                }
            }
        };
        chargerListeUtilisateurs();
        if(estFactureExistante){
            rechargerFactureEnCours();
        }

    }

    public void chargerListeUtilisateurs() {
        filEsclave = new Thread(new Runnable() {
            Message message;

            @Override
            public void run() {
                try {
                    List<Utilisateur> utilisateursGroupe= iGestionGroupes.trouverTousLesUtilisateurs(modele.getGroupeEnCours());
                    String[] membres=new String[utilisateursGroupe.size()];
                    int i=0;
                    for (Utilisateur utilisateur : utilisateursGroupe){
                        membres[i]=utilisateur.getNom();
                        i++;
                    }
                    message = handlerReponse.obtainMessage(MSG_TROUVER_MEMBRES);
                    message.obj = membres;

                }catch (SourceDonneeException e){
                    message = handlerReponse.obtainMessage(MSG_ERREUR_CNX);
                }
                handlerReponse.sendMessage(message);
            }

        });
        filEsclave.start();

    }

    /**
     *
     * @return liste des noms des utilisateurs du groupe
     */
    @Override
    public String[] presenterListeUtilsateur() {

        return nomsMembres;
    }

    /**
     * ajoute une facture a un groupe
     */
    @Override
    public void ajouterFacture() {
        vueAjouterFacture.ouvrirProgressBar();
        filEsclave= new Thread (()-> {
            Message msg = null;

            try {
                double montant = vueAjouterFacture.getMontantFactureCADInput();
                LocalDate date = vueAjouterFacture.getDateFactureInput();
                String titre = vueAjouterFacture.getTitreInput();
                if (titre == null) {
                    titre = activityAjouterFacture.getResources().getString(R.string.txt_facture_par_defaut) + " " + date.toString();
                }
                Facture factureAAjouter = new Facture();
                factureAAjouter.setMontantTotal(vueAjouterFacture.getMontantFactureCADInput());
                factureAAjouter.setDateFacture(vueAjouterFacture.getDateFactureInput());
                factureAAjouter.setLibelle(vueAjouterFacture.getTitreInput());
                factureAAjouter.setGroupe(modele.getGroupeEnCours());
                factureAAjouter.setUtilisateurCreateur(modele.getUtilisateurConnecte());
                HashMap<Utilisateur, Double> hashMap = new HashMap<>();//TODO provisoire ajoute seulement l'utilisateur connecte a la facture
                hashMap.put(modele.getUtilisateurConnecte(), vueAjouterFacture.getMontantFactureCADInput());
                factureAAjouter.setMontantPayeParParUtilisateur(hashMap);

                if (factureAAjouter.getLibelle() == null) {
                    factureAAjouter.setLibelle(activityAjouterFacture.getResources().getString(R.string.txt_facture_par_defaut) + " " + factureAAjouter.getDateFacture().toString());
                }

                if (vueAjouterFacture.getBitmapFacture() != null) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    vueAjouterFacture.getBitmapFacture().compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    factureAAjouter.getPhotos().add(bytes);
                }

                boolean factureAjoutee = iGestionFacture.creerFacture(factureAAjouter);
                msg = handlerReponse.obtainMessage(MSG_AJOUT_FACTURE_REUSSI, factureAjoutee);

            } catch (NumberFormatException | DateTimeParseException e) {
                msg = handlerReponse.obtainMessage(MSG_ERREUR);

            } catch (SourceDonneeException e) {
                msg = handlerReponse.obtainMessage(MSG_ERREUR_CNX);
            }
            handlerReponse.sendMessage(msg);

        });
        filEsclave.start();
    }

    @Override
    public String trouverMontantFactureEnCours() {

        return String.valueOf(modele.getFactureEnCours().getMontantTotal());
    }

    @Override
    public String trouverTitreFactureEnCours() {
        return modele.getFactureEnCours().getLibelle();
    }

    @Override
    public String trouverDateFactureEnCours() {
        return modele.getFactureEnCours().getDateFacture().toString();
    }


    /**
     * Redirige vers l'activite voirUnGroupe pour montrer la nouvelle facture ajoutee
     */
    @Override
    public void redirigerVersListeFactures() {
        Intent intent = new Intent(activityAjouterFacture, ActivityVoirUnGroupe.class);
        intent.putExtra(EXTRA_GROUPE_POSITION, modele.getGroupeEnCours());
        intent.putExtra(EXTRA_ID_UTILISATEUR, modele.getUtilisateurConnecte());

        activityAjouterFacture.startActivity(intent);

        //ne pourra pas retourner a l'activite de creation de facture
        activityAjouterFacture.finish();
    }

    @Override
    public void prendrePhoto() {
        Intent prendrePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try { //TODO verifier que l'appareil est capable de prendre des photos
            activityAjouterFacture.startActivityForResult(prendrePhotoIntent, REQUETE_PRENDRE_PHOTO);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activityAjouterFacture, R.string.pas_d_activite_photo_diponible, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public Monnaie getMonnaieUserConnecte() {
        SharedPreferences sharedPref = activityAjouterFacture.getSharedPreferences("SKILLBILL_USER_PREF", Context.MODE_PRIVATE);
        String strMonnaieUser = sharedPref.getString("monnaieUser", "CAD");
        return Monnaie.valueOf(strMonnaieUser);
    }


    @Override
    public void envoyerRequeteModificationFacture() {
        filEsclave = new Thread(new Runnable() {
            @Override
            public void run() {
                Message message;
                try{
                    Facture factureAModifier = modele.getFactureEnCours();
                    factureAModifier.setLibelle(vueAjouterFacture.getTitreInput());
                    factureAModifier.setDateFacture(vueAjouterFacture.getDateFactureInput());
                    factureAModifier.setMontantTotal(vueAjouterFacture.getMontantFactureCADInput());
                    //TODO solution provisoire seul l'utilisateur connecté est pris en compte
                    HashMap<Utilisateur, Double> montantsPayesParUtilisateur = new HashMap<>();
                    montantsPayesParUtilisateur.put(modele.getUtilisateurConnecte(),vueAjouterFacture.getMontantFactureCADInput());
                    if (photoChangee) {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        vueAjouterFacture.getBitmapFacture().compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] bytes = byteArrayOutputStream.toByteArray();
                        factureAModifier.getPhotos().add(bytes);

                    }
                    factureAModifier.setMontantPayeParParUtilisateur(montantsPayesParUtilisateur);



                    boolean estReussi = iGestionFacture.modifierFacture(factureAModifier);

                    if(estReussi) message = handlerReponse.obtainMessage(MSG_MODIF_FACTURE_FAIT);
                    else message = handlerReponse.obtainMessage(MSG_ERREUR_AJOUT_FACTURE);
                }
                catch (NumberFormatException | DateTimeParseException e) {
                    message = handlerReponse.obtainMessage(MSG_ERREUR);
                }
                catch (SourceDonneeException e){
                    message = handlerReponse.obtainMessage(MSG_ERREUR_CNX);
                }
                handlerReponse.sendMessage(message);
            }
        });
        filEsclave.start();
    }
    public void rechargerFactureEnCours(){
        filEsclave = new Thread(new Runnable() {
            @Override
            public void run() {
                Message message;
                try {
                    message = handlerReponse.obtainMessage(MSG_RECHARGER_FACTURE);
                    Facture factureRechargee = iGestionFacture.rechargerFacture(modele.getFactureEnCours());
                    message.obj = factureRechargee;
                } catch (SourceDonneeException e) {
                    message = handlerReponse.obtainMessage(MSG_ERREUR_CNX);
                }
                handlerReponse.sendMessage(message);
            }
        });
        filEsclave.start();
    }

    public boolean isPhotoChangee() {
        return photoChangee;
    }

    public void setPhotoChangee(boolean photoChangee) {
        this.photoChangee = photoChangee;
    }
}
