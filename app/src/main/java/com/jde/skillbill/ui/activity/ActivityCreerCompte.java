package com.jde.skillbill.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


import com.jde.skillbill.R;

import com.jde.skillbill.donnees.APIRest.SourceDonneesAPIRest;
import com.jde.skillbill.donnees.mockDAO.SourceDonneesMock;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.presenteur.PresenteurCreerCompte;
import com.jde.skillbill.presentation.vue.VueCreerCompte;


public class ActivityCreerCompte extends AppCompatActivity {

    private PresenteurCreerCompte _presenteur;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activite_creer_compte);

        Modele modele = new Modele();

        VueCreerCompte vue=new VueCreerCompte();
        _presenteur=new PresenteurCreerCompte(this,modele, vue);
        _presenteur.setDataSource(new SourceDonneesAPIRest());
        vue.setPresenteur(_presenteur);
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_creer_compte, vue);
        ft.commit();

    }
}
