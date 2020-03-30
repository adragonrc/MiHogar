package com.alexander_rodriguez.mihogar.UTILIDADES;

public class TUsuario {
    public static final String T_NOMBRE = "inquilino";
    public static final String DNI = "dni";
    public static final String NOMBRES = "nombres";
    public static final String APELLIDO_PAT = "apellidopat";
    public static final String APELLIDO_MAT= "apellidomat";
    public static final String NUMERO_TEL = "numeroTelefonico";
    public static final String CORREO = "correo";

    public static final String URI = "user_uri";

    public static final String CREATE_TABLE =
            "create table "+ T_NOMBRE+ "(" +
                    DNI + " integer primary key, " +
                    NOMBRES + " varchar(20) not null, " +
                    APELLIDO_PAT + " varchar(20) not null," +
                    APELLIDO_MAT + " varchar(20) not null," +
                    NUMERO_TEL + " varchar(15) ," +
                    CORREO + " varchar(50) ," +
                    URI + " varchar(250) not null" +
            ");";

}
