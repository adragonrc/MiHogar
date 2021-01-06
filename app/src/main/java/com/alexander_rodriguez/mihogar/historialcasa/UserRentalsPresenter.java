package com.alexander_rodriguez.mihogar.historialcasa;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.Adapters.RvAdapterAlquiler;
import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.tableActivity.TableActivity;
import com.alexander_rodriguez.mihogar.viewUser.DialogDetallesAlquiler;

public class UserRentalsPresenter extends BasePresenter<Interface.View> implements Interface.Presenter {
    public UserRentalsPresenter(Interface.View view, Intent intent) {
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
        if(holder instanceof RvAdapterAlquiler.Holder){
            RvAdapterAlquiler.Holder mHolder = (RvAdapterAlquiler.Holder) holder;

            Bundle bundle = new Bundle();
            ContentValues alquiler = getDetails(mHolder.getmId());

            DialogDetallesAlquiler dialogDetallesAlquiler = new DialogDetallesAlquiler(view.getContext(), alquiler);
            dialogDetallesAlquiler.setOnClickListenerVerCuarto(v -> {
                Intent i = new Intent(view.getContext(), HistorialCasaActivity.class);
                i.putExtra(HistorialCasaActivity.MODE, HistorialCasaActivity.ALL_USERS);
                i.putExtra(TAlquiler.ID, mHolder.getmId());
                view.goTo(i);
            });
            dialogDetallesAlquiler.setOnClickListenerVerPagos(v -> {
                Intent intent = new Intent(view.getContext(), TableActivity.class);
                intent.putExtra(TAlquiler.ID, mHolder.getmId());
                view.goTo(intent);
            });

            view.showDialog(dialogDetallesAlquiler);

        }
    }

    @Override
    public void iniciarComandos() {

    }
}
