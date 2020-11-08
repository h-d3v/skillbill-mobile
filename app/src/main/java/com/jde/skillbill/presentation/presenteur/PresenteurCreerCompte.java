package com.jde.skillbill.presentation.presenteur;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.os.Handler;
import android.os.Message;

import android.util.Log;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.GestionUtilisateur;
import com.jde.skillbill.domaine.interacteurs.ISourceDonnee;
import com.jde.skillbill.presentation.IContratVPCreerCompte;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueCreerCompte;


public class PresenteurCreerCompte implements IContratVPCreerCompte.PresenteurCreerCompte {
    private VueCreerCompte _vueCreerCompte;
    private Modele _modele;
    private ISourceDonnee _dataSource;
    private Activity _activite;
    private Handler handler;
    private Thread filEsclave;
    private static final class MESSAGE {
        //On n'utilise pas un enum parce que la constante doit être passée sous forme de int dans un Message.
        //Cette méthode est plus simple que l'équivalent avec un enum.
        static final int NOUVEAU_COMPTE = 0;
        static final int ERREUR = 1;
        static final int EMAIL_DEJA_PRIS = 2;
    };


    @SuppressLint("HandlerLeak")
    public PresenteurCreerCompte(Activity activite, Modele modele, VueCreerCompte vueCreerCompte) {
        _activite=activite;
        _modele = modele;
        _vueCreerCompte=vueCreerCompte;
        handler= new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                filEsclave =null;
                if(msg.what==MESSAGE.NOUVEAU_COMPTE){
                    //Affichage pour tester la creation du compte
                    Utilisateur utilisateurCree = (Utilisateur) msg.obj;
                    _vueCreerCompte.afficherCompteCreer(utilisateurCree.getNom(), utilisateurCree.getCourriel(), utilisateurCree.getMonnaieUsuelle());
                }
                else if(msg.what==MESSAGE.ERREUR){
                    //TODO Sera implementé lors de l'implémentation de la source API
                }
                else if(msg.what==MESSAGE.EMAIL_DEJA_PRIS){
                    _vueCreerCompte.afficherEmailDejaPrit();
                }
            }
        };
    }

    public void setDataSource(ISourceDonnee dataSource) {
        _dataSource = dataSource;
    }





    //TODO Faire les operations avec la source de donnees en fil esclave pour l'api
    //TODO la creation reele du compte si l'email n'est pas pris (persistance)
    @Override
    public void creerCompte() {
        filEsclave = new Thread(() -> {
            Message msg = null;
            GestionUtilisateur gestionUtilisateur= new GestionUtilisateur(_dataSource);
            //boolean emailDejaPris=gestionUtilisateur.utilisateurExiste( _vueCreerCompte.getEmail());

            //if(emailDejaPris){
                //msg = handler.obtainMessage(MESSAGE.EMAIL_DEJA_PRIS);
            //}
            //else {
                Utilisateur utilisateurCreer = gestionUtilisateur.creerUtilisateur(_vueCreerCompte.getNom(), _vueCreerCompte.getEmail(), _vueCreerCompte.getPass(), _vueCreerCompte.getMonnaieChoisie());
                if(utilisateurCreer!=null){
                    msg=handler.obtainMessage(MESSAGE.NOUVEAU_COMPTE,utilisateurCreer);
                }
                else msg=handler.obtainMessage(MESSAGE.ERREUR);
            //}

            handler.sendMessage(msg);
        });
        filEsclave.start();

    }

    @Override
    public void retourLogin() {
        _activite.finish();
    }


}
