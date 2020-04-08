package com.alexander_rodriguez.mihogar.agregarcuarto;

import android.view.View;

import com.alexander_rodriguez.mihogar.Base.BaseView;
import com.alexander_rodriguez.mihogar.Base.IBasePresenter;

public interface Interfaz{
    interface Presenter extends IBasePresenter {
        void insertarCuarto(String numCuarto, String precio, String detalles, String URL);
    }
    interface view extends BaseView {

        void onChooseFile(View v);

        void salir();
    }

}
