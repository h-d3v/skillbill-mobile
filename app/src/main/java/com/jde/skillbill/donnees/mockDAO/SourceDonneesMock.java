package com.jde.skillbill.donnees.mockDAO;

import android.util.Log;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.ISourceDonnee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class SourceDonneesMock implements ISourceDonnee{
    public static HashMap<Utilisateur, List<Groupe>> utilisateurGroupeHashMap =new HashMap<>();
    private List<Utilisateur> _utilisateurs;

    public SourceDonneesMock(){
        _utilisateurs= new LinkedList<>();
        //En att l'api rest seulement ces utilisateurs peuvent se connecter.

        _utilisateurs.add(new Utilisateur("Julien J","jj@jde.com","julien123"));
        _utilisateurs.add(new Utilisateur("Hedi","hedi@jde.com","hedi123"));
        _utilisateurs.add(new Utilisateur("Patrick","patrick@jde.com","jaimeUncleBob123"));
        if(utilisateurGroupeHashMap.isEmpty()) {
            //L'utilisateur 0 est julien.
            utilisateurGroupeHashMap.put(_utilisateurs.get(0), new ArrayList<Groupe>());
            utilisateurGroupeHashMap.get(_utilisateurs.get(0)).add( new Groupe("test groupe 1", _utilisateurs.get(0), null));
            utilisateurGroupeHashMap.get(_utilisateurs.get(0)).add( new Groupe("test groupe 2",_utilisateurs.get(0), null));
        }
    }

    @Override
    public  boolean creerGroupeParUtilisateur(Utilisateur utilisateur, Groupe groupe) {
        utilisateurGroupeHashMap.get(utilisateur).add(groupe);
        return true ; // Pas pertinent pour le mock
    }

    @Override
    public List<Groupe> lireTousLesGroupesAbonnes(Utilisateur utilisateur){
        Log.d("_p voir groupe", Integer.toString(utilisateurGroupeHashMap.get(_utilisateurs.get(0)).size()));
        return utilisateurGroupeHashMap.get(utilisateur);
    }


    @Override
    public Utilisateur lireUtilisateur(String email) {

            Utilisateur utilisateurALire=null;
            for (Utilisateur utilisateur:_utilisateurs) {
                int result=utilisateur.getCourriel().compareTo(email);
                if (result==0) utilisateurALire=utilisateur;
            }
            return utilisateurALire;
        }

        //Retourne l'utilisateur tel que recu, pas de persistace pour l'instant
        @Override
        public Utilisateur creerUtilisateur(Utilisateur utilisateurACreer) {
            for (Utilisateur utilisateur:_utilisateurs) {
                int result=utilisateur.getCourriel().compareTo(utilisateurACreer.getCourriel());
                if(result==0) throw new UnsupportedOperationException("Erreur d'insertion dans la bd, email deja utiliser");
                break;
            }
            return utilisateurACreer;
        }

        @Override
        public Utilisateur tenterConnexion(String email, String mdp){
            Utilisateur utilisateurAConnecter=null;
            for (Utilisateur utilisateur:_utilisateurs) {
                int resultEmail=utilisateur.getCourriel().compareTo(email);
                int resultMdp=utilisateur.getMotPasse().compareTo(mdp);
                if (resultEmail==0 && resultMdp==0) utilisateurAConnecter=utilisateur;
            }
            return utilisateurAConnecter;
        }


}
