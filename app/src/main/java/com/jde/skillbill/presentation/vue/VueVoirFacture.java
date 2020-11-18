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
    TextView textViewIamTiredOfThisShitOhDontAskWhyShowMeTheWayToTheNextWhiskeyBarOhDontAskWhy;
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

        super.editTextMontant.setText(presenteurVoirFacture.trouverMontantFactureEnCours());
        editTextTitre.setText(presenteurVoirFacture.trouverTitreFactureEnCours());
        date.setText(presenteurVoirFacture.trouverDateFactureEnCours());
        editTextTitre.setEnabled(false);
        date.setEnabled(false);
        editTextMontant.setEnabled(false);
        calendarView.setVisibility(View.GONE);
        spinnerChoixUtilisateursRedevables.setVisibility(View.GONE);
        spinnerChoix.setVisibility(View.GONE);

        boutonAnnuler.setOnClickListener(view -> {
            if(!estEnCoursDeModification){
                presenteurVoirFacture.envoyerRequeteModificationFacture();
            }else {
                estEnCoursDeModification = false;
                boutonModifier.setText(R.string.modifier);
                editTextTitre.setEnabled(false);
                editTextMontant.setEnabled(false);
                date.setEnabled(false);
            }
        });

        boutonModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(vue.getContext(),"dljklksdalkdsj", Toast.LENGTH_LONG);
                if(estEnCoursDeModification){
                    presenteurVoirFacture.envoyerRequeteModificationFacture();
                }
                else {
                    boutonModifier.setText(R.string.envoyer);
                    estEnCoursDeModification = true;
                    editTextMontant.setEnabled(true);
                    editTextTitre.setEnabled(true);
                    date.setEnabled(true);
                    boutonAnnuler.setText(R.string.action_annuler);
                }


            }
        });

        return vue;
    }
}
