package com.alexander_rodriguez.mihogar.listalquileres;

import com.alexander_rodriguez.mihogar.Base.BaseView;
import com.alexander_rodriguez.mihogar.adapters.Models.ModelAlquilerView;

import java.util.ArrayList;

public interface Interface {
    interface Presentador{
    }
    interface Vista extends BaseView {
        void mostarListAlquileres(ArrayList<ModelAlquilerView> list);
    }
}
