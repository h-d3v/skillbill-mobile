package com.jde.skillbill.presentation;

import android.widget.SpinnerAdapter;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.presentation.presenteur.PresenteurAjouterFacture;

import java.util.HashMap;
import java.util.Set;

public interface IContratVPAjouterFacture {

    interface IPresenteurAjouterFacture{

        void montrerListeUtilisateurMontant();
    }

    interface IVueAjouterFacture{

        void setPresenteur(IPresenteurAjouterFacture presenteurAjouterFacture);
        double getMontantFactureInput();



    }
}
