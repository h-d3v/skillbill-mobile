package com.jde.skillbill.presentation.vue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import com.jde.skillbill.R;
import com.jde.skillbill.presentation.IContratVPAjouterFacture;

public class VueAjouterFacture extends Fragment implements IContratVPAjouterFacture.IVueAjouterFacture {
    IContratVPAjouterFacture.IPresenteurAjouterFacture presenteurAjouterFacture;


    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        View racine = inflater.inflate(R.layout.frag_ajouter_facture, container, false);
        Button boutonAjouter= racine.findViewById(R.id.btn_ajouter_facture_groupe);
        Button boutonAnnuler= racine.findViewById(R.id.btnAnuller);
        EditText editTextMontant= racine.findViewById(R.id.txt_input_montant);
        Spinner spinnerChoix =racine.findViewById(R.id.spinner_choix_payeur);
        return racine;
    }

    @Override
    public void setPresenteur(IContratVPAjouterFacture.IPresenteurAjouterFacture presenteurAjouterFacture) {
        this.presenteurAjouterFacture=presenteurAjouterFacture;
    }


}
