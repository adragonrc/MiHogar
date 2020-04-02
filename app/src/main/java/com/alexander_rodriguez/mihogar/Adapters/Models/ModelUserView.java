package com.alexander_rodriguez.mihogar.Adapters.Models;

import android.database.Cursor;

import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.UTILIDADES.TUsuario;

import java.util.ArrayList;

public class ModelUserView {
    private String dni;
    private String nombres;
    private String path;
    private String alert;

    public ModelUserView(String dni, String nombres, String path) {
        this.dni = dni;
        this.nombres = nombres;
        this.path = path;
    }

    public ModelUserView(Cursor c){
        this.dni = c.getString(TUsuario.INT_DNI);
        this.nombres = c.getString(TUsuario.INT_NOMBRES) + " " +c.getString(TUsuario.INT_APELLIDO_PAT);
        this.path = c.getString(TUsuario.INT_URI);
        this.alert = c.getString(TAlquiler.INT_ALERT);
    }

    public static ArrayList<ModelUserView> createListModel(Cursor c){
        ArrayList<ModelUserView> list = new ArrayList<>();
        if (c.moveToFirst()){
            do{
                ModelUserView mcv = new ModelUserView(c);
                list.add(mcv);
            }while(c.moveToNext());
        }
        c.close();
        return list;
    }

    public String getNombres() {
        return nombres;
    }

    public String getDni() {
        return dni;
    }

    public String getPath() {
        return path;
    }

    public boolean getAlert() {
        if (alert == null) return  false;
        return alert.equals("1");
    }
}
