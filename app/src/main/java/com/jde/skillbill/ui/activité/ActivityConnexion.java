package com.jde.skillbill.ui.activité;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.jde.skillbill.R;
import com.jde.skillbill.dataSource.DataSourceUsersMock;
import com.jde.skillbill.presentation.IContratVPCreerCompte;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.presenteur.PresenteurConnexion;
import com.jde.skillbill.presentation.presenteur.PresenteurCreerCompte;
import com.jde.skillbill.presentation.vue.VueConnexion;
import com.jde.skillbill.presentation.vue.VueCreerCompte;

public class ActivityConnexion extends AppCompatActivity {
    private PresenteurConnexion _presenteur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activite_connexion);
        Modele modele = new Modele();

        VueConnexion vue=new VueConnexion();
        _presenteur=new PresenteurConnexion(this,modele, vue);
        _presenteur.setDataSource(new DataSourceUsersMock());
        vue.setPresenteur(_presenteur);
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_connexion, vue);
        ft.commit();
    }
}
