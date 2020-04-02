package com.alexander_rodriguez.mihogar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyAdminDate {
    private DateFormat dateFormat;
    public static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public MyAdminDate(){
        dateFormat = new SimpleDateFormat(FORMAT_DATE);
    }
    public void setFormat(String format){
        dateFormat = new SimpleDateFormat(format);
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

    public String getFechaSiguiente(String fechai, int modo) throws ParseException {
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
        return (new SimpleDateFormat(FORMAT_DATE_TIME)).format(new Date());
    }

    public static String buidFecha(String year, String month, String day){
        Date date  = new Date();
        String fecha = year + "-" + month + "-" + day + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
        return fecha;
    }

    public static String buidFecha(String year, String month, String day, String hour, String min, String ss){
        String fecha = year + "-" + month + "-" + day+ " " + hour+ ":" + min+ ":" + ss;
        return fecha;
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



}
