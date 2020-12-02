package com.jde.skillbill.presentation.vue;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.jde.skillbill.R;
import com.jde.skillbill.presentation.IContratVuePresenteurVoirGroupes;
import com.jde.skillbill.presentation.presenteur.PresenteurVoirGroupes;
import com.jde.skillbill.presentation.vue.recyclerview_adapters.RVVoirGroupesAdapter;

public class VueVoirGroupes extends Fragment implements IContratVuePresenteurVoirGroupes.IVueVoirGroupes, NavigationView.OnNavigationItemSelectedListener {

    private PresenteurVoirGroupes presenteurVoirGroupes;
    FloatingActionButton buttonCommencerActiviteCreerGroupe;
    RecyclerView rvMesGroupes;
    RVVoirGroupesAdapter rvVoirGroupesAdapter;
    Button buttonCommencerActivityAjouterFacture;
    Button buttonSoldeGroupe;
    TextView tvPasDeGroupes;
    TextView tvNomUserDrawer;
    ProgressBar progressBar;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        View racine = inflater.inflate(R.layout.frag_voir_groupes, container, false);
        drawerLayout=racine.findViewById(R.id.drawer_layout_voir_gp);
        toolbar=racine.findViewById(R.id.tb_voir_gp);
        navigationView = racine.findViewById(R.id.navigation_view_voir_gp);
        tvNomUserDrawer = navigationView.getHeaderView(0).findViewById(R.id.nom_compte_drawer);

        buttonCommencerActiviteCreerGroupe=racine.findViewById(R.id.floatingActionButtonAjouterGroupe);
        buttonCommencerActivityAjouterFacture=racine.findViewById(R.id.btn_ajouter_facture_groupe);
        buttonSoldeGroupe=racine.findViewById(R.id.btn_detail_solde);
        progressBar = racine.findViewById(R.id.progressBarVG);
        tvPasDeGroupes= racine.findViewById(R.id.tvMessagePasDeGroupes);
        tvPasDeGroupes.setVisibility(View.INVISIBLE);
        rvMesGroupes=racine.findViewById(R.id.rvMesGroupes);
        rvVoirGroupesAdapter=new RVVoirGroupesAdapter(presenteurVoirGroupes);
        if(rvVoirGroupesAdapter.getItemCount()==0 && progressBar.getVisibility()==View.INVISIBLE){
            tvPasDeGroupes.setVisibility(View.VISIBLE);
        }
        rvMesGroupes.setAdapter(rvVoirGroupesAdapter);
        rvMesGroupes.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMesGroupes.addItemDecoration(new DividerItemDecoration(rvMesGroupes.getContext(), DividerItemDecoration.VERTICAL));


        buttonCommencerActiviteCreerGroupe.setOnClickListener(view -> presenteurVoirGroupes.commencerCreerGroupeActivite());

        //code pour rendre focntionnel le navigation drawer et le lier avec le toolbar


        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        toggle.setDrawerSlideAnimationEnabled(true);
        toggle.setHomeAsUpIndicator(R.drawable.round_menu_black_18dp);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        if(presenteurVoirGroupes.getNomUserConnecte()!=null) {
            tvNomUserDrawer.setText(presenteurVoirGroupes.getNomUserConnecte());
        }
        return racine;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_suggestions:
                Toast.makeText(getContext(), "Écrivez nous à suggest@skillbill.ca", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_profil:
                presenteurVoirGroupes.redirigerModifCompte();
                break;

            case R.id.nav_logout:
                presenteurVoirGroupes.deconnexion();
                break;
        }
        return true;

        //video expliquant comment faire le nav drawer https://www.youtube.com/watch?v=lt6xbth-yQo
    }

    @Override
    public void setPresenteur(PresenteurVoirGroupes presenteurVoirGroupes){
        this.presenteurVoirGroupes=presenteurVoirGroupes;
    }


    @Override
    public void rafraichir() {
        rvVoirGroupesAdapter.notifyDataSetChanged();
        if(rvVoirGroupesAdapter.getItemCount()>0){
            tvPasDeGroupes.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void fermerProgressBar(){
        progressBar.setVisibility(View.INVISIBLE);
    }
    @Override
    public void ouvrirProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }

    public boolean isDrawerOpen(){
        return drawerLayout.isDrawerOpen(GravityCompat.START);
    }

    public void closeDrawer(){
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void setNomUserDrawer(String nom){
        tvNomUserDrawer.setText(nom);
    }

}
