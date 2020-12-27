package com.alexander_rodriguez.mihogar.DataBase.models;

import com.google.firebase.firestore.DocumentReference;

public class TRental {
    protected String entryDate;
    protected String departureDate;
    protected String reasonExit;
    protected String enabled;
    protected String roomNumber;
    protected DocumentReference currentMP;
    protected int paymentsNumber;

    public TRental(String entryDate, String departureDate, String reasonExit, String enabled, String roomNumber, DocumentReference currentMP, int paymentsNumber) {
        this.entryDate = entryDate;
        this.departureDate = departureDate;
        this.reasonExit = reasonExit;
        this.enabled = enabled;
        this.roomNumber = roomNumber;
        this.currentMP = currentMP;
        this.paymentsNumber = paymentsNumber;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getReasonExit() {
        return reasonExit;
    }

    public String getEnabled() {
        return enabled;
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
}
