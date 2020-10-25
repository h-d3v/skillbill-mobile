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
import com.jde.skillbill.donnees.mockDAO.SourceDonneesMock;
import com.jde.skillbill.presentation.IContratVuePresenteurCreerGroupe;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueCreerGroupe;
import com.jde.skillbill.ui.activity.ActivityVoirGroupes;
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

    @SuppressLint("HandlerLeak")
    public PresenteurCreerGroupe(Modele modele, VueCreerGroupe vueCreerGroupe, Activity activity) {
        this.modele = modele;
        this.vueCreerGroupe = vueCreerGroupe;
        this.activity = activity;

        //TODO ajuster la monnaie pour quelle ne sois pas coder en dur
        modele.setUtilisateurConnecte(new Utilisateur("", activity.getIntent().getStringExtra(EXTRA_ID_UTILISATEUR), null, Monnaie.CAD));
        handlerReponse = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                filEsclave = null;
                if (msg.what == MSG_GROUPE_CREER_REUSSI) {
                    redirigerVersGroupeCreer();

                } else if (msg.what == MSG_ERREUR) {
                   //TODO impleter lors de l'api
                }
            }
        };
    }



    @Override
    public void creerGroupe() {
        IGestionGroupes gestionGroupes = new GestionGroupes(new SourceDonneesMock());

        filEsclave= new Thread(() -> {
            Groupe groupeACreer=gestionGroupes.creerGroupe(vueCreerGroupe.getNomGroupe(), modele.getUtilisateurConnecte(),null);

            Message msg;

            if (groupeACreer!=null){
                //modele.setUtilisateurConnecte(utilisateurConnecter);
                msg = handlerReponse.obtainMessage(MSG_GROUPE_CREER_REUSSI);
            }

            else {
                msg = handlerReponse.obtainMessage(MSG_ERREUR);
            }
            handlerReponse.sendMessage(msg);
        });
        filEsclave.start();

    }

    private void redirigerVersGroupeCreer(){
        IGestionUtilisateur gestionUtilisateur = new GestionUtilisateur(new SourceDonneesMock());
        //redirection vers ses groupes
        Intent intent = new Intent(activity, ActivityVoirUnGroupe.class);
        intent.putExtra(EXTRA_ID_UTILISATEUR, modele.getUtilisateurConnecte().getCourriel());
        intent.putExtra(EXTRA_GROUPE_POSITION, gestionUtilisateur.trouverGroupesAbonne(modele.getUtilisateurConnecte()).size()-1);
        activity.startActivity(intent);

        //Pour eviter de retourner a l'activite de creation.
        activity.finish();
    }

    @Override
    public void retournerEnArriere(){
        activity.onBackPressed();
    }



}
