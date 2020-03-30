package com.alexander_rodriguez.mihogar.mi_casa.Models;

import android.database.Cursor;
import android.graphics.drawable.Drawable;

import com.alexander_rodriguez.mihogar.UTILIDADES.TCuarto;

import java.util.ArrayList;

public class ModelCuartoView {
    private String letra;
    private String numero;
    private String descripcion;
    private String precio;
    private String path;
    private Drawable background;

    public ModelCuartoView(String numero, String descripcion, String precio, String path) {
        this.numero = numero;
        this.descripcion = descripcion;
        this.precio = precio;
        this.path = path;
        this.letra = numero.substring(0,1);
    }
    public ModelCuartoView(Cursor c){
        this.numero = c.getString(TCuarto.INT_NUMERO);
        this.descripcion = c.getString(TCuarto.INT_DETALLES);
        this.precio = c.getString(TCuarto.INT_PRECIO_E);
        this.path = c.getString(TCuarto.INT_URL);
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

    public String getLetra() {
        return letra;
    }

    public String getPath() {
        return path;
    }

    public Drawable getBackground() {
        return background;
    }
}
