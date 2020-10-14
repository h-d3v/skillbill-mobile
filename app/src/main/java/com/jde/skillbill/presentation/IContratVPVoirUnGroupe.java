package com.jde.skillbill.presentation;

public interface IContratVPVoirUnGroupe {
    interface IPresenteurVoirUnGroupe{
        public int getMontantFacture();
        public String getNomFacture();

    }
    //interface pour la vue du groupe
    interface IVueVoirUnGroupe{
        public void setNomGroupe();
    }

    //pour l'adapter d'une facture au recyclerView.
    interface IVueVoirUneFacture{
        public void setNomFacture();
        public void setMontantFacture();
    }
}
