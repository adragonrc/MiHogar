package com.alexander_rodriguez.mihogar.agregarcuarto;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;


public class Presenter extends BasePresenter<Interfaz.view> implements Interfaz.Presenter {
    public Presenter(Interfaz.view view) {
        super(view);
    }

    public void insertarCuarto(String numCuarto, String precio, String detalles, String path){
        if (path == null ) path = "";
        if (!numCuarto.equals("") && !precio.equals("")) {
            if (db.existIntoCuarto(numCuarto)) {
                view.showMensaje("Numero de cuarto ya existe");
            } else {
                db.agregarCuarto(numCuarto, detalles, precio, path);
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
