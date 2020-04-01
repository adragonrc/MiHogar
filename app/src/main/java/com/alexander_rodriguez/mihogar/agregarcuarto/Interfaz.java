package com.alexander_rodriguez.mihogar.agregarcuarto;

import com.alexander_rodriguez.mihogar.Base.BaseView;
import com.alexander_rodriguez.mihogar.Base.IBasePresenter;

public interface Interfaz{
    interface Presenter extends IBasePresenter {
        void insertarCuarto(String numCuarto, String precio, String detalles, String URL);
    }
    interface View extends BaseView {
        void onClickAgregar(android.view.View view);
        void onClickCamara(android.view.View view);

        void salir();
    }

}
