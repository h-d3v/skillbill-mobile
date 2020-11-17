package com.jde.skillbill.presentation;

public interface IContratVPVoirFacture {

     interface VueVoirFacture{
         void setPresenteur(PresenteurVoirFacture presenteur);
     }

    interface PresenteurVoirFacture{
        void modifierFactureEnCours();

        String trouverMontantFactureEnCours();

        String trouverTitreFactureEnCours();

        String trouverDateFactureEnCours();
    }
}
