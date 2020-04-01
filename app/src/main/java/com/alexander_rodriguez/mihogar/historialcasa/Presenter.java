package com.alexander_rodriguez.mihogar.historialcasa;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.drawable.Drawable;

import com.alexander_rodriguez.mihogar.Adapters.Models.ModelCuartoView;
import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.MyAdminDate;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.TableCursor;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.UTILIDADES.TUsuario;
import com.alexander_rodriguez.mihogar.Adapters.Models.ModelAlquilerView;
import com.alexander_rodriguez.mihogar.Adapters.Models.ModelUserView;

import java.util.ArrayList;

public class Presenter extends BasePresenter<Interface.View> implements Interface.Presenter {
    private MyAdminDate myAdminDate;
    public Presenter(Interface.View view) {
        super(view);
    }

    @Override
    public void iniciarComandos(){
        myAdminDate = new MyAdminDate();
        mostrarUsuarios();
    }

    @Override
    public void mostrarUsuarios() {
        view.mostarListUsuarios(getListUsuarios());
    }
    private ArrayList<ModelUserView> getListUsuarios(){
        String columnas = "*";
        Cursor c = db.getAllUsuariosADDAlert(columnas);
        return ModelUserView.createListModel(c);

    }

    @Override
    public void mostrarAlquileres() {
        view.mostarListAlquileres(getListAlquileres());
    }

    private ArrayList<ModelAlquilerView> getListAlquileres(){
        String columnas = "*";
        Cursor c = db.getAllAlquilerJoinUser(columnas);
        return ModelAlquilerView.createListModel(c);
    }
}
