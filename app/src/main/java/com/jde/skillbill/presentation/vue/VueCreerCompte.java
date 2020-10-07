package com.jde.skillbill.presentation.vue;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import com.jde.skillbill.R;
import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.presentation.IContratVPCreerCompte;
import com.jde.skillbill.presentation.presenteur.PresenteurCreerCompte;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;



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
    private AutoCompleteTextView editTextFilledExposedDropdown;


    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return la vue telle que crée
     */
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
        editTextFilledExposedDropdown = vue.findViewById(R.id.monnaies_dropdown);


        btnRegister.setEnabled(false);

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _presenteur.retourLogin();
            }
        });

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

        //verif du mdp
        tfMdp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
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

        //Peupler la liste de monnaies, on va chercher toutes les valeurs de l'enum
        List<String> monnaiesString = Stream.of(Monnaie.values()).map(Enum::name).collect(Collectors.toList());

        ArrayAdapter<String> adapterMonnaies = new ArrayAdapter<>(
                Objects.requireNonNull(getContext()),
                        R.layout.dropdown_menu_item,
                        monnaiesString);

        editTextFilledExposedDropdown.setAdapter(adapterMonnaies);

        //on initialise la devise à CAD
        editTextFilledExposedDropdown.setText(Monnaie.CAD.name(),false);

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
    public String getPassVerif() {
        return Objects.requireNonNull(tfMdpVerif.getText()).toString();
    }

    @Override
    public boolean tousLesChampsValides(){
        return emailValide && nomValide && mdpValide;
    }

    @Override
    public Monnaie getMonnaieChoisie(){
        return Monnaie.valueOf(editTextFilledExposedDropdown.getText().toString());
    }

    @Override
    public void afficherEmailDejaPrit(){
        MaterialAlertDialogBuilder alertBuilder=new MaterialAlertDialogBuilder(Objects.requireNonNull(this.getContext()));
                alertBuilder.setTitle("Adresse e-mail deja utilisée");
                alertBuilder.setMessage("Veuillez choisir un autre courriel ou connectez-vous à votre compte");
                alertBuilder.show();
    }

    //Pour test seulement, devra etre enlever
    @Override
    public void afficherCompteCreer(String nom, String email, Monnaie monnaie){
        MaterialAlertDialogBuilder alertBuilder=new MaterialAlertDialogBuilder(Objects.requireNonNull(this.getContext()));
        alertBuilder.setTitle("Compte bien creer");
        alertBuilder.setMessage("Courriel: "+email+"Nom: "+nom+" Monnaie choisie: "+monnaie.name());
        alertBuilder.show();
    }


    // les trois methodes suivantes sont la pour la verification dans le presenteur si on veut instaurer
    // une verifcation dans le presenteur. Pout l'instant inutile.
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
