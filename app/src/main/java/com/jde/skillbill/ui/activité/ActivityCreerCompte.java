package com.jde.skillbill.ui.activité;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.jde.skillbill.R;
import com.jde.skillbill.dataSource.DataSourceUsersMock;

import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.presenteur.PresenteurCreerCompte;
import com.jde.skillbill.presentation.vue.VueCreerCompte;

import java.net.MalformedURLException;
import java.net.URL;

public class ActivityCreerCompte extends AppCompatActivity {

    private PresenteurCreerCompte _presenteur;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activite_creer_compte);
        Modele modele = new Modele();

        VueCreerCompte vue=new VueCreerCompte();
        _presenteur=new PresenteurCreerCompte(this,modele, vue);
        _presenteur.setDataSource(new DataSourceUsersMock());
        vue.setPresenteur(_presenteur);
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_creer_compte, vue);
        ft.commit();

    }
}
