package com.jde.skillbill.presentation.presenteur;

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


    public PresenteurCreerCompte(Activity activite,Modele modele, VueCreerCompte vueCreerCompte) {
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




    @Override
    public void creerCompte() {
        filEsclave = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = null;
                GestionUtilisateur gestionUtilisateur= new GestionUtilisateur(_dataSource);
                gestionUtilisateur.setSource(_dataSource);
                boolean emailDejaPris=gestionUtilisateur.utilisateurExiste( _vueCreerCompte.getEmail());
                if(emailDejaPris){
                    msg = handler.obtainMessage(MESSAGE.EMAIL_DEJA_PRIS);
                    Log.e("Presenteur creer compte", "je suis ligne 75");
                }
                else {
                    Utilisateur utilisateurCreer = gestionUtilisateur.creerUtilisateur(_vueCreerCompte.getNom(), _vueCreerCompte.getEmail(), _vueCreerCompte.getPass(), _vueCreerCompte.getMonnaieChoisie());
                    if(utilisateurCreer!=null){
                        handler.obtainMessage(MESSAGE.NOUVEAU_COMPTE,utilisateurCreer);
                        Log.e("Presenteur creer compte", "je suis ligne 81");
                    }
                    else handler.obtainMessage(MESSAGE.ERREUR);
                    Log.e("Presenteur creer compte", "je suis ligne 84");
                }

                handler.sendMessage(msg);
            }
        });
        filEsclave.start();

    }



    @Override
    public void retourLogin() {
        _activite.finish();
    }


}
