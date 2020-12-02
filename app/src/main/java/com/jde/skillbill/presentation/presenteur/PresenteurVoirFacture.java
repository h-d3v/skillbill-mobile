package com.jde.skillbill.presentation.presenteur;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import com.jde.skillbill.domaine.entites.Facture;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionFacture;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionGroupes;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionUtilisateur;
import com.jde.skillbill.domaine.interacteurs.interfaces.SourceDonneeException;
import com.jde.skillbill.presentation.IContratVPVoirFacture;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueVoirFacture;
import com.jde.skillbill.ui.activity.ActivityVoirFacture;

import java.util.HashMap;
import java.util.List;


public class PresenteurVoirFacture implements IContratVPVoirFacture.PresenteurVoirFacture {

    private static final int MSG_RECHARGER_FACTURE = 4444;
    private IGestionFacture iGestionFacture;
    private final Handler handler;
    private Thread threadEsclave;
    private final int MSG_MODIF_FACTURE_FAIT=75;
    private static final int MSG_ERREUR = 1;
    private static final int MSG_ERREUR_CNX = 4;
    private final int MSG_CHARGER_PHOTO =66;
    private Modele modele;
    private VueVoirFacture vueVoirFacture;
    private ActivityVoirFacture activityVoirFacture;
    private String EXTRA_ID_UTILISATEUR="com.jde.skillbill.utlisateur_identifiant";
    private String EXTRA_GROUPE_POSITION= "com.jde.skillbill.groupe_identifiant";

    private static final String EXTRA_FACTURE = "com.jde.skillbill.facture_identifiant";
    @SuppressLint("HandlerLeak")
    public PresenteurVoirFacture(ActivityVoirFacture activityVoirFacture, VueVoirFacture vueVoirFacture, Modele modele, IGestionFacture gestionFacture, IGestionUtilisateur gestionUtilisateur, IGestionGroupes gestionGroupes) {
        this.modele= modele;
        this.vueVoirFacture = vueVoirFacture;
        modele.setFactureEnCours((Facture) activityVoirFacture.getIntent().getSerializableExtra(EXTRA_FACTURE));
        modele.setGroupeEnCours((Groupe) activityVoirFacture.getIntent().getSerializableExtra(EXTRA_GROUPE_POSITION));
        modele.setUtilisateurConnecte((Utilisateur) activityVoirFacture.getIntent().getSerializableExtra(EXTRA_ID_UTILISATEUR));
        this.iGestionFacture = gestionFacture;
        this.activityVoirFacture = activityVoirFacture;
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                threadEsclave=null;
                if(msg.what==MSG_MODIF_FACTURE_FAIT){
                    redirigerVersListeFactures();
                }
                if(msg.what == MSG_ERREUR){
                    Toast.makeText(activityVoirFacture , "ERREUR", Toast.LENGTH_LONG ).show();
                }
                if(msg.what== MSG_ERREUR_CNX){
                    Toast.makeText(activityVoirFacture , "ERREUR PAS ACCES", Toast.LENGTH_LONG ).show();
                }
                if(msg.what == MSG_RECHARGER_FACTURE){
                    if(msg.obj!=null){
                        modele.setFactureEnCours((Facture) msg.obj);
                        //ne prend en charge qu'une seule photo car l'ui ne prend en charge qu'une photo pour le moment
                        if(!(modele.getFactureEnCours().getPhotos()==null || modele.getFactureEnCours().getPhotos().size()==0)){
                            byte[] photoByte = modele.getFactureEnCours().getPhotos().get(0);
                            vueVoirFacture.setImageFacture(BitmapFactory.decodeByteArray(photoByte, 0, photoByte.length ));
                        }

                    }

                }
            }
        };
        rechargerFactureEnCours();
    }

    public void redirigerVersListeFactures() {
        activityVoirFacture.onBackPressed(); //TODO

    }


    @Override
    public String trouverMontantFactureEnCours() {

        return String.valueOf(modele.getFactureEnCours().getMontantTotal());
    }

    @Override
    public String trouverTitreFactureEnCours() {
        return modele.getFactureEnCours().getLibelle();
    }

    @Override
    public String trouverDateFactureEnCours() {
        return modele.getFactureEnCours().getDateFacture().toString();
    }

    @Override
    public void envoyerRequeteModificationFacture() {
        threadEsclave = new Thread(new Runnable() {
            @Override
            public void run() {
                Message message;
                try{
                    Facture factureAModifier = modele.getFactureEnCours();
                    factureAModifier.setLibelle( vueVoirFacture.getTitreInput());
                    factureAModifier.setDateFacture(vueVoirFacture.getDateFactureInput());
                    factureAModifier.setMontantTotal(vueVoirFacture.getMontantFactureInput());
                    //TODO solution provisoire seul l'utilisateur connecté est pris en compte
                    HashMap<Utilisateur, Double> montantsPayesParUtilisateur = new HashMap<>();
                    montantsPayesParUtilisateur.put(modele.getUtilisateurConnecte(),vueVoirFacture.getMontantFactureInput());
                    factureAModifier.setMontantPayeParParUtilisateur(montantsPayesParUtilisateur);
                    Log.e("presenteur voir facture", String.valueOf(montantsPayesParUtilisateur.get(modele.getUtilisateurConnecte())));
                    Log.e("presenteur voir facture utilis ", montantsPayesParUtilisateur.keySet().toArray()[0].toString());
                    boolean estReussi = iGestionFacture.modifierFacture(factureAModifier);
                    Log.e("presenteur voir facture", String.valueOf(estReussi));

                    if(estReussi){
                        message = handler.obtainMessage(MSG_MODIF_FACTURE_FAIT);
                    }
                    else message = handler.obtainMessage(MSG_ERREUR);
                }catch (SourceDonneeException e){
                    message = handler.obtainMessage(MSG_ERREUR_CNX);
                }
                handler.sendMessage(message);
            }
        });
        threadEsclave.start();
    }

    public void rechargerFactureEnCours(){
        threadEsclave = new Thread(new Runnable() {
            @Override
            public void run() {
                Message message;
                try {
                    message = handler.obtainMessage(MSG_RECHARGER_FACTURE);
                    Facture factureRechargee = iGestionFacture.rechargerFacture(modele.getFactureEnCours());
                    message.obj = factureRechargee;
                } catch (SourceDonneeException e) {
                   message = handler.obtainMessage(MSG_ERREUR_CNX);
                }
                handler.sendMessage(message);
            }
        });
        threadEsclave.start();
    }

    public void chargerPhotoFactureEnCours(){

        threadEsclave = new Thread(new Runnable() {
            @Override
            public void run() {
                Message message;

                try{
                    message = handler.obtainMessage(MSG_CHARGER_PHOTO);
                    List<byte[]> photos = iGestionFacture.chargerPhotoFacture(modele.getFactureEnCours());
                    if(photos!=null && !photos.isEmpty()){
                        message.obj = photos;
                    }

                }catch (SourceDonneeException e){
                    message = handler.obtainMessage(MSG_ERREUR_CNX);
                }
                handler.sendMessage(message);

            }
        });
        threadEsclave.start();

    }

    public void prendrePhoto() {
    }

    public String[] presenterListeUtilsateur() {
        return new String[0];
    }
}

