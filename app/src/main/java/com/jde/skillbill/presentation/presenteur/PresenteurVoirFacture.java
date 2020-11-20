package com.jde.skillbill.presentation.presenteur;

import android.annotation.SuppressLint;
import com.jde.skillbill.domaine.entites.Facture;
import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.domaine.interacteurs.GestionFacture;
import com.jde.skillbill.domaine.interacteurs.GestionGroupes;
import com.jde.skillbill.domaine.interacteurs.GestionUtilisateur;
import com.jde.skillbill.domaine.interacteurs.ISourceDonnee;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionFacture;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionGroupes;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionUtilisateur;
import com.jde.skillbill.presentation.IContratVPVoirFacture;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueAjouterFacture;
import com.jde.skillbill.presentation.vue.VueVoirFacture;
import com.jde.skillbill.ui.activity.ActivityAjouterFacture;
import com.jde.skillbill.ui.activity.ActivityVoirFacture;

public class PresenteurVoirFacture extends PresenteurAjouterFacture implements IContratVPVoirFacture.PresenteurVoirFacture {
    ActivityVoirFacture activityVoirFacture;
    VueVoirFacture vueVoirFacture;
    Modele modele;
    ISourceDonnee sourceDonnee;
    IGestionFacture iGestionFacture;

    private static final String EXTRA_FACTURE = "com.jde.skillbill.facture_identifiant";

    public PresenteurVoirFacture(ActivityAjouterFacture activityAjouterFacture, VueAjouterFacture vueAjouterFacture, Modele modele, IGestionFacture gestionFacture, IGestionUtilisateur gestionUtilisateur, IGestionGroupes gestionGroupes){
        super(activityAjouterFacture, vueAjouterFacture, modele, gestionFacture, gestionUtilisateur, gestionGroupes);
        this.modele = super.modele;
        vueVoirFacture = (VueVoirFacture) vueAjouterFacture;
        this.modele.setFactureEnCours( (Facture) activityAjouterFacture.getIntent().getSerializableExtra(EXTRA_FACTURE));
        this.iGestionFacture = gestionFacture;


    }


    @Override
    public void modifierFactureEnCours() {
        iGestionFacture.modifierFacture(modele.getFactureEnCours());
    }

    @Override
    public String trouverMontantFactureEnCours() {

        return String.valueOf(modele.getFactureEnCours().getMontantTotal());
    }

    @Override
    public String trouverTitreFactureEnCours() {
        return modele.getFactureEnCours().getLibelle();
    }

    @Override
    public String trouverDateFactureEnCours() {
        return modele.getFactureEnCours().getDateFacture().toString();
    }

}

