package com.jde.skillbill.domaine.interacteurs;

import com.jde.skillbill.domaine.entites.*;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionGroupes;

import java.util.List;
import java.util.Map;

public class GestionGroupes implements IGestionGroupes {
    /**
     * @param nom
     * @param utilisateurCreateur
     * @param monnaieAUtiliserDansLeGroupe
     * @return le groupe crée
     */
    @Override
    public Groupe creerGroupe(String nom, Utilisateur utilisateurCreateur, Monnaie monnaieAUtiliserDansLeGroupe) {

        return new Groupe(nom, utilisateurCreateur, monnaieAUtiliserDansLeGroupe);
    }

    /**
     * @param groupe
     * @param utilisateur
     * @return le membre ajouté, null si deja dans le groupe
     */
    @Override
    public Utilisateur ajouterMembre(Groupe groupe, Utilisateur utilisateur) {

        if(groupe.getUtilisateurs().contains(utilisateur)){
            return null;
        }else return groupe.getUtilisateurs().get(-1);
    }

    /**
     * @param groupe
     * @param utilisateur
     * @return boolean true si supprime, false sinon
     */
    @Override
    public boolean supprimerMembre(Groupe groupe, Utilisateur utilisateur) {

        return groupe.getUtilisateurs().remove(utilisateur);
    }

    /**
     * @param groupe
     * @param facture
     * @return la facture ajoutéee
     */
    @Override
    public Facture ajouterFacture(Groupe groupe, Facture facture) {
        groupe.getFactures().add(facture);
        return groupe.getFactures().get(-1);
    }

    /**
     * @param groupe
     * @return toutes les factures du groupe
     */
    @Override
    public List<Facture> trouverToutesLesFactures(Groupe groupe) {
        return groupe.getFactures();
    }

    /**
     * @param groupe
     * @param facture
     * @return la facture touvée, null sinon
     */
    @Override
    public Facture trouverUneFacture(Groupe groupe, Facture facture) {
        int index =-1;
        if(index>-1) {
            return groupe.getFactures().get(index);
        }
        else return null;
    }

    /**
     * @param groupe
     * @return tous les utilisateurs du groupe
     */
    @Override
    public List<Utilisateur> trouverTousLesUtilisateurs(Groupe groupe) {
        return groupe.getUtilisateurs();
    }

    /**
     * @param groupe
     * @param utilisateur
     * @return le solde d'un utilisateur au sein du groupe
     */
    @Override
    public Double soldeUtilisateur(Groupe groupe, Utilisateur utilisateur) {
        return groupe.getSoldeParUtilisateur().get(utilisateur);
    }

    /**
     * @param groupe
     * @param unNom
     * @return le groupe modifié
     */
    @Override
    public Groupe modifierNomGroupe(Groupe groupe, String unNom) {
        groupe.setNomGroupe(unNom);
        return groupe;
    }

    /**
     * @param groupe
     * @param paiement
     */
    @Override
    public void payerUnUtilisateurDansSonGroupe(Groupe groupe, Paiement paiement) {
        Map<Utilisateur, Double> utilisateurMap = groupe.getSoldeParUtilisateur();
        Utilisateur utilisateurPayeur = paiement.getUtilisateurPayeur();
        Utilisateur utilisateurPaye = paiement.getUtilisateurPaye();
        if(utilisateurMap.containsKey(utilisateurPayeur)
            && utilisateurMap.containsKey(utilisateurPaye)) {

            double soldePayeur=utilisateurMap.get(paiement.getUtilisateurPayeur());
            double soldePaye=utilisateurMap.get(paiement.getUtilisateurPaye());
            utilisateurMap.put(utilisateurPayeur,soldePayeur+paiement.getMontant());
            utilisateurMap.put(utilisateurPaye, soldePaye- paiement.getMontant());
        }



    }
}
