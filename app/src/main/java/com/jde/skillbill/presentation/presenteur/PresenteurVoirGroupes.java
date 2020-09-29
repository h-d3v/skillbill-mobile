package com.jde.skillbill.presentation.presenteur;

import android.app.Activity;
import android.content.Intent;

import com.jde.skillbill.BuildConfig;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.GestionUtilisateur;
import com.jde.skillbill.donnees.mockDAO.SourceDonneesMock;
import com.jde.skillbill.presentation.IContratVuePresenteurVoirGroupes;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueVoirGroupes;
import com.jde.skillbill.ui.activity.ActivityCreerGroupe;
import com.jde.skillbill.ui.activity.ActivityVoirUnGroupe;

import java.util.List;

public class PresenteurVoirGroupes implements IContratVuePresenteurVoirGroupes.IPresenteurVoirGroupe {
    private Modele modele;
    private VueVoirGroupes vueVoirGroupes;
    private Activity activity;

    public PresenteurVoirGroupes(Modele modele, VueVoirGroupes vueVoirGroupes, Activity activity) {
        this.modele = modele;
        this.vueVoirGroupes = vueVoirGroupes;
        this.activity=activity;
        modele.setUtilisateurConnecte(new Utilisateur("Julien J","jj@jde.com","julien123"));//TODO mettre l'utilisateur qui sera connecter
    }

    @Override
    public List<Groupe> getGroupeAbonnes() {
        //TODO avec le vrai utilisateur connecter.
        modele.setGroupesAbonnesUtilisateurConnecte( new GestionUtilisateur(new SourceDonneesMock()).trouverGroupesAbonne(new Utilisateur("Julien J","jj@jde.com","julien123")));
        return modele.getListGroupeAbonneUtilisateurConnecte();
    }

    @Override
    public void commencerVoirGroupeActivite(int position) {
        Intent intent = new Intent(activity, ActivityVoirUnGroupe.class);
        activity.startActivity(intent);
    }



    @Override
    public String getNomGroupe(int position) {
        if (BuildConfig.DEBUG && position < 0) {
            throw new AssertionError("Assertion failed");
        }
        return modele.getListGroupeAbonneUtilisateurConnecte().get(position).getNomGroupe();
    }

    @Override
    public void commencerCreerGroupeActivite() {
        Intent intent = new Intent(activity, ActivityCreerGroupe.class);
        activity.startActivity(intent);
    }
}
