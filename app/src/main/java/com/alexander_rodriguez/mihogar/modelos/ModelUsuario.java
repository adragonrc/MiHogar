package com.alexander_rodriguez.mihogar.modelos;

public class ModelUsuario {
    private String dni;
    private String nombres;
    private String apellidoPat;
    private String apellidoMat;
    private String numero;
    private String correo;
    private String alert;
    private String uriPhoto;

    public ModelUsuario(String dni, String nombres, String apellidoPat, String apellidoMat, String numero, String correo, String alert, String uriPhoto) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidoPat = apellidoPat;
        this.apellidoMat = apellidoMat;
        this.numero = numero;
        this.correo = correo;
        this.alert = alert;
        this.uriPhoto = uriPhoto;
    }

    public String getAlert() {
        return alert;
    }

    public String getDni() {
        return dni;
    }

    public String getNombres() {
        return nombres;
    }

    public String getCorreo() {
        return correo;
    }

    public String getNumero() {
        return numero;
    }

    public String getApellidoMat() {
        return apellidoMat;
    }

    public String getApellidoPat() {
        return apellidoPat;
    }

    public String getUriPhoto() {
        return uriPhoto;
    }

}
