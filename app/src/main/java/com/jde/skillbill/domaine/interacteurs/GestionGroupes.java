package com.jde.skillbill.domaine.interacteurs;

import android.util.Log;
import com.jde.skillbill.domaine.entites.*;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionGroupes;
import com.jde.skillbill.domaine.interacteurs.interfaces.SourceDonneeException;

import java.util.List;

public class GestionGroupes implements IGestionGroupes {


    ISourceDonnee sourceDonnee;
    public GestionGroupes(ISourceDonnee iSourceDonnee){
        this.sourceDonnee=iSourceDonnee;
    }

    /**
     * @param nom
     * @param utilisateurCreateur
     * @param monnaieAUtiliserDansLeGroupe
     * @return le groupe crée
     */
    @Override
    public Groupe creerGroupe(String nom, Utilisateur utilisateurCreateur, Monnaie monnaieAUtiliserDansLeGroupe) throws SourceDonneeException {
        Groupe groupe= new Groupe(nom, utilisateurCreateur, monnaieAUtiliserDansLeGroupe);
        Groupe groupeCree =  sourceDonnee.creerGroupeParUtilisateur(utilisateurCreateur,groupe);
         return groupeCree;
    }

    /**
     * @param groupe
     * @param utilisateur
     * @return le membre ajouté, null si deja dans le groupe
     */
    @Override
    public boolean ajouterMembre(Groupe groupe, Utilisateur utilisateur) throws SourceDonneeException {

        return sourceDonnee.ajouterMembre(groupe, utilisateur);
    }


    /**
     * @param groupe
     * @param facture
     * @return la facture ajoutéee
     */
    @Override
    public Facture ajouterFacture(Groupe groupe, Facture facture) {
        return null;
    }

    /**
     * @param groupe
     * @return toutes les factures du groupe
     */
    @Override
    public List<Facture> trouverToutesLesFactures(Groupe groupe) throws SourceDonneeException {

        return sourceDonnee.lireFacturesParGroupe(groupe);
    }



    /**
     * @param groupe
     * @return tous les utilisateurs du groupe
     */
    @Override
    public List<Utilisateur> trouverTousLesUtilisateurs(Groupe groupe) throws SourceDonneeException {
        return sourceDonnee.lireUTilisateurParGroupe(groupe);
    }


    /**
     *
     * @param utilisateurConcerne un utilisateur du groupe
     * @param groupe un groupe
     * @return string le solde de l'utilisateur du total payé ou à payer pour toutes les factures de son groupe
     */

    @Override
    public String getSoldeParUtilisateurEtGroupe(Utilisateur utilisateurConcerne, Groupe groupe) throws SourceDonneeException {

        List<Facture> factures = sourceDonnee.lireFacturesParGroupe(groupe);
        if (factures == null || factures.size() == 0) {
            throw new NullPointerException("Pas de factures"); //TODO exception personalisée et NON nullPointer
        }
        double montantPayeUtilisateurConcerne = 0;
        double total = 0;
        double solde = 0;

        int nbrUtilisateurSurLaFacture = 0;
        for (Facture facture : factures) {

            nbrUtilisateurSurLaFacture = 0;
            for (Utilisateur utilisateur : facture.getMontantPayeParParUtilisateur().keySet()) {
                total += facture.getMontantPayeParParUtilisateur().get(utilisateur);
                nbrUtilisateurSurLaFacture++;

            }
            montantPayeUtilisateurConcerne += facture.getMontantPayeParParUtilisateur().get(utilisateurConcerne);

            double montantDuParUtilisateur = total / nbrUtilisateurSurLaFacture;
            solde = (montantPayeUtilisateurConcerne - montantDuParUtilisateur);

        }
        String retour = solde +"--"+groupe.getMonnaieDuGroupe().name();



        return retour;
    }
}
