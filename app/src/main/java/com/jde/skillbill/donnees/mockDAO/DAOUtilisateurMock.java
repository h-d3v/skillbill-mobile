package com.jde.skillbill.donnees.mockDAO;

import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.presentation.modele.DAO;

import java.io.UTFDataFormatException;

public class DAOUtilisateurMock implements DAO<Utilisateur> {

    int id;
    Utilisateur utilisateur;

    public DAOUtilisateurMock(int id, Utilisateur utilisateur){
        this.id=id;        this.utilisateur=utilisateur;
    }

    @Override
    public Utilisateur lire() {
        return utilisateur;
    }

    @Override
    public boolean modifier(DAO<Utilisateur> t) {

        return false;
    }

    @Override
    public boolean supprimer(DAO<Utilisateur> t) {
        return false;
    }

    @Override
    public DAO<Utilisateur> creer(Utilisateur utilisateur) {
        return null;
    }
}
