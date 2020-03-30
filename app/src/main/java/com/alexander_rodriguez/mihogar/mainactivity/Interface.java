package com.alexander_rodriguez.mihogar.mainactivity;

import android.view.View;

import com.alexander_rodriguez.mihogar.Base.BaseView;
import com.alexander_rodriguez.mihogar.Base.IBasePresenter;

public interface Interface {
    interface presenter extends IBasePresenter {
        void ingresar(String usuario, String contraseña);

        String getUser();
    }
    interface view extends BaseView {
        void ingresar();
        void negarIngreso();
        void onClickIngresar(View view);
        void onClikCambiarContraseña(View view);

        void setID(String s);
    }
}
