package com.jde.skillbill.presentation;

import com.jde.skillbill.domaine.entites.Utilisateur;

public interface IContratVuePresenteurVoirUnGroupe {

     interface IPresenteurVoirUnGroupe{
         String getMembresGroupe();
         boolean ajouterUtilisateurAuGroupe(Utilisateur utilisateur);
         void commencerAjouterUnMembreAuGroupe();

     }
     interface IVueVoirUnGroupe{
        void setPresenteur(IPresenteurVoirUnGroupe iPresenteurVoirUnGroupe);

     }


}
