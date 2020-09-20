package com.jde.skillbill.presentation.modele;

import java.util.List;

public  interface DAOFactory<T, U> {
    List<DAO<T>>  lireTous();
    List<DAO<U>> lirePar(DAO<T> tdao);
    DAO<U> creerPar(DAO<T> t, U u);
}
