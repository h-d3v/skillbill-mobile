package com.jde.skillbill.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.jde.skillbill.R;
import com.jde.skillbill.donnees.APIRest.SourceDonneesAPIRest;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.presenteur.PresenteurConnexion;
import com.jde.skillbill.presentation.presenteur.PresenteurModifProfil;
import com.jde.skillbill.presentation.vue.VueConnexion;
import com.jde.skillbill.presentation.vue.VueModifProfil;

public class ActivityModifProfil extends AppCompatActivity {
    private PresenteurModifProfil _presenteur;

    @Override
    final protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activite_connexion);
        Modele modele = new Modele();



        VueModifProfil vue=new VueModifProfil();
        _presenteur=new PresenteurModifProfil(this,modele, vue, new SourceDonneesAPIRest());
        vue.setPresenteur(_presenteur);

        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_modif_profil, vue);
        ft.commit();
    }
}
