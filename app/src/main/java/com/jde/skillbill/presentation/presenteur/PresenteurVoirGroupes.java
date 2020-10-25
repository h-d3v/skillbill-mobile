package com.jde.skillbill.presentation.presenteur;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import com.jde.skillbill.BuildConfig;
import com.jde.skillbill.R;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.GestionGroupes;
import com.jde.skillbill.domaine.interacteurs.GestionUtilisateur;
import com.jde.skillbill.domaine.interacteurs.ISourceDonnee;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionGroupes;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionUtilisateur;
import com.jde.skillbill.presentation.IContratVuePresenteurVoirGroupes;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueVoirGroupes;
import com.jde.skillbill.ui.activity.ActivityAjouterFacture;
import com.jde.skillbill.ui.activity.ActivityCreerGroupe;
import com.jde.skillbill.ui.activity.ActivityVoirUnGroupe;

import java.util.ArrayList;
import java.util.List;



public class PresenteurVoirGroupes implements IContratVuePresenteurVoirGroupes.IPresenteurVoirGroupe {
    private Modele modele;
    private VueVoirGroupes vueVoirGroupes;
    private Activity activity;
    private String EXTRA_ID_UTILISATEUR="com.jde.skillbill.utlisateur_identifiant";
    private String EXTRA_GROUPE_POSITION= "com.jde.skillbill.groupe_identifiant";
    private Handler handler;
    private Thread filEsclave;
    private ISourceDonnee sourceDonnee;
    private static final int MSG_GET_GROUPES=1;



    public PresenteurVoirGroupes(Modele modele, VueVoirGroupes vueVoirGroupes, Activity activity,  ISourceDonnee sourceDonnee) {
        this.modele = modele;
        this.vueVoirGroupes = vueVoirGroupes;
        this.activity=activity;
        modele.setUtilisateurConnecte(new Utilisateur("", activity.getIntent().getStringExtra(EXTRA_ID_UTILISATEUR),null, Monnaie.CAD));
        this.sourceDonnee = sourceDonnee;

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                filEsclave = null;

                if(msg.what == MSG_GET_GROUPES){
                     modele.setModele( (Modele) msg.obj);

                    vueVoirGroupes.rafraichir();
                }

            }
        };
        chargerGroupes();

    }



    public void chargerGroupes(){
        IGestionUtilisateur gestionUtilisateur = new GestionUtilisateur(sourceDonnee);
        IGestionGroupes gestionGroupe = new GestionGroupes(sourceDonnee);
        filEsclave = new Thread(new Runnable() {
            @Override
            public void run() {

                List<Groupe> groupes= gestionUtilisateur.trouverGroupesAbonne(modele.getUtilisateurConnecte());
                modele.setGroupesAbonnesUtilisateurConnecte(groupes);

                if(groupes!=null){
                    int position= 0;
                    int taille = groupes.size();
                    Log.e("taille", String.valueOf(taille));
                    String[] textes = new String[taille];
                    while (position<taille){

                        try {
                            double solde = gestionGroupe.getSoldeParUtilisateurEtGroupe(modele.getUtilisateurConnecte(), modele.getListGroupeAbonneUtilisateurConnecte().get(position));
                            if (solde == 0) {
                                textes[position] = activity.getResources().getString(R.string.solde_utilisateur_nul);

                            } else if (solde < 0) {
                                textes[position] = activity.getResources().getString(R.string.solde_utilisateur_debiteur) + " " + Math.abs(solde);
                            } else {
                                textes[position] = activity.getResources().getString(R.string.solde_utilisateur_crediteur) + " " + solde;
                            }


                        } catch (NullPointerException e) { //TODO vraie exception
                            textes[position] =  activity.getResources().getString(R.string.pas_de_facture_dans_le_groupe);

                        }
                        position++;

                    }

                    modele.setSoldeParPosition(textes);
                }

                Message msg = handler.obtainMessage(MSG_GET_GROUPES, modele);
                handler.sendMessage(msg);

            }
        });
        filEsclave.start();
    }

    @Override
    public List<Groupe> getGroupeAbonnes() {

        return modele.getListGroupeAbonneUtilisateurConnecte();
    }

    @Override
    public void commencerVoirUnGroupeActivite(int position) {
        Intent intent = new Intent(activity, ActivityVoirUnGroupe.class);
        intent.putExtra(EXTRA_GROUPE_POSITION, position);
        intent.putExtra(EXTRA_ID_UTILISATEUR, modele.getUtilisateurConnecte().getCourriel());
        activity.startActivity(intent);
    }

    @Override
    public String getMessageSoldeParPosition(int position){
        if(modele.getSoldeParPosition().length ==0 || position>= modele.getSoldeParPosition().length) return "";
        return modele.getSoldeParPosition()[position];
    }


    @Override
    public String getNomGroupe(int position) {
        if (BuildConfig.DEBUG && position < 0) {
            throw new AssertionError("Assertion failed");
        }
        return modele.getListGroupeAbonneUtilisateurConnecte().get(position).getNomGroupe();
    }

    @Override
    public void commencerAjouterFactureActivite(int position) {
        Intent intent = new Intent(activity, ActivityAjouterFacture.class);
        intent.putExtra(EXTRA_GROUPE_POSITION, position);
        intent.putExtra(EXTRA_ID_UTILISATEUR, modele.getUtilisateurConnecte().getCourriel());
        activity.startActivity(intent);
    }

    @Override
    public void commencerCreerGroupeActivite() {
        Intent intent = new Intent(activity, ActivityCreerGroupe.class);
        intent.putExtra(EXTRA_ID_UTILISATEUR,modele.getUtilisateurConnecte().getCourriel());
        activity.startActivity(intent);
    }
}
