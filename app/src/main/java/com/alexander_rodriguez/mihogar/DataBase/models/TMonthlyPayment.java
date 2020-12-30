package com.alexander_rodriguez.mihogar.DataBase.models;

public class TMonthlyPayment {
    protected String amount;
    protected String dateInit;
    protected String rentalId;

    public TMonthlyPayment(){}

    public TMonthlyPayment(String amount, String dateInit, String rentalId) {
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

    public String getDateInit() {
        return dateInit;
    }

    public void setDateInit(String dateInit) {
        this.dateInit = dateInit;
    }

    public String getRentalId() {
        return rentalId;
    }

    public void setRentalId(String rentalId) {
        this.rentalId = rentalId;
    }
}
