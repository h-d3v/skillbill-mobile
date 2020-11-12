package com.jde.skillbill.presentation;

public interface IContratVuePresenteurVoirUnGroupe {

     interface IPresenteurVoirUnGroupe{
         String getMembresGroupe();

         void ajouterUtilisateurAuGroupe(String courriel);

         void envoyerCourriel(String courriel);


     }
     interface IVueVoirUnGroupe{
        void setPresenteur(IPresenteurVoirUnGroupe iPresenteurVoirUnGroupe);

     }


}
