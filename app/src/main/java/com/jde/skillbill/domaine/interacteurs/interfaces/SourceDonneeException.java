package com.jde.skillbill.domaine.interacteurs.interfaces;

public class SourceDonneeException extends Throwable {

    public SourceDonneeException(Exception e){
        super(e);
    }

    public SourceDonneeException(String message){
        super(message);
    }
}
