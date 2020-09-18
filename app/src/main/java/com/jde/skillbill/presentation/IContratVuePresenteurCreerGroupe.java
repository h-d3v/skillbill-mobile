package com.jde.skillbill.presentation;

import com.jde.skillbill.domaine.entites.Utilisateur;

import java.util.List;

public interface IContratVuePresenteurCreerGroupe {
    interface VueCreerGroupe{
        String getNomGroupe();
        List<Utilisateur> getUtilisateursAjoutesAuGroupe();

    }

    interface PresenteurCreerGroupe {
       void creerGroupe();
       List<Utilisateur> getUtilisateursAmis();
    }
}
