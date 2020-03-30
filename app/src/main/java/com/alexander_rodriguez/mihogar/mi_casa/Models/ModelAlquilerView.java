package com.alexander_rodriguez.mihogar.mi_casa.Models;

import android.graphics.drawable.Drawable;

public class ModelAlquilerView {
    private String nombres;
    private String dni;
    private String fecha;
    private String numCuarto;
    private String letra;
    private Drawable background;
    private String id;

    public ModelAlquilerView(String id, String dni, String nombres, String fecha, String numCuarto, Drawable background) {
        this.id = id;
        this.nombres = nombres;
        this.dni = dni;
        this.fecha = fecha;
        this.numCuarto = numCuarto;
        this.background= background;
        letra = nombres.substring(0,1);
    }

    public String getDni() {
        return dni;
    }

    public String getFecha() {
        return fecha;
    }

    public String getLetra() {
        return letra;
    }

    public String getNombres() {
        return nombres;
    }

    public String getNumCuarto() {
        return numCuarto;
    }

    public Drawable getBackground() {
        return background;
    }

    public String getId() {
        return id;
    }
}
