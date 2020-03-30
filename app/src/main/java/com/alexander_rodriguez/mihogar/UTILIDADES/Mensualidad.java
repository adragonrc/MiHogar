package com.alexander_rodriguez.mihogar.UTILIDADES;

public class Mensualidad {
    public static final String T_NOMBRE = "mensualidad";
    public static final String ID = "idMensualidad";
    public static final String FECHA_I = "fecha_i";
    public static final String COSTO = "costo";
    public static final String ID_A= TAlquiler.ID;

    public static final String CREATE_TABLE=
            "create table " + T_NOMBRE + "(" +
                    ID + " integer primary key autoincrement not null,"+
                    FECHA_I + " datetime," +
                    COSTO + " double," +
                    ID_A + " integer," +
                    "foreign key ("+ ID_A+ ")references "+TAlquiler.T_NOMBRE +"("+TAlquiler.ID+")"+
                    ");";
}
