package com.jde.skillbill.presentation.vue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabLayout;
import com.jde.skillbill.R;
import com.jde.skillbill.presentation.IContratVuePresenteurVoirUnGroupe;
import com.jde.skillbill.presentation.presenteur.PresenteurVoirUnGroupe;

public class VueVoirUnGroupe extends Fragment implements IContratVuePresenteurVoirUnGroupe.IVueVoirUnGroupe {
    private IContratVuePresenteurVoirUnGroupe.IPresenteurVoirUnGroupe iPresenteurVoirUnGroupe;

    PresenteurVoirUnGroupe _presenteur;
    TextView tvNomGroupe;
    TabLayout tabLayout;
    TextView detailMembres;
    Button ajouterMembre;

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
        return racine;
    }

    @Override
    public void setPresenteur(IContratVuePresenteurVoirUnGroupe.IPresenteurVoirUnGroupe presenteur) {
        _presenteur = (PresenteurVoirUnGroupe) presenteur;
    }
}
