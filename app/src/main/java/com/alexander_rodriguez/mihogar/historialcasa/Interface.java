package com.alexander_rodriguez.mihogar.historialcasa;

import android.content.ContentValues;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.alexander_rodriguez.mihogar.Base.BaseView;
import com.alexander_rodriguez.mihogar.Base.IBasePresenter;
import com.alexander_rodriguez.mihogar.Adapters.Models.ModelAlquilerView;
import com.alexander_rodriguez.mihogar.Adapters.Models.ModelUserView;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemUser;

import java.util.ArrayList;

public interface Interface {
    interface Presenter extends IBasePresenter {
        void mostrarUsuarios();

        void mostrarAlquileres();

        void crearMenu(MenuInflater menuInflater, Menu menu);

        ContentValues getDetallesAlquiler(String id);

        void ordenarPorNombre();

        void ordenarPorNumero();

        void itemSelected(MenuItem item);
    }
    interface View extends BaseView {
        void mostarListUsuarios(ArrayList<ItemUser> list);

        void mostarListAlquileres(ArrayList<ModelAlquilerView> list);

        void setiMenu(int i);

        void salir();
    }
}
