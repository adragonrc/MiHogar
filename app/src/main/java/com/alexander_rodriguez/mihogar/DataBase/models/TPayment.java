package com.alexander_rodriguez.mihogar.DataBase.models;

import com.google.firebase.Timestamp;

public class TPayment {
    protected Timestamp date;
    protected String rentalId;
    protected String romNumber;
    protected String monthlyPaymentId;
    protected String amount;
    protected String dni;

    public TPayment(){}

    public TPayment(Timestamp date, String rentalId, String romNumber, String monthlyPaymentId, String amount, String dni) {
        this.date = date;
        this.rentalId = rentalId;
        this.romNumber = romNumber;
        this.monthlyPaymentId = monthlyPaymentId;
        this.amount = amount;
        this.dni = dni;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
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

    public String getDni() {
        return dni;
    }
}
