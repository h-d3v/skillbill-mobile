package com.jde.skillbill.domaine.interacteurs;

import com.jde.skillbill.domaine.entites.Facture;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.interfaces.SourceDonneeException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class GestionGroupesTest {
    private Groupe groupeCobaye;
    private ISourceDonnee iSourceDonnee;
    private GestionGroupes gestionGroupes;
    private Utilisateur utilisateurCobaye = new Utilisateur("Mi","Ma", "motDePasseAuTop", Monnaie.CAD );

    @Before
    public void setUp(){
        iSourceDonnee = mock(ISourceDonnee.class);
        gestionGroupes = new GestionGroupes(iSourceDonnee);
        groupeCobaye = new Groupe("le nom", utilisateurCobaye, Monnaie.CAD);
    }


    @Test
    public void TestcreerGroupeReussi()throws SourceDonneeException {
        when(iSourceDonnee.creerGroupeParUtilisateur(utilisateurCobaye,groupeCobaye)).thenReturn(groupeCobaye);
        assertEquals(gestionGroupes.creerGroupe("le nom",utilisateurCobaye, Monnaie.CAD),groupeCobaye);
    }
    @Test
    public void TestcreerGroupeEchoue()throws SourceDonneeException {
        when(iSourceDonnee.creerGroupeParUtilisateur(utilisateurCobaye,groupeCobaye)).thenReturn(new Groupe("le nom", null, null));
        assertEquals(gestionGroupes.creerGroupe("le nom",null, null),null);
    }

    @Test
    public void TestajouterMembreReussi()throws SourceDonneeException {
        when(iSourceDonnee.ajouterMembre(groupeCobaye,utilisateurCobaye)).thenReturn(true);
        assertTrue(gestionGroupes.ajouterMembre(groupeCobaye, utilisateurCobaye));
    }

    @Test
    public void TestajouterMembreEchoue()throws SourceDonneeException {
        when(iSourceDonnee.ajouterMembre(groupeCobaye,utilisateurCobaye)).thenReturn(false);
        assertFalse(gestionGroupes.ajouterMembre(groupeCobaye, utilisateurCobaye));
    }


    @Test
    public void trouverToutesLesFactures()throws SourceDonneeException {
        List<Facture> factures = new ArrayList<>();
        when(iSourceDonnee.lireFacturesParGroupe(groupeCobaye)).thenReturn(factures);
        assertEquals(factures, gestionGroupes.trouverToutesLesFactures(groupeCobaye));
    }


    @Test
    public void trouverTousLesUtilisateurs()throws SourceDonneeException {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        when(iSourceDonnee.lireUTilisateurParGroupe(groupeCobaye)).thenReturn(utilisateurs);
        assertEquals(utilisateurs, gestionGroupes.trouverTousLesUtilisateurs(groupeCobaye));
    }

    @Test
    public void getSoldeParUtilisateurEtGroupeTestPasDeFactureDansLeGroupe()throws SourceDonneeException {
        when(iSourceDonnee.lireFacturesParGroupe(groupeCobaye)).thenReturn(null);
        //given(gestionGroupes.getSoldeParUtilisateurEtGroupe(utilisateurCobaye,groupeCobaye)).willThrow(new NullPointerException("Pas de factures"));
        Exception e = null;

        try {
            gestionGroupes.getSoldeParUtilisateurEtGroupe(utilisateurCobaye,groupeCobaye);
        }
        catch (Exception e1){
            e=e1;
             }
        assertEquals(e.toString(), new NullPointerException("Pas de factures").toString());
        }

    @Test
    public void getSoldeParUtilisateurEtGroupeTestAvecFactureDansLeGroupe() throws SourceDonneeException{
        Facture facture = new Facture();
        List<Facture> factures = new ArrayList<>();
        Utilisateur utilisateur1 = new Utilisateur("","1","4",Monnaie.CAD);
        Utilisateur utilisateur2 = new Utilisateur("","2","5",Monnaie.CAD);
        HashMap<Utilisateur, Double> montantPayeParUtilisateur= new HashMap<>();
        montantPayeParUtilisateur.put(utilisateur1, 100.0);
        montantPayeParUtilisateur.put(utilisateur2, 0.0);
        montantPayeParUtilisateur.put(utilisateurCobaye, 0.0);

        facture.setMontantPayeParParUtilisateur(montantPayeParUtilisateur);
        factures.add(facture);
        facture = new Facture();
        montantPayeParUtilisateur = new HashMap<>();
        montantPayeParUtilisateur.put(utilisateur1, 200.0);
        montantPayeParUtilisateur.put(utilisateur2, 0.0);
        montantPayeParUtilisateur.put(utilisateurCobaye, 100.0);  //400 montant total


        facture.setMontantPayeParParUtilisateur(montantPayeParUtilisateur);
        factures.add(facture);
        when(iSourceDonnee.lireFacturesParGroupe(groupeCobaye)).thenReturn(factures);
        assertEquals(gestionGroupes.getSoldeParUtilisateurEtGroupe(utilisateur1, groupeCobaye), 300.0 -400.0/3.0 , 1e-3);
        assertEquals(gestionGroupes.getSoldeParUtilisateurEtGroupe(utilisateur2, groupeCobaye),0 - (400.0/3.0)  , 1e-3);
        assertEquals(gestionGroupes.getSoldeParUtilisateurEtGroupe(utilisateurCobaye, groupeCobaye),100.0 - (400.0/3.0)  , 1e-3);

    }



}