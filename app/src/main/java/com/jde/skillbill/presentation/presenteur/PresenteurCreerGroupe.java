package com.jde.skillbill.presentation.presenteur;

import android.app.Activity;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.GestionGroupes;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionGroupes;
import com.jde.skillbill.donnees.mockDAO.SourceDonneesMock;
import com.jde.skillbill.presentation.IContratVuePresenteurCreerGroupe;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueCreerGroupe;
import com.jde.skillbill.ui.activity.ActivityVoirUnGroupe;


public class PresenteurCreerGroupe implements IContratVuePresenteurCreerGroupe.PresenteurCreerGroupe {
    private Modele modele;
    private VueCreerGroupe vueCreerGroupe;
    private Activity activity;
    private String EXTRA_ID_UTILISATEUR="com.jde.skillbill.utlisateur_identifiant";

    public PresenteurCreerGroupe(Modele modele, VueCreerGroupe vueCreerGroupe, Activity activity) {
        this.modele = modele;
        this.vueCreerGroupe = vueCreerGroupe;
        this.activity=activity;
        modele.setUtilisateurConnecte(new Utilisateur("", activity.getIntent().getStringExtra(EXTRA_ID_UTILISATEUR),null));
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
