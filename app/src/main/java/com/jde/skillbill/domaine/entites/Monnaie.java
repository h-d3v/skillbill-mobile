package com.jde.skillbill.domaine.entites;

public enum Monnaie {
    CAD(1, "CA$", 1),
    USD(0.783038, "US$", 1.27708),
    GBP(0.593066, "£", 1.68615),
    EUR(0.646549, "€",1.54667) ,
    AUD(1.03931,"A$", 0.962176);

    private final double tauxCad;

    private final String symbol;

    private final double tauxDevise;

    /**
     *
     * @param tauxCad represente le taux de conversion du Canadien à cette monnaie
     * @param symbol le symbole de la monnaie
     * @param tauxDevise represente le taux de converstion de cette monnaie en Canadien
     */
    Monnaie(double tauxCad, String symbol, double tauxDevise) {
        this.tauxCad = tauxCad;
        this.symbol=symbol;
        this.tauxDevise=tauxDevise;
    }

    /**
     *
     * @return le taux CAD
     */
    public double getTauxCad() {
        return tauxCad;
    }

    /**
     *
     * @return le symbole
     */
    public String getSymbol() { return symbol; }

    /**
     *
     * @return le taux en devise
     */
    public double getTauxDevise() { return tauxDevise; }
}
