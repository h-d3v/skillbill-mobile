package com.jde.skillbill.presentation.presenteur;

import android.app.Activity;
import android.content.Intent;

import com.jde.skillbill.BuildConfig;
import com.jde.skillbill.R;
import com.jde.skillbill.domaine.entites.Facture;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.GestionUtilisateur;
import com.jde.skillbill.domaine.interacteurs.ISourceDonnee;
import com.jde.skillbill.donnees.mockDAO.SourceDonneesMock;
import com.jde.skillbill.presentation.IContratVuePresenteurVoirGroupes;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueVoirGroupes;
import com.jde.skillbill.ui.activity.ActivityAjouterFacture;
import com.jde.skillbill.ui.activity.ActivityCreerGroupe;
import com.jde.skillbill.ui.activity.ActivityVoirUnGroupe;

import java.util.List;
import java.util.Set;

public class PresenteurVoirGroupes implements IContratVuePresenteurVoirGroupes.IPresenteurVoirGroupe {
    private Modele modele;
    private VueVoirGroupes vueVoirGroupes;
    private Activity activity;
    private String EXTRA_ID_UTILISATEUR="com.jde.skillbill.utlisateur_identifiant";
    private String EXTRA_GROUPE_POSITION= "com.jde.skillbill.groupe_identifiant";
    private ISourceDonnee iSourceDonnee;

    public PresenteurVoirGroupes(Modele modele, VueVoirGroupes vueVoirGroupes, Activity activity, ISourceDonnee iSourceDonnee) {
        this.modele = modele;
        this.vueVoirGroupes = vueVoirGroupes;
        this.activity=activity;
        modele.setUtilisateurConnecte(new Utilisateur("", activity.getIntent().getStringExtra(EXTRA_ID_UTILISATEUR),null, Monnaie.CAD));
        this.iSourceDonnee=iSourceDonnee;

    }

    @Override
    public List<Groupe> getGroupeAbonnes() {
        modele.setGroupesAbonnesUtilisateurConnecte( new GestionUtilisateur(new SourceDonneesMock()).trouverGroupesAbonne(modele.getUtilisateurConnecte()));
        return modele.getListGroupeAbonneUtilisateurConnecte();
    }

    @Override
    public void commencerVoirGroupeActivite(int position) {
        Intent intent = new Intent(activity, ActivityVoirUnGroupe.class);
        activity.startActivity(intent);
    }
    @Override
    public String  getSoldeGroupe(int position) {
        if (BuildConfig.DEBUG && position < 0) {
            throw new AssertionError("Assertion failed");
        }

        List<Facture> factures= iSourceDonnee.lireFacturesParGroupe( modele.getListGroupeAbonneUtilisateurConnecte().get(position));
        if (factures == null || factures.size() == 0) {
            return activity.getResources().getString(R.string.pas_de_facture_dans_le_groupe);
        }
        Double solde=0.0;

        for (Facture facture : factures){
            for(Utilisateur utilisateur : facture.getMontantPayeParParUtilisateur().keySet()){
                if(utilisateur.equals(modele.getUtilisateurConnecte())){
                    solde+=facture.getMontantPayeParParUtilisateur().get(utilisateur);
                }else{
                    solde-=facture.getMontantPayeParParUtilisateur().get(utilisateur);
                }
            }



        }
        if(solde==0){
            return activity.getResources().getString(R.string.solde_utilisateur_nul) ;
        }else if(solde<0){
            return activity.getResources().getString(R.string.solde_utilisateur_debiteur)+" "+ Math.abs(solde);
        }else{
            return activity.getResources().getString(R.string.solde_utilisateur_crediteur)+" "+ solde;
        }

    }




    @Override
    public String getNomGroupe(int position) {
        if (BuildConfig.DEBUG && position < 0) {
            throw new AssertionError("Assertion failed");
        }
        return modele.getListGroupeAbonneUtilisateurConnecte().get(position).getNomGroupe();
    }

    @Override
    public void commencerAjouterFactureActivite(int position) {
        Intent intent = new Intent(activity, ActivityAjouterFacture.class);
        intent.putExtra(EXTRA_GROUPE_POSITION, position);
        intent.putExtra(EXTRA_ID_UTILISATEUR, modele.getUtilisateurConnecte().getCourriel());
        activity.startActivity(intent);
    }

    @Override
    public void commencerCreerGroupeActivite() {
        Intent intent = new Intent(activity, ActivityCreerGroupe.class);
        intent.putExtra(EXTRA_ID_UTILISATEUR,modele.getUtilisateurConnecte().getCourriel());
        activity.startActivity(intent);
    }
}
