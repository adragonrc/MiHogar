package com.alexander_rodriguez.mihogar.viewregistraralquiler;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.DataBase.models.TRental;
import com.google.firebase.firestore.DocumentReference;

public class ModelAA extends TRental {
    private String price;
    private String fecha;
    private String plazo;

    public ModelAA(String entryDate, String roomNumber, int paymentsNumber, String phoneNumber, String email, String price ) {
        super(entryDate, null, "", true, roomNumber, null,null, paymentsNumber, phoneNumber, email);
        this.price = price;
    }
    public ModelAA(String entryDate, String departureDate, String reasonExit, boolean enabled, String roomNumber, DocumentReference currentMP, String mainTenant, int paymentsNumber, String phoneNumber, String email) {
        super(entryDate, departureDate, reasonExit, enabled, roomNumber, currentMP, mainTenant, paymentsNumber, phoneNumber, email);
    }


    public TRental getRoot(){
        return (TRental) this;
    }

    public String getPrice() {
        return price;
    }

    public String getPlazo() {
        return plazo;
    }

    public boolean wasPaid() {
        return paymentsNumber != 0;
    }

    public boolean isCorrect() {
        return  BasePresenter.validarStrings(price, roomNumber, fecha, plazo);
    }
}
