package com.jde.skillbill.presentation.vue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.jde.skillbill.R;
import com.jde.skillbill.presentation.IContratVPAjouterFacture;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;

public class VueAjouterFacture extends Fragment implements IContratVPAjouterFacture.IVueAjouterFacture {
    IContratVPAjouterFacture.IPresenteurAjouterFacture presenteurAjouterFacture;
    Button boutonAjouter, boutonAnnuler;
    EditText editTextMontant;
    Spinner spinnerChoix;
    Spinner spinnerChoixUtilisateursRedevables;
    TextView text;
    CalendarView calendarView;
    EditText date;

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        View racine = inflater.inflate(R.layout.frag_ajouter_facture, container, false);
         boutonAjouter= racine.findViewById(R.id.btn_ajouter_facture_groupe);
         boutonAnnuler= racine.findViewById(R.id.btnAnuller);
         calendarView=racine.findViewById(R.id.calendarView);
         calendarView.setVisibility(View.GONE);
         date = racine.findViewById(R.id.editTextDate);

         date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
             @Override
             public void onFocusChange(View view, boolean b) {
                 if(b) {
                     calendarView.setVisibility(View.FOCUSABLE);
                 }else {
                     calendarView.setVisibility(View.GONE);
                 }
             }
         });
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                date.setText(LocalDate.of(year,month,dayOfMonth).toString());
            }
        });
         editTextMontant= racine.findViewById(R.id.txt_input_montant);
         spinnerChoixUtilisateursRedevables=racine.findViewById(R.id.spinner_choix_remboursement);
         spinnerChoixUtilisateursRedevables.setAdapter( ArrayAdapter.createFromResource(this.getContext(), R.array.spinner_ajouter_facture_choix_utilisateur_pour_remboursement, R.layout.support_simple_spinner_dropdown_item));
         spinnerChoixUtilisateursRedevables.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 if(i==1){
                     presenteurAjouterFacture.montrerListeUtilisateurMontant();
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
                     presenteurAjouterFacture.montrerListeUtilisateurMontant();
                 }
             }

             @Override
             public void onNothingSelected(AdapterView<?> adapterView) {

             }
         });

        return racine;
    }

    @Override
    public void setPresenteur(IContratVPAjouterFacture.IPresenteurAjouterFacture presenteurAjouterFacture) {
        this.presenteurAjouterFacture=presenteurAjouterFacture;
    }



    @Override
    public double getMontantFactureInput() throws NullPointerException, NumberFormatException {
        return Double.parseDouble( editTextMontant.getText().toString());
    }


}
