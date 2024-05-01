package com.alexander_rodriguez.mihogar.mi_casa;

import android.content.Context;
import android.view.MenuItem;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.DataBase.DBInterface;
import com.alexander_rodriguez.mihogar.adapters.Models.ModelRoomView;
import com.alexander_rodriguez.mihogar.R;

import java.util.ArrayList;

public class Presenter extends BasePresenter<Interface.View> implements Interface.Presenter{
    private final ArrayList<ModelRoomView> list;
    private Context mContext;
    private int contCallBack;
    private int option;

    public Presenter(Interface.View view) {
        super(view);
        mContext = view.getContext();
        list = new ArrayList<>();
        option = 0;
    }

    @Override
    public void iniciarComandos() {
    }

    @Override
    public void terminarAlquiler(String motivo, String id) {
        view.showMessage("Funcion aun no implementada");
    }

    @Override
    public void onResume() {
      /*  switch (option){
            case 0:{
                showAll();
                break;
            }
            case 1:{
                verCuartosAlquilados();
                break;
            }
            case 2:{
                verCuartosLibres();
                break;
            }
        }*/
    }

    @Override
    public void onOptionItemSelected(MenuItem item) {
      /*  int id = item.getItemId();

        if (id == R.id.iTodos) {
            option = 0;
            showAll();
        } else if (id == R.id.iCuartosAlquilados) {
            option = 1;
            verCuartosAlquilados();
        } else if (id == R.id.iCuartosLibres) {
            option = 2;
            verCuartosLibres();
        } else if (id == R.id.itFecha) {
            ordenarPorFecha();
        }*/
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.i_room){
            view.showRooms();
            return true;
        }else if(id == R.id.i_tenant){
            view.showTenants();
            return true;
        }
        return true;
    }

    @Override
    public DBInterface getDB() {
        return db;
    }

}
