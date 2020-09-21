package com.jde.skillbill.presentation;

import com.jde.skillbill.domaine.entites.Utilisateur;

import java.util.List;

public interface IContratVuePresenteurCreerGroupe {
    interface VueCreerGroupe{
        void setPresenteurCreerGroupe(PresenteurCreerGroupe presenteurCreerGroupe);

        String getNomGroupe();

    }

    interface PresenteurCreerGroupe {
       void creerGroupe();

        void retournerEnArriere();
    }
}
