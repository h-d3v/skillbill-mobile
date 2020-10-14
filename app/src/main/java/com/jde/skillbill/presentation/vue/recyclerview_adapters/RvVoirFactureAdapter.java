package com.jde.skillbill.presentation.vue.recyclerview_adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jde.skillbill.presentation.IContratVPVoirUnGroupe;
import com.jde.skillbill.presentation.presenteur.PresenteurVoirGroupes;
import com.jde.skillbill.presentation.presenteur.PresenteurVoirUnGroupe;

public class RvVoirFactureAdapter extends RecyclerView.Adapter implements IContratVPVoirUnGroupe.IVueVoirUneFacture {
    PresenteurVoirUnGroupe _presenteur;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void setNomFacture() {

    }

    @Override
    public void setMontantFacture() {

    }
}
