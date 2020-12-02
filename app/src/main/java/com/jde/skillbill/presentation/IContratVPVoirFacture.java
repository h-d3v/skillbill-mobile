package com.jde.skillbill.presentation;

import android.graphics.Bitmap;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public interface IContratVPVoirFacture {

     interface VueVoirFacture{
         void setPresenteur(PresenteurVoirFacture presenteur);

         LocalDate getDateFactureInput() throws NullPointerException, DateTimeParseException;

         double getMontantFactureInput() throws NullPointerException, NumberFormatException;

         void afficherMessageErreurAlertDialog(String message, String titre);

         Bitmap getBitmapFacture();

         String getTitreInput();

         void fermerProgressBar();

         void ouvrirProgressBar();
     }

    interface PresenteurVoirFacture{

        String trouverMontantFactureEnCours();

        String trouverTitreFactureEnCours();

        String trouverDateFactureEnCours();

        void envoyerRequeteModificationFacture();
    }
}
