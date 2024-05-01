package com.alexander_rodriguez.mihogar.DataBase.models;

public class TUser {
    protected String name;
    protected String apellidoPat;
    protected String apellidoMat;
    protected String path;
    protected boolean alerted;
    protected boolean main;

    public TUser(){}

    public TUser(String name, String apellidoPat, String apellidoMat, String path, boolean alerted, boolean main) {
        this.name = name;
        this.apellidoPat = apellidoPat;
        this.apellidoMat = apellidoMat;
        this.path = path;
        this.alerted = alerted;
        this.main = main;
    }

    public String getName() {
        return name;
    }

    public String getApellidoPat() {
        return apellidoPat;
    }

    public String getApellidoMat() {
        return apellidoMat;
    }

    public String getPath() {
        return path == null ? "": path;
    }

    public boolean isMain() {
        return main;
    }

    public boolean isAlerted(){ return alerted;}

    public void setName(String name) {
        this.name = name;
    }

    public void setApellidoPat(String apellidoPat) {
        this.apellidoPat = apellidoPat;
    }

    public void setApellidoMat(String apellidoMat) {
        this.apellidoMat = apellidoMat;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

}
