package com.alexander_rodriguez.mihogar.historial_casa;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.Base.BaseActivity;
import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.DataBase.DBInterface;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemTenant;
import com.alexander_rodriguez.mihogar.AdminDate;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.adapters.Models.ModelAlquilerView;
import com.alexander_rodriguez.mihogar.mi_casa.MyHouseFragment;

import java.util.ArrayList;
import java.util.Collections;

public class Presenter extends BasePresenter<Interface.View> implements Interface.Presenter, FragmentParent.presenter {
    private AdminDate adminDate;

    private String modo;
    private String rentalIdIgnore;
    private String dni;
    private String roomNumber;

    private int iMenu;
    private int idItemMenuSelected;

    private Menu menu;

    private boolean showMain;

    private ArrayList<ModelAlquilerView> listAlquileres;
    private ArrayList<ItemTenant> listUsuarios;
    private Intent mIntent;

    private int cont;
    private int iCont;

    public Presenter(Interface.View view, Intent i) {
        super(view);
        adminDate = new AdminDate();
        this.modo  = i.getStringExtra(HistorialCasaActivity.MODE);
        rentalIdIgnore = i.getStringExtra(HistorialCasaActivity.EXTRA_RENTAL_ID_IGNORE);
        dni = i.getStringExtra(HistorialCasaActivity.EXTRA_DNI);
        roomNumber = i.getStringExtra(HistorialCasaActivity.EXTRA_ROOM_NUMBER);
        idItemMenuSelected = -1;
        mIntent = i;
    }

    @Override
    public void iniciarComandos(){
        String mode = mIntent.getStringExtra(HistorialCasaActivity.MODE);
        MyHouseFragment fragment;
        switch (mode){
            case HistorialCasaActivity.ALL_RENTALS: {
                fragment = MyHouseFragment.newInstance(view);

                fragment.setPresenter(new AllRentalPresenter(fragment, this));
                break;
            }
            case HistorialCasaActivity.ALL_USERS:{
                fragment = MyHouseFragment.newInstance(view);
                fragment.setPresenter(new AllUsersPresenter(fragment, this));
                break;
            }
            case HistorialCasaActivity.USERS_OF_RENTAL:{
                Bundle args = new Bundle();
                args.putString(MyHouseFragment.ARG_RENTAL_ID, mIntent.getStringExtra(HistorialCasaActivity.EXTRA_RENTAL_ID));
                fragment = new MyHouseFragment(view);
                fragment.setArguments(args);
                fragment.setPresenter(new RentalUsersPresenter(fragment, this));
                break;
            }
            case HistorialCasaActivity.RENTALS_OF_USER:
            case HistorialCasaActivity.RENTALS_OF_ROOM:{
                fragment = MyHouseFragment.newInstance(view, roomNumber, rentalIdIgnore);
                fragment.setPresenter(new UserRentalsPresenter(fragment, this));
                break;

            }
            default: {
                fragment= MyHouseFragment.newInstance(view);
                view.showMessage("default mode not implemented");
                view.salir();
            }
        }
        view.showFragment(fragment);
    }


    public void showList() {
    }

    @Override
    public void crearMenu(MenuInflater menuInflater, Menu menu) {
        this.menu = menu;
        if (iMenu != 0){
            menuInflater.inflate(this.iMenu, menu);
            if (idItemMenuSelected == R.id.iVerAlquileres) {
                menu.findItem(R.id.item_ordenar_numero).setVisible(true);
                menu.findItem(R.id.item_ordenar_nombre).setVisible(false);
            } else {
                menu.findItem(R.id.item_ordenar_numero).setVisible(false);
                menu.findItem(R.id.item_ordenar_nombre).setVisible(true);
            }
        }
    }


    @Override
    public ContentValues getDetails(String id) {
       /* ContentValues cv = db.getFilaAlquilerOf("*", id);
        Cursor c = db.getRentalTenant("*", id);
        int cantidad = -1;
        if (c.moveToNext()){
            cantidad = c.getCount();
        }
        cv.put(DialogDetallesAlquiler.NUM_USUARIOS, cantidad);
        return cv;*/
        return null;
    }

    private void ordenarPorNombre() {
        Collections.sort(listUsuarios, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        showList();
    }

    private void ordenarPorNumero() {
      /*  Collections.sort(listAlquileres, (o1, o2) -> {
            int i = Integer.parseInt(o1.getNumCuarto());
            int j = Integer.parseInt(o2.getNumCuarto());
            return i - j;
        });
        mostrarAlquileres();*/
    }

    @Override
    public void itemSelected(MenuItem item) {
        if (item.getItemId() == BaseActivity.BACK_PRESSED){
            view.salir();
        }
        /*int id =  item.getItemId();
        switch (id){
            case R.id.iVerAlquileres:{
                idItemMenuSelected = R.id.iVerAlquileres;
                mostrarAlquileres();
                menu.findItem(R.id.item_ordenar_numero).setVisible(true);
                menu.findItem(R.id.item_ordenar_nombre).setVisible(false);
                break;
            }
            case R.id.iVerUsuario:{
                idItemMenuSelected = R.id.iVerAlquileres;
                showList();
                menu.findItem(R.id.item_ordenar_numero).setVisible(false);
                menu.findItem(R.id.item_ordenar_nombre).setVisible(true);
                break;
            }
            case R.id.item_ordenar_nombre:{
                ordenarPorNombre();
                break;
            }
            case R.id.item_ordenar_numero:{
                ordenarPorNumero();
                break;
            }
            case BaseActivity.BACK_PRESSED:{
                view.salir();
                break;
            }
        }*/
    }

    @Override
    public void onClickHolder(RecyclerView.ViewHolder holder) {

    }

    @Override
    public DBInterface getDB() {
        return db;
    }
}
