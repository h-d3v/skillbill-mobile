package com.jde.skillbill.ui.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.jde.skillbill.R;
import com.jde.skillbill.donnees.mockDAO.SourceDonneesMock;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.presenteur.PresenteurAjouterFacture;
import com.jde.skillbill.presentation.vue.VueAjouterFacture;

public class ActivityAjouterFacture extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activite_ajouter_facture);
        VueAjouterFacture vueAjouterFacture= new VueAjouterFacture();
        Modele modele= new Modele();
        PresenteurAjouterFacture presenteurAjouterFacture= new PresenteurAjouterFacture(this, vueAjouterFacture, modele, new SourceDonneesMock());
        vueAjouterFacture.setPresenteur(presenteurAjouterFacture);
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.layout_ajouter_facture, vueAjouterFacture);
        fragmentTransaction.commit();
    }
}
