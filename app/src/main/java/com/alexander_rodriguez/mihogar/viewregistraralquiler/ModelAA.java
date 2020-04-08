package com.alexander_rodriguez.mihogar.viewregistraralquiler;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.MyAdminDate;

public class ModelAA{
    private String precio;
    private String correo;
    private String numeroTelef;

    private String numCuarto;
    private String fecha;

    private String plazo;

    private int pagosRealizados;

    public ModelAA(String precio, String correo, String numeroTelef, String numCuarto, String fecha,  int pagosRealizados, String plazo){
        this.precio = precio;
        this.correo = correo;
        this.numeroTelef = numeroTelef;
        this.numCuarto = numCuarto;
        this.fecha = fecha;
        this.plazo = plazo;
        this.pagosRealizados = pagosRealizados;
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

    public boolean pago() {
        return pagosRealizados != 0;
    }

    public boolean isCorrect() {
        return  BasePresenter.validarStrings(precio, numCuarto, fecha, plazo);
    }


    public int getPagosRealizados() {
        return pagosRealizados;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
