package com.alexander_rodriguez.mihogar.DataBase.items;

import com.alexander_rodriguez.mihogar.DataBase.models.TUser;
import com.alexander_rodriguez.mihogar.Validator;
import com.google.firebase.firestore.DocumentSnapshot;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ItemTenant extends TUser {
    protected String dni;

    public ItemTenant(){}

    public ItemTenant(String dni, String name, String apellidoPat, String apellidoMat, String path, boolean main) {
        super(name, apellidoPat, apellidoMat, path, false, main);
        this.dni = dni;
    }
    public ItemTenant(String dni, String name, String apellidoPat, String apellidoMat, String path, boolean alert, boolean main) {
        super(name, apellidoPat, apellidoMat, path, alert, main);
        this.dni = dni;
    }

    public ItemTenant(TUser user) {
        super(user.getName(), user.getApellidoPat(), user.getApellidoMat(), user.getPath(), user.isAlerted(), user.isMain());
    }

    public static ItemTenant newInstance(DocumentSnapshot doc) {
        TUser user = doc.toObject(TUser.class);
        if(user != null) {
            ItemTenant iu = new ItemTenant(user);
            iu.setDni(doc.getId());
            return iu;
        }
        return null;
    }

    public TUser getRoot(){
        return  new TUser(name, apellidoPat, apellidoMat, path, alerted, main);
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getErrorIfExist(){
        return Validator.isEmptyOrNull(dni, name, apellidoPat, apellidoMat);
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

    public String getFullName() {
        return name.concat(", ").concat(apellidoPat).concat(" ").concat(apellidoMat);
    }

    public void setData(ItemTenant datos) {
        dni = datos.getDni();
        name = datos.getName();
        apellidoPat = datos.getApellidoPat();
        apellidoMat = datos.getApellidoMat();
        path = datos.getPath();
    }
}
