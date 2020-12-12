package com.jde.skillbill.domaine.interacteurs.interfaces;

import android.graphics.Bitmap;
import com.jde.skillbill.domaine.entites.Facture;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Utilisateur;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IGestionFacture {



    /**
     *
     * @param facture la facture a laquelle on veut ajouter une photo
     * @param uri uri de la photo
     * @param bitmap photo en bitmap
     * @return la facture ajoutée, null si pas ajouté
     */
    Facture ajouterPhotoFacture(Facture facture, String uri, Bitmap bitmap)throws SourceDonneeException;

    /**
     *
     * @param facture la facture à modifier
     * @return facture la facture créee dans la source de données
     * @throws SourceDonneeException
     */
    boolean modifierFacture(Facture facture) throws SourceDonneeException;

    /**
     *
     * @param facture la facture à peresiter dans la source de données
     * @return bool true si ajoutée, false sinon
     * @throws SourceDonneeException
     */

    boolean creerFacture(Facture facture) throws SourceDonneeException;

    /**
     *
     * @param facture la facture que l'on souhaite rechrager
     * @return la facture dans la source de données
     * @throws SourceDonneeException
     */

    Facture rechargerFacture(Facture facture) throws SourceDonneeException;
}
