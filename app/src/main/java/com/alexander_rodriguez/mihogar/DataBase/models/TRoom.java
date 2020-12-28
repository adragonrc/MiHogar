package com.alexander_rodriguez.mihogar.DataBase.models;

import com.alexander_rodriguez.mihogar.Adapters.Models.ModelCuartoView;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemRoom;
import com.alexander_rodriguez.mihogar.alquilerusuario.Item;
import com.alexander_rodriguez.mihogar.modelos.ModelCuarto;

import org.jetbrains.annotations.NotNull;

public class TRoom {
    protected String details;
    protected String currentRentalId;
    protected int numberTenants;
    protected String price_e;
    protected String pathImage;

    public TRoom(){

    }
    public TRoom(ItemRoom room){
        this.details = room.getDetails();
        this.currentRentalId = room.getCurrentRentalId();
        this.numberTenants = room.getNumberTenants();
        this.price_e = room.getPrice_e();
        this.pathImage = room.getPathImage();
    }
    public TRoom( String currentRentalId, String details, int numberTenants,  String pathImage, String price_e) {
        this.details = details;
        this.currentRentalId = currentRentalId;
        this.numberTenants = numberTenants;
        this.price_e = price_e;
        this.pathImage = pathImage;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCurrentRentalId() {
        return currentRentalId;
    }

    public void setCurrentRentalId(String currentRentalId) {
        this.currentRentalId = currentRentalId;
    }

    public String getPrice_e() {
        return price_e;
    }

    public void setPrice_e(String price_e) {
        this.price_e = price_e;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public int getNumberTenants() {
        return numberTenants;
    }

    public void setNumberTenants(int numberTenants) {
        this.numberTenants = numberTenants;
    }
}
