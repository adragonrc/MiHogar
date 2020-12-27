package com.alexander_rodriguez.mihogar.DataBase.items;

import com.alexander_rodriguez.mihogar.DataBase.models.TUser;
import com.alexander_rodriguez.mihogar.Validator;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ItemUser extends TUser {
    private String dni;
    public ItemUser(String dni, String nombre, String apellidoPat, String apellidoMat, String path, boolean main) {
        super(nombre, apellidoPat, apellidoMat, path, main);
        this.dni = dni;
    }

    public TUser getCuartoRoot(){
        return (TUser) this;
    }

    public int getErrorIfExist(){
        return Validator.isEmptyOrNull(dni, nombre, apellidoPat, apellidoMat);
    }

    @Contract(pure = true)
    public static @NotNull String getLabelName(int i){
        switch (i){
            case 0:{ return "DNI"; }
            case 1:{ return "Name"; }
            case 2:{ return "Last name"; }
            case 3:{ return "Mother's last name"; }
            default:{ return  "There's no answer";}
        }
    }

    public String getDni() {
        return dni;
    }

}
