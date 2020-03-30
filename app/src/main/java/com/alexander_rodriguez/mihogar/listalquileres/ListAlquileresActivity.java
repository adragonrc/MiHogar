package com.alexander_rodriguez.mihogar.listalquileres;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.Adapters.RvAdapterAlquiler;
import com.alexander_rodriguez.mihogar.Base.BaseActivity;
import com.alexander_rodriguez.mihogar.Base.IBasePresenter;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.UTILIDADES.TCuarto;
import com.alexander_rodriguez.mihogar.mi_casa.Models.ModelAlquilerView;
import com.alexander_rodriguez.mihogar.tableActivity.TableActivity;

import java.util.ArrayList;

public class ListAlquileresActivity extends BaseActivity<IBasePresenter> implements Interface.Vista, RvAdapterAlquiler.Interface{
    private RvAdapterAlquiler adapterAlquiler;
    private RecyclerView recyclerView;

    @Override
    protected void iniciarComandos() {
        setTitle("Lista Alquileres");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_list_alquileres;
    }

    @NonNull
    @Override
    protected IBasePresenter createPresenter() {
        return new Presentador(this, getIntent().getStringExtra(TCuarto.NUMERO));
    }

    @Override
    protected void iniciarViews() {
        recyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    public void mostarListAlquileres(ArrayList<ModelAlquilerView> list) {
        adapterAlquiler = new RvAdapterAlquiler(this, list);
        recyclerView.setAdapter(adapterAlquiler);
    }

    @Override
    public void onClickAlquiler(String id) {
        Intent intent = new Intent(this, TableActivity.class);
        intent.putExtra(TAlquiler.ID, id);
        startActivity(intent);
    }
}
