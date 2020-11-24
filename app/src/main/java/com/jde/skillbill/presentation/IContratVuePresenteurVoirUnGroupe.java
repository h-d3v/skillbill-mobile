package com.jde.skillbill.presentation;

public interface IContratVuePresenteurVoirUnGroupe {

     interface IPresenteurVoirUnGroupe{
         String getMembresGroupe();

         void ajouterUtilisateurAuGroupe(String courriel);

         void envoyerCourriel(String courriel);


         void commencerVoirDetailFacture(int position);
     }
     interface IVueVoirUnGroupe{
        void setPresenteur(IPresenteurVoirUnGroupe iPresenteurVoirUnGroupe);

         void fermerProgressBar();

         void ouvrirProgressBar();
     }


}
