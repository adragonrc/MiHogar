package com.alexander_rodriguez.mihogar.DataBase.models;


import com.google.firebase.Timestamp;

public class TAdvance {
    private Double amount;
    private Timestamp date;

    public TAdvance(Double amount, Timestamp date) {
        this.amount = amount;
        this.date = date;
    }

    public TAdvance() {}

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
