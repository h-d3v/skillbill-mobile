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
import com.jde.skillbill.domaine.entites.UtilisateurException;
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
    private static final int MSG_ERREUR_USER =1;
    private static  final int MSG_PAS_DE_CONNECTION=3;

    private final Handler handlerReponse;

    private Thread filEsclave = null;


    /**
     *
     * @param activite
     * @param modele
     * @param vue
     * @param sourceDonnee
     */
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
                if(msg.what == MSG_ERREUR_USER){
                    //mettre a jours les infos de l'utilisateurs dans l'application
                    Toast.makeText(activite, (String) msg.obj, Toast.LENGTH_SHORT).show();
                }
                if(msg.what == MSG_PAS_DE_CONNECTION){
                    Toast.makeText(activite, R.string.pas_de_connection_internet, Toast.LENGTH_SHORT).show();
                }
                _vue.activerDescativerBtn();
            }
        };

    }


    /**
     * initie l'action de modifier la modification de profil. Les informations
     * sont prises dans la vue et transmises à la source de données
     * qui va les traitées. Un message est ensuite transmis au handler si la modification
     * est réussie, échouée ou si la connexion à la source de données est impossible.
     */
    @Override
    public void modifierProfil() {
        _vue.activerDescativerBtn();
       //TODO ouvrir et fermer progress bar
        filEsclave = new Thread(() -> {
            Utilisateur utilisateurRetour;
            Message msg=null;
            GestionUtilisateur gestionUtilisateur= new GestionUtilisateur(_dataSource);
            Utilisateur userModifier= new Utilisateur(_vue.getNouveauNom(), _vue.getNouveauEmail(), _vue.getMdpCourrant(), _vue.getNouvelleMonnaie());
            if(_vue.getNouveauMdp().length()>=8){
                userModifier.setNouveauMotDePasse(_vue.getNouveauMdp());
            }
            try{
                utilisateurRetour = gestionUtilisateur.modifierUtilisateur(userModifier, _modele.getUtilisateurConnecte());
                if(utilisateurRetour!=null){
                    msg = handlerReponse.obtainMessage(MSG_MODIFICATION_REUSSIE,utilisateurRetour);
                }
            }catch (SourceDonneeException | UtilisateurException e){
                if(e.getClass()==UtilisateurException.class){
                    msg=handlerReponse.obtainMessage(MSG_ERREUR_USER);
                    msg.obj=((UtilisateurException) e).getMessage();
                }
                else {
                    msg = handlerReponse.obtainMessage(MSG_PAS_DE_CONNECTION);
                }
            }
            handlerReponse.sendMessage(msg);
        });
        filEsclave.start();
    }

    /**
     *
     * @return l'email de l'utilisateur connecté
     */
    @Override
    public String getEmailUserConnecte() {
        return _modele.getUtilisateurConnecte().getCourriel();
    }

    /**
     *
     * @return le nom  de l'utilisateur connecté
     */
    @Override
    public String getNomUserConnecte() {
        return _modele.getUtilisateurConnecte().getNom();
    }

    /**
     * @return la monnaie de l'utilisateur connecté
     */
    @Override
    public Monnaie getMonnaieConnecte() {
        return _modele.getUtilisateurConnecte().getMonnaieUsuelle();
    }

    /**
     * @return le mdp
     */
    @Override
    public String getMdpUserConnecte(){
        return _modele.getUtilisateurConnecte().getMotPasse();
    }



}
