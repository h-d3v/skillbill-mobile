package com.jde.skillbill.domaine.entites;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
public class MonnaieTest {

    @Test
    public void testMonnaieAUD(){
        assertEquals(1.03931, Monnaie.AUD.getTauxCad(), 0.0);
        assertEquals(0.962176, Monnaie.AUD.getTauxDevise(), 0.0);
        assertEquals(Monnaie.AUD.getSymbol(), "A$");
    }

    @Test
    public void testMonnaieCAD(){
        assertEquals(1, Monnaie.CAD.getTauxCad(), 0.0);
        assertEquals(1, Monnaie.CAD.getTauxDevise(), 0.0);
        assertEquals(Monnaie.CAD.getSymbol(), "CA$");
    }

    @Test
    public void testMonnaieUSD(){
        assertEquals(0.783038, Monnaie.USD.getTauxCad(), 0.0);
        assertEquals(1.27708, Monnaie.USD.getTauxDevise(), 0.0);
        assertEquals(Monnaie.USD.getSymbol(), "US$");
    }

    @Test
    public void testMonnaieGBP(){
        assertEquals(0.593066, Monnaie.GBP.getTauxCad(), 0.0);
        assertEquals(1.68615, Monnaie.GBP.getTauxDevise(), 0.0);
        assertEquals(Monnaie.GBP.getSymbol(), "£");
    }

    @Test
    public void testMonnaieEUR(){
        assertEquals(0.646549, Monnaie.EUR.getTauxCad(), 0.0);
        assertEquals(1.54667, Monnaie.EUR.getTauxDevise(), 0.0);
        assertEquals(Monnaie.EUR.getSymbol(), "€");
    }

}
