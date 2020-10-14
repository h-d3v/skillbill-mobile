package com.jde.skillbill.presentation;

import android.widget.ArrayAdapter;
import com.jde.skillbill.domaine.entites.Utilisateur;

import java.time.DateTimeException;
import java.time.LocalDate;

public interface IContratVPAjouterFacture {

    interface IPresenteurAjouterFacture{

        ArrayAdapter<Utilisateur> presenterListeUtilsateur();

        void ajouterFacture();
    }

    interface IVueAjouterFacture{

        void setPresenteur(IPresenteurAjouterFacture presenteurAjouterFacture);

        LocalDate getDateFactureInput() throws NullPointerException, DateTimeException;

        double getMontantFactureInput();


        void afficherMessageErreurAlertDialog(String message, String titre);

        String getTitreInput();
    }
}
