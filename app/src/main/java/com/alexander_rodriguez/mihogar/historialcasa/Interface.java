package com.alexander_rodriguez.mihogar.historialcasa;

import com.alexander_rodriguez.mihogar.Base.BaseView;
import com.alexander_rodriguez.mihogar.Base.IBasePresenter;
import com.alexander_rodriguez.mihogar.mi_casa.Models.ModelAlquilerView;
import com.alexander_rodriguez.mihogar.mi_casa.Models.ModelUserView;

import java.util.ArrayList;

public interface Interface {
    interface Presenter extends IBasePresenter {
        void mostrarUsuarios();
        void mostrarAlquileres();
    }
    interface View extends BaseView {
        void mostarListUsuarios(ArrayList<ModelUserView> list);
        void mostarListAlquileres(ArrayList<ModelAlquilerView> list);
    }
}
