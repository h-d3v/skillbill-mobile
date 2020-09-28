package com.jde.skillbill.presentation.vue;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        View racine = inflater.inflate(R.layout.frag_creer_groupe, container, false);
        texteEntre=racine.findViewById(R.id.textViewNomGroupeEntree);
        boutonEnregistrer=racine.findViewById(R.id.btnEnregistrer);
        boutonEnregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenteurCreerGroupe.creerGroupe();
            }
        });
        boutonAnnuler = racine.findViewById(R.id.btnAnuller);
        boutonAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenteurCreerGroupe.retournerEnArriere();
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



}
