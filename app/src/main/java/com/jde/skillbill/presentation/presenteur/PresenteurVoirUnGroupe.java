package com.jde.skillbill.presentation.presenteur;

import android.content.Intent;
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
    public static final int ERREUR_ACCES =0;
    public static final int AJOUT_OK=1;
    public static final int EMAIL_INCONNU=2;
    Modele modele;
    VueVoirUnGroupe vueVoirUnGroupe;
    ActivityVoirUnGroupe activityVoirUnGroupe;
    IGestionGroupes gestionGroupes;
    IGestionFacture gestionFacture;
    IGestionUtilisateur gestionUtilisateur;
    Groupe groupeEncours;
    private String EXTRA_ID_UTILISATEUR="com.jde.skillbill.utlisateur_identifiant";
    private String EXTRA_GROUPE_POSITION= "com.jde.skillbill.groupe_identifiant";

    public PresenteurVoirUnGroupe(Modele modele, VueVoirUnGroupe vueVoirUnGroupe, ActivityVoirUnGroupe activityVoirUnGroupe, IGestionGroupes gestionGroupes, IGestionFacture gestionFacture, IGestionUtilisateur gestionUtilisateur) {
        this.modele = modele;
        this.vueVoirUnGroupe = vueVoirUnGroupe;
        this.activityVoirUnGroupe = activityVoirUnGroupe;
        this.gestionGroupes = gestionGroupes;
        this.gestionFacture = gestionFacture;
        this.gestionUtilisateur = gestionUtilisateur;
        modele.setUtilisateurConnecte(new Utilisateur("", activityVoirUnGroupe.getIntent().getStringExtra(EXTRA_ID_UTILISATEUR),null, Monnaie.CAD));
        modele.setGroupesAbonnesUtilisateurConnecte(gestionUtilisateur.trouverGroupesAbonne(modele.getUtilisateurConnecte()));
        groupeEncours= modele.getListGroupeAbonneUtilisateurConnecte().get(activityVoirUnGroupe.getIntent().getIntExtra(EXTRA_GROUPE_POSITION,-1));
        groupeEncours.setUtilisateurs(gestionGroupes.trouverTousLesUtilisateurs(groupeEncours));
    }

    @Override
    public String getMembresGroupe() {
        if(groupeEncours.getUtilisateurs()==null || groupeEncours.getUtilisateurs().size()<1) return null;
        String noms="";
        for(Utilisateur utilisateur : groupeEncours.getUtilisateurs() ){
            if(!utilisateur.equals(modele.getUtilisateurConnecte())){
                noms+=utilisateur.getNom();
                noms+=", ";
            }
        }
        noms=noms.substring(0, noms.length()-2)+".";
        return noms;
    }

    public boolean isGroupeSolo(){

        if( gestionGroupes.trouverTousLesUtilisateurs(groupeEncours)==null) return false;
        return  gestionGroupes.trouverTousLesUtilisateurs(groupeEncours).size()<=1;
    }


    @Override
    public int ajouterUtilisateurAuGroupe(String courriel) {
        if(gestionUtilisateur.utilisateurExiste(courriel)){
            if(gestionGroupes.ajouterMembre(groupeEncours, new Utilisateur("","",courriel, ""))) {
                return AJOUT_OK;
            } else return ERREUR_ACCES;
        } else return EMAIL_INCONNU ;

    }

    @Override
    public void envoyerCourriel(String courriel) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{ courriel});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Invitation SkillBill");
        intent.putExtra(Intent.EXTRA_TEXT, "Telecharge SkillBill, mais bon on n'est pas sur le PlayStore, un APK ?");

        activityVoirUnGroupe.startActivity(Intent.createChooser(intent, "Invitation SkillBill"));
       }

}
