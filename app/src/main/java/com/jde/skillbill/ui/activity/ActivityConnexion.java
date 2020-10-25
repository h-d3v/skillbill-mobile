package com.jde.skillbill.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.jde.skillbill.R;
import com.jde.skillbill.donnees.APIRest.SourceDonneesAPIRest;
import com.jde.skillbill.donnees.mockDAO.SourceDonneesMock;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.presenteur.PresenteurConnexion;
import com.jde.skillbill.presentation.vue.VueConnexion;

public class ActivityConnexion extends AppCompatActivity {
    private PresenteurConnexion _presenteur;

    @Override
    final protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activite_connexion);
        Modele modele = new Modele();

        VueConnexion vue=new VueConnexion();
        _presenteur=new PresenteurConnexion(this,modele, vue);
        _presenteur.setDataSource(new SourceDonneesAPIRest());
        vue.setPresenteur(_presenteur);
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_connexion, vue);
        ft.commit();
    }
}
