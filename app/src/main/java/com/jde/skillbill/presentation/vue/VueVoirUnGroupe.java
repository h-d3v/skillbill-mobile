package com.jde.skillbill.presentation.vue;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabLayout;
import com.jde.skillbill.R;
import com.jde.skillbill.presentation.IContratVuePresenteurVoirUnGroupe;
import com.jde.skillbill.presentation.presenteur.PresenteurVoirUnGroupe;

public class VueVoirUnGroupe extends Fragment implements IContratVuePresenteurVoirUnGroupe.IVueVoirUnGroupe {


   private PresenteurVoirUnGroupe _presenteur;
   private TextView tvNomGroupe;
   private TabLayout tabLayout;
   private TextView detailMembres;
   private Button ajouterMembre;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View racine = inflater.inflate(R.layout.frag_voir_un_groupe, container, false);
        ajouterMembre = racine.findViewById(R.id.btnAjouter);
        detailMembres = racine.findViewById(R.id.detailMembres);
        detailMembres.setVisibility(View.INVISIBLE);
        ajouterMembre.setVisibility(View.INVISIBLE);
        tvNomGroupe=racine.findViewById(R.id.tvNomGroupe);
        tabLayout=racine.findViewById(R.id.tabLayoutFactureMembres);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==1){
                    detailMembres.setVisibility(View.VISIBLE);
                    ajouterMembre.setVisibility(View.VISIBLE);
                    if(_presenteur.isGroupeSolo()){
                        detailMembres.setText("Vous n'avez aucun autre membres dans ce groupe, cliquez sur Ajouter pour en ajouter");
                    }else{
                        detailMembres.setText("Vous partagez vos factures avec "+_presenteur.getMembresGroupe());
                    }
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if(tab.getPosition()==1) {
                    detailMembres.setVisibility(View.INVISIBLE);
                    ajouterMembre.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        ajouterMembre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(racine.getContext());
                builder.setTitle("Entrez le courriel du membre à ajouter");
                final View customLayout = getLayoutInflater().inflate(R.layout.alert_dialog_ajouter_membre, null);
                builder.setView(customLayout);
                builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        EditText champsCourriel = customLayout.findViewById(R.id.editTextTextEmailAddressAjouterMembre);
                        int code = _presenteur.ajouterUtilisateurAuGroupe(champsCourriel.getText().toString());
                        if(_presenteur.AJOUT_OK==code){
                            detailMembres.setText("Vous partagez vos factures avec "+_presenteur.getMembresGroupe());
                        }else if(_presenteur.EMAIL_INCONNU==code){
                            Toast.makeText(racine.getContext(), "Email inconnu, vous pouvez l'inviter ", Toast.LENGTH_LONG).show();
                        }else if(_presenteur.ERREUR_ACCES==code){
                            Toast.makeText(racine.getContext(), "Vous avez dejà ajouté cet utilisateur ", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.setNeutralButton("Inviter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText champsCourriel = customLayout.findViewById(R.id.editTextTextEmailAddressAjouterMembre);
                        _presenteur.envoyerCourriel(champsCourriel.getText().toString());
                        Toast.makeText(racine.getContext(), "NON IMPLÉMENTÉ DEMMARRERA UNE ACTIVITE AVEC COLLABORATION EMAIL ", Toast.LENGTH_LONG).show();
                    }
                });

                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });
        return racine;
    }

    @Override
    public void setPresenteur(IContratVuePresenteurVoirUnGroupe.IPresenteurVoirUnGroupe presenteur) {
        _presenteur = (PresenteurVoirUnGroupe) presenteur;
    }
}
