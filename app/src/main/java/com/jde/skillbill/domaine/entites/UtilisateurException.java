package com.jde.skillbill.domaine.entites;

public class UtilisateurException extends Throwable {

    public UtilisateurException( Exception e ) {
        super( e );
    }

    public UtilisateurException( String msg ) {
        super(msg);
    }
}
