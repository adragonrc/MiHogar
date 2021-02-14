package com.alexander_rodriguez.mihogar;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class AdminDate {
    private DateFormat dateFormat;
    public static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public AdminDate(){
        dateFormat = new SimpleDateFormat(FORMAT_DATE, Locale.getDefault());
    }

    public static int comparar(String fechaCancelar, String fechaCancelar1) throws ParseException {
        if ((fechaCancelar == null || fechaCancelar.isEmpty())&& (fechaCancelar1 == null || fechaCancelar1.isEmpty())){
            return 0;
        }
        if (fechaCancelar == null || fechaCancelar.isEmpty()){
            return 1;
        }
        if (fechaCancelar1 == null || fechaCancelar1.isEmpty()){
            return -1;
        }

        DateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_TIME, Locale.getDefault());
        Date d1 = dateFormat.parse(fechaCancelar);
        Date d2 = dateFormat.parse(fechaCancelar1);

        boolean f = d1.before(d2);
        if (f) return -1;
        else return  1;
    }

    public static String adelantarUnMes(String fecha) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE, Locale.getDefault());
        Date date = dateFormat.parse(fecha);
        date.setMonth(date.getMonth() + 1);
        return dateFormat.format(date);
    }

    public static String adelantarPorMeses(String fecha, int pagos) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_TIME, Locale.getDefault());
        Date date = dateFormat.parse(fecha);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, pagos);
        return dateFormat.format(calendar.getTime());
    }
    public static Date adelantarPorMeses(Date date, int pagos) {
        //DateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_TIME, Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, pagos);
        return calendar.getTime();
    }

    public void setFormat(String format){
        dateFormat = new SimpleDateFormat(format, Locale.getDefault());
    }

    public String[] getFechas(){
        String f[] = new String[2];
        f[0] = dateFormat.format(new Date());
        f[1] = "";
        try {
            Date date = dateFormat.parse(f[0]);
            date.setMonth(date.getMonth() + 1);
            f[1] = dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return f;
    }

    public String adelantarUnMes(String fechai, int modo) throws ParseException {
        String f = null;
        Date date = dateFormat.parse(fechai);
        switch (modo){
            case 0:{
                date.setMonth(date.getMonth() + 1);
                break;
            }
            case 1:{
                date = sumarTiempo(date, Calendar.DAY_OF_YEAR, 1);
                break;
            }
            case 2:{
                date = sumarTiempo(date, Calendar.HOUR_OF_DAY, 1);
                break;
            }
            case 3:{
                date = sumarTiempo(date, Calendar.MINUTE, 1);
                break;
            }
        }
        f = dateFormat.format(date);
        return f;
    }
    public Date sumarTiempo(Date fecha, int label, int num){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(label, num);
        return calendar.getTime();
    }
    public static String getFechaActual(){
        return (new SimpleDateFormat(FORMAT_DATE_TIME, Locale.getDefault())).format(new Date());
    }

    public static String buidFecha(String year, String month, String day){
        Date date  = new Date();
        String fecha;
        fecha = year + "-" + month + "-" + day + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
        return fecha;
    }

    public static String buidFecha(String year, String month, String day, String hour, String min, String ss){
        return year + "-" + month + "-" + day+ " " + hour+ ":" + min+ ":" + ss;
    }

    public DateFormat getDateFormat() {
        return dateFormat;
    }

    public Date stringToDate(String s){
        try {
            Date d= dateFormat.parse(s);
            return d;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String dateToString(Date date){
        if (date == null) date = new Date(0);
        return (new SimpleDateFormat(FORMAT_DATE, Locale.getDefault())).format(date);
    }
}
