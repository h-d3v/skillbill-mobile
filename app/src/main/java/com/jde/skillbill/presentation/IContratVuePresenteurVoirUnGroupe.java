package com.jde.skillbill.presentation;

public interface IContratVuePresenteurVoirUnGroupe {

     interface IPresenteurVoirUnGroupe{
         String getMembresGroupe();

         int ajouterUtilisateurAuGroupe(String courriel);

         int envoyerCourriel(String courriel);


     }
     interface IVueVoirUnGroupe{
        void setPresenteur(IPresenteurVoirUnGroupe iPresenteurVoirUnGroupe);

     }


}
