package com.jde.skillbill.domaine.interacteurs;

import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.domaine.entites.Utilisateur;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;



@RunWith(MockitoJUnitRunner.class)
public class GestionUtilisateurTest {

  private ISourceDonnee sourceDonnee = mock(ISourceDonnee.class);
  private GestionUtilisateur gestionUtilisateur;
  private Utilisateur utilisateurCobaye = new Utilisateur("Mi","Ma", "emailExiste","motDePasseAuTop" );



    @Before
    public void setUp(){
         gestionUtilisateur = new GestionUtilisateur(sourceDonnee);
         utilisateurCobaye.setMonnaieUsuelle(Monnaie.CAD);
    }

    @Test
    public void TestsetSource() {
        GestionUtilisateur gestionUtilisateur1 = new GestionUtilisateur(sourceDonnee);
        GestionUtilisateur gestionUtilisateur2 = new GestionUtilisateur(null);
        gestionUtilisateur2.setSource(sourceDonnee);
        assertEquals(sourceDonnee, gestionUtilisateur1._dataSource);
        assertEquals(gestionUtilisateur2._dataSource, gestionUtilisateur1._dataSource);
    }

    @Test
    public void TestUtilisateurExisteQuandUtilisateurExiste() {
        when(sourceDonnee.lireUtilisateur("emailExiste")).thenReturn(utilisateurCobaye);
        assertEquals(gestionUtilisateur.utilisateurExiste("emailExiste"), true);
    }

    @Test
    public void TestUtilisateurExisteQuandUtilisateurInexistant() {

        when(sourceDonnee.lireUtilisateur("emailInexistant")).thenReturn(null);
        assertEquals(gestionUtilisateur.utilisateurExiste("emailInexistant"), false);
    }

    @Test
    public void TestTenterConnexionUtilisateurExisteMotPasseVrai() {

        when(sourceDonnee.tenterConnexion("emailExiste", "motDePasseAuTop")).thenReturn(utilisateurCobaye);
        Utilisateur utilisateur = gestionUtilisateur.tenterConnexion("emailExiste", "motDePasseAuTop" );
        assertEquals(utilisateur, utilisateurCobaye);
        assertEquals(utilisateur.getCourriel(), utilisateurCobaye.getCourriel());
        assertEquals(utilisateur.getMotPasse(), utilisateurCobaye.getMotPasse());
        assertEquals(utilisateur.getNom(), utilisateurCobaye.getNom());
    }

    @Test
    public void TestTenterConnexionUtilisateurExisteMotPasseFaux() {

        when(sourceDonnee.tenterConnexion("emailExiste", "mauvaisMotPasse")).thenReturn(null);
        Utilisateur utilisateur = gestionUtilisateur.tenterConnexion("emailExiste", "mauvaisMotPasse" );
        assertEquals(utilisateur, null);

    }
    @Test
    public void TestTenterConnexionUtilisateurPasExisteMotPasseFaux() {

        when(sourceDonnee.tenterConnexion("emailExistePas", "mauvaisMotPasse")).thenReturn(null);
        Utilisateur utilisateur = gestionUtilisateur.tenterConnexion("emailExistePas", "mauvaisMotPasse" );
        assertEquals(utilisateur, null);
    }




    @Test
    public void creerUtilisateurTestUtilisateurExiste() {
        when(sourceDonnee.creerUtilisateur(utilisateurCobaye)).thenReturn(null);
       assertEquals(gestionUtilisateur.creerUtilisateur(utilisateurCobaye.getNom(),utilisateurCobaye.getCourriel(),
               utilisateurCobaye.getMotPasse(), utilisateurCobaye.getMonnaieUsuelle()),null);
    }


    @Test
    public void creerUtilisateurTestUtilisateurExistePas() {
        when(sourceDonnee.creerUtilisateur(utilisateurCobaye)).thenReturn(utilisateurCobaye);
        assertEquals(gestionUtilisateur.creerUtilisateur(utilisateurCobaye.getNom(),utilisateurCobaye.getCourriel(),
                utilisateurCobaye.getMotPasse(), utilisateurCobaye.getMonnaieUsuelle()),utilisateurCobaye);
    }

    @Test
    public void trouverGroupesAbonne() {
        List<Groupe> groupes = new ArrayList<>();
        when(sourceDonnee.lireTousLesGroupesAbonnes(utilisateurCobaye)).thenReturn(groupes);
        assertEquals(gestionUtilisateur.trouverGroupesAbonne(utilisateurCobaye), groupes);

    }
}