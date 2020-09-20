package com.jde.skillbill.presentation.presenteur;

import android.app.Activity;
import android.content.Intent;
import com.jde.skillbill.BuildConfig;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.presentation.IContratVuePresenteurVoirGroupes;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueVoirGroupes;
import com.jde.skillbill.ui.ActivityCreerGroupe;

import java.util.List;

import static com.jde.skillbill.donnees.mockDAO.DAOFactoryUtilisateurGroupeMock.utilisateurGroupeHashMap;

public class PresenteurVoirGroupes implements IContratVuePresenteurVoirGroupes.IPresenteurVoirGroupe {
    private Modele modele;
    private VueVoirGroupes vueVoirGroupes;
    private Activity activity;

    public PresenteurVoirGroupes(Modele modele, VueVoirGroupes vueVoirGroupes, Activity activity) {
        this.modele = modele;
        this.vueVoirGroupes = vueVoirGroupes;
        this.activity=activity;
        modele.setUtilisateurConnecte(utilisateurGroupeHashMap.keySet().iterator().next());//TODO OR NOT TODO
    }

    @Override
    public List<Groupe> getGroupeAbonnes() {
        return modele.getListGroupeAbonneUtilisateurConnecte();
    }

    @Override
    public void commencerVoirGroupeActivite(int position) {

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
