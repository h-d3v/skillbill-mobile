package com.jde.skillbill.presentation;

import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.domaine.entites.Utilisateur;

import java.util.List;

public interface IContratVuePresenteurCreerGroupe {
    interface VueCreerGroupe{
        void setPresenteurCreerGroupe(PresenteurCreerGroupe presenteurCreerGroupe);

        String getNomGroupe();

        void fermerProgressBar();

        void ouvrirProgressBar();

        Monnaie getMonnaieChoisie();
    }

    interface PresenteurCreerGroupe {
        void creerGroupe();

        void redirigerVersGroupeCreer();

        void retournerEnArriere();

    }
}
