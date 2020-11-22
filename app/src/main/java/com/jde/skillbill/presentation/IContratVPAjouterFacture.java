package com.jde.skillbill.presentation;

import com.jde.skillbill.domaine.entites.Monnaie;
import android.graphics.Bitmap;
import java.time.DateTimeException;
import java.time.LocalDate;

public interface IContratVPAjouterFacture {

    interface IPresenteurAjouterFacture{

        String[] presenterListeUtilsateur();

        void ajouterFacture();

        void prendrePhoto();

        void redirigerVersListeFactures();

        Monnaie getMonnaieUserConnecte();
    }

    interface IVueAjouterFacture{

        void setPresenteur(IPresenteurAjouterFacture presenteurAjouterFacture);

        LocalDate getDateFactureInput() throws NullPointerException, DateTimeException;

        double getMontantFactureCADInput();


        void afficherMessageErreurAlertDialog(String message, String titre);

        Bitmap getBitmapFacture();

        String getTitreInput();

        void fermerProgressBar();

        void ouvrirProgressBar();

    }
}
