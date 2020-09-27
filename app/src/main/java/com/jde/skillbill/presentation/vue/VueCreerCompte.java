package com.jde.skillbill.presentation.vue;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.jde.skillbill.R;
import com.jde.skillbill.presentation.IContratVPCreerCompte;
import com.jde.skillbill.presentation.presenteur.PresenteurCreerCompte;

import java.util.Objects;

public class VueCreerCompte extends Fragment implements IContratVPCreerCompte.VueCreerCompte {
    private PresenteurCreerCompte _presenteur;
    private MaterialButton btnRegister;
    private MaterialButton btnRetour;

    private TextInputEditText tfMdp;
    private TextInputLayout tlMdp;

    private TextInputEditText tfMdpVerif;
    private TextInputLayout tlMdpVerif;

    private TextInputEditText tfEmail;
    private TextInputEditText tlEmail;

    private TextInputEditText tfNom;
    private TextInputLayout tlNom;

    private boolean mdpValide=false;
    private boolean nomValide=false;
    private boolean emailValide=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vue=inflater.inflate(R.layout.frag_register, container,false);

        btnRegister=vue.findViewById(R.id.btnRegister);
        btnRetour=vue.findViewById(R.id.btnRetour);
        tfNom=vue.findViewById(R.id.tfNom);
        tlNom=vue.findViewById(R.id.tlNom);
        tfEmail=vue.findViewById(R.id.tfEmail);
        tfMdp=vue.findViewById(R.id.tfPass);
        tfMdpVerif=vue.findViewById(R.id.tfMdpVerif);


        btnRegister.setEnabled(false);

        //TODO le onclick du boutton qui redirige vers le connexion.
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _presenteur.creerCompte();
            }
        });

        //verification du nom
        tfNom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!getNom().matches("[A-z\\s]+")) {
                    btnRegister.setEnabled(false);
                    tfNom.setError("Le nom doit contenir uniquement des lettres.");
                }
                else{
                   nomValide=true;
                   if(tousLesChampsValides()){
                       btnRegister.setEnabled(true);
                   }
                }
            }
        });

        //verif du email
        tfEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(!getEmail().matches("[A-z0-9._%+-]+@[A-z0-9.-]+\\.[A-z]{2,4}")){
                    btnRegister.setEnabled(false);
                    tfEmail.setError("Veuillez entrer un email valide.");
                }
                else{
                    emailValide=true;
                    if (tousLesChampsValides()) {
                        btnRegister.setEnabled(true);
                    }
                }
            }
        });

        //verif fu mdp
        tfMdp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!getPass().matches("[.\\S]+") || getPass().length()<8){
                    btnRegister.setEnabled(false);
                    tfMdp.setError("Le mot de passe ne doit pas contenir d'espace et plus de 8 caractères.");
                }
                else {
                    mdpValide = true;
                    if (tousLesChampsValides()) {
                        btnRegister.setEnabled(true);
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //verif si le second champ du mot de passe correspond au premier
        tfMdpVerif.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(!getPass().equals(getPassVerif())){
                    btnRegister.setEnabled(false);
                    tfMdpVerif.setError("Le mot de passe ne correspond pas");
                    mdpValide=false;
                }
                else{
                    mdpValide=true;
                    if (tousLesChampsValides()) {
                        btnRegister.setEnabled(true);
                    }
                }
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

    public String getPassVerif() {
        return Objects.requireNonNull(tfMdpVerif.getText()).toString();
    }

    public boolean tousLesChampsValides(){
        return emailValide && nomValide && mdpValide;
    }


    public void afficherEmailDejaPrit(){
        MaterialAlertDialogBuilder alertBuilder=new MaterialAlertDialogBuilder(Objects.requireNonNull(this.getContext()));
                alertBuilder.setTitle("Adresse e-mail deja utilisée");
                alertBuilder.setMessage("Veuillez choisir un autre courriel ou connectez-vous à votre compte");
                alertBuilder.show();
    }

    //Pour test seulement, devra etre enlever
    public void afficherCompteCreer(String nom, String email){
        MaterialAlertDialogBuilder alertBuilder=new MaterialAlertDialogBuilder(Objects.requireNonNull(this.getContext()));
        alertBuilder.setTitle("Compte bien creer");
        alertBuilder.setMessage("Courriel: "+email+"Nom: "+nom);
        alertBuilder.show();
    }

    // les trois methodes suivantes sont la pour la verification dans le presenteur,
    //pour l'instant pas besoin
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

}
