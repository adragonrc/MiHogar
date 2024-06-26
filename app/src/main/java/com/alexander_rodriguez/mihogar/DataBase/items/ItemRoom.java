package com.alexander_rodriguez.mihogar.DataBase.items;

import com.alexander_rodriguez.mihogar.Base.BaseView;
import com.alexander_rodriguez.mihogar.DataBase.models.TRoom;
import com.alexander_rodriguez.mihogar.Validator;
import com.google.firebase.firestore.DocumentSnapshot;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ItemRoom extends TRoom{
    private String roomNumber;

    public ItemRoom(){

    }
    public ItemRoom(TRoom room) {
        super(room.getCurrentRentalId(), room.getDetails(), room.getPathImage(), room.getPrice_e(), room.getPathImageStorage());
        super.setHide(room.isHide());
        super.setNumberOfRentals(room.getNumberOfRentals());
    }

    public ItemRoom(String details, String pathImage, String price_e, String roomNumber) {
        super(null, details, pathImage, price_e, null);
        this.roomNumber = roomNumber;
    }


    public TRoom getCuartoRoot(){
        return new TRoom(this) ;
    }

    public int getErrorIfExist(){
        return Validator.isEmptyOrNull(roomNumber, details, price_e);
    }

    @Contract(pure = true)
    public static @NotNull String getLabelName(int i){
        switch (i){
            case 0: {return "room number"; }
            case 1:{ return "Details"; }
            //case 2:{ return "Current Rental"; }
            case 2:{ return "Suggested price"; }
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
            view.showMessage("Campo vacio en el campo: " + getLabelName(err));
            return true;
        }
        return false;
    }

    public static ItemRoom newInstance(DocumentSnapshot doc) {
        TRoom r = doc.toObject(TRoom.class);
        ItemRoom room;
        if (r==null) room = new ItemRoom();
        else room = new ItemRoom(r);
        room.setRoomNumber(doc.getId());
        return room;
    }
    public boolean isRented(){
        return (currentRentalId == null || currentRentalId.isEmpty());
    }
}
