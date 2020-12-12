package com.jde.skillbill.presentation.vue;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
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
    private ProgressBar progressBar;

    private boolean mdpValide = false;
    private boolean nomValide = false;
    private boolean emailValide = false;
    private AutoCompleteTextView editTextFilledExposedDropdown;


    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return la vue telle que crée
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vue = inflater.inflate(R.layout.frag_register, container, false);

        btnRegister = vue.findViewById(R.id.btnRegister);
        btnRetour = vue.findViewById(R.id.btnRetour);
        tfNom = vue.findViewById(R.id.tfNom);
        tlNom = vue.findViewById(R.id.tlNom);
        tfEmail = vue.findViewById(R.id.tfEmail);
        tfMdp = vue.findViewById(R.id.tfPass);
        progressBar = vue.findViewById(R.id.progressBarRegister);
        fermerProgressBar();
        tfMdpVerif = vue.findViewById(R.id.tfMdpVerif);
        editTextFilledExposedDropdown = vue.findViewById(R.id.monnaies_dropdown);


        btnRegister.setEnabled(false);

        btnRetour.setOnClickListener(v -> _presenteur.retourLogin());

        btnRegister.setOnClickListener(v -> _presenteur.creerCompte());

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
                if (!getNom().matches("[A-z\\s]+")) {
                    btnRegister.setEnabled(false);
                    tfNom.setError("Le nom doit contenir uniquement des lettres.");
                } else {
                    nomValide = true;
                    if (tousLesChampsValides()) {
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
                if (!getEmail().matches("[A-z0-9._%+-]+@[A-z0-9.-]+\\.[A-z]{2,4}")) {
                    btnRegister.setEnabled(false);
                    tfEmail.setError("Veuillez entrer un email valide.");
                } else {
                    emailValide = true;
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
                if (!getPass().matches("[.\\S]+") || getPass().length() < 8) {
                    btnRegister.setEnabled(false);
                    tfMdp.setError("Le mot de passe ne doit pas contenir d'espace et plus de 8 caractères.");
                } else if (!getPass().equals(getPassVerif())) {
                    btnRegister.setEnabled(false);
                    tfMdpVerif.setError("Les mots de passes ne correspondent pas");
                    mdpValide = false;
                } else {
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
                if (!getPass().equals(getPassVerif())) {
                    btnRegister.setEnabled(false);
                    tfMdpVerif.setError("Le mot de passe ne correspondent pas");
                    mdpValide = false;
                } else {
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

        //Peupler la liste de monnaies, on va chercher toutes les valeurs de l'enum
        List<String> monnaiesString = Stream.of(Monnaie.values()).map(Enum::name).collect(Collectors.toList());

        ArrayAdapter<String> adapterMonnaies = new ArrayAdapter<>(
                requireContext(),
                R.layout.dropdown_menu_item,
                monnaiesString);

        editTextFilledExposedDropdown.setAdapter(adapterMonnaies);

        //on initialise la devise à CAD
        editTextFilledExposedDropdown.setText(Monnaie.CAD.name(), false);

        return vue;
    }

    /**
     * @param presenteurCreerCompte
     */
    @Override
    public void setPresenteur(PresenteurCreerCompte presenteurCreerCompte) {
        _presenteur = presenteurCreerCompte;
    }

    /**
     * @return nom du user
     */
    @Override
    public String getNom() {
        return Objects.requireNonNull(tfNom.getText()).toString();
    }

    /**
     * @return email du user
     */
    @Override
    public String getEmail() {
        return Objects.requireNonNull(tfEmail.getText()).toString();
    }

    /**
     * @return pass du user
     */
    @Override
    public String getPass() {
        return Objects.requireNonNull(tfMdp.getText()).toString();
    }

    /**
     * @return pass du user
     */
    @Override
    public String getPassVerif() {
        return Objects.requireNonNull(tfMdpVerif.getText()).toString();
    }

    /**
     * @return true si tous les champs remplis par le user sont valides
     */
    @Override
    public boolean tousLesChampsValides() {
        return emailValide && nomValide && mdpValide;
    }

    /**
     * @return monnaie choisie par le user
     */
    @Override
    public Monnaie getMonnaieChoisie() {
        return Monnaie.valueOf(editTextFilledExposedDropdown.getText().toString());
    }

    /**
     * affiche un message comme quoi que l'email est deja prit
     */
    @Override
    public void afficherEmailDejaPrit() {
        MaterialAlertDialogBuilder alertBuilder = new MaterialAlertDialogBuilder(this.requireContext());
        alertBuilder.setTitle("Adresse courriel déjà utilisée");
        alertBuilder.setMessage("Veuillez choisir un autre courriel ou connectez-vous à votre compte");
        alertBuilder.show();
    }

    /**
     * Pour test seulement, devra etre enlever
     *
     * @param nom     du compte creer
     * @param email   du compte creer
     * @param monnaie du compte creer
     */
    @Override
    public void afficherCompteCreer(String nom, String email, Monnaie monnaie) {
        MaterialAlertDialogBuilder alertBuilder = new MaterialAlertDialogBuilder(this.requireContext());
        alertBuilder.setTitle("Compte créé");
        alertBuilder.setMessage("Votre adresse courriel: " + email);
        alertBuilder.show();
    }

    /**
     * devras s'afficher si l'utilisateur ne peut se connecter à l'api
     */
    public void afficherErreurConnection() {
        MaterialAlertDialogBuilder alertBuilder = new MaterialAlertDialogBuilder(this.requireContext());
        alertBuilder.setTitle("Erreur de connexion");
        alertBuilder.setMessage("Vérifiez que vous êtes bien connecté à Internet");
        alertBuilder.show();
    }

    @Override
    public void fermerProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void ouvrirProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }
}
