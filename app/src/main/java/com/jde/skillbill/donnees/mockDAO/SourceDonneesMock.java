package com.jde.skillbill.donnees.mockDAO;

import android.util.Log;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.DataSourceUsers;
import com.jde.skillbill.domaine.interacteurs.GestionUtilisateur;
import com.jde.skillbill.domaine.interacteurs.ISourceDonnee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SourceDonneesMock implements ISourceDonnee {
    public static HashMap<Utilisateur, List<Groupe>> utilisateurGroupeHashMap =new HashMap<>();
    public static Utilisateur utilisateurFake = new Utilisateur("Julien J","jj@jde.com","julien123");



    public SourceDonneesMock(){
        if(utilisateurGroupeHashMap.isEmpty()) {
            utilisateurGroupeHashMap.put(utilisateurFake, new ArrayList<Groupe>());
            utilisateurGroupeHashMap.get(utilisateurFake).add( new Groupe("test groupe 1", utilisateurFake, null));
            utilisateurGroupeHashMap.get(utilisateurFake).add( new Groupe("test groupe 2", utilisateurFake, null));


        }
    }


    @Override
    public  boolean creerGroupeParUtilisateur(Utilisateur utilisateur, Groupe groupe) {

        utilisateurGroupeHashMap.get(utilisateur).add(groupe);
        return true ; // Pas pertinent pour le mock
    }

    @Override
    public List<Groupe> lireTousLesGroupesAbonnes(Utilisateur utilisateur){
        Log.d("_p voir groupe", Integer.toString(utilisateurGroupeHashMap.get(utilisateurFake).size()));
        return utilisateurGroupeHashMap.get(utilisateur);
    }


}
