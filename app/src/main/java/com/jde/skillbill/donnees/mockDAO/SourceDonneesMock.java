package com.jde.skillbill.donnees.mockDAO;

import android.util.Log;
import com.jde.skillbill.domaine.entites.Facture;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.ISourceDonnee;

import java.time.LocalDate;
import java.util.*;

public class SourceDonneesMock implements ISourceDonnee{
    public static HashMap<Utilisateur, List<Groupe>> utilisateurGroupeHashMap =new HashMap<>();
    public static  HashMap<Groupe, List<Utilisateur>> groupeUtilisateursHashmap = new HashMap<>();
    public static HashMap<Groupe, List<Facture>> groupeFactureHashMap= new HashMap<>();
    private List<Utilisateur> _utilisateurs;

    public SourceDonneesMock(){
        _utilisateurs= new LinkedList<>();
        //En att l'api rest seulement ces utilisateurs peuvent se connecter.
        // Initialisation des donnes Mock
        _utilisateurs.add(new Utilisateur("Julien J","jj@jde.com","julien123"));
        _utilisateurs.add(new Utilisateur("Hedi","hedi@jde.com","hedi1234"));
        _utilisateurs.add(new Utilisateur("Patrick","patrick@jde.com","jaimeUncleBob123"));
        if(utilisateurGroupeHashMap.isEmpty()) {
            //L'utilisateur 0 est julien.
            for(Utilisateur u : _utilisateurs){
                utilisateurGroupeHashMap.putIfAbsent(u, new ArrayList<>());
            }
            utilisateurGroupeHashMap.get(_utilisateurs.get(0)).add( new Groupe("test groupe 1", _utilisateurs.get(0), null));
            utilisateurGroupeHashMap.get(_utilisateurs.get(0)).add( new Groupe("test groupe 2",_utilisateurs.get(0), null));
            utilisateurGroupeHashMap.get(_utilisateurs.get(1)).add( new Groupe("test groupe 2",_utilisateurs.get(0), null));
        }
        if (groupeUtilisateursHashmap.isEmpty()) {
            for (Utilisateur utilisateur : utilisateurGroupeHashMap.keySet()){
                for (Groupe groupe : utilisateurGroupeHashMap.get(utilisateur)){
                    groupeUtilisateursHashmap.putIfAbsent(groupe, new ArrayList<>());
                    groupeUtilisateursHashmap.get(groupe).add(utilisateur);
                }
            }
        }
    }

    @Override
    public  boolean creerGroupeParUtilisateur(Utilisateur utilisateur, Groupe groupe) {
        if(utilisateurGroupeHashMap.get(utilisateur)==null && !utilisateurGroupeHashMap.containsKey(utilisateur)) {
            utilisateurGroupeHashMap.put(utilisateur,new ArrayList<Groupe>());
        }
        utilisateurGroupeHashMap.get(utilisateur).add(groupe);
        return utilisateurGroupeHashMap.get(utilisateur).add(groupe);
    }

    @Override
    public List<Groupe> lireTousLesGroupesAbonnes(Utilisateur utilisateur){
        return utilisateurGroupeHashMap.get(utilisateur);
    }

    @Override
    public List<Utilisateur> lireUTilisateurParGroupe(Groupe groupe) {
        return groupeUtilisateursHashmap.get(groupe);
    }

    @Override
    public boolean ajouterFacture(double montantTotal, Utilisateur utilisateurPayeur, LocalDate localDate, Groupe groupe, String titre) {
        if(groupeFactureHashMap.get(groupe)==null){
            groupeFactureHashMap.putIfAbsent(groupe, new ArrayList<>());
        }
        Facture facture=new Facture();
        facture.setDateFacture(localDate);
        HashMap<Utilisateur,Double> hashMap= new HashMap<>();
        hashMap.put(utilisateurPayeur, montantTotal);
        facture.setMontantPayeParParUtilisateur(hashMap);
        facture.setLibelle(titre);;
        boolean estReussi = groupeFactureHashMap.get(groupe).add(facture);
        Set<Utilisateur> set = new HashSet<>();
        set.add(utilisateurPayeur);
        Log.e("Source Donnees Mock", groupeFactureHashMap.get(groupe).get(groupeFactureHashMap.get(groupe).size()-1).getLibelle()+" "+
                groupeFactureHashMap.get(groupe).get(groupeFactureHashMap.get(groupe).size()-1).getDateFacture().toString()+" "+
                groupeFactureHashMap.get(groupe).get(groupeFactureHashMap.get(groupe).size()-1).getMontantPayeParParUtilisateur(set).get(utilisateurPayeur)+" ");
        return estReussi;
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
