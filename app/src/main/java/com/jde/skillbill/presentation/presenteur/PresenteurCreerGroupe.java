package com.jde.skillbill.presentation.presenteur;

import android.app.Activity;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.interacteurs.GestionGroupes;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionGroupes;
import com.jde.skillbill.donnees.mockDAO.SourceDonneesMock;
import com.jde.skillbill.presentation.IContratVuePresenteurCreerGroupe;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueCreerGroupe;
import com.jde.skillbill.ui.ActivityVoirUnGroupe;

import static com.jde.skillbill.donnees.mockDAO.SourceDonneesMock.utilisateurFake;

public class PresenteurCreerGroupe implements IContratVuePresenteurCreerGroupe.PresenteurCreerGroupe {
    private Modele modele;
    private VueCreerGroupe vueCreerGroupe;
    private Activity activity;

    public PresenteurCreerGroupe(Modele modele, VueCreerGroupe vueCreerGroupe, Activity activity) {
        this.modele = modele;
        this.vueCreerGroupe = vueCreerGroupe;
        this.activity=activity;
        modele.setUtilisateurConnecte(utilisateurFake);//TODO OR NOT TODO
    }

    @Override
    public void creerGroupe() {
        IGestionGroupes gestionGroupes = new GestionGroupes(new SourceDonneesMock());

        gestionGroupes.creerGroupe(vueCreerGroupe.getNomGroupe(), modele.getUtilisateurConnecte(),null);
        Intent intent = new Intent(activity, ActivityVoirUnGroupe.class);
        activity.startActivity(intent);
    }


    @Override
    public void retournerEnArriere(){
        activity.onBackPressed();
    }


}
