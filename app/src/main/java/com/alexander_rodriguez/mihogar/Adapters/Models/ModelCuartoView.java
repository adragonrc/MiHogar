package com.alexander_rodriguez.mihogar.Adapters.Models;

import android.database.Cursor;
import android.os.Bundle;

import com.alexander_rodriguez.mihogar.MyAdminDate;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.UTILIDADES.TCuarto;

import java.text.ParseException;
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
        this.path = c.getString(TCuarto.INT_URL); this.alert = false;
        fechaCancelar = "";

        int index = c.getColumnIndex(TAlquiler.PAGOS_REALIZADOS);
        int index2 = c.getColumnIndex(TAlquiler.FECHA_INICIO);
        if (index != -1 && index2 != -1){
            String fechaInicio = c.getString(index2);
            if (fechaInicio != null) {
                int pagos = c.getInt(index);
                try {
                    fechaCancelar = MyAdminDate.adelantarPorMeses(fechaInicio, pagos);
                } catch (ParseException e) {
                    e.printStackTrace();
                    fechaCancelar = null;
                }

                if (fechaCancelar != null)
                    this.alert = date.stringToDate(fechaCancelar).before(new Date());
            }
        }
    }

    public static ArrayList<ModelCuartoView> createListModel(Cursor c){
        ArrayList<ModelCuartoView> list = new ArrayList<>();
        if (c.moveToFirst()){
            do{
                Bundle b = c.getExtras();
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
