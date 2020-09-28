package com.jde.skillbill.domaine.interacteurs.interfaces;
import com.jde.skillbill.domaine.entites.Paiement;
import com.jde.skillbill.domaine.entites.Utilisateur;

import java.util.Date;

public interface IGestionPaiement {

    Paiement creerPaiement(double montant, Utilisateur utilisateurPayeur, Utilisateur utilisateurPaye);
    Paiement creerPaiement(double montant, Utilisateur utilisateurPayeur, Utilisateur utilisateurPaye, Date date);
    Paiement modifierMontantPaiement(Paiement paiement,double montant);



}
