package com.alexander_rodriguez.mihogar.viewregistraralquiler;

import android.net.wifi.aware.Characteristics;

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
        boolean f = BasePresenter.validarStrings(price, roomNumber);
        try {
            Double.parseDouble(price);
            return f;
        }catch (NumberFormatException e){
            return false;
        }
    }

    public String getEntryDateAsString() {
        if(sEntryDate == null || sEntryDate.isEmpty()){
            sEntryDate = AdminDate.dateToString(entryDate.toDate());
        }
        return sEntryDate;
    }

    public String getDepartureDateAsString() {
        if(sDepartureDate == null || sDepartureDate.isEmpty()){
            sDepartureDate = AdminDate.dateToString(departureDate.toDate());
        }
        return sDepartureDate;
    }
}
