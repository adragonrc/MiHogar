package com.alexander_rodriguez.mihogar.Modelos;

public class ModelCuarto{
    private String numero;
    private String detalles;
    private String precio;

    public ModelCuarto(String numero, String detalles, String precio) {
        this.numero = numero;
        this.detalles = detalles;
        this.precio = precio;
    }

    public String getNumero() {
        return numero;
    }

    public String getDetalles() {
        return detalles;
    }

    public String getPrecio() {
        return precio;
    }
}
