package com.jde.skillbill.domaine.entites;

public enum Monnaie {
    CAD(1, "CA$", 1),
    USD(0.76, "US$", 1.31),
    GBP(0.58, "£", 1.73),
    EUR(0.64, "€",1.55),
    AUD(1.05,"A$", 0.95);
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
