package com.alexander_rodriguez.mihogar.modelos;

import android.content.Intent;

import com.alexander_rodriguez.mihogar.UTILIDADES.TUsuario;

public class ModelUsuario {
    private String dni;
    private String nombre;
    private String apellidoPat;
    private String apellidoMat;
    private String path;
    private boolean main;

    public ModelUsuario(String dni, String nombre, String apellidoPat, String apellidoMat, String path) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidoPat = apellidoPat;
        this.apellidoMat = apellidoMat;
        if (path == null ) this.path = "";
        else    this. path = path;
        main = false;
    }

    public ModelUsuario(String dni, String nombre, String apellidoPat, String apellidoMat, String path, boolean main) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidoPat = apellidoPat;
        this.apellidoMat = apellidoMat;
        if (path == null ) this.path = "";
        else   this. path = path;
        this.main = main;
    }

    public ModelUsuario(Intent i) {
        this.dni = i.getStringExtra(TUsuario.DNI);
        this.nombre = i.getStringExtra(TUsuario.NOMBRES);
        this.apellidoPat = i.getStringExtra(TUsuario.APELLIDO_PAT);
        this.apellidoMat = i.getStringExtra(TUsuario.APELLIDO_MAT);
        this.path = i.getStringExtra(TUsuario.URI);
        main = false;

    }

    public String[] toArray(){
        return new String[]{dni, nombre, apellidoPat, apellidoMat, path};
    }

    public void setMain(boolean main) {
        this.main = main;
    }

    public boolean isMain() {
        return main;
    }

    public String getPath() {
        return path;
    }

    public String getApellidoMat() {
        return apellidoMat;
    }

    public String getApellidoPat() {
        return apellidoPat;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
