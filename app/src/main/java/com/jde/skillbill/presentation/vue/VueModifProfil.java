package com.jde.skillbill.presentation.vue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

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
    private AutoCompleteTextView tfNewMonnaie;

    private boolean mdpValide = false;
    private boolean nomValide = false;
    private boolean emailValide = false;
    @Override
    public void setPresenteur(IContratVPModifProfil.PresenteurModifProfil presenteur) {
        _presenteur=presenteur;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View racine= inflater.inflate(R.layout.frag_modif_profil, container, false);
        btnSave=racine.findViewById(R.id.btnEnrgstProfil);
        tfNewNom=racine.findViewById(R.id.tfModifierNom);
        tfNewCourriel=racine.findViewById(R.id.tfModifEmail);
        tfNewMdp=racine.findViewById(R.id.tfModifMdp);
        tfNewMonnaie=racine.findViewById(R.id.modif_monnaie_dropdown);

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

        //todo, textwatchers pour tous les champs avec regex
        return racine;
    }

    @Override
    public String getNouveauNom() {
        return tfNewNom.getText().toString();
    }

    @Override
    public String getNouveauEmail() {
        return tfNewCourriel.getText().toString();
    }

    @Override
    public String getNouveauMdp() {
        return tfNewMdp.getText().toString();
    }

    @Override
    public Monnaie getNouvelleMonnaie() {
        return Monnaie.valueOf(tfNewMonnaie.getText().toString());
    }

    @Override
    public void setNomUser(String nom) {
        tfNewNom.setText(nom);
    }

    @Override
    public void setMonnaieUser(String monnaieStr) {
        tfNewMonnaie.setText(monnaieStr, false);
    }

    @Override
    public void setEmailUser(String email) {
        tfNewCourriel.setText(email);
    }

    @Override
    public void setMdpUser(String mdp){tfNewMdp.setText(mdp);}


}
