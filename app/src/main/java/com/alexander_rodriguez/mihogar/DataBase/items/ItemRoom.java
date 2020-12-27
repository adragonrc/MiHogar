package com.alexander_rodriguez.mihogar.DataBase.items;

import com.alexander_rodriguez.mihogar.DataBase.models.TCuarto;
import com.alexander_rodriguez.mihogar.Validator;

public class ItemCuarto  extends TCuarto {
    private String  numCuarto;
    public ItemCuarto(String numCuarto, String details, String currentRental, String price_e, String pathImage) {
        super(details, currentRental, price_e, pathImage);
        this.numCuarto = numCuarto;
    }

    public TCuarto getCuartoRoot(){
        return (TCuarto) this;
    }

    public boolean isCorrect(){
        return Validator.isEmptyOrNull(details, currentRental, price_e, pathImage);
    }
}
