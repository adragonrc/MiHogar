package com.alexander_rodriguez.mihogar.DataBase.models;

public class TRentalTenant {
    protected String rentalId;
    protected String DNI;
    protected boolean isMain;
    protected boolean isInside;

    public TRentalTenant(){}

    public TRentalTenant(String rentalId, String DNI, boolean isMain, boolean isInside) {
        this.rentalId = rentalId;
        this.DNI = DNI;
        this.isMain = isMain;
        this.isInside = isInside;
    }

    public String getRentalId() {
        return rentalId;
    }

    public void setRentalId(String rentalId) {
        this.rentalId = rentalId;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }

    public boolean isInside() {
        return isInside;
    }

    public void setInside(boolean inside) {
        isInside = inside;
    }
}
