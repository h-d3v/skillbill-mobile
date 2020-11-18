package com.jde.skillbill.presentation.presenteur;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.jde.skillbill.domaine.entites.Facture;
import com.jde.skillbill.domaine.interacteurs.ISourceDonnee;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionFacture;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionGroupes;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionUtilisateur;
import com.jde.skillbill.domaine.interacteurs.interfaces.SourceDonneeException;
import com.jde.skillbill.presentation.IContratVPVoirFacture;
import com.jde.skillbill.presentation.modele.Modele;
import com.jde.skillbill.presentation.vue.VueVoirFacture;
import com.jde.skillbill.ui.activity.ActivityVoirFacture;

import java.time.LocalDate;


public class PresenteurVoirFacture extends PresenteurAjouterFacture implements IContratVPVoirFacture.PresenteurVoirFacture {
    ActivityVoirFacture activityVoirFacture;
    VueVoirFacture vueVoirFacture;
    ISourceDonnee sourceDonnee;
    IGestionFacture iGestionFacture;
    Handler handler;
    Thread threadEsclave;
    private final int MSG_MODIF_FACTURE_FAIT=75;
    private static final int MSG_ERREUR = 1;
    private static final int MSG_ERREUR_CNX = 4;

    private static final String EXTRA_FACTURE = "com.jde.skillbill.facture_identifiant";
    @SuppressLint("HandlerLeak")
    public PresenteurVoirFacture(AppCompatActivity activityVoirFacture, VueVoirFacture vueVoirFacture, Modele modele, IGestionFacture gestionFacture, IGestionUtilisateur gestionUtilisateur, IGestionGroupes gestionGroupes) {
        super(activityVoirFacture, vueVoirFacture, modele, gestionFacture, gestionUtilisateur, gestionGroupes);
        super.modele.setFactureEnCours((Facture) activityVoirFacture.getIntent().getSerializableExtra(EXTRA_FACTURE));
        this.iGestionFacture = gestionFacture;
        handler = new android.os.Handler() {
            @Override
            public void handleMessage(Message msg) {
                threadEsclave=null;
                if(msg.what==MSG_MODIF_FACTURE_FAIT){
                    presenterListeUtilsateur();
                }
                if(msg.what == MSG_ERREUR){
                    Toast.makeText(PresenteurVoirFacture.super.activityAjouterFacture , "ERREUR", Toast.LENGTH_LONG );
                }
                if(msg.what== MSG_ERREUR_CNX){
                    Toast.makeText(PresenteurVoirFacture.super.activityAjouterFacture , "ERREUR PAS ACCES", Toast.LENGTH_LONG );
                }
            }
        };
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
                    factureAModifier.setLibelle(PresenteurVoirFacture.super.vueAjouterFacture.getTitreInput());
                    factureAModifier.setDateFacture(PresenteurVoirFacture.super.vueAjouterFacture.getDateFactureInput());
                    boolean estReussi = iGestionFacture.modifierFacture(factureAModifier);

                    if(estReussi) message = handler.obtainMessage(MSG_MODIF_FACTURE_FAIT);
                    else message = handler.obtainMessage(MSG_ERREUR);
                }catch (SourceDonneeException e){
                    message = handler.obtainMessage(MSG_ERREUR_CNX);
                }
                handler.sendMessage(message);
            }
        });
        threadEsclave.start();
    }
}

