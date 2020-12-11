package com.jde.skillbill.domaine.entites;

public enum Monnaie {
    CAD(1, "CA$", 1),
    USD(0.783038, "US$", 1.27708),
    GBP(0.593066, "£", 1.68615),
    EUR(0.646549, "€",1.54667) ,
    AUD(1.03931,"A$", 0.962176);
    //reprsente la valeur du dollard canadien en la devise
    private final double tauxCad;
    private final String symbol;
    //represente la valeur de la devise en dollard canadien
    private final double tauxDevise;
    Monnaie(double tauxCad, String symbol, double tauxDevise) {
        this.tauxCad = tauxCad;
        this.symbol=symbol;
        this.tauxDevise=tauxDevise;
    }

    public double getTauxCad() {
        return tauxCad;
    }
    public String getSymbol() { return symbol; }
    public double getTauxDevise() { return tauxDevise; }
}
