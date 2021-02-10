package com.alexander_rodriguez.mihogar.mi_casa;

import android.view.MenuItem;

import com.alexander_rodriguez.mihogar.Base.BaseView;
import com.alexander_rodriguez.mihogar.Base.IBasePresenter;
import com.alexander_rodriguez.mihogar.historialcasa.FragmentParent;

public interface Interface{
    interface Presenter extends IBasePresenter, FragmentParent.presenter {
        void terminarAlquiler(String motivo, String id);

        void onResume();

        void onOptionItemSelected(MenuItem item);

        boolean onNavigationItemSelected(MenuItem menuItem);
    }

    interface View extends BaseView, FragmentParent.view {
        //     void mostrarAlquileres(ArrayList<ModelAlquilerView> list);
        //void mostrarUsuarios(ArrayList<ModelUserView> list);

        void showDialogOptions(String idAlquiler);
        void showDialogImput(String idAlquiler);

        void notifyChangedOn(int posList);

        void nothingHere();

        void showRooms();

        void showTenants();
    }
}
