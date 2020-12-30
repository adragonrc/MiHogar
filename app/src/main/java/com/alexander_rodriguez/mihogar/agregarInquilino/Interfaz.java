package com.alexander_rodriguez.mihogar.agregarInquilino;

import android.widget.ArrayAdapter;

import com.alexander_rodriguez.mihogar.Adapters.RvAdapterUser;
import com.alexander_rodriguez.mihogar.Base.BaseView;
import com.alexander_rodriguez.mihogar.Base.IBasePresenter;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemUser;
import com.alexander_rodriguez.mihogar.viewregistraralquiler.ModelAA;
import com.alexander_rodriguez.mihogar.view_registrar_usuario.interfazUserView;

public interface Interfaz {
    interface presenter  extends IBasePresenter {

        ArrayAdapter<String> getAdapterCuartos ();

        void confirmar();

        void onClickAddUser();

        void agregarUsuario(ItemUser datos);

        void avanzar();

        void agregarAlquilerNuevo(ModelAA list);

        void setMain(RvAdapterUser.Holder holder);

        boolean saveChanges();
    }
    interface view extends BaseView, interfazUserView {

        void showError(String error);

        void showDialog(String s);

        void close();

        void startRegistroUsuario();

        void sinCuartos();

        void mostrarNuevoUsuario(ItemUser m);

        void doPrincipal(RvAdapterUser.Holder holder);
    }
}
