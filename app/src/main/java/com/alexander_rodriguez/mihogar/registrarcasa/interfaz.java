package com.alexander_rodriguez.mihogar.registrarcasa;

import android.view.View;

import com.alexander_rodriguez.mihogar.Base.BaseView;
import com.alexander_rodriguez.mihogar.Base.IBasePresenter;

public interface interfaz {
    interface presentador extends IBasePresenter {
        void ingresar(String dir, String corr);
        void onResume();
    }
    interface view extends BaseView {
        void ocAceptar(View view);
        void salir();

        void avanzar();
    }
}
