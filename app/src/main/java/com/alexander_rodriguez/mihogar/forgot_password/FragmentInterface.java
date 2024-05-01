package com.alexander_rodriguez.mihogar.forgot_password;

import com.alexander_rodriguez.mihogar.historial_casa.FragmentParent;

public interface FragmentInterface {
    interface presenter{
        void ocNext(String emailAddress);
    }
    interface view extends FragmentParent.view {
        void salir();
    }
}
