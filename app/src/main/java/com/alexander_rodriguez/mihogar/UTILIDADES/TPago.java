package com.alexander_rodriguez.mihogar.UTILIDADES;

public class TPago {
    public static final String T_NOMBRE = "pagos";
    public static final String ID = "idPago";
    public static final String FECHA = "fechaPago";
    public static final String URI_VOUCHER = "uri_voucher";
    public static final String ID_M=  Mensualidad.ID;
    public static final String DNI = TUsuario.DNI;


    public static final int INT_ID = 0;
    public static final int INT_FECHA = 1;
    public static final int INT_URI_VOUCHER = 2;
    public static final int INT_ID_M=  3;
    public static final int INT_DNI = 4;


    public static final String CREATE_TABLE =
            "create table " + T_NOMBRE + "(" +
                    ID + " integer primary key autoincrement not null,"+
                    FECHA + " datetime," +
                    URI_VOUCHER + " varchar(250), " +
                    ID_M+ " integer," +
                    DNI+ " integer," +
                    "foreign key ("+ ID_M + ")references " + Mensualidad.T_NOMBRE +"("+Mensualidad.ID+"),"+
                    "foreign key ("+ DNI  + ")references " + TUsuario.T_NOMBRE+"("+TUsuario.DNI+")"+
                    ");";
}
