package com.jde.skillbill.presentation;

import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.domaine.entites.Utilisateur;

public interface IContratVPModifProfil {
    interface VueModifProfil {
        void setPresenteur(PresenteurModifProfil presenuteur);

        String getNouveauNom();
        String getNouveauEmail();
        String getNouveauMdp();
        Monnaie getNouvelleMonnaie();

        void setNomUser(String nom);
        void setMonnaieUser(String monnaieStr);
        void setEmailUser(String nom);
    }

    interface PresenteurModifProfil {
        boolean modifierProfil(String nom, String email, String mdp, Monnaie monnaie);
        void remplirInfosUser();
        String getEmailUserConnecte();
        String getNomUserConnecte();
        Monnaie  getNomMonnaieConnecte();
    }
}
