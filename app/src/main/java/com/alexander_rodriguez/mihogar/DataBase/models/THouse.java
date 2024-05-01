package com.alexander_rodriguez.mihogar.DataBase.models;

import com.alexander_rodriguez.mihogar.registrarcasa.details.DetailsView;

public class THouse {
    private String names;
    private String lastNames;
    private String details;

    public THouse(){}

    public THouse(String names, String lastNames, String details) {
        this.names = names;
        this.lastNames = lastNames;
        this.details = details;
    }

    public String getDetails() {
        return details;
    }

    public String getLastNames() {
        return lastNames;
    }

    public String getNames() {
        return names;
    }
}
