package com.jde.skillbill.domaine.interacteurs;

import android.graphics.Bitmap;
import com.jde.skillbill.domaine.entites.Facture;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionFacture;
import com.jde.skillbill.domaine.interacteurs.interfaces.SourceDonneeException;


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
    public boolean creerFacture(double montantTotal, Utilisateur utilisateurPayeur, LocalDate localDate, Groupe groupe, String titre) throws SourceDonneeException {
        return iSourceDonnee.ajouterFacture(montantTotal, utilisateurPayeur, localDate, groupe , titre);
    }


    @Override
    public Facture ajouterPhotoFacture(Facture facture, String uri, Bitmap bitmap) {
        return null;
    }

    @Override
    public Facture modifierPhotoFacture(Facture facture, String uri, Bitmap bitmap) {
        return null;
    }

    @Override
    public boolean modifierFacture(Facture facture) throws SourceDonneeException {
        iSourceDonnee.modifierFacture(facture);
        return false;
    }

    @Override
    public boolean creerFacture(Facture facture) throws SourceDonneeException {
       return iSourceDonnee.creerFacture(facture);
    }
}
