package com.alexander_rodriguez.mihogar.Modelos;

public class ModelMensualidad {
    private String id;
    private String fechaInicio;
    private String costo;
    private String id_Alquiler;

    public ModelMensualidad(String id, String fechaInicio, String costo, String id_Alquiler) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.costo = costo;
        this.id_Alquiler = id_Alquiler;
    }

    public String getId() {
        return id;
    }

    public String getCosto() {
        return costo;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getId_Alquiler() {
        return id_Alquiler;
    }
}
