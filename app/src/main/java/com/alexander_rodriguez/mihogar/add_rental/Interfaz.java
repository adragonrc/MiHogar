package com.alexander_rodriguez.mihogar.add_rental;

import android.widget.ArrayAdapter;

import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.adapters.AdapterInterface;
import com.alexander_rodriguez.mihogar.Base.BaseView;
import com.alexander_rodriguez.mihogar.Base.IBasePresenter;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemTenant;
import com.alexander_rodriguez.mihogar.adapters.RvAdapterUser;
import com.alexander_rodriguez.mihogar.viewregistraralquiler.AddRentalInterface;
import com.alexander_rodriguez.mihogar.viewregistraralquiler.ModelAA;
import com.alexander_rodriguez.mihogar.view_registrar_usuario.InterfaceUserView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public interface Interfaz {
    interface presenter  extends IBasePresenter {

        ArrayAdapter<String> getAdapterCuartos ();

        void confirmar();

        void onClickAddUser();

        void agregarUsuario(ItemTenant datos);

        void avanzar();

        void agregarAlquilerNuevo(ModelAA list);

        void ocHolder(RecyclerView.ViewHolder holder);

        boolean saveChanges();

        void onLongClick(RecyclerView.ViewHolder holder);

        void onBackPressed();

        void onOptionItemSelected(int option);

        void saveUserChanges(ItemTenant datos);
    }
    interface view extends BaseView, InterfaceUserView, AddRentalInterface, AdapterInterface {

        void showError(String error);

        void showDialog(String s);

        void close(int status);

        void startRegistroUsuario();

        void sinCuartos();

        void addUserComplete();

        void addMenu(int menu_add_user_options);

        void removeMenuItem(int item);

        SlidingUpPanelLayout.PanelState getPanelState();

        void panelCollapsed();

        void setTitle(String title, boolean f);

        void editUser(ItemTenant model);

        void setAdapter(RvAdapterUser adapterUser);

        void editUserComplete();
    }
}
