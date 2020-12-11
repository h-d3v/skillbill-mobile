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
import java.util.Map;

/**
 * Presenteur qui assure la presentation de la modification d'un groupe, de l'ajout et de la modification d'un groupe
 */

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
    private Map<Utilisateur, Double> montantsUstilisateurAAjouter;

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

    /**
     * Fonction qui permet de chercher la liste des utilisateurs dans la source de données dans un fil esclave
     */

    public void chargerListeUtilisateurs() {
        filEsclave = new Thread(new Runnable() {
            Message message;

            @Override
            public void run() {
                try {
                    List<Utilisateur> utilisateursGroupe= iGestionGroupes.trouverTousLesUtilisateurs(modele.getGroupeEnCours());
                    message = handlerReponse.obtainMessage(MSG_TROUVER_MEMBRES);
                    modele.getGroupeEnCours().setUtilisateurs( utilisateursGroupe );


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
        String[] membres = new String[modele.getGroupeEnCours().getUtilisateurs().size()];
        int i = 0;
        for(Utilisateur utilisateur : modele.getGroupeEnCours().getUtilisateurs()){
            membres[i] = utilisateur.getNom();
            i++;
        }
        return membres;
    }

    /**
     * ajoute une facture a un groupe à partir des données entrées dans la vue
     * Persite dans la source de données du présenteur
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

                ajouterPayeursAFacture(factureAAjouter);


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
                if(factureAjoutee)
                    msg = handlerReponse.obtainMessage(MSG_AJOUT_FACTURE_REUSSI);
                else
                    msg = handlerReponse.obtainMessage(MSG_ERREUR_AJOUT_FACTURE);

            } catch (NumberFormatException | DateTimeParseException e) {
                msg = handlerReponse.obtainMessage(MSG_ERREUR);

            } catch (SourceDonneeException e) {
                msg = handlerReponse.obtainMessage(MSG_ERREUR_CNX);
            }
            handlerReponse.sendMessage(msg);

        });
        filEsclave.start();
    }

    private void ajouterPayeursAFacture(Facture facture){
        if(vueAjouterFacture.getMultipleUtilisateursPayeurs()==null){
            HashMap<Utilisateur, Double> hashMap = new HashMap<>();
            hashMap.put(modele.getUtilisateurConnecte(), vueAjouterFacture.getMontantFactureCADInput());
            for(Utilisateur utilisateur : modele.getGroupeEnCours().getUtilisateurs()){
                hashMap.putIfAbsent(utilisateur, 0.0);
            }
            facture.setMontantPayeParParUtilisateur(hashMap);
            }

            else {
                facture.setMontantPayeParParUtilisateur(montantsUstilisateurAAjouter);
            }
    }

    /**
     *
     * @return la valeur du Montant dans la vue
     */
    @Override
    public String trouverMontantFactureEnCours() {
        return String.valueOf(modele.getFactureEnCours().getMontantTotal());
    }

    /**
     *
     * @return la valeur du titre dans la vue
     */
    @Override
    public String trouverTitreFactureEnCours() {
        return modele.getFactureEnCours().getLibelle();
    }

    /**
     *
     * @return la date de la facture entrée dans la vue
     */

    @Override
    public String trouverDateFactureEnCours() {//TODO verif pourquoi la date disparait du modele quand on appuie sur annuler dans l'activite voir modif facture
        if(modele.getFactureEnCours().getDateFacture()!=null)
            return modele.getFactureEnCours().getDateFacture().toString();
        else
            rechargerFactureEnCours();
        return null;
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
        try {
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

    /**
     * Envoie les données d'une facture entrées dans la vue à la source de données du présenteur
     */
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

                    if (photoChangee) {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        vueAjouterFacture.getBitmapFacture().compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] bytes = byteArrayOutputStream.toByteArray();
                        factureAModifier.getPhotos().add(bytes);

                    }
                    ajouterPayeursAFacture(factureAModifier);


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

    /**
     *
     * @return la liste des payeurs d'une facture à ajouter ou à modifier
     */
    @Override
    public String presenterPayeurs() {
        String payeurs = "";

        if(modele.getFactureEnCours()!=null && modele.getFactureEnCours().getMontantPayeParParUtilisateur()!=null){//Cas d'une facture deja existante
        Map<Utilisateur, Double> montantsUstilisateur = modele.getFactureEnCours().getMontantPayeParParUtilisateur();
            for( Utilisateur utilisateur : modele.getGroupeEnCours().getUtilisateurs() ){
                if(montantsUstilisateur.get(utilisateur)!=null &&  montantsUstilisateur.get(utilisateur)>0){
                    if(vueAjouterFacture.getMultipleUtilisateursPayeurs()==null){
                        payeurs+= utilisateur.getNom() + activityAjouterFacture.getResources().getString(R.string.a_paye) +
                                montantsUstilisateur.get(utilisateur) + System.getProperty("line.separator");
                    }
                }
            }
        }else { //Cas ajout d'une facure
            montantsUstilisateurAAjouter = new HashMap<Utilisateur, Double>();
            try{vueAjouterFacture.getMontantFactureCADInput();}catch (NumberFormatException e) {return "";}
            int i = 0;
            int nbrPayeurs = 0 ;
            if (vueAjouterFacture.getMultipleUtilisateursPayeurs() != null) { // Cas ou il y a plusieurs payeurs
                while (i< vueAjouterFacture.getMultipleUtilisateursPayeurs().length ){
                    if (vueAjouterFacture.getMultipleUtilisateursPayeurs()[i] ) {
                        nbrPayeurs++;
                        montantsUstilisateurAAjouter.putIfAbsent(modele.getGroupeEnCours().getUtilisateurs().get(i),
                                                                 0.0);
                    }
                    i++;
                }
                for(Utilisateur utilisateur : montantsUstilisateurAAjouter.keySet()){
                    montantsUstilisateurAAjouter.put(utilisateur, vueAjouterFacture.getMontantFactureCADInput()/nbrPayeurs);
                    payeurs+= utilisateur.getNom() + activityAjouterFacture.getResources().getString(R.string.a_paye) +
                            montantsUstilisateurAAjouter.get(utilisateur) +System.getProperty("line.separator");
                }
                for(Utilisateur utilisateur : modele.getGroupeEnCours().getUtilisateurs()){
                    montantsUstilisateurAAjouter.putIfAbsent(utilisateur, 0.0);
                }

            }else

            payeurs+= modele.getUtilisateurConnecte().getNom() + activityAjouterFacture.getResources().getString(R.string.a_paye) +
                    vueAjouterFacture.getMontantFactureCADInput() +System.getProperty("line.separator");
        }


        return payeurs;
    }

    /**
     * Recharge la facture en cours enregistrée dans le modele avec celle qui est dans la source de données
     */

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


    public void setPhotoChangee(boolean photoChangee) {
        this.photoChangee = photoChangee;
    }
}
