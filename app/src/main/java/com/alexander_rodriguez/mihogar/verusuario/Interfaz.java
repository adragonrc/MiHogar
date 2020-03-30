package com.alexander_rodriguez.mihogar.verusuario;

import android.content.ContentValues;

public interface Interfaz{

    interface Presenter{
        void iniciarComandos() ;
    }
    interface View{
        void setTextOfTV(ContentValues du, ContentValues dC, ContentValues dA, ContentValues dM);

    }
}
