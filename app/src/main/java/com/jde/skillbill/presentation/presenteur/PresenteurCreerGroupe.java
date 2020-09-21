package com.jde.skillbill.presentation.presenteur;

import android.app.Activity;
import android.content.Intent;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.interacteurs.GestionGroupes;
import com.jde.skillbill.presentation.IContratVuePresenteurCreerGroupe;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueCreerGroupe;
import com.jde.skillbill.ui.ActivityVoirUnGroupe;

import static com.jde.skillbill.donnees.mockDAO.DAOFactoryUtilisateurGroupeMock.utilisateurGroupeHashMap;

public class PresenteurCreerGroupe implements IContratVuePresenteurCreerGroupe.PresenteurCreerGroupe {
    private Modele modele;
    private VueCreerGroupe vueCreerGroupe;
    private Activity activity;

    public PresenteurCreerGroupe(Modele modele, VueCreerGroupe vueCreerGroupe, Activity activity) {
        this.modele = modele;
        this.vueCreerGroupe = vueCreerGroupe;
        this.activity=activity;
        modele.setUtilisateurConnecte(utilisateurGroupeHashMap.keySet().iterator().next());//TODO OR NOT TODO
    }

    @Override
    public void creerGroupe() {
        GestionGroupes gestionGroupes = new GestionGroupes();
        Groupe groupeCree = new Groupe(vueCreerGroupe.getNomGroupe(), modele.getUtilisateurConnecte(), null);
        modele.ajouterGroupe(groupeCree);
        Intent intent = new Intent(activity, ActivityVoirUnGroupe.class);
        activity.startActivity(intent);
    }


    @Override
    public void retournerEnArriere(){
        activity.onBackPressed();
    }


}
