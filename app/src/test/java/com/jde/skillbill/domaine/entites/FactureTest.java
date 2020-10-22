package com.jde.skillbill.domaine.entites;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.Assert.*;

public class FactureTest {
    Facture factureDeTest;
    Utilisateur utilisateurTest;
    Groupe groupeTest;


    @Before
    public void setUp() {
        utilisateurTest = new Utilisateur("Zidane", "test@jde.com", "Zizou123", Monnaie.CAD);
        groupeTest = new Groupe("JDE", utilisateurTest, Monnaie.CAD);
        HashMap<Utilisateur, Double> hashMap= new HashMap<>();
        hashMap.put(utilisateurTest, 200.00);
        factureDeTest = new Facture(LocalDate.now(), hashMap, "test1");
    }

    @Test
    public void testGetDate(){
        assertEquals(factureDeTest.getDateFacture(), LocalDate.now());
    }

    @Test
    public void testGetLibelle(){
        assertEquals(factureDeTest.getLibelle(), "test1");
    }

    @Test
    public void testGetMontantPayeParUtilisateur(){
        assertEquals(factureDeTest.getMontantPayeParParUtilisateur().get(utilisateurTest), 200.00 , 0.00);
    }

    @Test
    public void testGetSetUriImg(){
        factureDeTest.setUriImageFacture("hedi.com");
        assertEquals(factureDeTest.getUriImageFacture(), "hedi.com");
    }

}
