package com.alexander_rodriguez.mihogar.DataBase.models;

public class TCuarto {
    protected String details;
    protected String currentRental;
    protected String price_e;
    protected String pathImage;

    public TCuarto(String details, String currentRental, String price_e, String pathImage) {
        this.details = details;
        this.currentRental = currentRental;
        this.price_e = price_e;
        this.pathImage = pathImage;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCurrentRental() {
        return currentRental;
    }

    public void setCurrentRental(String currentRental) {
        this.currentRental = currentRental;
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

}
