package com.jde.skillbill.presentation.presenteur;

import android.app.Instrumentation;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.ISourceDonnee;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueCreerGroupe;
import com.jde.skillbill.ui.activity.ActivityCreerGroupe;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class PresenteurCreerGroupeTest {

    @Test
    public void testCreerGroupeNomValide(){
        final ActivityCreerGroupe _activity=mock(ActivityCreerGroupe.class);
        final Modele modele = mock(Modele.class);
        final VueCreerGroupe _vue = mock(VueCreerGroupe.class);
        final PresenteurCreerGroupe _presenteur=mock(PresenteurCreerGroupe.class);
        final Utilisateur utilisateur = new Utilisateur("Al","bonemail","bonMotPasse", Monnaie.CAD);
        final Groupe groupeCree;

        modele.setUtilisateurConnecte(utilisateur);
        when(modele.getUtilisateurConnecte()).thenReturn(utilisateur);

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();

        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                final PresenteurCreerGroupe presenteurCreerGroupe= new PresenteurCreerGroupe( modele,_vue, _activity);
                presenteurCreerGroupe.creerGroupe();
            }
        });
        instrumentation.waitForIdle(new Runnable() {
            @Override
            public void run() {
                verify(_presenteur).redirigerVersGroupeCreer();
            }
        });
    }
}
