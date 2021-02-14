package com.alexander_rodriguez.mihogar.DataBase.models;

import com.alexander_rodriguez.mihogar.DataBase.items.ItemRoom;

public class TRoom {
    protected String details;
    protected String currentRentalId;
    protected String price_e;
    protected String pathImage;
    protected String pahtImageStorage;
    protected int numberOfRentals;
    protected boolean hide;

    public TRoom(){}

    public TRoom(ItemRoom room){
        this.details = room.getDetails();
        this.currentRentalId = room.getCurrentRentalId();
        this.price_e = room.getPrice_e();
        this.pathImage = room.getPathImage();
        this.pahtImageStorage = room.getPahtImageStorage();
        numberOfRentals = room.getNumberOfRentals();
        hide = room.isHide();
    }
    public TRoom(String currentRentalId, String details, String pathImage, String price_e, String pahtImageStorage) {
        this.details = details;
        this.currentRentalId = currentRentalId;
        this.price_e = price_e;
        this.pathImage = pathImage;
        this.pahtImageStorage = pahtImageStorage;
        numberOfRentals = 0;
        hide = false;
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public void setNumberOfRentals(int numberOfRentals) {
        this.numberOfRentals = numberOfRentals;
    }

    public int getNumberOfRentals() {
        return numberOfRentals;
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

    public String getPahtImageStorage() {
        return pahtImageStorage;
    }

    public void setPahtImageStorage(String pahtImageStorage) {
        this.pahtImageStorage = pahtImageStorage;
    }
}
