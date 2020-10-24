package com.jde.skillbill.ui.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.jde.skillbill.R;
import com.jde.skillbill.domaine.interacteurs.GestionGroupes;
import com.jde.skillbill.domaine.interacteurs.GestionUtilisateur;
import com.jde.skillbill.domaine.interacteurs.ISourceDonnee;
import com.jde.skillbill.domaine.interacteurs.GestionFacture;
import com.jde.skillbill.donnees.mockDAO.SourceDonneesMock;
import com.jde.skillbill.presentation.IContratVuePresenteurVoirUnGroupe;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.presenteur.PresenteurVoirUnGroupe;
import com.jde.skillbill.presentation.vue.VueVoirUnGroupe;

public class ActivityVoirUnGroupe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activite_voir_un_groupe);


        VueVoirUnGroupe vueVoirUnGroupe= new VueVoirUnGroupe();
        Modele modele= new Modele();
        ISourceDonnee iSourceDonnee = new SourceDonneesMock();

        IContratVuePresenteurVoirUnGroupe.IPresenteurVoirUnGroupe presenteurVoirUnGroupe =new PresenteurVoirUnGroupe(modele, vueVoirUnGroupe,
                this, new GestionGroupes(iSourceDonnee), new GestionFacture(iSourceDonnee), new GestionUtilisateur(iSourceDonnee));

        vueVoirUnGroupe.setPresenteur(presenteurVoirUnGroupe);
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.layout_voir_un_groupe, vueVoirUnGroupe);
        fragmentTransaction.commit();
    }
}
