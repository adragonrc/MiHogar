package com.alexander_rodriguez.mihogar.historialcasa;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.Adapters.AdapterInterface;
import com.alexander_rodriguez.mihogar.Adapters.RvAdapterAlquiler;
import com.alexander_rodriguez.mihogar.Adapters.RvAdapterUser;
import com.alexander_rodriguez.mihogar.Base.BaseActivity;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemUser;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.UTILIDADES.TUsuario;
import com.alexander_rodriguez.mihogar.historialUserPakage.HistorialUsuarioActivity;
import com.alexander_rodriguez.mihogar.tableActivity.TableActivity;
import com.alexander_rodriguez.mihogar.viewUser.DialogDetallesAlquiler;

import java.util.ArrayList;

public class HistorialCasaActivity extends BaseActivity<Interface.Presenter> implements Interface.View, AdapterInterface{

    public static final String ALL_USERS = "allUsers";

    public static final String USERS_OF_RENTAL = "usersOfRental";

    public static final String RENTALS_OF_USER = "rentalOfUsers";

    public static final String ALL_RENTALS = "allRentals";

    public static final String MODE = "mode";

    public static final String TAG_MOSTRAR_PAGOS = "tag_table_pagos";

    private RecyclerView recyclerView;

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
            case RENTALS_OF_USER:{
                return new UserRentalsPresenter(this, getIntent());
            }
            default: {
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
    }

    @Override
    public void showList(ArrayList<ItemUser> list, RecyclerView.LayoutManager manager) {
        adapterUser = new RvAdapterUser(this, list);

        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(adapterUser);
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
