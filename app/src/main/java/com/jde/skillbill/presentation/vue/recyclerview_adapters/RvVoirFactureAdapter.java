package com.jde.skillbill.presentation.vue.recyclerview_adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.jde.skillbill.R;
import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.presentation.IContratVPVoirUnGroupe;
import com.jde.skillbill.presentation.presenteur.PresenteurVoirUnGroupe;

import java.util.Locale;

public class RvVoirFactureAdapter extends RecyclerView.Adapter implements IContratVPVoirUnGroupe.IAdapterVoirUneFacture {
    PresenteurVoirUnGroupe _presenteur;
    MaterialButton btnNomActivite;
    TextView tvMontant;
    TextView tvMontantConversion;

    public RvVoirFactureAdapter(PresenteurVoirUnGroupe presenteur){
        super();
        _presenteur=presenteur;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout constraintLayout= (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.vue_rv_facture_item, parent, false);
        return new  RecyclerView.ViewHolder(constraintLayout){};
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        btnNomActivite =holder.itemView.findViewById(R.id.nomActivite);
        tvMontant =holder.itemView.findViewById(R.id.tvMontant);
        tvMontantConversion = holder.itemView.findViewById(R.id.tcMontantConvert);

        btnNomActivite.setText(_presenteur.getFacturesGroupe().get(position).getLibelle());
        btnNomActivite.setOnClickListener(view -> _presenteur.commencerVoirDetailFacture(position));

        Monnaie monnaieUser= _presenteur.getMonnaieUserConnecte();
        Monnaie monnaieGroupe = _presenteur.getMonnaieGroupe();
        Double montantFacturePayer=_presenteur.getMontantFacturePayerParUser(position);
        tvMontant.setText(String.format(Locale.CANADA,"%.2f",montantFacturePayer)+" "+monnaieGroupe.getSymbol());
        if (!monnaieUser.name().equals(monnaieGroupe.name())){
            double montantConvertiCAD = montantFacturePayer*monnaieGroupe.getTauxDevise();
            double montantConverti = montantConvertiCAD*monnaieUser.getTauxCad();
            tvMontantConversion.setText(String.format(Locale.CANADA,"%.2f",montantConverti)+ monnaieUser.getSymbol());
        }
    }

    @Override
    public int getItemCount() {
        if (_presenteur==null || _presenteur.getFacturesGroupe()==null) return 0;
        return _presenteur.getFacturesGroupe().size();
    }

    @Override
    public void setNomFacture(String nomActivite) {
    }

    @Override
    public void setMontantFacture(double montant) {

    }

    @Override
    public void setPresenteur(PresenteurVoirUnGroupe presenteur) {
        _presenteur=presenteur;
    }
}
