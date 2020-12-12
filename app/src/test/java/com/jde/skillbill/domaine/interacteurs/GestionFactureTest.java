package com.jde.skillbill.domaine.interacteurs;

import com.jde.skillbill.domaine.entites.Facture;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.entites.UtilisateurException;
import com.jde.skillbill.domaine.interacteurs.interfaces.SourceDonneeException;
import com.jde.skillbill.donnees.mockDAO.SourceDonneesMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class GestionFactureTest {
    private Facture factureCobaye;
    private ISourceDonnee iSourceDonnee;
    private GestionFacture gestionFacture;
    private SourceDonneesMock sourceDonneesMock;

    @Before
    public void setup(){
        iSourceDonnee = mock(ISourceDonnee.class);
        gestionFacture = new GestionFacture(iSourceDonnee);
        factureCobaye = new Facture();

    }

    @Test//Non implémemté
    public void ajouterPhotoFacture() {

    }

    @Test
    public void modifierFactureTestModificationFaite() throws SourceDonneeException {
        when(iSourceDonnee.modifierFacture(factureCobaye)).thenReturn(true);
        assertEquals(gestionFacture.modifierFacture(factureCobaye), true);
        verify(iSourceDonnee).modifierFacture(factureCobaye);
    }

    @Test
    public void modifierFactureTestModificationNONFaite() throws SourceDonneeException {
        when(iSourceDonnee.modifierFacture(factureCobaye)).thenReturn(false);
        assertEquals(gestionFacture.modifierFacture(factureCobaye), false);
        verify(iSourceDonnee).modifierFacture(factureCobaye);
    }

    @Test
    public void creerFactureAjoutFait() throws SourceDonneeException {
        when(iSourceDonnee.creerFacture(factureCobaye)).thenReturn(true);
        assertEquals(gestionFacture.creerFacture(factureCobaye), true);
        verify(iSourceDonnee).creerFacture(factureCobaye);
    }
    @Test
    public void creerFactureAjoutNONFait() throws SourceDonneeException {
        when(iSourceDonnee.creerFacture(factureCobaye)).thenReturn(false);
        assertEquals(gestionFacture.creerFacture(factureCobaye), false);
        verify(iSourceDonnee).creerFacture(factureCobaye);
    }

    @Test
    public void rechargerFacture() throws SourceDonneeException {
        gestionFacture.rechargerFacture(factureCobaye);
        verify(iSourceDonnee).rechargerFacture(factureCobaye);
    }
}