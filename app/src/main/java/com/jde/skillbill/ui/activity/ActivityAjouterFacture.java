package com.jde.skillbill.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.jde.skillbill.R;
import com.jde.skillbill.domaine.interacteurs.GestionGroupes;
import com.jde.skillbill.domaine.interacteurs.GestionUtilisateur;
import com.jde.skillbill.domaine.interacteurs.ISourceDonnee;
import com.jde.skillbill.domaine.interacteurs.GestionFacture;
import com.jde.skillbill.donnees.mockDAO.SourceDonneesMock;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.presenteur.PresenteurAjouterFacture;
import com.jde.skillbill.presentation.vue.VueAjouterFacture;

public class ActivityAjouterFacture extends AppCompatActivity {
    private static final int REQUETE_PRENDRE_PHOTO= 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activite_ajouter_facture);
        VueAjouterFacture vueAjouterFacture= new VueAjouterFacture();
        Modele modele= new Modele();
        ISourceDonnee sourceDonnee = new SourceDonneesMock();
        PresenteurAjouterFacture presenteurAjouterFacture= new PresenteurAjouterFacture(this, vueAjouterFacture, modele, new GestionFacture(sourceDonnee), new GestionUtilisateur(sourceDonnee), new GestionGroupes(sourceDonnee));
        vueAjouterFacture.setPresenteur(presenteurAjouterFacture);

        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.layout_ajouter_facture, vueAjouterFacture);
        fragmentTransaction.commit();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUETE_PRENDRE_PHOTO && resultCode == ActivityVoirGroupes.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            if (imageBitmap != null) {
                ImageView imageView = findViewById(R.id.imageFact);
                imageView.setImageBitmap(imageBitmap);
            }


        }
    }
}
