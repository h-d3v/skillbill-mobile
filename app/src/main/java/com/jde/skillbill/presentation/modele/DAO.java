package com.jde.skillbill.presentation.modele;

import java.util.List;

public interface DAO<T> {
    T lire();
    boolean modifier(DAO<T> t);
    boolean supprimer(DAO<T> t);
    DAO<T> creer(T t);
}
