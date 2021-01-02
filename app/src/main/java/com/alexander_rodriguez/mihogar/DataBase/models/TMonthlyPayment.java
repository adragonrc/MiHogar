package com.alexander_rodriguez.mihogar.DataBase.models;

import com.google.firebase.Timestamp;

public class TMonthlyPayment {
    protected String amount;
    protected Timestamp dateInit;
    protected String rentalId;

    public TMonthlyPayment(){}

    public TMonthlyPayment(String amount, Timestamp dateInit, String rentalId) {
        this.amount = amount;
        this.dateInit = dateInit;
        this.rentalId = rentalId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Timestamp getDateInit() {
        return dateInit;
    }

    public void setDateInit(Timestamp dateInit) {
        this.dateInit = dateInit;
    }

    public String getRentalId() {
        return rentalId;
    }

    public void setRentalId(String rentalId) {
        this.rentalId = rentalId;
    }
}
