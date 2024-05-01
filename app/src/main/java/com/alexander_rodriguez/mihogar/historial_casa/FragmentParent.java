package com.alexander_rodriguez.mihogar.historial_casa;

import android.content.Intent;

import com.alexander_rodriguez.mihogar.DataBase.DBInterface;

public interface FragmentParent {
    interface view{

        void goTo(Intent intent);

        void showMessage(String message);
    }
    interface presenter{
        DBInterface getDB();
    }
}
