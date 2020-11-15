package com.jde.skillbill.presentation.presenteur;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.GestionGroupes;
import com.jde.skillbill.domaine.interacteurs.GestionUtilisateur;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionGroupes;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionUtilisateur;
import com.jde.skillbill.donnees.APIRest.SourceDonneesAPIRest;
import com.jde.skillbill.donnees.mockDAO.SourceDonneesMock;
import com.jde.skillbill.presentation.IContratVuePresenteurCreerGroupe;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueCreerGroupe;
import com.jde.skillbill.ui.activity.ActivityVoirUnGroupe;


public class PresenteurCreerGroupe implements IContratVuePresenteurCreerGroupe.PresenteurCreerGroupe {
    private Modele modele;
    private VueCreerGroupe vueCreerGroupe;
    private Activity activity;

    private String EXTRA_ID_UTILISATEUR = "com.jde.skillbill.utlisateur_identifiant";
    private String EXTRA_GROUPE_POSITION = "com.jde.skillbill.groupe_identifiant";

    private Thread filEsclave = null;
    private final Handler handlerReponse;
    private static final int MSG_GROUPE_CREER_REUSSI = 0;
    private static final int MSG_ERREUR = 1;
    private Groupe groupeCree;


    @SuppressLint("HandlerLeak")
    public PresenteurCreerGroupe(Modele modele, VueCreerGroupe vueCreerGroupe, Activity activity) {
        this.modele = modele;
        this.vueCreerGroupe = vueCreerGroupe;
        this.activity = activity;


        if (modele.getUtilisateurConnecte() == null) {
            modele.setUtilisateurConnecte((Utilisateur) activity.getIntent().getSerializableExtra(EXTRA_ID_UTILISATEUR));
        }
        handlerReponse = new Handler() {
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                filEsclave = null;
                if (msg.what == MSG_GROUPE_CREER_REUSSI) {
                    groupeCree = (Groupe) msg.obj;
                    vueCreerGroupe.fermerProgressBar();
                    redirigerVersGroupeCreer();

                } else if (msg.what == MSG_ERREUR) {
                    vueCreerGroupe.fermerProgressBar();
                }
            }
        };
    }


    /**
     * cree un groupe
     */
    @Override
    public void creerGroupe() {
        IGestionGroupes gestionGroupes = new GestionGroupes(new SourceDonneesAPIRest());
        vueCreerGroupe.ouvrirProgressBar();
        filEsclave = new Thread(() -> {
            Groupe groupeACreer = gestionGroupes.creerGroupe(vueCreerGroupe.getNomGroupe(), modele.getUtilisateurConnecte(), Monnaie.CAD);

            Message msg;

            if (groupeACreer != null) {

                msg = handlerReponse.obtainMessage(MSG_GROUPE_CREER_REUSSI, groupeACreer);
            } else {
                msg = handlerReponse.obtainMessage(MSG_ERREUR);
            }
            handlerReponse.sendMessage(msg);
        });
        filEsclave.start();

    }

    /**
     * redirige vers voirUnGroupe pour le groupe qui vient d'etre creer
     */
    @Override
    public void redirigerVersGroupeCreer() {

        Intent intent = new Intent(activity, ActivityVoirUnGroupe.class);
        intent.putExtra(EXTRA_ID_UTILISATEUR, modele.getUtilisateurConnecte());
        intent.putExtra(EXTRA_GROUPE_POSITION, groupeCree);
        activity.startActivity(intent);

        //Pour eviter de retourner a l'activite de creation.
        activity.finish();
    }

    /**
     * retours arriere
     */
    @Override
    public void retournerEnArriere() {
        activity.onBackPressed();
    }


}
