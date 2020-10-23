package com.jde.skillbill.domaine.interacteurs;

import com.jde.skillbill.domaine.entites.*;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionGroupes;

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
    public Groupe creerGroupe(String nom, Utilisateur utilisateurCreateur, Monnaie monnaieAUtiliserDansLeGroupe) {
        Groupe groupe= new Groupe(nom, utilisateurCreateur, monnaieAUtiliserDansLeGroupe);

        if(sourceDonnee.creerGroupeParUtilisateur(utilisateurCreateur,groupe)){
            return new Groupe(nom, utilisateurCreateur, monnaieAUtiliserDansLeGroupe);
        }else return null;
    }

    /**
     * @param groupe
     * @param utilisateur
     * @return le membre ajouté, null si deja dans le groupe
     */
    @Override
    public boolean ajouterMembre(Groupe groupe, Utilisateur utilisateur) {

      return sourceDonnee.ajouterMembre(groupe, utilisateur);
    }

    /**
     * @param groupe
     * @param utilisateur
     * @return boolean true si supprime, false sinon
     */
    @Override
    public boolean supprimerMembre(Groupe groupe, Utilisateur utilisateur) {

        return false;
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
    public List<Facture> trouverToutesLesFactures(Groupe groupe) {

        return sourceDonnee.lireFacturesParGroupe(groupe);
    }

    /**
     * @param groupe
     * @param facture
     * @return la facture touvée, null sinon
     */
    @Override
    public Facture trouverUneFacture(Groupe groupe, Facture facture) {
       return null;
    }

    /**
     * @param groupe
     * @return tous les utilisateurs du groupe
     */
    @Override
    public List<Utilisateur> trouverTousLesUtilisateurs(Groupe groupe) {
        return sourceDonnee.lireUTilisateurParGroupe(groupe);
    }

    /**
     * @param groupe
     * @param utilisateur
     * @return le solde d'un utilisateur au sein du groupe
     */
    @Override
    public Double soldeUtilisateur(Groupe groupe, Utilisateur utilisateur) {
        return null;
    }

    /**
     * @param groupe
     * @param unNom
     * @return le groupe modifié
     */
    @Override
    public Groupe modifierNomGroupe(Groupe groupe, String unNom) {

        return null;
    }

    /**
     * @param groupe
     * @param paiement
     */
    @Override
    public void payerUnUtilisateurDansSonGroupe(Groupe groupe, Paiement paiement) {
       /** Map<Utilisateur, Double> utilisateurMap = groupe.getSoldeParUtilisateur();
        Utilisateur utilisateurPayeur = paiement.getUtilisateurPayeur();
        Utilisateur utilisateurPaye = paiement.getUtilisateurPaye();
        if(utilisateurMap.containsKey(utilisateurPayeur)
            && utilisateurMap.containsKey(utilisateurPaye)) {

            double soldePayeur=utilisateurMap.get(paiement.getUtilisateurPayeur());
            double soldePaye=utilisateurMap.get(paiement.getUtilisateurPaye());
            utilisateurMap.put(utilisateurPayeur,soldePayeur+paiement.getMontant());
            utilisateurMap.put(utilisateurPaye, soldePaye- paiement.getMontant());
        }**/



    }

    @Override
    public double getSoldeParUtilisateurEtGroupe(Utilisateur utilisateurConcerne, Groupe groupe) {

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
                if (utilisateur.equals(utilisateurConcerne)) {
                    montantPayeUtilisateurConcerne += facture.getMontantPayeParParUtilisateur().get(utilisateur);
                }
            }
            double montantDuParUtilisateur = total / nbrUtilisateurSurLaFacture;
            solde = (montantPayeUtilisateurConcerne - montantDuParUtilisateur);
        }
        return solde;
    }
}
