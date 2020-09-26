package com.jde.skillbill.presentation.presenteur;

import android.app.Activity;
import android.content.Intent;
import com.jde.skillbill.BuildConfig;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.interacteurs.GestionUtilisateur;
import com.jde.skillbill.donnees.mockDAO.SourceDonneesMock;
import com.jde.skillbill.presentation.IContratVuePresenteurVoirGroupes;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueVoirGroupes;
import com.jde.skillbill.ui.ActivityCreerGroupe;
import com.jde.skillbill.ui.ActivityVoirUnGroupe;

import java.util.List;

import static com.jde.skillbill.donnees.mockDAO.SourceDonneesMock.utilisateurFake;

public class PresenteurVoirGroupes implements IContratVuePresenteurVoirGroupes.IPresenteurVoirGroupe {
    private Modele modele;
    private VueVoirGroupes vueVoirGroupes;
    private Activity activity;

    public PresenteurVoirGroupes(Modele modele, VueVoirGroupes vueVoirGroupes, Activity activity) {
        this.modele = modele;
        this.vueVoirGroupes = vueVoirGroupes;
        this.activity=activity;
        modele.setUtilisateurConnecte(utilisateurFake);//TODO OR NOT TODO
    }

    @Override
    public List<Groupe> getGroupeAbonnes() {

        modele.setGroupesAbonnesUtilisateurConnecte( new GestionUtilisateur(new SourceDonneesMock()).trouverGroupesAbonne(utilisateurFake));
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
