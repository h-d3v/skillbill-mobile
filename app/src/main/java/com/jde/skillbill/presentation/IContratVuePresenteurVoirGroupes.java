package com.jde.skillbill.presentation;

import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.presentation.presenteur.PresenteurVoirGroupes;

import java.util.List;


public interface IContratVuePresenteurVoirGroupes {
    interface IVueVoirGroupes{
        void setPresenteur(PresenteurVoirGroupes presenteurVoirGroupes);


    }

    interface IPresenteurVoirGroupe{
        List<Groupe> getGroupeAbonnes();
        void commencerCreerGroupeActivite();

        void commencerVoirUnGroupeActivite(int position);


        String getSoldeGroupe(int position);

        String getNomGroupe(int position);

        void commencerAjouterFactureActivite(int position);
    }
}
