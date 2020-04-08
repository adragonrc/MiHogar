package com.alexander_rodriguez.mihogar.UTILIDADES;

public class TAlquilerUsuario {
    public static final String T_NOMBRE = "alquiler_usuario";
    public static final String DNI = TUsuario.DNI;
    public static final String ID_AL = TAlquiler.ID;
    public static final String IS_ENCARGADO = "es_encargado";

    public static final int INT_DNI = 0;
    public static final int INT_ID_AL = 1;
    public static final int INT_IS_ENCARGADO = 2;

    public static final String CREATE_TABLE =
            "create table " + T_NOMBRE + "(" +
                    DNI + " integer, "+
                    ID_AL + " integer, "+
                    IS_ENCARGADO + " boolean, "+
                    "primary key (" + DNI + ", " + ID_AL + "), "+
                    "foreign key ("+ ID_AL + ")references "+TAlquiler.T_NOMBRE +"("+TAlquiler.ID+"),"+
                    "foreign key ("+ DNI+ ")references "+TUsuario.T_NOMBRE+"("+TUsuario.DNI+")"+
                    ");";

}
