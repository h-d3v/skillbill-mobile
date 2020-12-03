package com.jde.skillbill.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.jde.skillbill.R;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.GestionGroupes;

import com.jde.skillbill.donnees.APIRest.SourceDonneesAPIRest;

import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.presenteur.PresenteurVoirGroupes;
import com.jde.skillbill.presentation.vue.VueVoirGroupes;

public class ActivityVoirGroupes extends AppCompatActivity {
    PresenteurVoirGroupes presenteurVoirGroupes;
    private VueVoirGroupes vueVoirGroupes;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onStart();

    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activite_voir_groupes);
        vueVoirGroupes= new VueVoirGroupes();
        Modele modele= new Modele();
        presenteurVoirGroupes = new PresenteurVoirGroupes(modele,vueVoirGroupes, this, new SourceDonneesAPIRest());
        vueVoirGroupes.setPresenteur(presenteurVoirGroupes);
        fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.layout_voir_groupe, vueVoirGroupes);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if(vueVoirGroupes.isDrawerOpen()){
            vueVoirGroupes.closeDrawer();
        }
        else{
            super.onBackPressed();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == -1) {
            assert data != null;
            this.getIntent().putExtra("com.jde.skillbill.utlisateur_identifiant", (Utilisateur) data.getSerializableExtra("com.jde.skillbill.utlisateur_identifiant"));
            onStart();
        }

    }
}
