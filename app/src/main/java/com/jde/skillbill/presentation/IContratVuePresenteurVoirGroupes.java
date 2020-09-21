package com.jde.skillbill.presentation;

import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.presentation.presenteur.PresenteurVoirGroupes;

import java.util.List;


public interface IContratVuePresenteurVoirGroupes {
    interface IVueVoirGroupes{
        void setPresenteur(PresenteurVoirGroupes presenteurVoirGroupes);

        int getPosition();
    }

    interface IPresenteurVoirGroupe{
        List<Groupe> getGroupeAbonnes();
        void commencerCreerGroupeActivite();

        void commencerVoirGroupeActivite(int position);


        String getNomGroupe(int position);
    }
}
