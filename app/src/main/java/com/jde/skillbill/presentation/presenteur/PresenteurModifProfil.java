package com.jde.skillbill.presentation.presenteur;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.jde.skillbill.R;
import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.ISourceDonnee;
import com.jde.skillbill.presentation.IContratVPModifProfil;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueConnexion;
import com.jde.skillbill.presentation.vue.VueModifProfil;

public class PresenteurModifProfil implements IContratVPModifProfil.PresenteurModifProfil {
    private VueModifProfil _vue;
    private Modele _modele;
    private ISourceDonnee _dataSource;
    private Activity _activite;
    private String EXTRA_ID_UTILISATEUR="com.jde.skillbill.utlisateur_identifiant";
    private static final int MSG_MODIFICATION_REUSSIE =0;
    private static final int MSG_ERREUR =1;
    private static  final int MSG_PAS_DE_CONNECTION=3;

    private final Handler handlerReponse;

    private Thread filEsclave = null;


    @SuppressLint("HandlerLeak")
    public PresenteurModifProfil(Activity activite, Modele modele, VueModifProfil vue, ISourceDonnee sourceDonnee){

        _modele = modele;
        _vue=vue;
        _activite=activite;
        modele.setUtilisateurConnecte((Utilisateur) activite.getIntent().getSerializableExtra(EXTRA_ID_UTILISATEUR));
        _dataSource = sourceDonnee;

        handlerReponse = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                filEsclave = null;

                if(msg.what == MSG_MODIFICATION_REUSSIE){
                    //afficher un message indiquant la reussite
                }
                if(msg.what == MSG_ERREUR){
                    Toast.makeText(activite, "Vos informations ont été modifiées avec succès", Toast.LENGTH_LONG);
                }
                if(msg.what == MSG_PAS_DE_CONNECTION){
                    Toast.makeText(activite, R.string.pas_de_connection_internet, Toast.LENGTH_LONG);
                }
            }
        };

    }


    @Override
    public boolean modifierProfil(String nom, String email, String mdp, Monnaie monnaie) {
        return false;
    }

    @Override
    public void remplirInfosUser() {
        _vue.setEmailUser(_modele.getUtilisateurConnecte().getCourriel());
        _vue.setNomUser(_modele.getUtilisateurConnecte().getNom());
        _vue.setMonnaieUser(_modele.getUtilisateurConnecte().getMonnaieUsuelle().toString());
    }

    @Override
    public String getEmailUserConnecte() {
        return _modele.getUtilisateurConnecte().getCourriel();
    }

    @Override
    public String getNomUserConnecte() {
        return _modele.getUtilisateurConnecte().getNom();
    }

    @Override
    public Monnaie getNomMonnaieConnecte() {
        return _modele.getUtilisateurConnecte().getMonnaieUsuelle();
    }

}
