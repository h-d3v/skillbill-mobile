package com.jde.skillbill.presentation.vue;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.jde.skillbill.R;
import com.jde.skillbill.presentation.IContratVuePresenteurCreerGroupe;

import java.util.List;

public class VueCreerGroupe extends Fragment implements IContratVuePresenteurCreerGroupe.VueCreerGroupe {

    private IContratVuePresenteurCreerGroupe.PresenteurCreerGroupe presenteurCreerGroupe;
    private TextView texteEntre;
    private Button boutonEnregistrer;
    private Button boutonAnnuler;
    private ProgressBar progressBar;

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        View racine = inflater.inflate(R.layout.frag_creer_groupe, container, false);
        texteEntre=racine.findViewById(R.id.textViewNomGroupeEntree);
        progressBar= racine.findViewById(R.id.progressBarAjoutGroupe);
        fermerProgressBar();

        boutonEnregistrer=racine.findViewById(R.id.btnEnregistrer);
        boutonEnregistrer.setOnClickListener(view -> presenteurCreerGroupe.creerGroupe());
        boutonAnnuler = racine.findViewById(R.id.btnAnuller);
        boutonAnnuler.setOnClickListener(view -> presenteurCreerGroupe.retournerEnArriere());

        boutonEnregistrer.setEnabled(false);

        texteEntre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!getNomGroupe().matches("^[A-z0-9]+(([',. -][A-z0-9])?[a-z-Z0-9]*)*$")){
                    boutonEnregistrer.setEnabled(false);
                    texteEntre.setError("Le nom du groupe doit etre valide.");
                }

                else {
                    boutonEnregistrer.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return racine;
    }

    @Override
    public void setPresenteurCreerGroupe(IContratVuePresenteurCreerGroupe.PresenteurCreerGroupe presenteurCreerGroupe) {
        this.presenteurCreerGroupe = presenteurCreerGroupe;
    }

    @Override
    public String getNomGroupe() {
        return texteEntre.getText().toString();
    }
    @Override
    public void fermerProgressBar(){
        progressBar.setVisibility(View.INVISIBLE);
    }
    @Override
    public void ouvrirProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }



}
