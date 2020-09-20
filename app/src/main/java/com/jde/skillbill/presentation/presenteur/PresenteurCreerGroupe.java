package com.jde.skillbill.presentation.presenteur;

import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.GestionGroupes;
import com.jde.skillbill.donnees.mockDAO.DAOFactoryUtilisateurGroupeMock;
import com.jde.skillbill.presentation.IContratVuePresenteurCreerGroupe;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueCreerGroupe;

import java.util.List;

public class PresenteurCreerGroupe implements IContratVuePresenteurCreerGroupe.PresenteurCreerGroupe {
    private Modele modele;
    private VueCreerGroupe vueCreerGroupe;

    public PresenteurCreerGroupe(Modele modele, VueCreerGroupe vueCreerGroupe) {
        this.modele = modele;
        this.vueCreerGroupe = vueCreerGroupe;
        modele.setUtilisateurConnecte(DAOFactoryUtilisateurGroupeMock.utilisateurGroupeHashMap.keySet().iterator().next());//TODO OR NOT TODO
    }

    @Override
    public void creerGroupe() {
        GestionGroupes gestionGroupes = new GestionGroupes();
        Groupe groupeCree = new Groupe(vueCreerGroupe.getNomGroupe(), modele.getUtilisateurConnecte(), null);
        modele.ajouterGroupe(groupeCree);
    }


}
