package com.jde.skillbill.presentation;

public interface IContratVPVoirFacture {

     interface VueVoirFacture{
         void setPresenteur(PresenteurVoirFacture presenteur);

     }

    interface PresenteurVoirFacture{

        String trouverMontantFactureEnCours();

        String trouverTitreFactureEnCours();

        String trouverDateFactureEnCours();

        void envoyerRequeteModificationFacture();
    }
}
