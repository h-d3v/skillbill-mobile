package com.jde.skillbill.ui.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.jde.skillbill.R;
import com.jde.skillbill.presentation.vue.VueCreerGroupe;

public class ActivityCreerGroupe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activite_creer_groupe);
        VueCreerGroupe vueCreerGroupe = new VueCreerGroupe();
        /*Modele modele= new Modele(new DAOFactoryUtilisateurGroupeMock());
        PresenteurCreerGroupe presenteurCreerGroupe = new PresenteurCreerGroupe(modele, vueCreerGroupe);
        vueCreerGroupe.setPresenteurCreerGroupe(presenteurCreerGroupe);
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.layout_creer_groupe, vueCreerGroupe);
        fragmentTransaction.commit();*/
    }
}
