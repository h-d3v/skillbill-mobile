package com.jde.skillbill.presentation.vue;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.jde.skillbill.R;
import com.jde.skillbill.presentation.IContratVPConnexion;
import com.jde.skillbill.presentation.presenteur.PresenteurConnexion;

import java.util.Objects;

public class VueConnexion extends Fragment implements IContratVPConnexion.IVueConnexion, NavigationView.OnNavigationItemSelectedListener {
    private PresenteurConnexion _presenteur;
    private FloatingActionButton btnCnx;
    private MaterialButton btnInscription;
    private MaterialButton btnMdpOublie;
    private EditText etEmail;
    private EditText etMdp;
    private ProgressBar progressBar;
    private boolean emailValide;
    private boolean mdpValide;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vue = inflater.inflate(R.layout.frag_cnx, container, false);

        etMdp=vue.findViewById(R.id.etMdpCnx);
        etEmail=vue.findViewById(R.id.etEmailCnx);
        btnCnx=vue.findViewById(R.id.btnCnx);
        btnInscription=vue.findViewById(R.id.btnInscriptionCnx);
        btnMdpOublie=vue.findViewById(R.id.btnMdpOublie);
        progressBar = vue.findViewById(R.id.progressBarConnexion);
        drawerLayout=vue.findViewById(R.id.drawer_layout_cnx);
        navigationView=vue.findViewById(R.id.navigation_view_cnx);
        toolbar=vue.findViewById(R.id.tb_cnx);
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
        //code pour rendre focntionnel le navigation drawer et le lier avec le toolbar
        navigationView.bringToFront();
        toolbar.setNavigationIcon(R.drawable.round_menu_black_18dp);
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        toggle.setDrawerSlideAnimationEnabled(true);
        toggle.setHomeAsUpIndicator(R.drawable.round_menu_black_18dp);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        return vue;
    }

    public boolean isDrawerOpen(){
        return drawerLayout.isDrawerOpen(GravityCompat.START);
    }

    public void closeDrawer(){
        drawerLayout.closeDrawer(GravityCompat.START);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
        //3:19 video https://www.youtube.com/watch?v=lt6xbth-yQo
    }


}
