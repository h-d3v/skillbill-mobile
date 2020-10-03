package com.jde.skillbill.presentation.presenteur;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.jde.skillbill.R;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.GestionGroupes;
import com.jde.skillbill.domaine.interacteurs.GestionUtilisateur;
import com.jde.skillbill.domaine.interacteurs.ISourceDonnee;
import com.jde.skillbill.domaine.interacteurs.interfaces.GestionFacture;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionFacture;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionGroupes;
import com.jde.skillbill.presentation.IContratVPAjouterFacture;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueAjouterFacture;
import com.jde.skillbill.ui.activity.ActivityAjouterFacture;
import com.jde.skillbill.ui.activity.ActivityVoirUnGroupe;

import java.time.LocalDate;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class PresenteurAjouterFacture implements IContratVPAjouterFacture.IPresenteurAjouterFacture {
    ActivityAjouterFacture activityAjouterFacture;
    VueAjouterFacture vueAjouterFacture;
    Modele modele;
    private String EXTRA_ID_UTILISATEUR="com.jde.skillbill.utlisateur_identifiant";
    private String EXTRA_GROUPE_POSITION= "com.jde.skillbill.groupe_identifiant";
    private final int position;
    private final ISourceDonnee iSourceDonnee;
    private final Utilisateur utilisateurConnecte;
    private final Groupe groupe;


    public PresenteurAjouterFacture(ActivityAjouterFacture activityAjouterFacture, VueAjouterFacture vueAjouterFacture, Modele modele, ISourceDonnee iSourceDonnee1) {
        this.activityAjouterFacture = activityAjouterFacture;
        this.vueAjouterFacture = vueAjouterFacture;
        this.modele = modele;
        utilisateurConnecte=new Utilisateur("", activityAjouterFacture.getIntent().getStringExtra(EXTRA_ID_UTILISATEUR),null);
        modele.setUtilisateurConnecte(utilisateurConnecte);
        position=activityAjouterFacture.getIntent().getIntExtra(EXTRA_GROUPE_POSITION,-1);
        iSourceDonnee=iSourceDonnee1;
        modele.setGroupesAbonnesUtilisateurConnecte(new GestionUtilisateur(iSourceDonnee).trouverGroupesAbonne(utilisateurConnecte));
        groupe = modele.getListGroupeAbonneUtilisateurConnecte().get(position);
    }


    @Override
    public void montrerListeUtilisateurMontant() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(activityAjouterFacture);
        builderSingle.setTitle("Coisir un payeur"); //TODO

        final ArrayAdapter<Utilisateur> arrayAdapter = new ArrayAdapter<Utilisateur>(activityAjouterFacture, android.R.layout.select_dialog_multichoice);
        IGestionGroupes gestionGroupes= new GestionGroupes(iSourceDonnee);
        List<Utilisateur> utilisateursGroupe= gestionGroupes.trouverTousLesUtilisateurs(groupe);
        for (Utilisateur utilisateur : utilisateursGroupe){
            arrayAdapter.add(utilisateur);
        }


        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which).getNom();
                AlertDialog.Builder builderInner = new AlertDialog.Builder(activityAjouterFacture);
                builderInner.setMessage(strName);
                builderInner.setTitle("Your Selected Item is");
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();
    }

    @Override
    public void ajouterFacture() {
        try{
            Double montant =  vueAjouterFacture.getMontantFactureInput();
            LocalDate date = vueAjouterFacture.getDateFactureInput();
            String titre = vueAjouterFacture.getTitreInput();
            if(titre==null){
                titre=activityAjouterFacture.getResources().getString(R.string.txt_facture_par_defaut)+" "+date.toString();
            }
            IGestionFacture iGestionFacture= new GestionFacture(iSourceDonnee);
            if(iGestionFacture.creerFacture(montant,utilisateurConnecte,date, groupe,  titre)){
                Intent intent = new Intent(activityAjouterFacture, ActivityVoirUnGroupe.class);
                activityAjouterFacture.startActivity(intent);
            };
        }catch (NumberFormatException  | DateTimeParseException  e){
            vueAjouterFacture.afficherMessageErreurAlertDialog(activityAjouterFacture.getResources().getString(R.string.txt_message_erreur)
            ,activityAjouterFacture.getResources().getString(R.string.titre_erreur_generique) );
        }

    }
}
