package com.alexander_rodriguez.mihogar.historialcasa;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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

import java.util.ArrayList;

public class HistorialCasaActivity extends BaseActivity<Interface.Presenter> implements Interface.View, RvAdapterUser.Interface, RvAdapterAlquiler.Interface{
    private RecyclerView recyclerView;
    private RvAdapterUser adapterUser;
    private RvAdapterAlquiler adapterAlquiler;
    private RecyclerView.LayoutManager manager;

    private int menu;
    @Override
    protected void iniciarComandos() {
        setTitle("Historial");
        ActionBar ab =  getSupportActionBar();
        if (ab != null){
            ab.setDisplayHomeAsUpEnabled(true);
        }
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        menu = R.menu.menu_historial_mi_casa;
    }
    @NonNull
    @Override
    protected Interface.Presenter createPresenter() {
        return new Presenter(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mi_casa;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(this.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.iVerAlquileres:{
                presenter.mostrarAlquileres();
                break;
            }
            case R.id.iVerUsuario:{
                presenter.mostrarUsuarios();
                break;
            }
            case BACK_PRESSED:{
                onBackPressed();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void iniciarViews() {
        recyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    public void mostarListUsuarios(ArrayList<ModelUserView> list) {
        adapterUser = new RvAdapterUser(this, list);
        recyclerView.setAdapter(adapterUser);
    }

    @Override
    public void mostarListAlquileres(ArrayList<ModelAlquilerView> list) {
        adapterAlquiler = new RvAdapterAlquiler(this, list);
        recyclerView.setAdapter(adapterAlquiler);

    }
    @Override
    public void onClickUsuario(View view) {
        String dni = ((TextView) view).getText().toString();
        Intent intent = new Intent(this, HistorialUsuarioActivity.class);
        intent.putExtra(TUsuario.DNI,dni);
        startActivity(intent);
    }

    @Override
    public void onClickAlquiler(String id) {
        Intent intent = new Intent(this, TableActivity.class);
        intent.putExtra(TAlquiler.ID, id);
        startActivity(intent);
    }
}
