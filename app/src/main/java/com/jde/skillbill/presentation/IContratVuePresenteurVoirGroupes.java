package com.jde.skillbill.presentation;

import com.jde.skillbill.domaine.entites.Groupe;

import java.util.List;


public interface IContratVuePresenteurVoirGroupes {
    interface IVueVoirGroupes{
       int getPosition();
    }

    interface IPresenteurVoirGroupe{
        List<Groupe> getGroupeAbonnes();
        void commencerVoirGroupeActivite();
        void commencerCreerGroupeActivite();

    }
}
