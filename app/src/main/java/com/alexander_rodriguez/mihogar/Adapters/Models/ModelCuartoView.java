package com.alexander_rodriguez.mihogar.Adapters.Models;

import com.alexander_rodriguez.mihogar.DataBase.items.ItemRoom;
import com.alexander_rodriguez.mihogar.DataBase.models.TRoom;
import com.alexander_rodriguez.mihogar.AdminDate;

import java.util.Date;

public class ModelCuartoView extends ItemRoom {
    private String paymentDate;
    private int posList;
    private boolean alert;

    public ModelCuartoView(TRoom room) {
        super(room);
    }

    public ModelCuartoView(TRoom room, int posList) {
        super(room); this.posList = posList;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
        AdminDate date = new AdminDate();
        this.alert = false;
        if (paymentDate != null)
            this.alert = date.stringToDate(paymentDate).before(new Date());
    }

    public void setPosList(int posList) {
        this.posList = posList;
    }

    public int getPosList() {
        return posList;
    }

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
