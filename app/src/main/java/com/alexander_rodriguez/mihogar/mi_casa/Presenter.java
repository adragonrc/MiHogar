package com.alexander_rodriguez.mihogar.mi_casa;

import android.database.Cursor;
import android.os.Bundle;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.MyAdminDate;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.Adapters.Models.ModelCuartoView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class Presenter extends BasePresenter<Interface.View> implements Interface.Presenter {
    private MyAdminDate myAdminDate;
    private ArrayList<ModelCuartoView> lista;
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
        db.upDateAlquiler(TAlquiler.FECHA_SALIDA, MyAdminDate.getFechaActual(), id);
        db.upDateAlquiler(TAlquiler.MOTIVO, motivo, id);
        //view.mostrarAlquileres(getListAlquileres());
        verTodos();
    }

    @Override
    public void verTodos() {
        lista = getListCuartos();
        view.mostratCuartos(lista);
    }

    @Override
    public void verCuartosAlquilados() {
        lista = getListCuartosAlquilados();
        view.mostratCuartos(lista);
    }

    @Override
    public void verCuartosLibres() {
        lista = getListCuartosLibres();
        view.mostratCuartos(lista);}

    @Override
    public void ordenarPorFecha() {
        Collections.sort(lista, new Comparator<ModelCuartoView>() {
            @Override
            public int compare(ModelCuartoView o1, ModelCuartoView o2) {
                try {
                    return MyAdminDate.comparar(o1.getFechaCancelar(), o2.getFechaCancelar());
                } catch (ParseException e) {
                    e.printStackTrace();
                    view.showMensaje("Error con la fecha");
                    return 0;
                }
            }
        });
        view.mostratCuartos(lista);
    }

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
        Cursor c = db.getAllCuartosJoinAlquiler(columnas);
        return ModelCuartoView.createListModel(c);
    }
}
