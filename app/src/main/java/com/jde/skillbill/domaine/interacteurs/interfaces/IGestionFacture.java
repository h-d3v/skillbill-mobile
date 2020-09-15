package com.jde.skillbill.domaine.interacteurs.interfaces;

import com.jde.skillbill.domaine.entites.Facture;
import com.jde.skillbill.domaine.entites.Paiement;
import com.jde.skillbill.domaine.entites.Utilisateur;

import java.util.Map;

public interface IGestionFacture {
    Facture creerFactureAvecMultiplePayeurs(double montantTotal, Map<Utilisateur, Double> mantantPayeParUtilisateur);
    Facture creerFacture(double montantTotal, Utilisateur utilisateurPayeur);
    Facture ajouterPaiement(Facture facture, Paiement paiement);

}
