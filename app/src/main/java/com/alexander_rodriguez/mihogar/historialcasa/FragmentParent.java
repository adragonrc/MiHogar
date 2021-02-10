package com.alexander_rodriguez.mihogar.historialcasa;

import android.content.Intent;

import com.alexander_rodriguez.mihogar.DataBase.DBInterface;
import com.alexander_rodriguez.mihogar.mi_casa.FragmentInterface;
import com.alexander_rodriguez.mihogar.mi_casa.MyHouseFragment;
import com.alexander_rodriguez.mihogar.viewUser.DialogDetallesAlquiler;

public interface FragmentParent {
    interface view{

        void goTo(Intent intent);

        void showMessage(String message);
    }
    interface presenter{
        DBInterface getDB();
    }
}
