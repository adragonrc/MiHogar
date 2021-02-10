package com.alexander_rodriguez.mihogar.DataBase.items;

import com.alexander_rodriguez.mihogar.DataBase.models.TMonthlyPayment;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import org.jetbrains.annotations.NotNull;

public class ItemMonthlyPayment extends TMonthlyPayment {
    private String id;
    public ItemMonthlyPayment(){}
    public ItemMonthlyPayment(Double amount, Timestamp dateInit, String rentalId) {
        super(amount, dateInit, rentalId);
    }
    public ItemMonthlyPayment(Double amount, Timestamp dateInit, String rentalId, String lastPaymentId) {
        super(amount, dateInit, rentalId, lastPaymentId);
    }

    public ItemMonthlyPayment(@NotNull TMonthlyPayment monthlyPayment){
        super(monthlyPayment.getAmount(), monthlyPayment.getDateInit(), monthlyPayment.getRentalId(), monthlyPayment.getLastPaymentId());
    }

    public static @NotNull ItemMonthlyPayment newInstance(@NotNull DocumentSnapshot documentSnapshot) {
        TMonthlyPayment mp = documentSnapshot.toObject(TMonthlyPayment.class);
        ItemMonthlyPayment imp = mp == null? new ItemMonthlyPayment():new ItemMonthlyPayment(mp);
        imp.setId(documentSnapshot.getId());
        return imp;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
