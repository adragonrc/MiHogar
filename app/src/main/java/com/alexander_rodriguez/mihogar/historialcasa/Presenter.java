package com.alexander_rodriguez.mihogar.historialcasa;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.alexander_rodriguez.mihogar.Base.BaseActivity;
import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.MyAdminDate;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.Adapters.Models.ModelAlquilerView;
import com.alexander_rodriguez.mihogar.Adapters.Models.ModelUserView;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquilerUsuario;
import com.alexander_rodriguez.mihogar.UTILIDADES.TUsuario;
import com.alexander_rodriguez.mihogar.viewUser.DialogDetallesAlquiler;

import java.util.ArrayList;
import java.util.Collections;

public class Presenter extends BasePresenter<Interface.View> implements Interface.Presenter {
    private MyAdminDate myAdminDate;

    private String modo;
    private String idAlquiler;
    private String dni;

    private int iMenu;
    private int idItemMenuSelected;

    private Menu menu;

    private boolean showMain;

    private ArrayList<ModelAlquilerView> listAlquileres;
    private ArrayList<ModelUserView> listUsuarios;
    private Intent mIntent;

    public Presenter(Interface.View view, Intent i) {
        super(view);
        myAdminDate = new MyAdminDate();
        this.modo  = i.getStringExtra(HistorialCasaActivity.TYPE_MODE);
        idAlquiler = i.getStringExtra(TAlquiler.ID);

        idItemMenuSelected = -1;
        mIntent = i;
    }

    private void mostrarTodo(){
        iMenu = R.menu.menu_historial_mi_casa;
        modo = HistorialCasaActivity.MODO_DEFAULT;
        switch (idItemMenuSelected){
            case R.id.iVerAlquileres:{
                mostrarAlquileres();
                break;
            }
            case R.id.iVerUsuario:{
                mostrarUsuarios();
                break;
            }
            default:
                mostrarUsuarios();
                break;

        }
    }
    private void mostrarSoloUsuarios(){
        mostrarUsuariosDeAlquiler();
        iMenu = 0;
    }
    private void mostrarSoloAlquileres(){
        mostrarAlquileresDeUsuario();
        iMenu = 0;
    }

    private void mostrarAlquileresDeUsuario() {
        int dni = mIntent.getIntExtra(TUsuario.DNI, -1);
        if (dni != -1) {
            view.mostarListAlquileres(getListAlquileresDeUsuario(dni));
        }
    }

    private ArrayList<ModelAlquilerView> getListAlquileresDeUsuario(int dni) {
        Cursor c = db.getAllAlquileres("*", TAlquilerUsuario.DNI, dni);
        listAlquileres = ModelAlquilerView.createListModel(c);
        return listAlquileres;
    }

    @Override
    public void iniciarComandos(){
        switch (modo){
            case HistorialCasaActivity.MODO_SOLO_ALQUILERES: {
                mostrarSoloAlquileres();
                break;
            }
            case HistorialCasaActivity.MODO_SOLO_USUARIOS:{
                mostrarSoloUsuarios();
                break;
            }
            default:{
                mostrarTodo();
                break;
            }
        }
    }


    @Override
    public void mostrarUsuarios() {
        view.mostarListUsuarios(getListUsuarios());
    }

    @Override
    public void mostrarAlquileres() {
        view.mostarListAlquileres(getListAlquileres());
    }
    public void mostrarUsuariosDeAlquiler() {

        view.mostarListUsuarios(getListUsuariosDealquiler());
    }

    @Override
    public void crearMenu(MenuInflater menuInflater, Menu menu) {
        this.menu = menu;
        if (iMenu != 0){
            menuInflater.inflate(this.iMenu, menu);
            switch (idItemMenuSelected) {
                case R.id.iVerAlquileres: {
                    menu.findItem(R.id.item_ordenar_numero).setVisible(true);
                    menu.findItem(R.id.item_ordenar_nombre).setVisible(false);
                    break;
                }
                default: {
                    menu.findItem(R.id.item_ordenar_numero).setVisible(false);
                    menu.findItem(R.id.item_ordenar_nombre).setVisible(true);
                    break;
                }
            }
        }

    }


    @Override
    public ContentValues getDetallesAlquiler(String id) {
        ContentValues cv = db.getFilaAlquilerOf("*", id);
        Cursor c = db.getUsuariosForAlquiler("*", id);
        int cantidad = -1;
        if (c.moveToNext()){
            cantidad = c.getCount();
        }
        cv.put(DialogDetallesAlquiler.NUM_USUARIOS, cantidad);
        return cv;
    }

    @Override
    public void ordenarPorNombre() {
        Collections.sort(listUsuarios, (o1, o2) -> o1.getNombres().compareTo(o2.getNombres()));
        mostrarUsuarios();
    }

    @Override
    public void ordenarPorNumero() {
        Collections.sort(listAlquileres, (o1, o2) -> {
            int i = Integer.parseInt(o1.getNumCuarto());
            int j = Integer.parseInt(o2.getNumCuarto());
            return i - j;
        });
        mostrarAlquileres();
    }

    @Override
    public void itemSelected(MenuItem item) {
        int id =  item.getItemId();
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
                mostrarUsuarios();
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
        }
    }

    private ArrayList<ModelAlquilerView> getListAlquileres(){
        if (listAlquileres == null) {
            String columnas = "*";
            Cursor c = db.getAllAlquileres(columnas);
            listAlquileres = ModelAlquilerView.createListModel(c);
        }
        return listAlquileres;
    }

    private ArrayList<ModelUserView> getListUsuarios(){
        if (listUsuarios == null) {
            String columnas = "*";
            Cursor c = db.getAllUsuariosADDAlert(columnas);
            listUsuarios = ModelUserView.createListModel(c, false);
        }
        return listUsuarios;
    }

    private ArrayList<ModelUserView> getListUsuariosDealquiler() {
        if (listUsuarios == null) {
            String columnas = "*";
            Cursor c = db.getUsuariosForAlquiler(columnas, idAlquiler);
            listUsuarios = ModelUserView.createListModel(c, true);
        }
        return listUsuarios;
    }
}
