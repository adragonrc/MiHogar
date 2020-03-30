package com.alexander_rodriguez.mihogar.viewUser;

import android.content.Context;

public interface Interfaz {
    interface Presenter{
        void iniciar();
        void terminarAlquiler();
    }
    interface View{
        void doPago();
        void doNoPago();
        void setAttributes(String nombres, String dni);
        void layoutOnClick();
        void onClickMoreOptions();
        Context getContext();

    }
}
