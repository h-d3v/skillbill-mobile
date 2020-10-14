package com.jde.skillbill.ui.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.jde.skillbill.R;
import com.jde.skillbill.donnees.mockDAO.SourceDonneesMock;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.presenteur.PresenteurVoirGroupes;
import com.jde.skillbill.presentation.vue.VueVoirGroupes;

public class ActivityVoirGroupes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onStart();

    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activite_voir_groupes);
        VueVoirGroupes vueVoirGroupes= new VueVoirGroupes();
        Modele modele= new Modele();
        PresenteurVoirGroupes presenteurVoirGroupes = new PresenteurVoirGroupes(modele,vueVoirGroupes, this, new SourceDonneesMock());
        vueVoirGroupes.setPresenteur(presenteurVoirGroupes);
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.layout_voir_groupe, vueVoirGroupes);
        fragmentTransaction.commit();
    }

}
