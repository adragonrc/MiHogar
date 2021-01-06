package com.alexander_rodriguez.mihogar.DataBase.items;

import com.alexander_rodriguez.mihogar.DataBase.models.TPayment;
import com.alexander_rodriguez.mihogar.AdminDate;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.QueryDocumentSnapshot;

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

    public ItemPayment(TPayment p) {
        super(p.getDate(), p.getRentalId(),p.getRomNumber(), p.getMonthlyPaymentId(), p.getAmount(), p.getDni());
    }

    public static ItemPayment getInstance(QueryDocumentSnapshot doc) {
        ItemPayment p = new ItemPayment(doc.toObject(TPayment.class));
        p.setId(doc.getId());
        return p;
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
