package com.jde.skillbill.dataSource;

import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.DataSourceUsers;


import java.util.LinkedList;
import java.util.List;

public class DataSourceUsersMock implements DataSourceUsers {
    //Simulation d'une source de donnée

    //Tous les utilisateurs inscris
    private List<Utilisateur> _utilisateurs;

    @Override
    public Utilisateur lire(String email) {
        _utilisateurs= new LinkedList<>();
        //En att l'api rest seulement ces utilisateurs peuvent se connecter.
        _utilisateurs.add(new Utilisateur("Julien J","jj@jde.com","julien123"));
        _utilisateurs.add(new Utilisateur("Hedi","hedi@jde.com","hedi123"));
        _utilisateurs.add(new Utilisateur("Patrick","patrick@jde.com","jaimeUncleBob123"));
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
        _utilisateurs= new LinkedList<>();
        _utilisateurs.add(new Utilisateur("Julien J","jj@jde.com","julien123"));
        _utilisateurs.add(new Utilisateur("Hedi","hedi@jde.com","hedi123"));
        _utilisateurs.add(new Utilisateur("Patrick","patrick@jde.com","jaimeUncleBob123"));

        for (Utilisateur utilisateur:_utilisateurs) {
            int result=utilisateur.getCourriel().compareTo(utilisateurACreer.getCourriel());
            if(result==0) throw new UnsupportedOperationException("Erreur d'insertion dans la bd, email deja utiliser");
            break;
        }
        return utilisateurACreer;
    }

    @Override
    public Utilisateur tenterConnexion(String email, String mdp){
        _utilisateurs= new LinkedList<>();
        //En att l'api rest seulement ces utilisateurs peuvent se connecter.
        _utilisateurs.add(new Utilisateur("Julien J","jj@jde.com","julien123"));
        _utilisateurs.add(new Utilisateur("Hedi","hedi@jde.com","hedi1234"));
        _utilisateurs.add(new Utilisateur("Patrick","patrick@jde.com","jaimeUncleBob123"));
        Utilisateur utilisateurAConnecter=null;
        for (Utilisateur utilisateur:_utilisateurs) {
            int resultEmail=utilisateur.getCourriel().compareTo(email);
            int resultMdp=utilisateur.getMotPasse().compareTo(mdp);
            if (resultEmail==0 && resultMdp==0) utilisateurAConnecter=utilisateur;
        }
        return utilisateurAConnecter;
    }


}
