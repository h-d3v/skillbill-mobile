package com.jde.skillbill.presentation.vue;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jde.skillbill.R;
import com.jde.skillbill.presentation.IContratVPAjouterFacture;
import com.jde.skillbill.presentation.IContratVPVoirFacture;

import com.jde.skillbill.presentation.presenteur.PresenteurVoirFacture;

public class VueVoirFacture extends VueAjouterFacture implements IContratVPVoirFacture.VueVoirFacture {
    Button boutonModifier;
    PresenteurVoirFacture presenteurVoirFacture;
    boolean estEnCoursDeModification;

    @Override
    public void setPresenteur(IContratVPVoirFacture.PresenteurVoirFacture presenteur) {
        super.setPresenteur((IContratVPAjouterFacture.IPresenteurAjouterFacture) presenteur);
        this.presenteurVoirFacture = (PresenteurVoirFacture) presenteur;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vue = super.onCreateView(inflater,container,savedInstanceState);
        boutonModifier = super.boutonAjouter;


        toggleVueModifierOuVoir(false);

        boutonAnnuler.setOnClickListener(view -> {
            if(!estEnCoursDeModification){
                presenteurVoirFacture.redirigerVersListeFactures();
            }else {
                estEnCoursDeModification=!estEnCoursDeModification;
                toggleVueModifierOuVoir(estEnCoursDeModification);
            }
        });

        boutonModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(estEnCoursDeModification){
                    presenteurVoirFacture.envoyerRequeteModificationFacture();
                }
                else {
                    estEnCoursDeModification=!estEnCoursDeModification;
                    toggleVueModifierOuVoir(estEnCoursDeModification);

                }


            }
        });

        return vue;
    }

    private void toggleVueModifierOuVoir(boolean estEnCoursDeModification){
        if(estEnCoursDeModification){
            boutonModifier.setText(R.string.envoyer);
            estEnCoursDeModification = true;
            editTextMontant.setEnabled(true);
            editTextTitre.setEnabled(true);
            date.setEnabled(true);
            boutonAnnuler.setText(R.string.action_annuler);
            spinnerChoixUtilisateursRedevables.setVisibility(View.VISIBLE);
            spinnerChoix.setVisibility(View.VISIBLE);
        }
        else{
            estEnCoursDeModification = false;
            boutonModifier.setText(R.string.modifier);
            editTextTitre.setEnabled(false);
            editTextMontant.setEnabled(false);
            date.setEnabled(false);
            boutonAnnuler.setText(R.string.action_retour);
            spinnerChoixUtilisateursRedevables.setVisibility(View.GONE);
            spinnerChoix.setVisibility(View.GONE);
            super.editTextMontant.setText(presenteurVoirFacture.trouverMontantFactureEnCours());
            super.editTextTitre.setText(presenteurVoirFacture.trouverTitreFactureEnCours());
            super.date.setText(presenteurVoirFacture.trouverDateFactureEnCours());
        }
    }
}
