package com.jde.skillbill.donnees.APIRest;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.domaine.interacteurs.interfaces.SourceDonneeException;
import com.jde.skillbill.donnees.APIRest.entites.GroupeRestApi;
import com.jde.skillbill.donnees.APIRest.entites.UtilisateurRestAPI;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class SourceDonneesAPIRestTest {

    @Mock
    HttpURLConnection httpURLConnection;

    @Before
    public void setUP(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testCreeGroupeParUtilisateur() throws SourceDonneeException {
        try{

            when(httpURLConnection.getResponseCode()).thenReturn(200);
            when(httpURLConnection.getInputStream()).thenReturn(new ByteArrayInputStream("{\"Id\": 1}".getBytes()));
        }catch (IOException e){}



        GroupeRestApi groupeAttendu = new GroupeRestApi(null, null, Monnaie.CAD, 1);
        SourceDonneesAPIRest sourceDonneesAPIRest= new SourceDonneesAPIRest();
        assertEquals(groupeAttendu.getId(), sourceDonneesAPIRest.creerGroupeParUtilisateur(new UtilisateurRestAPI(1), null));
    }

}