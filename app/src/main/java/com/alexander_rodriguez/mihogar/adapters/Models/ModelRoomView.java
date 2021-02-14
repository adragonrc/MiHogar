package com.alexander_rodriguez.mihogar.adapters.Models;

import androidx.annotation.NonNull;

import com.alexander_rodriguez.mihogar.DataBase.items.ItemRoom;
import com.alexander_rodriguez.mihogar.DataBase.models.TRoom;
import com.alexander_rodriguez.mihogar.AdminDate;

import java.util.Date;

public class ModelRoomView extends ItemRoom {
    private Date paymentDate;
    private String sPaymentDate;
    private int posList;
    private boolean alert;

    public ModelRoomView(TRoom room) {
        super(room);
        this.alert = false;
    }

    public ModelRoomView(TRoom room, int posList) {
        super(room);
        this.posList = posList;
        this.alert = false;
    }

    public void setPaymentDate(@NonNull Date paymentDate) {
        this.paymentDate = paymentDate;
        this.sPaymentDate = null;
        this.alert = paymentDate.before(new Date());
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

    public String getPaymentDateAsString() {
        if (sPaymentDate == null || sPaymentDate.isEmpty()){
            sPaymentDate = AdminDate.dateToString(paymentDate);
        }
        return sPaymentDate;
    }


    public void setAlert(boolean alert) {
        this.alert = alert;
    }
}
