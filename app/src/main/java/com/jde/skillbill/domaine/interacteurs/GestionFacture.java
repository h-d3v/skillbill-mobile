package com.jde.skillbill.domaine.interacteurs;

import android.graphics.Bitmap;
import com.jde.skillbill.domaine.entites.Facture;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionFacture;
import com.jde.skillbill.domaine.interacteurs.interfaces.SourceDonneeException;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Javadoc dans l'interface
 */

public class GestionFacture implements IGestionFacture {

    private ISourceDonnee iSourceDonnee;

    public GestionFacture(ISourceDonnee iSourceDonnee) {
        this.iSourceDonnee = iSourceDonnee;
    }


    @Override
    public Facture ajouterPhotoFacture(Facture facture, String uri, Bitmap bitmap) {
        return null;
    }



    @Override
    public boolean modifierFacture(Facture facture) throws SourceDonneeException {
        return iSourceDonnee.modifierFacture(facture);

    }

    @Override
    public boolean creerFacture(Facture facture) throws SourceDonneeException {
       return iSourceDonnee.creerFacture(facture);
    }


    @Override
    public Facture rechargerFacture(Facture facture) throws SourceDonneeException {
        return iSourceDonnee.rechargerFacture(facture);
    }


}
