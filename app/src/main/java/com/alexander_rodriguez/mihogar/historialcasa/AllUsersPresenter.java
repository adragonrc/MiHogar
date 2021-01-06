package com.alexander_rodriguez.mihogar.historialcasa;

import android.content.ContentValues;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;

public class AllUsersPresenter extends BasePresenter<Interface.View> implements Interface.Presenter {

    public AllUsersPresenter(Interface.View view, Intent intent) {
        super(view);
    }

    @Override
    public void showList() {

    }


    @Override
    public void crearMenu(MenuInflater menuInflater, Menu menu) {

    }

    @Override
    public ContentValues getDetails(String id) {
        return null;
    }

    @Override
    public void ordenarPorNombre() {

    }

    @Override
    public void ordenarPorNumero() {

    }

    @Override
    public void itemSelected(MenuItem item) {

    }

    @Override
    public void onClickHolder(RecyclerView.ViewHolder holder) {

    }

    @Override
    public void iniciarComandos() {

    }
}
