package com.alexander_rodriguez.mihogar.historialcasa;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.Adapters.RvAdapterAlquiler;
import com.alexander_rodriguez.mihogar.Adapters.RvAdapterUser;
import com.alexander_rodriguez.mihogar.Base.BaseActivity;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.UTILIDADES.TUsuario;
import com.alexander_rodriguez.mihogar.historialUserPakage.HistorialUsuarioActivity;
import com.alexander_rodriguez.mihogar.Adapters.Models.ModelAlquilerView;
import com.alexander_rodriguez.mihogar.Adapters.Models.ModelUserView;
import com.alexander_rodriguez.mihogar.tableActivity.TableActivity;
import com.alexander_rodriguez.mihogar.viewUser.DialogDetallesAlquiler;

import java.util.ArrayList;

public class HistorialCasaActivity extends BaseActivity<Interface.Presenter> implements Interface.View, RvAdapterUser.Interface, RvAdapterAlquiler.Interface{
    public static final String MODO_SOLO_USUARIOS = "solo_usuarios";
    public static final String MODO_SOLO_ALQUILERES = "solo_alquileres";

    public static final String MODO_DEFAULT = "default";

    public static final String TYPE_MODE = "tipo_modo";

    public static final String TAG_MOSTRAR_PAGOS = "tag_table_pagos";
    private RecyclerView recyclerView;
    private RvAdapterUser adapterUser;
    private RvAdapterAlquiler adapterAlquiler;
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
        return new Presenter(this, getIntent());
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
    public void mostarListUsuarios(ArrayList<ModelUserView> list) {
        adapterUser = new RvAdapterUser(this, list);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterUser);
    }

    @Override
    public void mostarListAlquileres(ArrayList<ModelAlquilerView> list) {
        adapterAlquiler = new RvAdapterAlquiler(this, list);
        GridLayoutManager manager = new GridLayoutManager(this, 2);

        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(adapterAlquiler);
    }

    @Override
    public void setiMenu(int i) {
        iMenu = i;
    }

    @Override
    public void salir() {
        onBackPressed();
    }

    @Override
    public void onClickUsuario(RvAdapterUser.Holder view) {
        TextView tv = view.getDNI();
        String dni = tv.getText().toString();
        Intent intent = new Intent(this, HistorialUsuarioActivity.class);
        intent.putExtra(TUsuario.DNI,dni);
        startActivity(intent);
    }

    @Override
    public void onClickAlquiler(String id) {
        Bundle bundle = new Bundle();
        ContentValues alquiler = presenter.getDetallesAlquiler(id);

        DialogDetallesAlquiler dialogDetallesAlquiler = new DialogDetallesAlquiler(this, alquiler);
        dialogDetallesAlquiler.setOnClickListenerVerCuarto(v -> {
            Intent i = new Intent(this, HistorialCasaActivity.class);
            i.putExtra(HistorialCasaActivity.TYPE_MODE, HistorialCasaActivity.MODO_SOLO_USUARIOS);
            i.putExtra(TAlquiler.ID, id);
            startActivity(i);
        });
        dialogDetallesAlquiler.setOnClickListenerVerPagos(v -> {
            Intent intent = new Intent(HistorialCasaActivity.this, TableActivity.class);
            intent.putExtra(TAlquiler.ID, id);
            startActivity(intent);
        });

        dialogDetallesAlquiler.show(getSupportFragmentManager(), TAG_MOSTRAR_PAGOS);

    }
}
