package com.alexander_rodriguez.mihogar.viewregistraralquiler;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;

public class ModelAA{
    private String precio;
    private String correo;
    private String numeroTelef;

    private String numCuarto;
    private String fecha;

    private String plazo;

    private String fecha_c;
    private boolean pago;

    public ModelAA(String precio, String correo, String numeroTelef, String numCuarto, String fecha, String plazo, boolean pago){
        this.precio = precio;
        this.correo = correo;
        this.numeroTelef = numeroTelef;
        this.numCuarto = numCuarto;
        this.fecha = fecha;
        this.plazo = plazo;
        this.fecha_c = fecha;
        this.pago = pago;
    }

    public String getPrecio() {
        return precio;
    }

    public String getCorreo() {
        return correo;
    }

    public String getNumeroTelef() {
        return numeroTelef;
    }

    public String getNumCuarto() {
        return numCuarto;
    }

    public String getFecha() {
        return fecha;
    }

    public String getPlazo() {
        return plazo;
    }

    public boolean isPago() {
        return pago;
    }

    public boolean isCorrect() {
        return  BasePresenter.validarStrings(precio, numCuarto, fecha, plazo);
    }

    public void setFecha_c(String fecha_c) {
        this.fecha_c = fecha_c;
    }

    public String getFechaC() {
        return fecha_c;
    }
}
