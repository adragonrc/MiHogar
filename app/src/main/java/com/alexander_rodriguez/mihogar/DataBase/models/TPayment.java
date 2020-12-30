package com.alexander_rodriguez.mihogar.DataBase.models;

public class TPayment {
    String date;
    String rentalId;
    String romNumber;
    String monthlyPaymentId;
    String amount;

    public TPayment(){}

    public TPayment(String date, String rentalId, String romNumber, String monthlyPaymentId, String amount) {
        this.date = date;
        this.rentalId = rentalId;
        this.romNumber = romNumber;
        this.monthlyPaymentId = monthlyPaymentId;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRentalId() {
        return rentalId;
    }

    public void setRentalId(String rentalId) {
        this.rentalId = rentalId;
    }

    public String getRomNumber() {
        return romNumber;
    }

    public void setRomNumber(String romNumber) {
        this.romNumber = romNumber;
    }

    public String getMonthlyPaymentId() {
        return monthlyPaymentId;
    }

    public void setMonthlyPaymentId(String monthlyPaymentId) {
        this.monthlyPaymentId = monthlyPaymentId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
