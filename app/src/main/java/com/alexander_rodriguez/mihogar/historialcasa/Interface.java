package com.alexander_rodriguez.mihogar.historialcasa;

import android.content.ContentValues;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.Base.BaseView;
import com.alexander_rodriguez.mihogar.Base.IBasePresenter;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemRental;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemTenant;
import com.alexander_rodriguez.mihogar.viewUser.DialogDetallesAlquiler;

import java.util.ArrayList;

public interface Interface {
    interface Presenter extends IBasePresenter {
        
        void showList();

        void crearMenu(MenuInflater menuInflater, Menu menu);

        ContentValues getDetails(String id);

        void ordenarPorNombre();

        void ordenarPorNumero();

        void itemSelected(MenuItem item);

        void onClickHolder(RecyclerView.ViewHolder holder);
    }
    interface View extends BaseView {

        void showUsersList(ArrayList<ItemTenant> list, RecyclerView.LayoutManager layout, boolean showMain);

        void showRentalsList(ArrayList<ItemRental> list, RecyclerView.LayoutManager layout);

        void setiMenu(int i);

        void salir();

        void goTo(Intent intent);

        void showDialog(DialogDetallesAlquiler dialogDetallesAlquiler);
    }
}
