package com.alexander_rodriguez.mihogar.DataBase.items;

import com.alexander_rodriguez.mihogar.Base.BaseView;
import com.alexander_rodriguez.mihogar.DataBase.models.TRoom;
import com.alexander_rodriguez.mihogar.Validator;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ItemRoom extends TRoom{
    private String roomNumber;

    public ItemRoom(String roomNumber, String details, String currentRentalId, int numberTenants, String price_e, String pathImage) {
        super(details, currentRentalId, numberTenants, price_e, pathImage);
        this.roomNumber = roomNumber;
    }
    public ItemRoom(String roomNumber, String details, String  currentRentalId, String price_e, String pathImage) {
        super(details, currentRentalId, 0, price_e, pathImage);
        this.roomNumber = roomNumber;
    }


    public TRoom getCuartoRoot(){
        return (TRoom) this;
    }

    public int getErrorIfExist(){
        return Validator.isEmptyOrNull(details, currentRentalId, price_e, pathImage);
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

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public boolean reportError(BaseView view){
        int err =getErrorIfExist();
        if (err != -1) {
            view.showMensaje("Campo vacio en el campo: " + getLabelName(err));
            return true;
        }
        return false;
    }

    public boolean isRented(){
        return (currentRentalId == null || currentRentalId.isEmpty());
    }
}
