package com.jde.skillbill.presentation.vue;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.jde.skillbill.R;
import com.jde.skillbill.presentation.IContratVPCreerCompte;
import com.jde.skillbill.presentation.presenteur.PresenteurCreerCompte;

import java.util.Objects;

public class VueCreerCompte extends Fragment implements IContratVPCreerCompte.VueCreerCompte {
    private PresenteurCreerCompte _presenteur;
    private MaterialButton btnRegister;
    private MaterialButton btnRetour;
    private TextInputEditText tfMdp;
    private TextInputEditText tfMdpVerif;
    private TextInputEditText tfEmail;
    private TextInputEditText tfNom;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vue=inflater.inflate(R.layout.frag_register, container,false);
        btnRegister=vue.findViewById(R.id.btnRegister);
        btnRetour=vue.findViewById(R.id.btnRetour);
        tfNom=vue.findViewById(R.id.tfNom);
        tfEmail=vue.findViewById(R.id.tfEmail);
        tfMdp=vue.findViewById(R.id.tfPass);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Verifier le mot de passe, le nom et l'email. Voir Ticket dans git
                _presenteur.creerCompte();
            }
        });
        return vue;
    }

    @Override
    public void setPresenteur(PresenteurCreerCompte presenteurCreerCompte) {
        _presenteur=presenteurCreerCompte;
    }


    @Override
    public String getNom() {
        return Objects.requireNonNull(tfNom.getText()).toString();
    }

    @Override
    public String getEmail() {
        return Objects.requireNonNull(tfEmail.getText()).toString();
    }

    @Override
    public String getPass() {
        return Objects.requireNonNull(tfMdp.getText()).toString();
    }

    @Override
    public boolean verifierMDP() {
        return false;
    }

    @Override
    public boolean verifierNom() {
        return false;
    }

    @Override
    public boolean verifierEmail() {
        return false;
    }

    public void afficherEmailDejaPrit(){
        MaterialAlertDialogBuilder alertBuilder=new MaterialAlertDialogBuilder(Objects.requireNonNull(this.getContext()));
                alertBuilder.setTitle("Adresse e-mail deja utilisée");
                alertBuilder.setMessage("Veuillez choisir un autre courriel ou connectez-vous à votre compte");
                alertBuilder.show();
    }

    public void afficherCompteCreer(String nom, String email){
        MaterialAlertDialogBuilder alertBuilder=new MaterialAlertDialogBuilder(Objects.requireNonNull(this.getContext()));
        alertBuilder.setTitle("Compte bien creer");
        alertBuilder.setMessage("Courriel: "+email+"Nom: "+nom);
        alertBuilder.show();
    }
}
