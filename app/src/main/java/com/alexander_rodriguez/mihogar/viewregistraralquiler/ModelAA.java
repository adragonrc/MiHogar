package com.alexander_rodriguez.mihogar.viewregistraralquiler;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.DataBase.models.TRental;
import com.alexander_rodriguez.mihogar.MyAdminDate;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;

public class ModelAA extends TRental {
    private String precio;
    private String correo;
    private String numeroTelef;

    private String numCuarto;
    private String fecha;

    private String plazo;

    private int pagosRealizados;


    public ModelAA(String precio, String correo, String numeroTelef, String roomNumber, String entryDate,  int pagosRealizados, String plazo){
        super(entryDate, null, null, "true", roomNumber, null, 0);
        this.precio = precio;
        this.correo = correo;
        this.numeroTelef = numeroTelef;
        this.numCuarto = roomNumber;
        this.fecha = entryDate;
        this.plazo = plazo;
        this.pagosRealizados = pagosRealizados;
    }

    public TRental getRoot(){
        return (TRental) this;
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

    public boolean wasPaid() {
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
