package com.jde.skillbill.donnees.mockDAO;

import android.util.Log;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.GestionUtilisateur;
import com.jde.skillbill.presentation.modele.DAO;
import com.jde.skillbill.presentation.modele.DAOFactory;

import java.util.*;

public class DAOFactoryUtilisateurGroupeMock implements DAOFactory<Utilisateur, Groupe> {
    public static HashMap<DAO<Utilisateur>, List<DAO<Groupe>>> utilisateurGroupeHashMap =new HashMap<>();

    Utilisateur utilisateurFake = new GestionUtilisateur().creerUtilisateur("Fake", "", "allo@allo.com","");
    DAO<Utilisateur> utilisateurDAO = new DAOUtilisateurMock(utilisateurGroupeHashMap.size(), utilisateurFake);



    public DAOFactoryUtilisateurGroupeMock() {
        if(utilisateurGroupeHashMap.isEmpty()) {
            utilisateurGroupeHashMap.put(utilisateurDAO, new ArrayList<DAO<Groupe>>());
            utilisateurGroupeHashMap.get(utilisateurDAO).add(new DAOGroupeMock(0, new Groupe("test", utilisateurFake, null)));
        }
    }

    @Override
    public  List<DAO<Utilisateur>> lireTous() {
        LinkedList<DAO<Utilisateur>> listeDAOUtilisateurs= new LinkedList<>();
        Set<DAO<Utilisateur>> daoSet =utilisateurGroupeHashMap.keySet();
        for(DAO<Utilisateur> utilisateurDAO : daoSet){
            listeDAOUtilisateurs.add(utilisateurDAO);
        }

        return listeDAOUtilisateurs;
    }

    @Override
    public  List<DAO<Groupe>> lirePar(DAO<Utilisateur> utilisateurDAO) {
        return utilisateurGroupeHashMap.get(utilisateurDAO);
    }

    @Override
    public  DAO<Groupe> creerPar(DAO<Utilisateur> utilisateurDAO, Groupe groupe) {
        DAOGroupeMock  daoGroupeMock = new DAOGroupeMock(utilisateurGroupeHashMap.get(utilisateurDAO).size(), groupe);
        utilisateurGroupeHashMap.get(utilisateurDAO).add(daoGroupeMock);
        return daoGroupeMock;
    }


}
