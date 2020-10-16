package com.jde.skillbill.presentation.vue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jde.skillbill.R;
import com.jde.skillbill.presentation.IContratVPVoirUnGroupe;
import com.jde.skillbill.presentation.presenteur.PresenteurVoirUnGroupe;
import com.jde.skillbill.presentation.vue.recyclerview_adapters.RVVoirGroupesAdapter;
import com.jde.skillbill.presentation.vue.recyclerview_adapters.RvVoirFactureAdapter;

public class VueVoirUnGroupe extends Fragment implements IContratVPVoirUnGroupe.IVueVoirUnGroupe {
    PresenteurVoirUnGroupe _presenteur;
    TextView tvNomGroupe;
    RecyclerView rvFactures;
    RvVoirFactureAdapter rvFacturesAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View racine = inflater.inflate(R.layout.frag_voir_un_groupe, container, false);
        tvNomGroupe=racine.findViewById(R.id.tvNomGroupe);
        rvFactures=racine.findViewById(R.id.rvFactures);
        rvFacturesAdapter=new RvVoirFactureAdapter(_presenteur);
        rvFactures.setAdapter(rvFacturesAdapter);
        rvFactures.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFactures.addItemDecoration(new DividerItemDecoration(rvFactures.getContext(), DividerItemDecoration.VERTICAL));
        return racine;
    }

    @Override
    public void setNomGroupe(String nom) {
        tvNomGroupe.setText(nom);
    }

    @Override
    public void setPresenteur(PresenteurVoirUnGroupe presenteur) {
        _presenteur=presenteur;
    }
}
