package com.jde.skillbill.presentation.vue;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

public class ListMembresAlertDialogBuilder extends AlertDialog.Builder {


   private String[] noms;


    public ListMembresAlertDialogBuilder(Context context) {
        super(context);


        final boolean[] nomsSelectionnes = new boolean[noms.length];

        int i=0;
        while(i<noms.length){
            Log.e("vueAjouterFacture", noms[i]);
            nomsSelectionnes[i] = false;
            i++;
        }

        final List<String> listNoms = Arrays.asList(noms);

        setMultiChoiceItems(noms, nomsSelectionnes, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                nomsSelectionnes[which] = isChecked;

                String currentItem = listNoms.get(which);

            }
        });


        setCancelable(false);


        setTitle("Choisir les payeurs NON IMPLÉMETÉ !!!!! A VENIR");//TODO

        // Set the positive/yes button click listener
        setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click positive button

            }
        });

        setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click the negative button
            }
        });


        setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    public String[] getNoms() {
        return noms;
    }

    public void setNoms(String[] noms) {
        this.noms = noms;
    }
}
