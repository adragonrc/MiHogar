package com.alexander_rodriguez.mihogar.Adapters.Models;

import com.alexander_rodriguez.mihogar.DataBase.items.ItemRoom;
import com.alexander_rodriguez.mihogar.DataBase.models.TRoom;
import com.alexander_rodriguez.mihogar.MyAdminDate;
import com.alexander_rodriguez.mihogar.mi_casa.MListener;

import java.util.Date;

public class ModelCuartoView extends ItemRoom {
    private String paymentDate;
    private boolean alert;

    public ModelCuartoView(TRoom room) {
        super(room);
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
        MyAdminDate date = new MyAdminDate();
        this.alert = false;
        if (paymentDate != null)
            this.alert = date.stringToDate(paymentDate).before(new Date());

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

    public String getPaymentDate() {
        return paymentDate;
    }


    public void setAlert(boolean alert) {
        this.alert = alert;
    }
}
