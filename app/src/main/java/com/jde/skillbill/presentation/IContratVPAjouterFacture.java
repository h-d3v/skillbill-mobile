package com.jde.skillbill.presentation;

import com.jde.skillbill.presentation.presenteur.PresenteurAjouterFacture;

public interface IContratVPAjouterFacture {

    interface IPresenteurAjouterFacture{

    }

    interface IVueAjouterFacture{

        void setPresenteur(IPresenteurAjouterFacture presenteurAjouterFacture);

    }
}
