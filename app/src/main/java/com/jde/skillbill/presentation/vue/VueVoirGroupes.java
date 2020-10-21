package com.jde.skillbill.presentation.vue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        View racine = inflater.inflate(R.layout.frag_voir_groupes, container, false);
        buttonCommencerActiviteCreerGroupe=racine.findViewById(R.id.floatingActionButtonAjouterGroupe);
        buttonCommencerActivityAjouterFacture=racine.findViewById(R.id.btn_ajouter_facture_groupe);
        buttonSoldeGroupe=racine.findViewById(R.id.btn_detail_solde);
        rvMesGroupes=racine.findViewById(R.id.rvMesGroupes);
        rvVoirGroupesAdapter=new RVVoirGroupesAdapter(presenteurVoirGroupes);
        rvMesGroupes.setAdapter(rvVoirGroupesAdapter);
        rvMesGroupes.setLayoutManager(new LinearLayoutManager(getContext()));
        buttonCommencerActiviteCreerGroupe.setOnClickListener(view -> presenteurVoirGroupes.commencerCreerGroupeActivite());

        rvMesGroupes.addItemDecoration(new DividerItemDecoration(rvMesGroupes.getContext(), DividerItemDecoration.VERTICAL));
        return racine;
    }

    @Override
    public void setPresenteur(PresenteurVoirGroupes presenteurVoirGroupes){
        this.presenteurVoirGroupes=presenteurVoirGroupes;
    }



}
