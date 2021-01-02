package com.alexander_rodriguez.mihogar.DataBase.items;

import com.alexander_rodriguez.mihogar.DataBase.models.TPayment;
import com.alexander_rodriguez.mihogar.AdminDate;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ItemPayment  extends TPayment {
    private String id;

    public ItemPayment(String id) {
        this.id = id;
    }

    public ItemPayment(Timestamp date, String rentalId, String romNumber, String monthlyPaymentId, String amount, String dni) {
        super(date, rentalId, romNumber, monthlyPaymentId, amount, dni);
    }

    public ItemPayment(Timestamp date, String rentalId, String romNumber, String monthlyPaymentId, String amount, String dni, String id) {
        super(date, rentalId, romNumber, monthlyPaymentId, amount, dni);
        this.id = id;
    }

    public String getDateAsString() {
        return (new SimpleDateFormat(AdminDate.FORMAT_DATE, Locale.getDefault())).format(date.toDate());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TPayment getRoot() {
        return new TPayment(date, rentalId, romNumber, monthlyPaymentId, amount, dni);
    }
}
