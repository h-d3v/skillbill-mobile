package com.jde.skillbill.presentation.presenteur;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

import android.os.Handler;
import android.os.Message;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.GestionUtilisateur;
import com.jde.skillbill.domaine.interacteurs.ISourceDonnee;
import com.jde.skillbill.donnees.mockDAO.SourceDonneesMock;
import com.jde.skillbill.presentation.IContratVPConnexion;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueConnexion;
import com.jde.skillbill.ui.activity.ActivityCreerCompte;
import com.jde.skillbill.ui.activity.ActivityVoirGroupes;

public class PresenteurConnexion implements IContratVPConnexion.IPresenteurConnexion {

    private VueConnexion _vueConnexion;
    private Modele _modele;
    private ISourceDonnee _dataSource;
    private Activity _activite;
    private String EXTRA_ID_UTILISATEUR="com.jde.skillbill.utlisateur_identifiant";
    private static final int MSG_TENTER_CONNECTION_REUSSI =0;
    private static final int MSG_ERREUR =1;

    private final Handler handlerRéponse;

    private Thread filEsclave = null;


    @SuppressLint("HandlerLeak")
    public PresenteurConnexion(Activity activite, Modele modele, VueConnexion vueConnexion) {
        _activite = activite;
        _modele = modele;
        _vueConnexion = vueConnexion;
        handlerRéponse = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                filEsclave = null;
                if(msg.what == MSG_TENTER_CONNECTION_REUSSI){
                    _vueConnexion.afficherMsgConnecter(_modele.getUtilisateurConnecte().getCourriel(), _modele.getUtilisateurConnecte().getNom());
                    redirigerVersActiviteVoirLesGroupes();
                }
                else if(msg.what == MSG_ERREUR){
                    _vueConnexion.afficherMsgErreur();
                }

            }


        };
    }

    public void setDataSource(ISourceDonnee dataSource) {
        _dataSource = dataSource;
    }

    @Override
    public void tenterConnexion(String email, String mdp){
        GestionUtilisateur gestionUtilisateur= new GestionUtilisateur(_dataSource);
        gestionUtilisateur.setSource(_dataSource);

        filEsclave= new Thread(() -> {
            Utilisateur utilisateurConnecter= gestionUtilisateur.tenterConnexion(email, mdp);
            Message msg = null;

            if (utilisateurConnecter!=null){
                _modele.setUtilisateurConnecte(utilisateurConnecter);
                msg = handlerRéponse.obtainMessage(MSG_TENTER_CONNECTION_REUSSI);
            }

            else {
                msg = handlerRéponse.obtainMessage(MSG_ERREUR);
            }
            handlerRéponse.sendMessage(msg);
        });
        filEsclave.start();




    }

    @Override
    public void allerInscription() {
        Intent intentInscription = new Intent(_activite, ActivityCreerCompte.class);
        _activite.startActivity(intentInscription);
    }

    private void redirigerVersActiviteVoirLesGroupes(){

        //redirection vers ses groupes

        Intent intent = new Intent(_activite, ActivityVoirGroupes.class);
        intent.putExtra(EXTRA_ID_UTILISATEUR, _modele.getUtilisateurConnecte());
        _activite.startActivity(intent);
        //Pour eviter de retourner au login
        _activite.finish();


    }


}
