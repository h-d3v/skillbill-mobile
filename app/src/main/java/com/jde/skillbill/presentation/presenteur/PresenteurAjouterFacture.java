package com.jde.skillbill.presentation.presenteur;

import android.content.ActivityNotFoundException;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.os.Handler;
import android.os.Message;

import com.jde.skillbill.R;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionFacture;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionGroupes;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionUtilisateur;
import com.jde.skillbill.presentation.IContratVPAjouterFacture;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueAjouterFacture;
import com.jde.skillbill.ui.activity.ActivityAjouterFacture;
import com.jde.skillbill.ui.activity.ActivityVoirUnGroupe;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class PresenteurAjouterFacture implements IContratVPAjouterFacture.IPresenteurAjouterFacture {
    ActivityAjouterFacture activityAjouterFacture;
    VueAjouterFacture vueAjouterFacture;
    Modele modele;
    private String EXTRA_ID_UTILISATEUR="com.jde.skillbill.utlisateur_identifiant";
    private String EXTRA_GROUPE_POSITION= "com.jde.skillbill.groupe_identifiant";
    private static final String IMAGE ="com.jde.skillbill.BitmapImage";
    private final Utilisateur utilisateurConnecte;
    private final Groupe groupe;
    private final IGestionFacture iGestionFacture;
    private final IGestionUtilisateur iGestionUtilisateur;
    private final IGestionGroupes iGestionGroupes;
    private static final int REQUETE_PRENDRE_PHOTO= 2;

    private Thread filEsclave = null;
    private final Handler handlerReponse;
    private static final int MSG_AJOUT_FACTURE_REUSSI = 0;
    private static final int MSG_ERREUR = 1;

    @SuppressLint("HandlerLeak")
    public PresenteurAjouterFacture(ActivityAjouterFacture activityAjouterFacture, VueAjouterFacture vueAjouterFacture, Modele modele, IGestionFacture gestionFacture, IGestionUtilisateur gestionUtilisateur, IGestionGroupes gestionGroupes) {
        this.activityAjouterFacture = activityAjouterFacture;
        this.vueAjouterFacture = vueAjouterFacture;
        this.modele = modele;
        utilisateurConnecte=(Utilisateur) activityAjouterFacture.getIntent().getSerializableExtra(EXTRA_ID_UTILISATEUR);
        this.iGestionFacture = gestionFacture;
        this.iGestionUtilisateur = gestionUtilisateur;
        this.iGestionGroupes = gestionGroupes;
        modele.setUtilisateurConnecte(utilisateurConnecte);
        groupe=(Groupe) activityAjouterFacture.getIntent().getSerializableExtra(EXTRA_GROUPE_POSITION);




        handlerReponse = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                filEsclave = null;
                if (msg.what == MSG_AJOUT_FACTURE_REUSSI) {
                    redirigerVersListeFactures();

                } else if (msg.what == MSG_ERREUR) {

                    vueAjouterFacture.afficherMessageErreurAlertDialog(
                            activityAjouterFacture.getResources().getString(R.string.txt_message_erreur)
                            ,activityAjouterFacture.getResources().getString(R.string.titre_erreur_generique)
                    );
                }
            }
        };
    }

    /**
     *
     * @return liste des noms des utilisateurs du groupe
     */
    @Override
    public String[] presenterListeUtilsateur() {



        List<Utilisateur> utilisateursGroupe= iGestionGroupes.trouverTousLesUtilisateurs(groupe);
        String[] membres=new String[utilisateursGroupe.size()];
        int i=0;
        for (Utilisateur utilisateur : utilisateursGroupe){
            membres[i]=utilisateur.getNom();
            i++;
        }
        return membres;
    }

    /**
     * ajoute une facture a un groupe
     */
    @Override
    public void ajouterFacture() {

        filEsclave= new Thread (()-> {
            Message msg;

            try {
                double montant = vueAjouterFacture.getMontantFactureInput();
                LocalDate date = vueAjouterFacture.getDateFactureInput();
                String titre = vueAjouterFacture.getTitreInput();
                if (titre == null) {
                    titre = activityAjouterFacture.getResources().getString(R.string.txt_facture_par_defaut) + " " + date.toString();
                }

                boolean factureAjoutee = iGestionFacture.creerFacture(montant, utilisateurConnecte, date, groupe, titre);
                msg = handlerReponse.obtainMessage(MSG_AJOUT_FACTURE_REUSSI, factureAjoutee);

            } catch (NumberFormatException | DateTimeParseException e) {
                msg = handlerReponse.obtainMessage(MSG_ERREUR);

            }
            handlerReponse.sendMessage( msg );
        });
        filEsclave.start();
    }


    /**
     * Redirige vers l'activite voirUnGroupe pour montrer la nouvelle facture ajoutee
     */
    @Override
    public void redirigerVersListeFactures(){
        Intent intent = new Intent(activityAjouterFacture, ActivityVoirUnGroupe.class);
        intent.putExtra(EXTRA_GROUPE_POSITION, groupe);
        intent.putExtra(EXTRA_ID_UTILISATEUR, modele.getUtilisateurConnecte());

        activityAjouterFacture.startActivity(intent);

        //ne pourra pas retourner a l'activite de creation de facture
        activityAjouterFacture.finish();
    }

    @Override
    public void prendrePhoto() {
        Intent prendrePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try { //TODO verifier que l'appareil est capable de prendre des photos
            prendrePhotoIntent.putExtra(EXTRA_GROUPE_POSITION, groupe);
            activityAjouterFacture.startActivityForResult(prendrePhotoIntent, REQUETE_PRENDRE_PHOTO );
        }catch (ActivityNotFoundException e){
            //TODO
        }
    }
}
