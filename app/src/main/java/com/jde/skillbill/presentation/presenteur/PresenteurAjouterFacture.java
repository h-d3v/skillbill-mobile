package com.jde.skillbill.presentation.presenteur;

import android.content.Intent;
import com.jde.skillbill.R;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionFacture;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionGroupes;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionUtilisateur;
import com.jde.skillbill.presentation.IContratVPAjouterFacture;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueAjouterFacture;
import com.jde.skillbill.ui.activity.ActivityAjouterFacture;
import com.jde.skillbill.ui.activity.ActivityVoirUnGroupe;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class PresenteurAjouterFacture implements IContratVPAjouterFacture.IPresenteurAjouterFacture {
    ActivityAjouterFacture activityAjouterFacture;
    VueAjouterFacture vueAjouterFacture;
    Modele modele;
    private String EXTRA_ID_UTILISATEUR="com.jde.skillbill.utlisateur_identifiant";
    private String EXTRA_GROUPE_POSITION= "com.jde.skillbill.groupe_identifiant";
    private final int position;

    private final Utilisateur utilisateurConnecte;
    private final Groupe groupe;
    private final IGestionFacture iGestionFacture;
    private final IGestionUtilisateur iGestionUtilisateur;
    private final IGestionGroupes iGestionGroupes;


    public PresenteurAjouterFacture(ActivityAjouterFacture activityAjouterFacture, VueAjouterFacture vueAjouterFacture, Modele modele, IGestionFacture gestionFacture, IGestionUtilisateur gestionUtilisateur, IGestionGroupes gestionGroupes) {
        this.activityAjouterFacture = activityAjouterFacture;
        this.vueAjouterFacture = vueAjouterFacture;
        this.modele = modele;
        utilisateurConnecte=new Utilisateur("", activityAjouterFacture.getIntent().getStringExtra(EXTRA_ID_UTILISATEUR),null, Monnaie.CAD);
        this.iGestionFacture = gestionFacture;
        this.iGestionUtilisateur = gestionUtilisateur;
        this.iGestionGroupes = gestionGroupes;
        modele.setUtilisateurConnecte(utilisateurConnecte);
        position=activityAjouterFacture.getIntent().getIntExtra(EXTRA_GROUPE_POSITION,-1);
        modele.setGroupesAbonnesUtilisateurConnecte(gestionUtilisateur.trouverGroupesAbonne(utilisateurConnecte));
        groupe = modele.getListGroupeAbonneUtilisateurConnecte().get(position);
    }


    /**
     *
     * @return liste des noms des utilisateurs du groupe
     */
    @Override
    public String[] presenterListeUtilsateur() {



        List<Utilisateur> utilisateursGroupe= iGestionGroupes.trouverTousLesUtilisateurs(groupe);
        String[] membres=new String[utilisateursGroupe.size()];
        int i=0;
        for (Utilisateur utilisateur : utilisateursGroupe){
            membres[i]=utilisateur.getNom();
            i++;
        }
        return membres;
    }

    /**
     * ajoute une facture a un groupe
     */
    @Override
    public void ajouterFacture() {
        try{
            Double montant =  vueAjouterFacture.getMontantFactureInput();
            LocalDate date = vueAjouterFacture.getDateFactureInput();
            String titre = vueAjouterFacture.getTitreInput();
            if(titre==null){
                titre=activityAjouterFacture.getResources().getString(R.string.txt_facture_par_defaut)+" "+date.toString();
            }

            if(iGestionFacture.creerFacture(montant,utilisateurConnecte,date, groupe,  titre)){
                Intent intent = new Intent(activityAjouterFacture, ActivityVoirUnGroupe.class);
                intent.putExtra(EXTRA_GROUPE_POSITION, position);
                intent.putExtra(EXTRA_ID_UTILISATEUR, modele.getUtilisateurConnecte().getCourriel());
                intent.putExtra("com.jde.skillbill.utlisateur_identifiant", modele.getUtilisateurConnecte().getCourriel());
                intent.putExtra("com.jde.skillbill.groupe_identifiant", activityAjouterFacture.getIntent().getIntExtra(EXTRA_GROUPE_POSITION, -1));
                activityAjouterFacture.startActivity(intent);
            };
        }catch (NumberFormatException  | DateTimeParseException  e){
             vueAjouterFacture.afficherMessageErreurAlertDialog(
                     activityAjouterFacture.getResources().getString(R.string.txt_message_erreur)
                    ,activityAjouterFacture.getResources().getString(R.string.titre_erreur_generique)
             );
        }

    }
}
