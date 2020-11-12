package com.jde.skillbill.presentation.presenteur;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.jde.skillbill.R;
import com.jde.skillbill.domaine.entites.Facture;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.domaine.entites.Utilisateur;

import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionFacture;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionGroupes;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionUtilisateur;

import com.jde.skillbill.presentation.IContratVuePresenteurVoirUnGroupe;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueVoirUnGroupe;
import com.jde.skillbill.ui.activity.ActivityVoirUnGroupe;

import java.util.List;

public class PresenteurVoirUnGroupe implements IContratVuePresenteurVoirUnGroupe.IPresenteurVoirUnGroupe {
    public static final int ERREUR_ACCES =0;
    public static final int AJOUT_OK=1;
    public static final int EMAIL_INCONNU=2;

    private static final int MSG_GET_MEMBRES = 4;
    private static final int MSG_GET_FACTURES = 5;
    private static final int MSG_SUPPRIMER_FACTURE = 6;
    private static final int MSG_AJOUTER_MEMBRES = 7;


    private Modele modele;
    private VueVoirUnGroupe vueVoirUnGroupe;
    private ActivityVoirUnGroupe activityVoirUnGroupe;
    private IGestionGroupes gestionGroupes;
    private IGestionFacture gestionFacture;
    private IGestionUtilisateur gestionUtilisateur;
    private Groupe groupeEncours;
    private String EXTRA_ID_UTILISATEUR="com.jde.skillbill.utlisateur_identifiant";
    private String EXTRA_GROUPE_POSITION= "com.jde.skillbill.groupe_identifiant";
    private Handler handler;
    private Thread filEsclave;
    private List<Facture> facturesGroupe; //Ajouter au modele


    /**
     *
     * @param modele
     * @param vueVoirUnGroupe
     * @param activityVoirUnGroupe
     * @param gestionGroupes
     * @param gestionFacture
     * @param gestionUtilisateur
     */

    @SuppressLint("HandlerLeak")
    public PresenteurVoirUnGroupe(Modele modele, VueVoirUnGroupe vueVoirUnGroupe, ActivityVoirUnGroupe activityVoirUnGroupe, IGestionGroupes gestionGroupes, IGestionFacture gestionFacture, IGestionUtilisateur gestionUtilisateur) {
        this.modele = modele;
        this.vueVoirUnGroupe = vueVoirUnGroupe;
        this.activityVoirUnGroupe = activityVoirUnGroupe;
        this.gestionGroupes = gestionGroupes;
        this.gestionFacture = gestionFacture;
        this.gestionUtilisateur = gestionUtilisateur;

        modele.setUtilisateurConnecte( (Utilisateur) activityVoirUnGroupe.getIntent().getSerializableExtra(EXTRA_ID_UTILISATEUR));
        groupeEncours= (Groupe) activityVoirUnGroupe.getIntent().getSerializableExtra(EXTRA_GROUPE_POSITION);

        handler =  new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                filEsclave = null;
                if(msg.what == MSG_GET_MEMBRES){
                    groupeEncours.setUtilisateurs((List<Utilisateur>) msg.obj);
                    vueVoirUnGroupe.rafraichir();
                }
                if(msg.what == MSG_GET_FACTURES){
                    facturesGroupe = (List<Facture>) msg.obj;
                    vueVoirUnGroupe.rafraichir();
                }
                if(msg.what== AJOUT_OK || msg.what==EMAIL_INCONNU|| msg.what==ERREUR_ACCES){


                    vueVoirUnGroupe.setVueAjouterMembres(msg.what);
                    if(msg.what==AJOUT_OK){
                        chargerMembres();
                        vueVoirUnGroupe.rafraichir();
                    }
                }
            }
        };
        chargerMembres();
        chargerFacturesGroupe();


    }

    private void chargerMembres(){
        filEsclave = new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = handler.obtainMessage(MSG_GET_MEMBRES);
                message.obj = gestionGroupes.trouverTousLesUtilisateurs(groupeEncours);
                handler.sendMessage(message);
            }
        });
        filEsclave.start();

    }


    /**
     *
     * @return noms des membres du groupe
     */
    @Override
    public String getMembresGroupe() {


        if(groupeEncours.getUtilisateurs()==null || groupeEncours.getUtilisateurs().size()<1) return null;
        String noms="";
        for(Utilisateur utilisateur : groupeEncours.getUtilisateurs() ){
            if(!utilisateur.equals(modele.getUtilisateurConnecte())){
                noms+=utilisateur.getNom();
                noms+=", ";
            }
        }
        noms=noms.substring(0, noms.length()-2)+".";
        return noms;
    }

    /**
     *
     * @return true si le groupe ne contient qu'un seul utilisateur
     */
    public boolean isGroupeSolo(){
        if( groupeEncours.getUtilisateurs()==null) return false;
        return  groupeEncours.getUtilisateurs().size()<=1;
    }


    /**
     *
     * @param courriel de l'utilisateur a ajouter
     */
    @Override
    public void ajouterUtilisateurAuGroupe(String courriel) {
        filEsclave = new Thread(new Runnable() {
            @Override
            public void run() {

                if(gestionUtilisateur.utilisateurExiste(courriel)){
                    if(gestionGroupes.ajouterMembre(groupeEncours, new Utilisateur("",courriel, "", Monnaie.CAD))) {
                        Message message = handler.obtainMessage(AJOUT_OK);
                        handler.sendMessage(message);
                    } else{
                        Message message = handler.obtainMessage(ERREUR_ACCES);
                        handler.sendMessage(message);
                    }
                } else{
                    Message message = handler.obtainMessage(EMAIL_INCONNU);
                    handler.sendMessage(message);
                }
            }
        });
        filEsclave.start();

    }

    /**
     *
     * @param courriel courriel auquel on veut envoyer une invitation
     */
    @Override
    public void envoyerCourriel(String courriel) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{ courriel});
        intent.putExtra(Intent.EXTRA_SUBJECT, activityVoirUnGroupe.getResources().getString(R.string.invitation_objet_courriel));
        intent.putExtra(Intent.EXTRA_TEXT,activityVoirUnGroupe.getResources().getString(R.string.texte_courriel) );

        activityVoirUnGroupe.startActivity(Intent.createChooser(intent, activityVoirUnGroupe.getResources().getString(R.string.invitation_objet_courriel)));
       }

    /**
     *
     * @return factures du groupes
     */
    public void chargerFacturesGroupe(){
        filEsclave = new Thread(new Runnable() {
            @Override
            public void run() {
              List<Facture> factures =  gestionGroupes.trouverToutesLesFactures(groupeEncours);
              Message message = handler.obtainMessage(MSG_GET_FACTURES);
              message.obj=factures;
              handler.sendMessage(message);
            }
        });
        filEsclave.start();

    }

    /**
     *
     * @param posFacture dans le rv
     * @return montant qu'a payer l'utilisateur dans la facture
     */

    //@Override
    public double getMontantFacturePayerParUser(int posFacture) {
        if(facturesGroupe==null) return 0;
        return facturesGroupe.get(posFacture).getMontantTotal();
    }


    /**
     *
     * @param position de la facture dans le rv
     */
    //Todo supprimer suelement par l'utilisateur qui l'a creer
    //Todo supprimer dans la bd ou le service
    //@Override
    public void requeteSupprimerFacture(int position) {
        vueVoirUnGroupe.rafraichir();

        this.facturesGroupe.remove(position);
    }


    public List<Facture> getFacturesGroupe(){
        return facturesGroupe;
    }


    /**
     *
     * @return nom du groupe
     */
    public String getNomGroupe(){
        return groupeEncours.getNomGroupe();
    }


}
