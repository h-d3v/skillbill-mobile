package com.jde.skillbill.presentation.vue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jde.skillbill.R;
import com.jde.skillbill.presentation.IContratVuePresenteurVoirGroupes;
import com.jde.skillbill.presentation.presenteur.PresenteurVoirGroupes;
import com.jde.skillbill.presentation.vue.recyclerview_adapters.RVVoirGroupesAdapter;

public class VueVoirGroupes extends Fragment implements IContratVuePresenteurVoirGroupes.IVueVoirGroupes {

    private PresenteurVoirGroupes presenteurVoirGroupes;

    FloatingActionButton buttonCommencerActiviteCreerGroupe;
    RecyclerView rvMesGroupes;
    RVVoirGroupesAdapter rvVoirGroupesAdapter;
    Button buttonCommencerActivityAjouterFacture;
    Button buttonSoldeGroupe;
    TextView tvPasDeGroupes;

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        View racine = inflater.inflate(R.layout.frag_voir_groupes, container, false);
        buttonCommencerActiviteCreerGroupe=racine.findViewById(R.id.floatingActionButtonAjouterGroupe);
        buttonCommencerActivityAjouterFacture=racine.findViewById(R.id.btn_ajouter_facture_groupe);
        buttonSoldeGroupe=racine.findViewById(R.id.btn_detail_solde);
        tvPasDeGroupes= racine.findViewById(R.id.tvMessagePasDeGroupes);
        tvPasDeGroupes.setVisibility(View.INVISIBLE);
        rvMesGroupes=racine.findViewById(R.id.rvMesGroupes);
        rvVoirGroupesAdapter=new RVVoirGroupesAdapter(presenteurVoirGroupes);
        if(rvVoirGroupesAdapter.getItemCount()==0){
            tvPasDeGroupes.setVisibility(View.VISIBLE);
        }
        rvMesGroupes.setAdapter(rvVoirGroupesAdapter);
        rvMesGroupes.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMesGroupes.addItemDecoration(new DividerItemDecoration(rvMesGroupes.getContext(), DividerItemDecoration.VERTICAL));


        buttonCommencerActiviteCreerGroupe.setOnClickListener(view -> presenteurVoirGroupes.commencerCreerGroupeActivite());


        return racine;
    }

    @Override
    public void setPresenteur(PresenteurVoirGroupes presenteurVoirGroupes){
        this.presenteurVoirGroupes=presenteurVoirGroupes;
    }

    @Override
    public void rafraichir() {
        rvVoirGroupesAdapter.notifyDataSetChanged();
        if(rvVoirGroupesAdapter.getItemCount()>0){
            tvPasDeGroupes.setVisibility(View.INVISIBLE);
        }
    }


}
