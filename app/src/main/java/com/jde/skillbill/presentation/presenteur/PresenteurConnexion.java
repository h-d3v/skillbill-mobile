package com.jde.skillbill.presentation.presenteur;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import com.jde.skillbill.R;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.GestionUtilisateur;
import com.jde.skillbill.domaine.interacteurs.ISourceDonnee;
import com.jde.skillbill.domaine.interacteurs.interfaces.SourceDonneeException;
import com.jde.skillbill.donnees.mockDAO.SourceDonneesMock;
import com.jde.skillbill.presentation.IContratVPConnexion;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueConnexion;
import com.jde.skillbill.ui.activity.ActivityCreerCompte;
import com.jde.skillbill.ui.activity.ActivityVoirGroupes;

import static android.content.Context.MODE_PRIVATE;
import static android.provider.Settings.System.getString;

public class PresenteurConnexion implements IContratVPConnexion.IPresenteurConnexion {

    private VueConnexion _vueConnexion;
    private Modele _modele;
    private ISourceDonnee _dataSource;
    private Activity _activite;
    private String EXTRA_ID_UTILISATEUR="com.jde.skillbill.utlisateur_identifiant";
    private static final int MSG_TENTER_CONNECTION_REUSSI =0;
    private static final int MSG_ERREUR =1;
    private static  final int MSG_PAS_DE_CONNECTION=3;

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
                    //todo mettre le string de la monnaie usuelle dans les shared prefs a la connection.
                    _vueConnexion.afficherMsgConnecter(_modele.getUtilisateurConnecte().getCourriel(), _modele.getUtilisateurConnecte().getNom());
                    _vueConnexion.fermerProgressBar();
                    Log.println(Log.ASSERT, "Monnaie user connecter", _modele.getUtilisateurConnecte().getMonnaieUsuelle().name());
                    //ajout de la monnaie du user connecte aux shared pref de l'app
                    SharedPreferences.Editor editor = activite.getSharedPreferences("SKILLBILL_USER_PREF",
                            Context.MODE_PRIVATE).edit();
                    editor.putString("monnaieUser", _modele.getUtilisateurConnecte().getMonnaieUsuelle().name());
                    editor.apply();
                    redirigerVersActiviteVoirLesGroupes();
                }
                else if(msg.what == MSG_ERREUR){
                    _vueConnexion.fermerProgressBar();
                    _vueConnexion.afficherMsgErreur();
                }
                else if(msg.what== MSG_PAS_DE_CONNECTION){
                    _vueConnexion.fermerProgressBar();
                    Toast.makeText(_activite, R.string.pas_de_connection_internet , Toast.LENGTH_LONG ).show();
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
        _vueConnexion.ouvrirProgressBar();
        filEsclave= new Thread(() -> {
            Message msg = null;
            try{
                Utilisateur utilisateurConnecter= gestionUtilisateur.tenterConnexion(email, mdp);


                if (utilisateurConnecter!=null){
                    _modele.setUtilisateurConnecte(utilisateurConnecter);
                    msg = handlerRéponse.obtainMessage(MSG_TENTER_CONNECTION_REUSSI);
                }

                else {
                    msg = handlerRéponse.obtainMessage(MSG_ERREUR);
                }

            } catch (SourceDonneeException e) {

                msg = handlerRéponse.obtainMessage(MSG_PAS_DE_CONNECTION);

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
