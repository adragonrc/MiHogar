package com.alexander_rodriguez.mihogar.historial_casa;

import android.content.ContentValues;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.Base.BaseView;
import com.alexander_rodriguez.mihogar.Base.IBasePresenter;
import com.alexander_rodriguez.mihogar.mi_casa.MyHouseFragment;
import com.alexander_rodriguez.mihogar.viewUser.DialogDetallesAlquiler;

public interface Interface {
    interface Presenter extends IBasePresenter {

        void crearMenu(MenuInflater menuInflater, Menu menu);

        ContentValues getDetails(String id);

        void itemSelected(MenuItem item);

        void onClickHolder(RecyclerView.ViewHolder holder);
    }
    interface View extends BaseView, FragmentParent.view {

        void salir();

        void goTo(Intent intent);

        void showDialog(DialogDetallesAlquiler dialogDetallesAlquiler);

        void showFragment(MyHouseFragment myHouseFragment);
    }
}
