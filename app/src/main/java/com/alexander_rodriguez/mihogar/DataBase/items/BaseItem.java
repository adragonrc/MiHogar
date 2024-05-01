package com.alexander_rodriguez.mihogar.DataBase.items;

import com.alexander_rodriguez.mihogar.Base.BaseView;
import com.alexander_rodriguez.mihogar.AdminDate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public abstract class BaseItem {
    public abstract int getErrorIfExist();
    public abstract String getLabelName(int i);
    public  boolean reportError(BaseView view){
        int err = getErrorIfExist();
        if (err != -1) {
            view.showMessage("Campo vacio en el campo: " + getLabelName(err));
            return true;
        }
        return false;
    }

    private String dateToString(Date date, String cache){
        if(cache == null || cache.isEmpty()){
            cache = (new SimpleDateFormat(AdminDate.FORMAT_DATE, Locale.getDefault())).format(date);
        }
        return cache;
    }
}
