package com.alexander_rodriguez.mihogar.DataBase.models;

import com.google.firebase.Timestamp;

public class TMonthlyPayment {
    protected Double amount;
    protected Timestamp dateInit;
    protected String rentalId;
    protected String lastPaymentId;

    public TMonthlyPayment(){}

    public TMonthlyPayment(Double amount, Timestamp dateInit, String rentalId) {
        this.amount = amount;
        this.dateInit = dateInit;
        this.rentalId = rentalId;
        this.lastPaymentId = lastPaymentId;
    }
    public TMonthlyPayment(Double amount, Timestamp dateInit, String rentalId, String lastPaymentId) {
        this.amount = amount;
        this.dateInit = dateInit;
        this.rentalId = rentalId;
        this.lastPaymentId = lastPaymentId;
    }

    public Double getAmount() {
        return amount == null? 0: amount;
    }

    public void setAmount(Double amount) {
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

    public String getLastPaymentId() {
        return lastPaymentId;
    }

    public void setLastPaymentId(String lastPaymentId) {
        this.lastPaymentId = lastPaymentId;
    }
}
