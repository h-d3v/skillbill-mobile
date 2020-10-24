package com.jde.skillbill.domaine.entites;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
public class UtilisateurTest {
    Utilisateur utilisateurTest;

    @Before
    public void setUp() {
       utilisateurTest = new Utilisateur("Zidane", "test@jde.com", "Zizou123", Monnaie.CAD);
    }

    @Test
    public void testAccesseurEmail(){
        assertEquals(utilisateurTest.getCourriel(), "test@jde.com");
    }

    @Test
    public void testAccesseurNom(){
        assertEquals(utilisateurTest.getNom(), "Zidane");
    }

    @Test
    public void testAccesseurMdp(){
        assertEquals(utilisateurTest.getMotPasse(), "Zizou123");
    }

    @Test
    public void testAccesseurMonnaie(){
        assertEquals(utilisateurTest.getMonnaieUsuelle(), Monnaie.CAD);
    }

    @Test
    public void testAccesseurNumtel(){
        utilisateurTest.setNumeroTelephone("5143761620");
        assertEquals(utilisateurTest.getNumeroTelephone(), "5143761620");
    }

    @Test
    public void testAccesseurUriPhoto(){
        utilisateurTest.setUriPhoto("http//unePic.com");
        assertEquals(utilisateurTest.getUriPhoto(), "http//unePic.com");
    }

    @Test
    public void testVerifierUtilisateurParreil(){
        Utilisateur utilisateurTest2= new Utilisateur("Zidane", "test@jde.com", "Zizou123", Monnaie.CAD);
        assertEquals(utilisateurTest, utilisateurTest2);
    }

    @Test
    public void testVerifierUtilisateursDiff(){
        Utilisateur utilisateurTest2= new Utilisateur("Zidane", "test@jdee.com", "Zizou123", Monnaie.CAD);
        assertNotEquals(utilisateurTest, utilisateurTest2);
    }





}
