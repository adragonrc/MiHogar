package com.alexander_rodriguez.mihogar.registrarcasa;

import com.alexander_rodriguez.mihogar.Base.BaseView;
import com.alexander_rodriguez.mihogar.Base.IBasePresenter;
import com.alexander_rodriguez.mihogar.ButtonsAC.ButtonsAceptarCancelar;
import com.alexander_rodriguez.mihogar.registrarcasa.details.DetailsView;
import com.alexander_rodriguez.mihogar.registrarcasa.register.RegisterView;

public interface interfaz {
    interface presentador extends IBasePresenter {
        void ingresar(RegisterView dir, DetailsView corr);
        void onResume();
    }
    interface view extends BaseView{
        void salir();

        void avanzar();

        void onlyDetailsMode();

        void newUserMode();
    }
}
