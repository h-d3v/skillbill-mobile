package com.jde.skillbill.presentation.vue;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.jde.skillbill.R;
import com.jde.skillbill.presentation.IContratVPAjouterFacture;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class VueAjouterFacture extends Fragment implements IContratVPAjouterFacture.IVueAjouterFacture {
    IContratVPAjouterFacture.IPresenteurAjouterFacture presenteurAjouterFacture;
    Button boutonAjouter, boutonAnnuler;
    EditText editTextMontant;
    EditText editTextTitre;
    Spinner spinnerChoix;
    Spinner spinnerChoixUtilisateursRedevables;
    CalendarView calendarView;
    EditText date;
    ImageView imageFacture;
    private ImageButton btnAjouterFacture;


    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        View racine = inflater.inflate(R.layout.frag_ajouter_facture, container, false);
        btnAjouterFacture =racine.findViewById(R.id.btn_ajouter_facture_groupe_avec_photo);
        btnAjouterFacture.setOnClickListener(view -> {
            presenteurAjouterFacture.prendrePhoto();

        });

         imageFacture = (ImageView) racine.findViewById(R.id.imageFact);
         editTextTitre=racine.findViewById(R.id.edit_t_nom_activie);
         boutonAjouter= racine.findViewById(R.id.btnAjouter);
         boutonAjouter.setOnClickListener(view -> {
             presenteurAjouterFacture.ajouterFacture();
         });

         boutonAnnuler= racine.findViewById(R.id.btnAnuller);
         boutonAnnuler.setOnClickListener(view -> {
             Objects.requireNonNull(this.getActivity()).onBackPressed();
         });
         calendarView=racine.findViewById(R.id.calendarView);
         calendarView.setVisibility(View.GONE);
         date = racine.findViewById(R.id.editTextDate);

         date.setOnFocusChangeListener((view, b) -> {
             if(b) {
                 calendarView.setVisibility(View.VISIBLE);
             }else {
                 calendarView.setVisibility(View.GONE);
             }
         });


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                date.setText(LocalDate.of(year,month+1/*Ok google?*/,dayOfMonth).toString());
            }
        });
         editTextMontant= racine.findViewById(R.id.txt_input_montant);
         spinnerChoixUtilisateursRedevables=racine.findViewById(R.id.spinner_choix_remboursement);
         spinnerChoixUtilisateursRedevables.setAdapter( ArrayAdapter.createFromResource(this.getContext(), R.array.spinner_ajouter_facture_choix_utilisateur_pour_remboursement, R.layout.support_simple_spinner_dropdown_item));

         spinnerChoixUtilisateursRedevables.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 date.clearFocus();
                 editTextMontant.clearFocus();
                 if(i==1){

                     presenteurAjouterFacture.presenterListeUtilsateur();
                 }
             }

             @Override
             public void onNothingSelected(AdapterView<?> adapterView) {

             }
         });
         spinnerChoix =racine.findViewById(R.id.spinner_choix_payeur);
         spinnerChoix.setAdapter(ArrayAdapter.createFromResource(this.getContext(),R.array.spinner_ajouter_facture_choix, R.layout.support_simple_spinner_dropdown_item));
         spinnerChoix.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 if(i==1){
                     afficherAlertDialog();
                 }
             }

             @Override
             public void onNothingSelected(AdapterView<?> adapterView) {

             }
         });

        return racine;
    }

    /**
     *
     * @param presenteurAjouterFacture
     */
    @Override
    public void setPresenteur(IContratVPAjouterFacture.IPresenteurAjouterFacture presenteurAjouterFacture) {
        this.presenteurAjouterFacture=presenteurAjouterFacture;
    }

    /**
     *
     * @return la date de la facture
     * @throws NullPointerException
     * @throws DateTimeParseException
     */
    @Override
    public LocalDate getDateFactureInput() throws NullPointerException, DateTimeParseException{
        return  LocalDate.parse( date.getText());
    }


    /**
     *
     * @return le montant de la facture
     * @throws NullPointerException
     * @throws NumberFormatException
     */
    @Override
    public double getMontantFactureInput() throws NullPointerException, NumberFormatException {
        if(Double.parseDouble( editTextMontant.getText().toString())<=0){
            throw new NumberFormatException();
        }
        return Double.parseDouble( editTextMontant.getText().toString());
    }

    /**
     *
     * @param message erreur si la facture ne peut etre ajoutee
     * @param titre du message
     */
    @Override
    public void afficherMessageErreurAlertDialog(String message, String titre){
        MaterialAlertDialogBuilder alertBuilder=new MaterialAlertDialogBuilder(Objects.requireNonNull(this.getContext()));
        alertBuilder.setTitle(titre);
        alertBuilder.setMessage(message);
        alertBuilder.show();
    }

    /**
     *
     * @return titre de la facture
     */
    @Override
    public String getTitreInput(){
        if(editTextTitre.getText().toString()==null || editTextTitre.getText().toString().trim().equals("")){
            return null;
        }
        return editTextTitre.getText().toString();
    }

    public void setImageFacture(Bitmap bitmap) {

    }

    //TODO implementer le partage inegale de la facture(vision future)
    /**
     * affiche la selection des autres utilisateurs
     * dans le cas ou on veut separer la facture innegalement
     */
    private void afficherAlertDialog(){
        Context context= this.getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        String[] noms = presenteurAjouterFacture.presenterListeUtilsateur();

        final boolean[] nomsSelectionnes = new boolean[noms.length];

        int i=0;
        while(i<noms.length){
            Log.e("vueAjouterFacture", noms[i]);
            nomsSelectionnes[i] = false;
            i++;
        }

        final List<String> listNoms = Arrays.asList(noms);

        builder.setMultiChoiceItems(noms, nomsSelectionnes, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                nomsSelectionnes[which] = isChecked;

                String currentItem = listNoms.get(which);

            }
        });


        builder.setCancelable(false);


        builder.setTitle("Choisir les payeurs NON IMPLÉMETÉ !!!!! A VENIR");//TODO

        // Set the positive/yes button click listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click positive button

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click the negative button
            }
        });


        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }



    }



