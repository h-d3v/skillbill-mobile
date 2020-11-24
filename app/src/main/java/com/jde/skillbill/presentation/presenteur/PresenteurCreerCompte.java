package com.jde.skillbill.presentation.presenteur;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.os.Handler;
import android.os.Message;

import android.widget.Toast;
import com.jde.skillbill.R;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.GestionUtilisateur;
import com.jde.skillbill.domaine.interacteurs.ISourceDonnee;
import com.jde.skillbill.domaine.interacteurs.interfaces.SourceDonneeException;
import com.jde.skillbill.presentation.IContratVPCreerCompte;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueCreerCompte;

import java.util.Objects;


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
        static final int ERREUR_CNX =3;
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
                    _vueCreerCompte.afficherErreurConnection();
                }
                else if(msg.what==MESSAGE.EMAIL_DEJA_PRIS){
                    _vueCreerCompte.afficherEmailDejaPrit();
                }
                else if(msg.what==MESSAGE.ERREUR_CNX){
                    Toast.makeText(_activite, R.string.pas_de_connection_internet , Toast.LENGTH_LONG ).show();
                }
                _vueCreerCompte.fermerProgressBar();
            }
        };
    }

    public void setDataSource(ISourceDonnee dataSource) {
        _dataSource = dataSource;
    }

    

    @Override
    public void creerCompte() {
        _vueCreerCompte.ouvrirProgressBar();
        filEsclave = new Thread(() -> {
            Message msg=null;
            GestionUtilisateur gestionUtilisateur= new GestionUtilisateur(_dataSource);

                try{
                    //todo, appeler methode utilisateurExiste(), si existe, on envoie le msg, si non on continu
                    Utilisateur utilisateurCreer = gestionUtilisateur.creerUtilisateur(_vueCreerCompte.getNom(), _vueCreerCompte.getEmail(), _vueCreerCompte.getPass(), _vueCreerCompte.getMonnaieChoisie());
                    if(utilisateurCreer==null){
                        msg=handler.obtainMessage(MESSAGE.EMAIL_DEJA_PRIS);
                    }
                    else if(utilisateurCreer.getCourriel().equals(_vueCreerCompte.getEmail())) {
                        msg = handler.obtainMessage(MESSAGE.NOUVEAU_COMPTE, utilisateurCreer);
                    }
                }catch (SourceDonneeException e){
                    msg = handler.obtainMessage(MESSAGE.ERREUR_CNX);
                }

            handler.sendMessage(msg);
        });
        filEsclave.start();

    }



    @Override
    public void retourLogin() {
        _activite.finish();
    }


}
