package com.alexander_rodriguez.mihogar.DataBase.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class TRental {
    protected Timestamp entryDate;
    protected Timestamp departureDate;
    protected String reasonExit;
    protected String roomNumber;
    protected DocumentReference currentMP;
    protected String mainTenant;
    protected int tenantsNumber;
    protected int paymentsNumber;
    protected String phoneNumber;
    protected String email;

    public TRental(){}

    public TRental(Timestamp entryDate, Timestamp departureDate, String reasonExit, String roomNumber, DocumentReference currentMP, String mainTenant, int tenantsNumber, int paymentsNumber, String phoneNumber, String email) {
        this.entryDate = entryDate;
        this.departureDate = departureDate;
        this.reasonExit = reasonExit;
        this.roomNumber = roomNumber;
        this.currentMP = currentMP;
        this.mainTenant = mainTenant;
        this.tenantsNumber = tenantsNumber;
        this.paymentsNumber = paymentsNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Timestamp getEntryDate() {
        return entryDate;
    }

    public Timestamp getDepartureDate() {
        return departureDate;
    }

    public String getReasonExit() {
        return reasonExit;
    }

    public int getTenantsNumber() {
        return tenantsNumber;
    }

    public void setTenantsNumber(int tenantsNumber) {
        this.tenantsNumber = tenantsNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public int getPaymentsNumber() {
        return paymentsNumber;
    }

    public void setPaymentsNumber(int paymentsNumber) {
        this.paymentsNumber = paymentsNumber;
    }

    public DocumentReference getCurrentMP() {
        return currentMP;
    }

    public void setCurrentMP(DocumentReference currentMP) {
        this.currentMP = currentMP;
    }

    public String getMainTenant() {
        return mainTenant;
    }

    public void setMainTenant(String mainTenant) {
        this.mainTenant = mainTenant;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
