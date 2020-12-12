package com.jde.skillbill.presentation.vue;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jde.skillbill.R;
import com.jde.skillbill.presentation.IContratVPConnexion;
import com.jde.skillbill.presentation.presenteur.PresenteurConnexion;

public class VueConnexion extends Fragment implements IContratVPConnexion.IVueConnexion {
    private PresenteurConnexion _presenteur;
    private FloatingActionButton btnCnx;
    private MaterialButton btnInscription;
    private MaterialButton btnMdpOublie;
    private EditText etEmail;
    private EditText etMdp;
    private ProgressBar progressBar;
    private boolean emailValide;
    private boolean mdpValide;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vue = inflater.inflate(R.layout.frag_cnx, container, false);

        etMdp=vue.findViewById(R.id.etMdpCnx);
        etEmail=vue.findViewById(R.id.etEmailCnx);
        btnCnx=vue.findViewById(R.id.btnCnx);
        btnInscription=vue.findViewById(R.id.btnInscriptionCnx);
        btnMdpOublie=vue.findViewById(R.id.btnMdpOublie);
        progressBar = vue.findViewById(R.id.progressBarModifProfil);
        fermerProgressBar();


        btnInscription.setOnClickListener(v -> _presenteur.allerInscription());

        btnCnx.setEnabled(false);
        btnCnx.setOnClickListener(v -> _presenteur.tenterConnexion(getEmail(), getMdp()));


        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!getEmail().matches("[A-z0-9._%+-]+@[A-z0-9.-]+\\.[A-z]{2,4}")){
                    btnCnx.setEnabled(false);
                    etEmail.setError("Veuillez entrer un email valide.");
                }
                else{
                    emailValide=true;
                    if (tousLesChampsValides()) {
                        btnCnx.setEnabled(true);
                    }
                }
            }
        });

        etMdp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!getMdp().matches("[.\\S]+") || getMdp().length()<8){
                    btnCnx.setEnabled(false);
                    etMdp.setError("Le mot de passe ne doit pas contenir d'espace et plus de 8 caractères.");
                }
                else {
                    mdpValide = true;
                    if (tousLesChampsValides()) {
                        btnCnx.setEnabled(true);
                    }
                }
            }
        });

        return vue;
    }


    /**
     *
     * @return le mdp
     */
    @Override
    public String getMdp() {
       return etMdp.getText().toString();
    }

    /**
     *
     * @return l'email
     */
    @Override
    public String getEmail() {
        return etEmail.getText().toString();
    }

    /**
     *
     * @return true si tous les champs remplis par l'utilisateur sont valide
     */
    @Override
    public boolean tousLesChampsValides() {
        return emailValide && mdpValide;
    }

    /**
     *
     * @param presenteur
     */
    @Override
    public void setPresenteur(PresenteurConnexion presenteur) {
        _presenteur=presenteur;
    }

    /**
     * Affiche que l'email ou le mot de passe ne sont pas bons
     */

    public void afficherMsgErreur() {
        MaterialAlertDialogBuilder alertBuilder=new MaterialAlertDialogBuilder(this.requireContext());
        alertBuilder.setTitle("Connexion impossible!");
        alertBuilder.setMessage("L'email et le mot de passe ne correspondent pas.");
        alertBuilder.show();
    }

    /**
     * A des fins de tests, sera enlever a la livraison finale
     * @param email l'email de l'utilisateur
     * @param nom le nom de l'utilisateur
     */
    
    public void afficherMsgConnecter(String email, String nom) {
        MaterialAlertDialogBuilder alertBuilder=new MaterialAlertDialogBuilder(this.requireContext());
        alertBuilder.setTitle("Connexion réussie!");
        alertBuilder.setMessage("Courriel: "+email+"  Nom: "+nom);
        alertBuilder.show();
    }

    @Override
    public void fermerProgressBar(){
        progressBar.setVisibility(View.INVISIBLE);
    }
    @Override
    public void ouvrirProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }




}
