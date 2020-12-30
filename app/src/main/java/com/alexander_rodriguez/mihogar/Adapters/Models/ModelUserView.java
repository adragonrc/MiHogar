package com.alexander_rodriguez.mihogar.Adapters.Models;

import android.content.Intent;
import android.database.Cursor;

import com.alexander_rodriguez.mihogar.DataBase.items.ItemUser;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquilerUsuario;
import com.alexander_rodriguez.mihogar.UTILIDADES.TUsuario;
import com.alexander_rodriguez.mihogar.modelos.ModelUsuario;

import java.util.ArrayList;

public class ModelUserView  {
    private String dni;
    private String nombres;
    private String path;
    private boolean alert;
    private Boolean main;

    public ModelUserView(String dni, String nombres, String path) {
        this.dni = dni;
        this.nombres = nombres;
        this.path = path==null ? "": path;
        main = false;
    }

    public ModelUserView(Cursor c, boolean showMain){
        this.dni = c.getString(TUsuario.INT_DNI);
        this.nombres = c.getString(TUsuario.INT_NOMBRES) + " " +c.getString(TUsuario.INT_APELLIDO_PAT);
        this.path = c.getString(TUsuario.INT_URI);
        this.alert = false;
        main = false;
        if (showMain) {
            String flag = c.getString(c.getColumnIndex(TAlquilerUsuario.IS_ENCARGADO));
            if (flag != null && flag.equals("1"))
                main = true;
        }
        this.path = path==null ? "": path;
    }

    public ModelUserView(Intent i){
        this.dni = i.getStringExtra(TUsuario.DNI);
        this.nombres = i.getStringExtra(TUsuario.NOMBRES) + " " +i.getStringExtra(TUsuario.APELLIDO_PAT);
        this.path = i.getStringExtra(TUsuario.URI);
        //this.alert = i.getStringExtra(TAlquiler.ALERT);
        main = false;
        this.path = path==null ? "": path;
    }

    public ModelUserView(ModelUsuario m) {
        this.dni = m.getDni();
        this.nombres = m.getNombre();
        this.path = m.getPath();
        this.alert = false;
        main = false;
        this.path = path==null ? "": path;
    }

    public ModelUserView(ItemUser m) {
        this.dni = m.getDni();
        this.nombres = m.getName();
        this.path = m.getPath();
        this.alert = m.isAlerted();
        main = false;
        this.path = path==null ? "": path;
    }
    public static ArrayList<ItemUser> createListModel(Cursor c, boolean showMain){
        ArrayList<ItemUser> list = new ArrayList<>();
        if (c.moveToFirst()){
            do{
                ModelUserView mcv = new ModelUserView(c, showMain);
                //list.add(mcv);
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

    public void setMain(boolean main) {
        this.main = main;
    }

    public Boolean isMain() {
        return main;
    }

    public boolean isAlert() {
        return alert;
    }
}
