package com.jde.skillbill.presentation.presenteur;

import android.app.Instrumentation;
import androidx.test.platform.app.InstrumentationRegistry;
import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.ISourceDonnee;
import com.jde.skillbill.domaine.interacteurs.interfaces.SourceDonneeException;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueCreerCompte;
import com.jde.skillbill.ui.activity.ActivityCreerCompte;
import org.junit.Test;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

public class PresenteurCreerCompteTest {

    @Test
    public void creerCompteCourrielExistant() throws SourceDonneeException {
       final ActivityCreerCompte activityCreerCompte = mock(ActivityCreerCompte.class);
       final Modele modele = mock(Modele.class);
       final VueCreerCompte vueCreerCompte = mock(VueCreerCompte.class);
       final ISourceDonnee sourceDonnee = mock(ISourceDonnee.class);
       when(sourceDonnee.utilisateurExiste("email@email.com")).thenReturn(true);
       when(vueCreerCompte.getEmail()).thenReturn("email@email.com");


        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();

        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                final PresenteurCreerCompte presenteurCreerCompte = new PresenteurCreerCompte(activityCreerCompte, modele,vueCreerCompte);
                presenteurCreerCompte.setDataSource(sourceDonnee);
                presenteurCreerCompte.creerCompte();
            }
        });
        instrumentation.waitForIdle(new Runnable() {
            @Override
            public void run() {
                verify(vueCreerCompte).afficherEmailDejaPrit();
            }
        });

    }

    @Test
    public void creerCompteCourrielNONExistant() throws SourceDonneeException {
        final ActivityCreerCompte activityCreerCompte = mock(ActivityCreerCompte.class);
        final Modele modele = mock(Modele.class);
        final VueCreerCompte vueCreerCompte = mock(VueCreerCompte.class);
        final ISourceDonnee sourceDonnee = mock(ISourceDonnee.class);
        when(sourceDonnee.utilisateurExiste("email@email.com")).thenReturn(false);
        when(vueCreerCompte.getEmail()).thenReturn("email@email.com");
        when(vueCreerCompte.getNom()).thenReturn("nom");
        when(vueCreerCompte.getPass()).thenReturn("motdepasse");
        when(vueCreerCompte.getMonnaieChoisie()).thenReturn(Monnaie.CAD);
        when(sourceDonnee.creerUtilisateur(new Utilisateur("nom","email@email.com","motdepasse",Monnaie.CAD))).thenReturn(new Utilisateur("nom","email@email.com","motdepasse",Monnaie.CAD));


        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();

        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                final PresenteurCreerCompte presenteurCreerCompte = new PresenteurCreerCompte(activityCreerCompte, modele,vueCreerCompte);
                presenteurCreerCompte.setDataSource(sourceDonnee);
                presenteurCreerCompte.creerCompte();
            }
        });
        instrumentation.waitForIdle(new Runnable() {
            @Override
            public void run() {
                verify(vueCreerCompte).afficherCompteCreer(vueCreerCompte.getNom(), vueCreerCompte.getEmail(),vueCreerCompte.getMonnaieChoisie());
            }
        });

    }

}