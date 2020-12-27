package com.alexander_rodriguez.mihogar.DataBase.items;

import com.alexander_rodriguez.mihogar.Base.BaseView;

public abstract class BaseItem {
    public abstract int getErrorIfExist();
    public abstract String getLabelName(int i);
    public  boolean reportError(BaseView view){
        int err = getErrorIfExist();
        if (err != -1) {
            view.showMensaje("Campo vacio en el campo: " + getLabelName(err));
            return true;
        }
        return false;
    }
}
