package com.jde.skillbill.presentation.presenteur;

import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionFacture;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionGroupes;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionUtilisateur;
import com.jde.skillbill.presentation.IContratVuePresenteurVoirUnGroupe;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueVoirUnGroupe;
import com.jde.skillbill.ui.activity.ActivityVoirUnGroupe;

public class PresenteurVoirUnGroupe implements IContratVuePresenteurVoirUnGroupe.IPresenteurVoirUnGroupe {
    Modele modele;
    VueVoirUnGroupe vueVoirUnGroupe;
    ActivityVoirUnGroupe activityVoirUnGroupe;
    IGestionGroupes gestionGroupes;
    IGestionFacture gestionFacture;
    Groupe groupeEncours;
    Utilisateur utilisateurConnecte;
    private String EXTRA_ID_UTILISATEUR="com.jde.skillbill.utlisateur_identifiant";
    private String EXTRA_GROUPE_POSITION= "com.jde.skillbill.groupe_identifiant";

    public PresenteurVoirUnGroupe(Modele modele, VueVoirUnGroupe vueVoirUnGroupe, ActivityVoirUnGroupe activityVoirUnGroupe, IGestionGroupes gestionGroupes, IGestionFacture gestionFacture, IGestionUtilisateur iGestionUtilisateur) {
        this.modele = modele;
        this.vueVoirUnGroupe = vueVoirUnGroupe;
        this.activityVoirUnGroupe = activityVoirUnGroupe;
        this.gestionGroupes = gestionGroupes;
        this.gestionFacture = gestionFacture;
        modele.setUtilisateurConnecte(new Utilisateur("", activityVoirUnGroupe.getIntent().getStringExtra(EXTRA_ID_UTILISATEUR),null, Monnaie.CAD));
        modele.setGroupesAbonnesUtilisateurConnecte(iGestionUtilisateur.trouverGroupesAbonne(modele.getUtilisateurConnecte()));
        groupeEncours= modele.getListGroupeAbonneUtilisateurConnecte().get(activityVoirUnGroupe.getIntent().getIntExtra(EXTRA_GROUPE_POSITION,-1));
        groupeEncours.setUtilisateurs(gestionGroupes.trouverTousLesUtilisateurs(groupeEncours));
    }

    @Override
    public String getMembresGroupe() {
        if(groupeEncours.getUtilisateurs()==null || groupeEncours.getUtilisateurs().size()<1) return null;
        String noms="";
        for(Utilisateur utilisateur : groupeEncours.getUtilisateurs() ){
            if(!utilisateur.equals(utilisateurConnecte)){
                noms+=utilisateur.getNom();
                noms+=", ";
            }
        }
        noms=noms.substring(0, noms.length()-1)+".";
        return noms;
    }

    public boolean isGroupeSolo(){

        if( gestionGroupes.trouverTousLesUtilisateurs(groupeEncours)==null) return false;
        return  gestionGroupes.trouverTousLesUtilisateurs(groupeEncours).size()<=1;

    }


    @Override
    public boolean ajouterUtilisateurAuGroupe(Utilisateur utilisateur) {
        return false;
    }

    @Override
    public void commencerAjouterUnMembreAuGroupe() {

    }
}
