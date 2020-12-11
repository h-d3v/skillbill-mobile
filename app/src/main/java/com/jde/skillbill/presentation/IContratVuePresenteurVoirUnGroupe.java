package com.jde.skillbill.presentation;

import com.jde.skillbill.domaine.entites.Monnaie;

public interface IContratVuePresenteurVoirUnGroupe {

     interface IPresenteurVoirUnGroupe{
         String getMembresGroupe();

         void ajouterUtilisateurAuGroupe(String courriel);

         void envoyerCourriel(String courriel);


         void commencerVoirDetailFacture(int position);

         Monnaie getMonnaieGroupe();
     }
     interface IVueVoirUnGroupe{
        void setPresenteur(IPresenteurVoirUnGroupe iPresenteurVoirUnGroupe);

         void fermerProgressBar();

         void ouvrirProgressBar();
     }


}
