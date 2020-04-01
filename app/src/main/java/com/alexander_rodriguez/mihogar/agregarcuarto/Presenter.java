package com.alexander_rodriguez.mihogar.agregarcuarto;
import android.content.Intent;

import com.alexander_rodriguez.mihogar.AdministradorCamara;
import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.menuPrincipal.MenuPricipal;


public class Presenter extends BasePresenter<Interfaz.View> implements Interfaz.Presenter {
    public Presenter(Interfaz.View view) {
        super(view);
    }

    public void insertarCuarto(String numCuarto, String precio, String detalles, String URL){
        if (!numCuarto.equals("") && !precio.equals("")) {
            if (db.existIntoCuarto(numCuarto)) {
                view.showMensaje("Numero de cuarto ya existe");
            } else {
                db.agregarCuarto(numCuarto, detalles, precio, URL);
                view.showMensaje("Agregado");
                view.salir();
            }
        }else{
            view.showMensaje("Campo vacio");
        }
    }

    @Override
    public void iniciarComandos() {
    }

}
