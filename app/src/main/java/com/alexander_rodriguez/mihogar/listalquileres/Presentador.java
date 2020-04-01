package com.alexander_rodriguez.mihogar.listalquileres;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.TableCursor;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.UTILIDADES.TCuarto;
import com.alexander_rodriguez.mihogar.UTILIDADES.TUsuario;
import com.alexander_rodriguez.mihogar.Adapters.Models.ModelAlquilerView;

import java.util.ArrayList;

public class Presentador extends BasePresenter<Interface.Vista>{
    private String numeroCuarto;
    private String dniUsuario;
    private Intent i;
    public Presentador(Interface.Vista view, Intent i) {
        super(view);
        this.i = i;
        numeroCuarto = i.getStringExtra(TCuarto.NUMERO);
        dniUsuario = i.getStringExtra(TUsuario.DNI);
    }

    @Override
    public void iniciarComandos() {
        view.mostarListAlquileres(getListAlquileres());
    }
    private ArrayList<ModelAlquilerView> getListAlquileres(){
        String columnas = "*";
        Cursor c = db.getAllAlquileresJoinUserExept(columnas, TCuarto.NUMERO, numeroCuarto, dniUsuario);
        return ModelAlquilerView.createListModel(c);
    }
}
