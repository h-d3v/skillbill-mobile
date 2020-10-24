package com.jde.skillbill.presentation.presenteur;

import android.app.Instrumentation;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.ISourceDonnee;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionUtilisateur;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueConnexion;
import com.jde.skillbill.ui.activity.ActivityConnexion;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;

@RunWith(AndroidJUnit4.class)
public class PresenteurConnexionTest {



    @Test
    public void testTenterConnectionReussie(){
        final ISourceDonnee source = mock(ISourceDonnee.class);
        final IGestionUtilisateur iGestionUtilisateur;
        final Modele modele =mock(Modele.class);
        final VueConnexion vueConnexion = mock(VueConnexion.class);
        final ActivityConnexion activityConnexion = mock(ActivityConnexion.class);
        final Utilisateur utilisateur = new Utilisateur("Al","bonemail","bonMotPasse", Monnaie.CAD);

        when(source.tenterConnexion("bonemail", "bonMotPasse")).thenReturn(utilisateur);
        when(modele.getUtilisateurConnecte()).thenReturn(utilisateur);
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.runOnMainSync(
                new Runnable() {
                    @Override
                    public void run() {
                        final PresenteurConnexion presenteurConnexion = new PresenteurConnexion(activityConnexion, modele, vueConnexion);
                        presenteurConnexion.setDataSource(source);
                        presenteurConnexion.tenterConnexion(utilisateur.getCourriel(), utilisateur.getMotPasse());

                    }

                }
        );


        instrumentation.waitForIdle(new Runnable() {
            @Override
            public void run() {
                verify(vueConnexion).afficherMsgConnecter(modele.getUtilisateurConnecte().getCourriel(), modele.getUtilisateurConnecte().getNom());
                verify(modele).setUtilisateurConnecte(utilisateur);

            }
        });

    }

    @Test
    public void testTenterConnectionEchouee() {
        final ISourceDonnee source = mock(ISourceDonnee.class);
        final IGestionUtilisateur iGestionUtilisateur;
        final Modele modele = mock(Modele.class);
        final VueConnexion vueConnexion = mock(VueConnexion.class);
        final ActivityConnexion activityConnexion = mock(ActivityConnexion.class);
        final Utilisateur utilisateur = new Utilisateur("Al", "mauvaisemail", "mauvaisMotPasse", Monnaie.CAD);
        when(source.tenterConnexion("mauvaisemail", "mauvaisMotPasse")).thenReturn(null);
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.runOnMainSync(
                new Runnable() {
                    @Override
                    public void run() {
                        final PresenteurConnexion presenteurConnexion = new PresenteurConnexion(activityConnexion, modele, vueConnexion);
                        presenteurConnexion.setDataSource(source);
                        presenteurConnexion.tenterConnexion(utilisateur.getCourriel(), utilisateur.getMotPasse());

                    }

                }
        );
        instrumentation.waitForIdle(new Runnable() {
            @Override
            public void run() {
                verify(vueConnexion).afficherMsgErreur();

            }
        });
    }

}