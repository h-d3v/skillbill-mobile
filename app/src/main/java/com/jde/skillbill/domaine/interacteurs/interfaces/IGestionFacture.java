package com.jde.skillbill.domaine.interacteurs.interfaces;

import android.graphics.Bitmap;
import com.jde.skillbill.domaine.entites.Facture;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Utilisateur;

import java.time.LocalDate;
import java.util.Map;

public interface IGestionFacture {

    /**
     *
     * @param montantTotal montant total de la facture
     * @param mantantPayeParUtilisateur le montant payé par utilisateur
     * @param localDate la date de la facture
     * @return true si crée, false sinon
     */
    boolean creerFactureAvecMultiplePayeurs(double montantTotal, Map<Utilisateur, Double> mantantPayeParUtilisateur, LocalDate localDate)throws SourceDonneeException;


    /**
     *
     * @param montantTotal   montant total de la facture
     * @param utilisateurPayeur  le montant payé par l' utilisateur
     * @param localDate localDate la date de la facture
     * @param groupe le groupe de la facture
     * @param titre le nom de la facture
     * @return
     */
    boolean creerFacture(double montantTotal, Utilisateur utilisateurPayeur, LocalDate localDate, Groupe groupe, String titre)throws SourceDonneeException;

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
     * @param facture la facture a laquelle on veut modifier une photo
     * @param uri uri de la photo
     * @param bitmap photo en bitmap
     * @return la facture ajoutée, null si pas ajouté
     */
    Facture modifierPhotoFacture(Facture facture, String uri, Bitmap bitmap)throws SourceDonneeException;

    void modifierFacture(Facture facture);
}
