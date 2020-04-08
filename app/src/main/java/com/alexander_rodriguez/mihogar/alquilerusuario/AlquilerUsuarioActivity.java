package com.alexander_rodriguez.mihogar.alquilerusuario;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.Base.BaseActivity;
import com.alexander_rodriguez.mihogar.Adapters.RecyclercViewAdapter;
import com.alexander_rodriguez.mihogar.Base.BaseActivity;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.tableActivity.TableActivity;

import java.util.ArrayList;

public class AlquilerUsuarioActivity extends BaseActivity<Interface.Presentador> implements Interface.Vista {
    public static final String EXTRA_DNI = "extra_dni";
    private RecyclerView rv;

    private RecyclercViewAdapter recyclercViewAdapter;
    private RecyclerView.LayoutManager manager;
    private View.OnClickListener listener;

    @Override
    protected void iniciarComandos() {
        setTitle("Registros");
        manager = new LinearLayoutManager(this);
        rv.setLayoutManager(manager);
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemListener(v);
            }
        };
    }
    public void mostrarRecycleView(ArrayList<Item> list){
        recyclercViewAdapter = new RecyclercViewAdapter(this, list);
        recyclercViewAdapter.setOnClickItemListener(listener);
        rv.setAdapter(recyclercViewAdapter);

    }

    @Override
    public void onClickItemListener(View v) {
        String id = ((TextView)v.findViewById(R.id.tvIdAlquiler)).getText().toString();
        Intent i = new Intent(this, TableActivity.class);
        i.putExtra(TAlquiler.ID,id);
        startActivity(i);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_alquiler_usuario;
    }

    @NonNull
    @Override
    protected Interface.Presentador createPresenter() {
        return new Presenter(this, getIntent().getIntExtra(EXTRA_DNI, -1));
    }

    @Override
    protected void iniciarViews() {
        rv = findViewById(R.id.recyclerView);
    }
}
