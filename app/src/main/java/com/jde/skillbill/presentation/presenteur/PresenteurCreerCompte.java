package com.jde.skillbill.presentation.presenteur;

import android.app.Activity;

import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.DataSourceUsers;
import com.jde.skillbill.domaine.interacteurs.GestionUtilisateur;
import com.jde.skillbill.presentation.IContratVPCreerCompte;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueCreerCompte;


public class PresenteurCreerCompte implements IContratVPCreerCompte.PresenteurCreerCompte {
    private VueCreerCompte _vueCreerCompte;
    private Modele _modele;
    private DataSourceUsers _dataSource;
    private Activity _activite;


    public PresenteurCreerCompte(Activity activite,Modele modele, VueCreerCompte vueCreerCompte) {
        _activite=activite;
        _modele = modele;
        _vueCreerCompte=vueCreerCompte;
    }

    public void setDataSource(DataSourceUsers dataSource) {
        _dataSource = dataSource;
    }

    //TODO Faire les operations avec les messages...
    class MESSAGE {
        //On n'utilise pas un enum parce que la constante doit être passée sous forme de int dans un Message.
        //Cette méthode est plus simple que l'équivalent avec un enum.
        static final int NOUVEAU_COMPTE = 0;
        static final int ERREUR = 1;
        static final int EMAIL_DEJA_PRIS = 2;
    };

    @Override
    public void creerCompte() {
        //TODO Faire les operations avec la source de donnees en fil esclave pour l'api
        GestionUtilisateur gestionUtilisateur= new GestionUtilisateur(_dataSource);
        gestionUtilisateur.setSource(_dataSource);
        boolean emailDejaPris=gestionUtilisateur.utilisateurExiste( _vueCreerCompte.getEmail());
        if(emailDejaPris) _vueCreerCompte.afficherEmailDejaPrit();
        else{
            Utilisateur utilisateurCreer= gestionUtilisateur.creerUtilisateur(_vueCreerCompte.getNom(),
                    _vueCreerCompte.getEmail(),_vueCreerCompte.getPass());
            //Affichage pour tester
            _vueCreerCompte.afficherCompteCreer(utilisateurCreer.getNom(), utilisateurCreer.getCourriel());
        }

        //TODO la creation du compte si l'email n'est pas pris
    }



    @Override
    public void retourLogin() {

    }


}
