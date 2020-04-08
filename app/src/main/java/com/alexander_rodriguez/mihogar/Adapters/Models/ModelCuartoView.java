package com.alexander_rodriguez.mihogar.Adapters.Models;

import android.database.Cursor;

import com.alexander_rodriguez.mihogar.MyAdminDate;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.UTILIDADES.TCuarto;

import java.util.ArrayList;
import java.util.Date;

public class ModelCuartoView {
    private String numero;
    private String descripcion;
    private String precio;
    private String path;
    private String fechaCancelar;
    private boolean alert;

    public ModelCuartoView(String numero, String descripcion, String precio, String path) {
        this.numero = numero;
        this.descripcion = descripcion;
        this.precio = precio;
        this.path = path;
    }
    public ModelCuartoView(Cursor c){
        MyAdminDate date = new MyAdminDate();
        date.setFormat(MyAdminDate.FORMAT_DATE_TIME);
        this.numero = c.getString(TCuarto.INT_NUMERO);
        this.descripcion = c.getString(TCuarto.INT_DETALLES);
        this.precio = c.getString(TCuarto.INT_PRECIO_E);
        this.path = c.getString(TCuarto.INT_URL);
        int index = c.getColumnIndex(TAlquiler.Fecha_PAGO);
        this.alert = false;
        fechaCancelar = "";
        if (index != -1){
            fechaCancelar = c.getString(index);
            if(fechaCancelar != null)
                this.alert = date.stringToDate(fechaCancelar).before(new Date());
        }
    }

    public static ArrayList<ModelCuartoView> createListModel(Cursor c){
        ArrayList<ModelCuartoView> list = new ArrayList<>();
        if (c.moveToFirst()){
            do{
                ModelCuartoView mcv = new ModelCuartoView(c);
                list.add(mcv);
            }while(c.moveToNext());
        }
        c.close();
        return list;
    }
    public String getNumero() {
        return numero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public String getPath() {
        return path;
    }

    public boolean isAlert() {
        return alert;
    }

    public String getFechaCancelar() {
        return fechaCancelar;
    }
}
