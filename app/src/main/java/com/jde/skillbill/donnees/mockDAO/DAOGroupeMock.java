package com.jde.skillbill.donnees.mockDAO;

import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.presentation.modele.DAO;

public class DAOGroupeMock implements DAO<Groupe> {
    private int id;
    private Groupe groupe;
    public DAOGroupeMock(int id, Groupe groupe){
        this.id=id;
        this.groupe=groupe;
    }


    @Override
    public Groupe lire() {
        return groupe;
    }

    @Override
    public boolean modifier(DAO<Groupe> t) {
        return false;
    }

    @Override
    public boolean supprimer(DAO<Groupe> t) {
        return false;
    }

    @Override
    public DAO<Groupe> creer(Groupe groupe) {

        return null;
    }
}
