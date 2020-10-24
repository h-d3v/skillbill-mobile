package com.jde.skillbill.domaine.entites;

import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class GroupeTest {
    Facture factureDeTest;
    Utilisateur utilisateurTest, utilisateurTest2;
    Groupe groupeTest;
    List<Utilisateur> utilisateurs;
    List<Facture> factures;

    HashMap<Utilisateur, Double> hashMap;

    @Before
    public void setUp() {
        utilisateurTest = new Utilisateur("Zidane", "test@jde.com", "Zizou123", Monnaie.CAD);
        utilisateurTest2 = new Utilisateur("Ronaldo", "paul@jde.com", "Roro123", Monnaie.EUR);
        utilisateurs= new LinkedList<>();
        utilisateurs.add(utilisateurTest);
        utilisateurs.add(utilisateurTest2);

        groupeTest = new Groupe("JDE", utilisateurTest, Monnaie.CAD);
        groupeTest.setUtilisateurs(utilisateurs);

        hashMap= new HashMap<>();
        hashMap.put(utilisateurTest, 200.00);
        factureDeTest = new Facture(LocalDate.now(), hashMap, "factureTest");
        factures= new LinkedList<>();
        factures.add(factureDeTest);
        groupeTest.setFactures(factures);

        groupeTest.setSoldeParUtilisateur(hashMap);
    }

    @Test
    public  void testGetUtilisateurCreateur(){
        assertEquals(groupeTest.getUtilisateurCreateurGroupe(), utilisateurTest);
    }

    @Test
    public  void testGetNomGroupe(){
        assertEquals(groupeTest.getNomGroupe(), "JDE");
    }

    @Test
    public  void testGetDateCreation(){
        groupeTest.setDateCreation(Date.from(Instant.now()));
        assertEquals(groupeTest.getDateCreation(), Date.from(Instant.now()));
    }

    @Test
    public  void testGetSoldeParUser(){
        HashMap<Utilisateur, Double> hashMap2;
        hashMap2= new HashMap<>();
        hashMap2.put(utilisateurTest, 200.00);

        assertEquals(groupeTest.getSoldeParUtilisateur(), hashMap2);
    }


    @Test
    public void testGetMonnaieDuGroupe(){
        groupeTest.setMonnaieDuGroupe(Monnaie.GBP);
        assertEquals(groupeTest.getMonnaieDuGroupe(), Monnaie.GBP);
    }
    
    @Test
    public  void testGetUsers(){
        LinkedList<Utilisateur> listeSuppose= new LinkedList<>();
        listeSuppose.add(utilisateurTest);
        listeSuppose.add(utilisateurTest2);
        assertEquals(groupeTest.getUtilisateurs(), listeSuppose);
    }

}
