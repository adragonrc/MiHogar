package com.alexander_rodriguez.mihogar.UTILIDADES;

public class TAlquiler{
    public static final String T_NOMBRE = "alquiler";
    public static final String ID = "idAlquiler";
    public static final String FECHA = "fechaAlquiler";
    public static final String FECHA_C = "fecha_c";
    public static final String MOTIVO = "motivo";
    public static final String VAL = "estadoc";
    public static final String ALERT = "alert";

    public static final String DNI = TUsuario.DNI;
    public static final String NUMERO_C= TCuarto.NUMERO;

    public static final int INT_ID = 0;
    public static final int INT_FECHA = 1;
    public static final int INT_FECHA_C = 2;
    public static final int INT_MOTIVO = 3;
    public static final int INT_VAL = 4;
    public static final int INT_ALERT = 5;

    public static final int INT_DNI = 6;
    public static final int INT_NUMERO_C= 7;

    public static final String CREATE_TABLE =
            "create table " + T_NOMBRE + "(" +
                    ID + " integer primary key autoincrement not null,"+
                    FECHA + " datetime," +
                    FECHA_C + " datetime," +
                    MOTIVO + " text," +
                    VAL + " boolean," +
                    ALERT+ " bool," +

                    DNI + " integer," +
                    NUMERO_C + " varchar(10)," +
                    "foreign key ("+ NUMERO_C + ")references "+TCuarto.T_NOMBRE +"("+TCuarto.NUMERO+"),"+
                    "foreign key ("+ DNI+ ")references "+TUsuario.T_NOMBRE+"("+TUsuario.DNI+")"+
            ");";

}
