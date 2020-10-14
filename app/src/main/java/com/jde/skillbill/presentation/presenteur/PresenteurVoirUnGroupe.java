package com.jde.skillbill.presentation.presenteur;

import android.app.Activity;

import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.ISourceDonnee;
import com.jde.skillbill.presentation.IContratVPVoirUnGroupe;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueVoirGroupes;
import com.jde.skillbill.presentation.vue.VueVoirUnGroupe;

public class PresenteurVoirUnGroupe implements IContratVPVoirUnGroupe.IPresenteurVoirUnGroupe {
    private Modele _modele;
    private VueVoirUnGroupe _vue;
    private Activity _activity;
    private String EXTRA_ID_UTILISATEUR="com.jde.skillbill.utlisateur_identifiant";
    private String EXTRA_GROUPE_POSITION= "com.jde.skillbill.groupe_identifiant";
    private ISourceDonnee iSourceDonnee;

    public PresenteurVoirUnGroupe(Modele modele, VueVoirUnGroupe vueVoirUnGroupe, Activity activity, ISourceDonnee iSourceDonnee) {
        _modele = modele;
        _vue= vueVoirUnGroupe;
        _activity=activity;
        modele.setUtilisateurConnecte(new Utilisateur("", activity.getIntent().getStringExtra(EXTRA_ID_UTILISATEUR),null, Monnaie.CAD));
        this.iSourceDonnee=iSourceDonnee;
    }
    @Override
    public int getMontantFacture() {
        return 0;
    }

    @Override
    public String getNomFacture() {
        return null;
    }
}
