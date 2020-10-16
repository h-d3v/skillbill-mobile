package com.jde.skillbill.presentation.presenteur;

import android.app.Activity;
import android.util.Log;
import com.jde.skillbill.domaine.entites.Facture;
import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.GestionUtilisateur;
import com.jde.skillbill.domaine.interacteurs.ISourceDonnee;
import com.jde.skillbill.donnees.mockDAO.SourceDonneesMock;
import com.jde.skillbill.presentation.IContratVPVoirUnGroupe;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueVoirUnGroupe;
import java.util.List;

public class PresenteurVoirUnGroupe implements IContratVPVoirUnGroupe.IPresenteurVoirUnGroupe {
    private Modele _modele;
    private VueVoirUnGroupe _vue;
    private Activity _activity;
    private String EXTRA_ID_UTILISATEUR="com.jde.skillbill.utlisateur_identifiant";
    private String EXTRA_GROUPE_POSITION= "com.jde.skillbill.groupe_identifiant";
    private ISourceDonnee iSourceDonnee;

    public PresenteurVoirUnGroupe(Modele modele, VueVoirUnGroupe vueVoirUnGroupe, Activity activity, ISourceDonnee iSourceDonnee) {
        _modele = modele;
        _vue= vueVoirUnGroupe;
        _activity=activity;
        modele.setUtilisateurConnecte(new Utilisateur("", activity.getIntent().getStringExtra(EXTRA_ID_UTILISATEUR),null, Monnaie.CAD));
        this.iSourceDonnee=iSourceDonnee;
    }



    public List<Facture> getFacturesGroupe(){
        _modele.setGroupesAbonnesUtilisateurConnecte( new GestionUtilisateur(new SourceDonneesMock()).trouverGroupesAbonne(_modele.getUtilisateurConnecte()));
        Log.i("courriel user connecte",_activity.getIntent().getStringExtra(EXTRA_ID_UTILISATEUR));
        return iSourceDonnee.lireFacturesParGroupe(
                _modele.getListGroupeAbonneUtilisateurConnecte().get(_activity.getIntent().getIntExtra(EXTRA_GROUPE_POSITION, 0)));
    }

    //TODO faire en sorte que ce montant reflete ce que l'utilisateur dois payer et non ce qu'il a deja payer
    @Override
    public double getMontantFacturePayerParUser(int posFacture) {
        return this.getFacturesGroupe().get(posFacture).getMontantPayeParParUtilisateur().get(_modele.getUtilisateurConnecte());
    }


}
