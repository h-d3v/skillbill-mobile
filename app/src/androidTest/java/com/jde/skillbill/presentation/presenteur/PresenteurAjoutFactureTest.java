package com.jde.skillbill.presentation.presenteur;

import android.app.Instrumentation;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.GestionFacture;
import com.jde.skillbill.domaine.interacteurs.GestionGroupes;
import com.jde.skillbill.domaine.interacteurs.GestionUtilisateur;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueAjouterFacture;
import com.jde.skillbill.presentation.vue.VueCreerGroupe;
import com.jde.skillbill.ui.activity.ActivityAjouterFacture;
import com.jde.skillbill.ui.activity.ActivityCreerGroupe;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class PresenteurAjoutFactureTest {
    @Test
    public void testAjoutFactureInfosValides(){
        final ActivityAjouterFacture _activity=mock(ActivityAjouterFacture.class);
        final Modele modele = mock(Modele.class);
        final VueAjouterFacture _vue = mock(VueAjouterFacture.class);
        final GestionFacture gestionFacture=mock(GestionFacture.class);
        final GestionGroupes gestionGroupes=mock(GestionGroupes.class);
        final GestionUtilisateur gestionUtilisateur=mock(GestionUtilisateur.class);
        final PresenteurAjouterFacture _presenteur=mock(PresenteurAjouterFacture.class);
        final Utilisateur utilisateur = new Utilisateur("Al","bonemail","bonMotPasse", Monnaie.CAD);
        modele.setUtilisateurConnecte(utilisateur);
        when(modele.getUtilisateurConnecte()).thenReturn(utilisateur);

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();

        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                _presenteur.ajouterFacture();
            }
        });
        instrumentation.waitForIdle(new Runnable() {
            @Override
            public void run() {
                verify(_presenteur).redirigerVersListeFactures();
            }
        });
    }

}
