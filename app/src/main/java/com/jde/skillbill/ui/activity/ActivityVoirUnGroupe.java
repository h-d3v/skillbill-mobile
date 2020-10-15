package com.jde.skillbill.ui.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.jde.skillbill.R;
import com.jde.skillbill.donnees.mockDAO.SourceDonneesMock;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.presenteur.PresenteurVoirGroupes;
import com.jde.skillbill.presentation.presenteur.PresenteurVoirUnGroupe;
import com.jde.skillbill.presentation.vue.VueVoirGroupes;
import com.jde.skillbill.presentation.vue.VueVoirUnGroupe;

public class ActivityVoirUnGroupe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activite_voir_un_groupe);
        VueVoirUnGroupe vueVoirUnGroupe= new VueVoirUnGroupe();
        Modele modele= new Modele();
        PresenteurVoirUnGroupe presenteurVoirUnGroupe = new PresenteurVoirUnGroupe(modele,vueVoirUnGroupe, this, new SourceDonneesMock());
        vueVoirUnGroupe.setPresenteur(presenteurVoirUnGroupe);
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.layout_voir_un_groupe, vueVoirUnGroupe);
        fragmentTransaction.commit();
    }
}
