package com.jde.skillbill.presentation.vue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
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

        btnCnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _presenteur.tenterConnexion(getEmail(), getMdp());
            }
        });

        return vue;
    }

    @Override
    public String getMdp() {
       return etMdp.getText().toString();
    }

    @Override
    public String getEmail() {
        return etEmail.getText().toString();
    }

    @Override
    public boolean verifierLesChamps() {
        return false;
    }

    @Override
    public void setPresenteur(PresenteurConnexion presenteur) {
        _presenteur=presenteur;
    }

}
