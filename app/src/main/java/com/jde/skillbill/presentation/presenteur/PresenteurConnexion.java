package com.jde.skillbill.presentation.presenteur;

import android.app.Activity;

import com.jde.skillbill.domaine.interacteurs.DataSourceUsers;
import com.jde.skillbill.presentation.IContratVPConnexion;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueConnexion;

public class PresenteurConnexion implements IContratVPConnexion.IPresenteurConnexion {

    private VueConnexion _vueConnexion;
    private Modele _modele;
    private DataSourceUsers _dataSource;
    private Activity _activite;

    public PresenteurConnexion(Activity activite,Modele modele, VueConnexion vueConnexion) {
        _activite=activite;
        _modele = modele;
        _vueConnexion= vueConnexion;
    }

    public void setDataSource(DataSourceUsers dataSource) {
        _dataSource = dataSource;
    }

    @Override
    public boolean tenterConnexion(String email, String mdp) {
        return false;
    }

    @Override
    public void allerInscription() {

    }
}
