package com.alexander_rodriguez.mihogar.mi_casa.Models;

import android.graphics.drawable.Drawable;

public class ModelUserView {
    private String dni;
    private String nombres;
    private Drawable drawable;
    private String letra;
    public ModelUserView(String dni, String nombres, Drawable drawable) {
        this.dni = dni;
        this.nombres = nombres;
        this.drawable = drawable;
        letra = nombres.substring(0,1);
    }

    public String getNombres() {
        return nombres;
    }

    public String getDni() {
        return dni;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public String getLetra() {
        return letra;
    }
}
