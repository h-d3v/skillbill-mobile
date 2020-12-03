package com.jde.skillbill.presentation.presenteur;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.jde.skillbill.R;
import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.GestionUtilisateur;
import com.jde.skillbill.domaine.interacteurs.ISourceDonnee;
import com.jde.skillbill.domaine.interacteurs.interfaces.SourceDonneeException;
import com.jde.skillbill.donnees.APIRest.entites.UtilisateurRestAPI;
import com.jde.skillbill.presentation.IContratVPModifProfil;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueModifProfil;
import com.jde.skillbill.ui.activity.ActivityVoirGroupes;

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
                    modele.setUtilisateurConnecte((Utilisateur) msg.obj);
                    SharedPreferences.Editor editor = _activite.getSharedPreferences("SKILLBILL_USER_PREF",
                            Context.MODE_PRIVATE).edit();
                    editor.putString("monnaieUser", modele.getUtilisateurConnecte().getMonnaieUsuelle().name());
                    editor.apply();
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra(EXTRA_ID_UTILISATEUR,modele.getUtilisateurConnecte());
                    activite.setResult(Activity.RESULT_OK,returnIntent);
                    Toast.makeText(activite, "Vos informations ont été modifiées avec succès", Toast.LENGTH_SHORT).show();
                    //afficher un message indiquant la reussite
                }
                if(msg.what == MSG_ERREUR){
                    //mettre a jours les infos de l'utilisateurs dans l'application
                    Toast.makeText(activite, "Ce courriel est deja pris, veuillez en choisir un autre", Toast.LENGTH_SHORT).show();
                }
                if(msg.what == MSG_PAS_DE_CONNECTION){
                    Toast.makeText(activite, R.string.pas_de_connection_internet, Toast.LENGTH_SHORT).show();
                }
            }
        };

    }



    @Override
    public void modifierProfil() {
        //todo methode rest classe api qui retourne un utilisateur
       //TODO ouvrir et fermer progress bar
        filEsclave = new Thread(() -> {
            Utilisateur utilisateurRetour;
            Message msg;
            GestionUtilisateur gestionUtilisateur= new GestionUtilisateur(_dataSource);
            Utilisateur userModifier= new Utilisateur(_vue.getNouveauNom(), _vue.getNouveauEmail(), _vue.getNouveauMdp(), _vue.getNouvelleMonnaie());
            try{

                utilisateurRetour = gestionUtilisateur.modifierUtilisateur(userModifier, _modele.getUtilisateurConnecte());
                if(utilisateurRetour!=null){

                    msg = handlerReponse.obtainMessage(MSG_MODIFICATION_REUSSIE,utilisateurRetour);

                }
                else {
                    msg=handlerReponse.obtainMessage(MSG_ERREUR);
                }
            }catch (SourceDonneeException e){
                msg = handlerReponse.obtainMessage(MSG_PAS_DE_CONNECTION);
            }

            handlerReponse.sendMessage(msg);
        });
        filEsclave.start();
    }

    @Override
    public void remplirInfosUser() {
        //todo remove, cette methode devrais etre creer dans la vue
        //_vue.setEmailUser(_modele.getUtilisateurConnecte().getCourriel());
        //_vue.setNomUser(_modele.getUtilisateurConnecte().getNom());
        //_vue.setMonnaieUser(_modele.getUtilisateurConnecte().getMonnaieUsuelle().toString());
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
    public Monnaie getMonnaieConnecte() {
        return _modele.getUtilisateurConnecte().getMonnaieUsuelle();
    }

    @Override
    public String getMdpUserConnecte(){
        return _modele.getUtilisateurConnecte().getMotPasse();
    }



}
