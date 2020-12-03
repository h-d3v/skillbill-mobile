package com.jde.skillbill.presentation.vue;

import android.graphics.Canvas;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.jde.skillbill.R;
import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.presentation.IContratVuePresenteurVoirUnGroupe;
import com.jde.skillbill.presentation.presenteur.PresenteurVoirUnGroupe;
import com.jde.skillbill.presentation.vue.recyclerview_adapters.RvVoirFactureAdapter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class VueVoirUnGroupe extends Fragment implements IContratVuePresenteurVoirUnGroupe.IVueVoirUnGroupe {


   private PresenteurVoirUnGroupe _presenteur;
   private TextView tvNomGroupe;
   //TODO afficher un message si il n'y a aucune facture dans le groupe
   private TextView tvMsgFactures;
   private TabLayout tabLayout;
   private TextView detailMembres;
   private Button ajouterMembre;
   private View racine;
   private ProgressBar progressBar;

    RecyclerView rvFactures;
    RvVoirFactureAdapter rvFacturesAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        racine = inflater.inflate(R.layout.frag_voir_un_groupe, container, false);
        ajouterMembre = racine.findViewById(R.id.btnAjouter);
        detailMembres = racine.findViewById(R.id.detailMembres);
        detailMembres.setVisibility(View.INVISIBLE);
        ajouterMembre.setVisibility(View.INVISIBLE);
        progressBar = racine.findViewById(R.id.pbVoirUnGroupe);
        tvNomGroupe=racine.findViewById(R.id.tvNomGroupe);
        tabLayout=racine.findViewById(R.id.tabLayoutFactureMembres);
        tvMsgFactures=racine.findViewById(R.id.tvMsgFactures);
        rvFactures=racine.findViewById(R.id.rvFactures);
        rvFacturesAdapter=new RvVoirFactureAdapter(_presenteur);
        rvFactures.setAdapter(rvFacturesAdapter);
        rvFactures.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFactures.addItemDecoration(new DividerItemDecoration(rvFactures.getContext(), DividerItemDecoration.VERTICAL));
        tvNomGroupe.setText(_presenteur.getNomGroupe());

        //pour le swipe to delete
        ItemTouchHelper itemTouchHelper= new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvFactures);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){
                    rvFactures.setVisibility(View.VISIBLE);
                }
                else if(tab.getPosition()==1){
                    detailMembres.setVisibility(View.VISIBLE);
                    ajouterMembre.setVisibility(View.VISIBLE);
                    if(_presenteur.isGroupeSolo()){
                        detailMembres.setText(getString(R.string.pas_de_membres_dans_groupe));
                    }else{
                        detailMembres.setText(getString(R.string.membres_dans_le_groupe)+_presenteur.getMembresGroupe());
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if(tab.getPosition()==0) {
                  rvFactures.setVisibility(View.INVISIBLE);
                }
                else if(tab.getPosition()==1){
                    detailMembres.setVisibility(View.INVISIBLE);
                    ajouterMembre.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        ajouterMembre.setOnClickListener((View.OnClickListener) view -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(racine.getContext());
            builder.setTitle(getString(R.string.titre_ajouter_un_membre_au_groupe));
            final View customLayout = getLayoutInflater().inflate(R.layout.alert_dialog_ajouter_membre, null);
            EditText champsCourriel = customLayout.findViewById(R.id.editTextTextEmailAddressAjouterMembre);
            builder.setView(customLayout);

            builder.setPositiveButton(getString(R.string.ajouter), (dialogInterface, i) -> {

                 _presenteur.ajouterUtilisateurAuGroupe(champsCourriel.getText().toString());

            });

            builder.setNeutralButton(getString(R.string.Inviter), (dialogInterface, i) -> _presenteur.envoyerCourriel(champsCourriel.getText().toString()));

            builder.setNegativeButton(getString(R.string.Annuler), (dialogInterface, i) -> dialogInterface.dismiss());

            AlertDialog dialog = builder.create();
            dialog.setOnShowListener(dialog1 -> {
                ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_NEUTRAL).setEnabled(false);
                champsCourriel.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(champsCourriel.getText().toString().matches("[A-z0-9._%+-]+@[A-z0-9.-]+\\.[A-z]{2,4}")){
                            ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                            ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_NEUTRAL).setEnabled(true);
                        }
                        else{
                            ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                            ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_NEUTRAL).setEnabled(false);
                            champsCourriel.setError("Entrez un email valide");
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            });
            dialog.show();
        });

        return racine;
    }

    /**
     *
     * @param presenteur presenteur qui sera attribuer a la vue
     */
    @Override

    public void setPresenteur(IContratVuePresenteurVoirUnGroupe.IPresenteurVoirUnGroupe presenteur) {
        _presenteur = (PresenteurVoirUnGroupe) presenteur;
    }

    public void setVueAjouterMembres(int code){

        if(_presenteur.AJOUT_OK==code){
            detailMembres.setText(getString(R.string.membres_dans_le_groupe)+_presenteur.getMembresGroupe());
        }else if(_presenteur.EMAIL_INCONNU==code){
            Toast.makeText(racine.getContext(), getString(R.string.email_inconnu), Toast.LENGTH_LONG).show();
        }else if(_presenteur.ERREUR_ACCES==code){
            Toast.makeText(racine.getContext(), getString(R.string.email_deja_dans_groupe), Toast.LENGTH_LONG).show();
        }

    }

   // @Override
    public void setNomGroupe(String nom) {
        tvNomGroupe.setText(nom);
    }


    /**
     * Rafraichit la vue
     */

    public void rafraichir() {
        if(rvFacturesAdapter!=null) rvFacturesAdapter.notifyDataSetChanged();
    }

    /**
     * crée le swipe gauche pour le rv
     */
    final ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

        /**
         * Cette méthode n'Est pa utilisée pour l'intsant
         * @param recyclerView le rv des factures
         * @param viewHolder
         * @param target
         * @return null
         */
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        /**
         * Ce qu'il se passe quand on swipe un element du rv selon une direction
         * @param viewHolder le viewHolder
         * @param direction la dr
         */
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();
            Snackbar.make(rvFactures, _presenteur.getFacturesGroupe().get(position).getLibelle(), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.supprimer_facture), new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            _presenteur.requeteSupprimerFacture(position);
                        }
                    }).setActionTextColor(Color.RED).setBackgroundTint(Color.LTGRAY).show();
                    rvFacturesAdapter.notifyDataSetChanged();
        }

        /**
         * Décore le swipe vers la droite, y met la couleure ansi que l'icone
         * J'ai pris cette méthode d'une librairie sur git, voir le fichier gradle.build de l'app
         * @param c
         * @param recyclerView
         * @param viewHolder
         * @param dX
         * @param dY
         * @param actionState
         * @param isCurrentlyActive
         */
        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.error))
                    .addActionIcon(R.drawable.ic_remove_circle_black_24dp)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
    @Override
    public void fermerProgressBar(){
        progressBar.setVisibility(View.INVISIBLE);
    }
    @Override
    public void ouvrirProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }

}
