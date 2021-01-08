package com.alexander_rodriguez.mihogar.viewregistraralquiler;

import com.alexander_rodriguez.mihogar.AdminDate;
import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.DataBase.models.TRental;
import com.alexander_rodriguez.mihogar.Validator;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class ModelAA extends TRental {
    private String price;
    private String sEntryDate;
    private String sDepartureDate;


    public ModelAA(Timestamp entryDate, String roomNumber, int paymentsNumber, String phoneNumber, String email, String price ) {
        super(entryDate, null, "",  roomNumber, null,null,  0, paymentsNumber, phoneNumber, email);
        this.price = price;
    }
    public ModelAA(Timestamp entryDate, Timestamp departureDate, String reasonExit, String roomNumber, DocumentReference currentMP, String mainTenant, int paymentsNumber, String phoneNumber, String email) {
        super(entryDate, departureDate, reasonExit, roomNumber, currentMP, mainTenant,0, paymentsNumber, phoneNumber, email);
    }


    public TRental getRoot(){
        return new TRental(entryDate, departureDate, reasonExit, roomNumber, currentMP, mainTenant, tenantsNumber, paymentsNumber, phoneNumber, email);
    }

    public String getPrice() {
        return price;
    }

   // public String getPlazo() {
  //      return plazo;
   // }

    public boolean wasPaid() {
        return paymentsNumber != 0;
    }

    public boolean isCorrect() {
        return  BasePresenter.validarStrings(price, roomNumber);
    }

    public String getEntryDateAsString() {
        return AdminDate.dateToString(entryDate.toDate(), sEntryDate);
    }

    public String getDepartureDateAsString() {
        return AdminDate.dateToString(departureDate.toDate(), sDepartureDate);
    }
}
