package com.jde.skillbill.donnees.APIRest.entites;


import com.google.gson.annotations.SerializedName;

public class PhotoRestApi {
    @SerializedName("LowResEncodeBase64")
    private String photoEncodee;
    @SerializedName("Id")
    private int id;
    @SerializedName("IdFacture")
    private int idFacture;

    public PhotoRestApi(String photoEncodee) {
        this.photoEncodee = photoEncodee;
    }

    public String getPhotoEncodee() {
        return photoEncodee;
    }

    public void setPhotoEncodee(String photoEncodee) {
        this.photoEncodee = photoEncodee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdFacture() {
        return idFacture;
    }

    public void setIdFacture(int idFacture) {
        this.idFacture = idFacture;
    }

    @Override
    public String toString() {
        return photoEncodee;
    }
}
