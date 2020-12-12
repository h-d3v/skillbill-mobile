package com.jde.skillbill.presentation.vue;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.jde.skillbill.R;
import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.presentation.IContratVPModifProfil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VueModifProfil extends Fragment implements IContratVPModifProfil.VueModifProfil {
    private IContratVPModifProfil.PresenteurModifProfil _presenteur;
    private MaterialButton btnSave;
    private TextInputEditText tfNewNom;
    private TextInputEditText tfNewCourriel;
    private TextInputEditText tfNewMdp;
    private TextInputEditText tfMdpCourrant;
    private AutoCompleteTextView tfNewMonnaie;
    private ProgressBar progressBar;

    private boolean nouveauMdpValide =true;
    private boolean mdpCourrantValide =false;
    private boolean nomValide = true;
    private boolean emailValide = true;

    /**
     *
     * @param presenteur
     */
    @Override
    public void setPresenteur(IContratVPModifProfil.PresenteurModifProfil presenteur) {
        _presenteur=presenteur;
    }

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return la vue telle que créée
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View racine= inflater.inflate(R.layout.frag_modif_profil, container, false);
        progressBar=racine.findViewById(R.id.progressBarModifProfil);
        btnSave=racine.findViewById(R.id.btnEnrgstProfil);
        tfNewNom=racine.findViewById(R.id.tfModifierNom);
        tfNewCourriel=racine.findViewById(R.id.tfModifEmail);
        tfNewMdp=racine.findViewById(R.id.tfModifMdp);
        tfNewMonnaie=racine.findViewById(R.id.modif_monnaie_dropdown);
        tfMdpCourrant=racine.findViewById(R.id.tfMdpCourrant);

        progressBar.setVisibility(View.INVISIBLE);
        //Peupler la liste de monnaies, on va chercher toutes les valeurs de l'enum
        List<String> monnaiesString = Stream.of(Monnaie.values()).map(Enum::name).collect(Collectors.toList());

        ArrayAdapter<String> adapterMonnaies = new ArrayAdapter<>(
                requireContext(),
                R.layout.dropdown_menu_item,
                monnaiesString);

        tfNewMonnaie.setAdapter(adapterMonnaies);


        setNomUser(_presenteur.getNomUserConnecte());
        setEmailUser(_presenteur.getEmailUserConnecte());
        setMonnaieUser(_presenteur.getMonnaieConnecte().toString());
        setMdpUser(_presenteur.getMdpUserConnecte());
        btnSave.setOnClickListener(v -> {
            _presenteur.modifierProfil();
        });

        btnSave.setEnabled(false);

        tfNewNom.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!getNouveauNom().matches("[A-z\\s]+")) {
                    nomValide=false;
                    btnSave.setEnabled(false);
                    tfNewNom.setError("Le nom doit être valide.");
                } else {
                    nomValide = true;
                    if (tousLesChampsValides()) {
                        btnSave.setEnabled(true);
                    }
                }
            }
        });


        tfNewCourriel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!getNouveauEmail().matches("[A-z0-9._%+-]+@[A-z0-9.-]+\\.[A-z]{2,4}")) {
                    emailValide=false;
                    btnSave.setEnabled(false);
                    tfNewCourriel.setError("Le courriel doit être valide.");
                } else {
                    emailValide = true;
                    if (tousLesChampsValides()) {
                        btnSave.setEnabled(true);
                    }
                }
            }
        });

        tfMdpCourrant.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!getMdpCourrant().matches("[.\\S]+") || getMdpCourrant().length() < 8 && getMdpCourrant().length()!=0) {
                    mdpCourrantValide=false;
                    btnSave.setEnabled(false);
                    tfMdpCourrant.setError("Le mot de passe actuel doit être valide et contenir au moins 8 caractères.");
                } else {
                    mdpCourrantValide = true;
                    if (tousLesChampsValides()) {
                        btnSave.setEnabled(true);
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tfNewMdp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(getNouveauMdp().length()<=0){
                    nouveauMdpValide=true;
                }
                else if (!getNouveauMdp().matches("[.\\S]+") || getNouveauMdp().length() < 8) {
                    nouveauMdpValide=false;
                    btnSave.setEnabled(false);
                    tfNewMdp.setError("Le mot de passe doit être valide et contenir au moins 8 caractères.");
                } else {
                    nouveauMdpValide = true;
                    if (tousLesChampsValides()) {
                        btnSave.setEnabled(true);
                    }
                }
            }
        });

        return racine;
    }

    @Override
    public String getNouveauNom() {
        return tfNewNom.getText().toString();
    }

    /**
     *
     * @return l'email tel quel dans la vue
     */
    @Override
    public String getNouveauEmail() {
        return tfNewCourriel.getText().toString();
    }

    /**
     *
     * @return le nouvel mdp tel quel dans la vue
     */
    @Override
    public String getNouveauMdp() {
        return tfNewMdp.getText().toString();
    }

    /**
     *
     * @return le mdp courrant tel quel dans la vue
     */
    @Override
    public String getMdpCourrant() {
        return tfMdpCourrant.getText().toString();
    }

    /**
     *
     * @return le monnaie tel quel dans la vue
     */
    @Override
    public Monnaie getNouvelleMonnaie() {
        return Monnaie.valueOf(tfNewMonnaie.getText().toString());
    }

    /**
     *
     * @param nom
     */
    @Override
    public void setNomUser(String nom) {
        tfNewNom.setText(nom);
    }


    /**
     *
     * @param monnaieStr la monnaie en string
     */
    @Override
    public void setMonnaieUser(String monnaieStr) {
        tfNewMonnaie.setText(monnaieStr, false);
    }

    /**
     *
     * @param email
     */
    @Override
    public void setEmailUser(String email) {
        tfNewCourriel.setText(email);
    }

    /**
     *
     * @param mdp le mot de passe actuel.
     */
    @Override
    public void setMdpUser(String mdp){tfNewMdp.setText(mdp);}

    /**
     * @return true si tous les champs remplis par le user sont valides
     */
    @Override
    public boolean tousLesChampsValides() {
        return emailValide && nomValide && mdpCourrantValide && nouveauMdpValide;
    }

    /**
     * rend le boutton invisible et la progressBar visible
     * fait le contraire si le boutton est deja invisible
     */
    @Override
    public void activerDescativerBtn(){
        if (btnSave.getVisibility()==View.INVISIBLE){
            btnSave.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
        else{
            btnSave.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

}
