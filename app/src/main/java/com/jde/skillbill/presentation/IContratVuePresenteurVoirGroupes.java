package com.jde.skillbill.presentation;

import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.presentation.presenteur.PresenteurVoirGroupes;

import java.util.List;


public interface IContratVuePresenteurVoirGroupes {
    interface IVueVoirGroupes{
        void setPresenteur(PresenteurVoirGroupes presenteurVoirGroupes);


        void rafraichir();
    }

    interface IPresenteurVoirGroupe{
        void chargerGroupes();

        List<Groupe> getGroupeAbonnes();
        void commencerCreerGroupeActivite();

        void commencerVoirUnGroupeActivite(int position);


        String getMessageSoldeParPosition(int position);

        String getNomGroupe(int position);

        void commencerAjouterFactureActivite(int position);

        void commencerPrendrePhotoFacture(int position);
    }
}
