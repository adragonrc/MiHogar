package com.alexander_rodriguez.mihogar.DataBase.items;

public class ModelAlquiler {

    private String id;
    private String fechaInicial;
    private String fechaFinal;
    private String motivo;
    private String valid;

    public String dni;
    public String numeroCuarto;

    public ModelAlquiler(String id, String fechaInicial, String fechaFinal, String motivo, String valid, String dni, String numeroCuarto) {
        this.id = id;
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
        this.motivo = motivo;
        this.valid = valid;
        this.dni = dni;
        this.numeroCuarto = numeroCuarto;
    }

    public String getId() {
        return id;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public String getFechaInicial() {
        return fechaInicial;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getValid() {
        return valid;
    }

    public String getDni() {
        return dni;
    }

    public String getNumeroCuarto() {
        return numeroCuarto;
    }
}
