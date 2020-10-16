package com.jde.skillbill.presentation.vue;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jde.skillbill.R;
import com.jde.skillbill.presentation.IContratVPVoirUnGroupe;
import com.jde.skillbill.presentation.presenteur.PresenteurVoirUnGroupe;
import com.jde.skillbill.presentation.vue.recyclerview_adapters.RvVoirFactureAdapter;

import java.util.Objects;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class VueVoirUnGroupe extends Fragment implements IContratVPVoirUnGroupe.IVueVoirUnGroupe {
    PresenteurVoirUnGroupe _presenteur;
    TextView tvNomGroupe;
    RecyclerView rvFactures;
    RvVoirFactureAdapter rvFacturesAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View racine = inflater.inflate(R.layout.frag_voir_un_groupe, container, false);
        tvNomGroupe=racine.findViewById(R.id.tvNomGroupe);
        rvFactures=racine.findViewById(R.id.rvFactures);
        rvFacturesAdapter=new RvVoirFactureAdapter(_presenteur);
        rvFactures.setAdapter(rvFacturesAdapter);
        rvFactures.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFactures.addItemDecoration(new DividerItemDecoration(rvFactures.getContext(), DividerItemDecoration.VERTICAL));

        //pour le swipe to delete
        ItemTouchHelper itemTouchHelper= new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvFactures);

        return racine;
    }

    @Override
    public void setNomGroupe(String nom) {
        tvNomGroupe.setText(nom);
    }

    @Override
    public void setPresenteur(PresenteurVoirUnGroupe presenteur) {
        _presenteur=presenteur;
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
            _presenteur.requeteSupprimerFacture(position);
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
}
