package com.alexander_rodriguez.mihogar.mi_casa;

import android.database.Cursor;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.MyAdminDate;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.mi_casa.Models.ModelCuartoView;

import java.util.ArrayList;

public class Presenter extends BasePresenter<Interface.View> implements Interface.Presenter {
    private MyAdminDate myAdminDate;
    public Presenter(Interface.View view) {
        super(view);
        myAdminDate = new MyAdminDate();
    }

    @Override
    public void iniciarComandos() {

        //View.mostratCuartos(getListCuartos());
        //View.mostrarAlquileres(getListAlquileres());
        view.mostratCuartos(getListCuartos());
    }

    @Override
    public void terminarAlquiler(String motivo, String id) {
        db.upDateAlquiler(TAlquiler.VAL, "0", id);
        db.upDateAlquiler(TAlquiler.MOTIVO, motivo, id);
        //view.mostrarAlquileres(getListAlquileres());
        verTodos();
    }

    @Override
    public void verTodos() {
        view.mostratCuartos(getListCuartos());
    }

    @Override
    public void verCuartosAlquilados() {
        view.mostratCuartos(getListCuartosAlquilados());
    }

    @Override
    public void verCuartosLibres() {view.mostratCuartos(getListCuartosLibres());}

    private ArrayList<ModelCuartoView> getListCuartosLibres(){
        String columnas = "*";
        Cursor c = db.getCuartosLibres(columnas);
        return ModelCuartoView.createListModel(c);
    }
    private ArrayList<ModelCuartoView> getListCuartosAlquilados(){
        String columnas = "*";
        Cursor c = db.getCuartosAlquilados(columnas);
        return ModelCuartoView.createListModel(c);
    }
    private ArrayList<ModelCuartoView> getListCuartos() {
        String columnas = "*";
        Cursor c = db.getAllCuartos(columnas);
        return ModelCuartoView.createListModel(c);
    }
}
