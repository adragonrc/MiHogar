package com.alexander_rodriguez.mihogar.Adapters.Models;

import android.content.ContentValues;
import android.database.Cursor;

import com.alexander_rodriguez.mihogar.DataBase.DataBaseAdmin;
import com.alexander_rodriguez.mihogar.DataBase.DataBaseInterface;
import com.alexander_rodriguez.mihogar.MyAdminDate;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ModelAlquilerView {
    private String nombres;
    private String fecha;
    private String numCuarto;
    private boolean alert;
    private String id;
    private String path;

    public ModelAlquilerView(String nombres, String dni, String fecha, String numCuarto, boolean alert, String id, String path) {
        this.nombres = nombres;
        this.fecha = fecha;
        this.numCuarto = numCuarto;
        this.alert = alert;
        this.id = id;
        this.path = path;
    }

    public ModelAlquilerView(Cursor c){
//        this.nombres = c.getString(c.getColumnIndex(TUsuario.NOMBRES)) + " " +c.getString(c.getColumnIndex(TUsuario.APELLIDO_PAT));
        this.fecha = c.getString(c.getColumnIndex(TAlquiler.FECHA_INICIO));
        Format f = new SimpleDateFormat(MyAdminDate.FORMAT_DATE, Locale.getDefault());
        fecha = f.format(new Date());
        this.numCuarto = c.getString(c.getColumnIndex(TAlquiler.NUMERO_C));
//      this.alert = c.getString(TAlquiler.INT_ALERT).equals("1");
        this.id = c.getString(TAlquiler.INT_ID);
//        this.path = c.getString(c.getColumnIndex(TUsuario.URI));
    }

    public static ArrayList<ModelAlquilerView> createListModel(Cursor c){
        ArrayList<ModelAlquilerView> list = new ArrayList<>();
        if (c.moveToFirst()){
            do{
                ContentValues cv = DataBaseInterface.cursorToCV(c);
                ModelAlquilerView mav = new ModelAlquilerView(c);
                list.add(mav);
            }while(c.moveToNext());
        }
        c.close();
        return list;
    }

    public String getNombres() {
        return nombres;
    }

    public String getFecha() {
        return fecha;
    }

    public String getNumCuarto() {
        return numCuarto;
    }

    public boolean isAlert() {
        return alert;
    }

    public String getId() {
        return id;
    }

    public String getPath() {
        return path;
    }
}
