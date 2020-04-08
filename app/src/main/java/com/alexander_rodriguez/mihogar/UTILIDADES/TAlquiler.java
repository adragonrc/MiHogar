package com.alexander_rodriguez.mihogar.UTILIDADES;

public class TAlquiler{
    public static final long LABEL_MONTH = -1;
    public static final long LABEL_YEAR = -2;
    public static final String T_NOMBRE = "alquiler";
    public static final String ID = "idAlquiler";
    public static final String FECHA_INICIO = "fecha_inicio";
    public static final String Fecha_PAGO = "fecha_pago";
    public static final String FECHA_SALIDA = "fecha_SALIDA";
    public static final String NUMERO_TEL = "numeroTelefonico";
    public static final String CORREO = "correo";
    public static final String MOTIVO = "motivo";
    public static final String PLAZO = "plazo";
    public static final String VAL = "estadoc";
    public static final String ALERT = "alert";

    public static final String NUMERO_C= TCuarto.NUMERO;

    public static final int INT_ID = 0;
    public static final int INT_FECHA = 1;
    public static final int INT_FECHA_C = 2;
    public static final int INT_FECHA_SALIDA = 3;
    public static final int INT_NUMERO_TEL = 4;
    public static final int INT_CORREO = 5;
    public static final int INT_MOTIVO = 6;
    public static final int INT_PLAZO = 7;
    public static final int INT_VAL = 8;
    public static final int INT_ALERT = 9;

    public static final int INT_NUMERO_C = 10;

    public static final String CREATE_TABLE =
            "create table " + T_NOMBRE + "(" +
                    ID + " integer primary key autoincrement not null,"+
                    FECHA_INICIO + " datetime, " +
                    Fecha_PAGO + " datetime, " +
                    NUMERO_TEL + " varchar(15) ," +
                    CORREO + " varchar(50) ," +
                    MOTIVO + " text, " +
                    PLAZO + " long, " +
                    VAL + " boolean, " +
                    ALERT+ " bool, " +

                    NUMERO_C + " varchar(10), " +
                    "foreign key ("+ NUMERO_C + ")references "+TCuarto.T_NOMBRE +"("+TCuarto.NUMERO+")"+
            ");";

}
