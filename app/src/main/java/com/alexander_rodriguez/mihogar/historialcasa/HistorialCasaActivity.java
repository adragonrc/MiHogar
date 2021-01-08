package com.alexander_rodriguez.mihogar.historialcasa;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.Adapters.AdapterInterface;
import com.alexander_rodriguez.mihogar.Adapters.RVARentals;
import com.alexander_rodriguez.mihogar.Adapters.RvAdapterUser;
import com.alexander_rodriguez.mihogar.Base.BaseActivity;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemRental;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemTenant;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.viewUser.DialogDetallesAlquiler;

import java.util.ArrayList;

public class HistorialCasaActivity extends BaseActivity<Interface.Presenter> implements Interface.View, AdapterInterface{

    public static final String ALL_USERS = "allUsers";

    public static final String USERS_OF_RENTAL = "usersOfRental";

    public static final String RENTALS_OF_USER = "rentalOfUsers";

    public static final String RENTALS_OF_ROOM = "rentalsOfRoom";

    public static final String ALL_RENTALS = "allRentals";

    public static final String MODE = "mode";

    public static final String TAG_MOSTRAR_PAGOS = "tag_table_pagos";
    public static final String EXTRA_ROOM_NUMBER = "extraRoomNumber";
    public static final String EXTRA_RENTAL_ID = "extraRentalId";
    public static final String EXTRA_DNI = "dni";


    private RecyclerView recyclerView;
    
    private View nothingToshow;
    
    private RvAdapterUser adapterUser;

    private RecyclerView.LayoutManager manager;

    private int iMenu;

    @Override
    protected void iniciarComandos() {
        setTitle("Historial");
        ActionBar ab =  getSupportActionBar();
        if (ab != null){
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    @NonNull
    @Override
    protected Interface.Presenter createPresenter() {
        String mode = getIntent().getStringExtra(MODE);
        switch (mode){
            case ALL_RENTALS:{
                return new AllRentalPresenter(this, getIntent());
            }
            case ALL_USERS:{
                return new AllUsersPresenter(this, getIntent());
            }
            case USERS_OF_RENTAL:{
                return new RentalUsersPresenter(this, getIntent());
            }
            case RENTALS_OF_USER:
            case RENTALS_OF_ROOM:{
                return new UserRentalsPresenter(this, getIntent());

            }
            default: {
                showMessage("default mode");
                salir();
                return new AllRentalPresenter(this, getIntent());
            }
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mi_casa;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        presenter.crearMenu(getMenuInflater(), menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        presenter.itemSelected(item);

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void iniciarViews() {
        recyclerView = findViewById(R.id.recyclerView);
        nothingToshow = findViewById(R.id.nothingToShow);
    }

    @Override
    public void showUsersList(ArrayList<ItemTenant> list, RecyclerView.LayoutManager manager, boolean showMain) {
        recyclerView.setVisibility(View.VISIBLE);
        nothingToshow.setVisibility(View.GONE);
        adapterUser = new RvAdapterUser(this, list, showMain);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterUser);
    }

    @Override
    public void showRentalsList(ArrayList<ItemRental> list, RecyclerView.LayoutManager manager) {
        recyclerView.setVisibility(View.VISIBLE);
        nothingToshow.setVisibility(View.GONE);
        RVARentals adapter = new RVARentals(this, list);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
/*
    @Override
    public void mostarListAlquileres(ArrayList<ModelAlquilerView> list) {
        adapterAlquiler = new RvAdapterAlquiler(this, list);
        //GridLayoutManager manager = new GridLayoutManager(this, 2);

        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(adapterAlquiler);
    }
*/

    @Override
    public void setiMenu(int i) {
        iMenu = i;
    }

    @Override
    public void salir() {
        onBackPressed();
    }

    @Override
    public void goTo(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void showDialog(DialogDetallesAlquiler dialogDetallesAlquiler) {
        dialogDetallesAlquiler.show(getSupportFragmentManager(), TAG_MOSTRAR_PAGOS);
    }

    @Override
    public void onClickHolder(RecyclerView.ViewHolder holder) {
        presenter.onClickHolder(holder);
    }

    public void onClickUsuario(RvAdapterUser.Holder view) {
    }

    public void onClickAlquiler(String id) {


    }
}
