package com.jde.skillbill.presentation;

import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.presentation.presenteur.PresenteurVoirGroupes;

import java.util.List;


public interface IContratVuePresenteurVoirGroupes {
    interface IVueVoirGroupes{
        void setPresenteur(PresenteurVoirGroupes presenteurVoirGroupes);

        void setNomUserDrawer(String nomUser);
        void rafraichir();

        void ouvrirProgressBar();

        void fermerProgressBar();
    }

    interface IPresenteurVoirGroupe{
        void chargerGroupes();

        List<Groupe> getGroupeAbonnes();
        void commencerCreerGroupeActivite();

        void commencerVoirUnGroupeActivite(int position);


        String getMessageSoldeParPosition(int position);

        String getNomGroupe(int position);

        void commencerAjouterFactureActivite(int position);

        void redirigerModifCompte();

        void commencerPrendrePhotoFacture(int position);

        String getNomUserConnecte();

        void deconnexion();
    }
}
