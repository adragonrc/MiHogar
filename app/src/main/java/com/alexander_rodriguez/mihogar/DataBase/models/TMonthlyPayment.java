package com.alexander_rodriguez.mihogar.DataBase.models;

public class TMonthlyPayment {
    protected String price;
    protected String dateInit;
    protected String rentalId;

    public TMonthlyPayment(String price, String dateInit, String rentalId) {
        this.price = price;
        this.dateInit = dateInit;
        this.rentalId = rentalId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
