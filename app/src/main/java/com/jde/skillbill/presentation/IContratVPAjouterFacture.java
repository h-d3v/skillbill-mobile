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

        String trouverMontantFactureEnCours();

        String trouverTitreFactureEnCours();

        String trouverDateFactureEnCours();

        void redirigerVersListeFactures();

        Monnaie getMonnaieUserConnecte();

        void envoyerRequeteModificationFacture();

        String presenterPayeurs();
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

        boolean[] getMultipleUtilisateursPayeurs();
    }
}
