package com.jde.skillbill.domaine.entites;

public enum Monnaie {
    CAD(1),
    USD(0.75),
    GBP(0.58),
    EUR(0.64);

    private final double tauxCad;

    private Monnaie(double tauxCad) {
        this.tauxCad = tauxCad;
    }

    public double getTauxCad() {
        return tauxCad;
    }
}
