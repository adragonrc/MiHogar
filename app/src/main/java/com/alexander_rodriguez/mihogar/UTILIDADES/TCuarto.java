package com.alexander_rodriguez.mihogar.UTILIDADES;
public class TCuarto {
    public static final String T_NOMBRE = "cuartos";
    public static final String NUMERO= "numero";
    public static final String DETALLES = "detalles";
    public static final String PRECIO_E= "precio_e";
    public static final String URL = "url";

    public static final int INT_NUMERO= 0;
    public static final int INT_DETALLES = 1;
    public static final int INT_PRECIO_E= 2;
    public static final int INT_URL = 3;

    public static final String CREATE_TABLE =
            "create table "+ T_NOMBRE+ "(" +
                    NUMERO + " varchar(10) primary key, " +
                    DETALLES + " varchar(45)," +
                    PRECIO_E + " double," +
                    URL + " varchar(250)" +
            ")";
}
