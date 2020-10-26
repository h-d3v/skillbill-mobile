package com.jde.skillbill.presentation.presenteur;

import android.content.Intent;
import android.util.Log;

import com.jde.skillbill.R;
import com.jde.skillbill.domaine.entites.Facture;
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

import java.util.List;

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


    /**
     *
     * @param modele
     * @param vueVoirUnGroupe
     * @param activityVoirUnGroupe
     * @param gestionGroupes
     * @param gestionFacture
     * @param gestionUtilisateur
     */
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


    /**
     *
     * @return noms des membres du groupe
     */
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

    /**
     *
     * @return true si le groupe ne contient qu'un seul utilisateur
     */
    public boolean isGroupeSolo(){
        if( gestionGroupes.trouverTousLesUtilisateurs(groupeEncours)==null) return false;
        return  gestionGroupes.trouverTousLesUtilisateurs(groupeEncours).size()<=1;
    }


    /**
     *
     * @param courriel de l'utilisateur a ajouter
     * @return int du message, pour le handler
     */
    @Override
    public int ajouterUtilisateurAuGroupe(String courriel) {
        if(gestionUtilisateur.utilisateurExiste(courriel)){
            if(gestionGroupes.ajouterMembre(groupeEncours, new Utilisateur("",courriel, "", Monnaie.CAD))) {
                return AJOUT_OK;
            } else return ERREUR_ACCES;
        } else return EMAIL_INCONNU ;
    }

    /**
     *
     * @param courriel courriel auquel on veut envoyer une invitation
     */
    @Override
    public void envoyerCourriel(String courriel) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{ courriel});
        intent.putExtra(Intent.EXTRA_SUBJECT, activityVoirUnGroupe.getResources().getString(R.string.invitation_objet_courriel));
        intent.putExtra(Intent.EXTRA_TEXT,activityVoirUnGroupe.getResources().getString(R.string.texte_courriel) );

        activityVoirUnGroupe.startActivity(Intent.createChooser(intent, activityVoirUnGroupe.getResources().getString(R.string.invitation_objet_courriel)));
       }

    /**
     *
     * @return factures du groupes
     */
    public List<Facture> getFacturesGroupe(){

        //return groupeEncours.getFactures();

        return gestionGroupes.trouverToutesLesFactures(modele.getListGroupeAbonneUtilisateurConnecte().get(activityVoirUnGroupe.getIntent().getIntExtra(EXTRA_GROUPE_POSITION, -1)));

    }

    /**
     *
     * @param posFacture dans le rv
     * @return montant qu'a payer l'utilisateur dans la facture
     */
    //TODO faire en sorte que ce montant reflete ce que l'utilisateur dois payer et non ce qu'il a deja payer
    //@Override
    public double getMontantFacturePayerParUser(int posFacture) {
        return this.getFacturesGroupe().get(posFacture).getMontantPayeParParUtilisateur().get(modele.getUtilisateurConnecte());
    }


    /**
     *
     * @param position de la facture dans le rv
     */
    //Todo supprimer suelement par l'utilisateur qui l'a creer
    //Todo supprimer dans la bd ou le service
    //@Override
    public void requeteSupprimerFacture(int position) {
        vueVoirUnGroupe.rafraichir();
        this.getFacturesGroupe().remove(position);
    }


    /**
     *
     * @return nom du groupe
     */
    public String getNomGroupe(){
        return groupeEncours.getNomGroupe();
    }


}
