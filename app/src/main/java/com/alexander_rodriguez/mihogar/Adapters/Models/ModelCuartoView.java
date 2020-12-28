package com.alexander_rodriguez.mihogar.Adapters.Models;

import android.database.Cursor;
import android.os.Bundle;

import com.alexander_rodriguez.mihogar.DataBase.items.ItemRoom;
import com.alexander_rodriguez.mihogar.DataBase.models.TRoom;
import com.alexander_rodriguez.mihogar.MyAdminDate;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.UTILIDADES.TCuarto;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class ModelCuartoView extends ItemRoom {
    private String fechaCancelar;
    private boolean alert;

    public ModelCuartoView(String roomNumber, String details, String currentRentalId, int numberTenants, String price_e, String pathImage) {
        super(roomNumber, details, currentRentalId, numberTenants, price_e, pathImage);
    }

    public ModelCuartoView(String roomNumber, String details, String currentRentalId, int numberTenants, String price_e, String pathImage, String fechaCancelar, boolean alert) {
        super(roomNumber, details, currentRentalId, numberTenants, price_e, pathImage);
        this.fechaCancelar = fechaCancelar;
        this.alert = alert;
    }

    public void setFechaCancelar(String fechaCancelar) {
        this.fechaCancelar = fechaCancelar;
        MyAdminDate date = new MyAdminDate();
        this.alert = false;
        if (fechaCancelar != null)
            this.alert = date.stringToDate(fechaCancelar).before(new Date());

    }
/*
    public static ArrayList<ModelCuartoView> createListModel(Cursor c){
        ArrayList<ModelCuartoView> list = new ArrayList<>();
        if (c.moveToFirst()){
            do{
                Bundle b = c.getExtras();
                ModelCuartoView mcv = new ModelCuartoView(c);
                list.add(mcv);
            }while(c.moveToNext());
        }
        c.close();
        return list;
    }*/
    public boolean isAlert() {
        return alert;
    }

    public String getFechaCancelar() {
        return fechaCancelar;
    }

    public void setPaymentDate(String fechaCancelar) {
        this.fechaCancelar = fechaCancelar;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }
}
