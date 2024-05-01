package com.alexander_rodriguez.mihogar.DataBase.items;

import com.alexander_rodriguez.mihogar.DataBase.models.TPayment;
import com.alexander_rodriguez.mihogar.AdminDate;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ItemPayment  extends TPayment {
    private String id;

    public ItemPayment() {
        amount = 0d;
    }

    public ItemPayment(Timestamp date, String rentalId, String romNumber, String monthlyPaymentId, Double amount, String dni, String paymentParent) {
        super(date, rentalId, romNumber, monthlyPaymentId, amount, dni, paymentParent);
    }

    public ItemPayment(TPayment p) {
        super(p.getDate(), p.getRentalId(),p.getRomNumber(), p.getMonthlyPaymentId(), p.getAmount(), p.getDni(), p.getPaymentParent());
    }

    public static ItemPayment getInstance(DocumentSnapshot doc) {
        TPayment payment = doc.toObject(TPayment.class);
        ItemPayment p = payment == null ? new ItemPayment(): new ItemPayment(payment);
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
        return new TPayment(date, rentalId, romNumber, monthlyPaymentId, amount, dni, paymentParent);
    }
}
