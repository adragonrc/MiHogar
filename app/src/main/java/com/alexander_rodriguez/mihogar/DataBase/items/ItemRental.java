package com.alexander_rodriguez.mihogar.DataBase.items;

import com.alexander_rodriguez.mihogar.Base.BaseView;
import com.alexander_rodriguez.mihogar.DataBase.models.TRental;
import com.alexander_rodriguez.mihogar.Validator;
import com.google.firebase.firestore.DocumentReference;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ItemRental extends TRental {
    private String id;
    private String paymentDate;

    public ItemRental(String id, String entryDate, String departureDate, String reasonExit, boolean enabled, String roomNumber, String phoneNumber, String email) {
        super(entryDate, departureDate, reasonExit, enabled, roomNumber, null, null, 0, phoneNumber, email);
        this.id = id;
    }

    public ItemRental(String entryDate, String departureDate, String reasonExit, boolean enabled, String roomNumber, DocumentReference currentMP, String mainTenant, int paymentsNumber, String phoneNumber, String email) {
        super(entryDate, departureDate, reasonExit, enabled, roomNumber, currentMP, mainTenant, paymentsNumber, phoneNumber, email);
        this.id = id;
    }

    @Contract(pure = true)
    public static @NotNull String getLabelName(int i){
        switch (i){
            case 0:{ return "Details"; }
            case 1:{ return "Current Rental"; }
            case 2:{ return "Suggested price"; }
            case 3:{ return "Image Path"; }
            default:{ return  "There's no answer";}
        }
    }


    public TRental getCuartoRoot(){
        return (TRental) this;
    }

    public int getErrorIfExist(){
        return Validator.isEmptyOrNull(entryDate, departureDate, reasonExit, roomNumber);
    }

    public String getId() {
        return id;
    }

    public boolean reportError(BaseView view){
        int err =getErrorIfExist();
        if (err != -1) {
            view.showMensaje("Campo vacio en el campo: " + getLabelName(err));
            return true;
        }
        return false;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setId(String id) {
        this.id = id;
    }
}