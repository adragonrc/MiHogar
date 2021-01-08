package com.alexander_rodriguez.mihogar.add_rental;

import android.widget.ArrayAdapter;

import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.Adapters.RvAdapterUser;
import com.alexander_rodriguez.mihogar.Base.BaseView;
import com.alexander_rodriguez.mihogar.Base.IBasePresenter;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemTenant;
import com.alexander_rodriguez.mihogar.viewregistraralquiler.ModelAA;
import com.alexander_rodriguez.mihogar.view_registrar_usuario.interfazUserView;

public interface Interfaz {
    interface presenter  extends IBasePresenter {

        ArrayAdapter<String> getAdapterCuartos ();

        void confirmar();

        void onClickAddUser();

        void agregarUsuario(ItemTenant datos);

        void avanzar();

        void agregarAlquilerNuevo(ModelAA list);

        void setMain(RecyclerView.ViewHolder holder);

        boolean saveChanges();
    }
    interface view extends BaseView, interfazUserView {

        void showError(String error);

        void showDialog(String s);

        void close();

        void startRegistroUsuario();

        void sinCuartos();

        void mostrarNuevoUsuario(ItemTenant m);

        void doPrincipal(RvAdapterUser.Holder holder);
    }
}
