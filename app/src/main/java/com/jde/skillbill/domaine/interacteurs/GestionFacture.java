package com.jde.skillbill.domaine.interacteurs;

import android.graphics.Bitmap;
import com.jde.skillbill.domaine.entites.Facture;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Paiement;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.ISourceDonnee;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionFacture;


import java.time.LocalDate;
import java.util.Map;

public class GestionFacture implements IGestionFacture {

    private ISourceDonnee iSourceDonnee;

    public GestionFacture(ISourceDonnee iSourceDonnee) {
        this.iSourceDonnee = iSourceDonnee;
    }

    @Override
    public boolean creerFactureAvecMultiplePayeurs(double montantTotal, Map<Utilisateur, Double> mantantPayeParUtilisateur, LocalDate localDate) {
        return false;
    }

    @Override
    public boolean creerFacture(double montantTotal, Utilisateur utilisateurPayeur, LocalDate localDate, Groupe groupe, String titre) {
        return iSourceDonnee.ajouterFacture(montantTotal, utilisateurPayeur, localDate, groupe , titre);
    }

    @Override
    public Facture ajouterPaiement(Facture facture, Paiement paiement) {
        return null;
    }

    @Override
    public Facture ajouterPhotoFacture(Facture facture, String uri, Bitmap bitmap) {
        return null;
    }

    @Override
    public Facture modifierPhotoFacture(Facture facture, String uri, Bitmap bitmap) {
        return null;
    }
}
