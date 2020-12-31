package com.alexander_rodriguez.mihogar.historialUserPakage;

import android.content.ContentValues;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.UTILIDADES.TUsuario;

import java.io.File;

public class Presenter extends BasePresenter<Interfaz.view> implements Interfaz.presenter {

    private String dni;
    private ContentValues datosUsuario;
    public Presenter(Interfaz.view view, String dni) {
        super(view);
        this.dni = dni;
    }

    @Override
    public void iniciarComandos() {
        if (dni != null && !dni.isEmpty()) {
            solicitarDatos();
        }else {
            view.showMensaje("DNI no encontrado");
            view.salir();
        }
    }

    private void solicitarDatos() {
        try {
            datosUsuario = db.getFilaInUsuariosOf("*", dni);
            String cont = db.contAlquileresOf(TUsuario.DNI, dni);
            if (!(new File(datosUsuario.getAsString(TUsuario.URI))).exists()){
                datosUsuario.remove(TUsuario.URI);
                datosUsuario.put(TUsuario.URI, "");
            }
            view.mostrarDatosUsuario(datosUsuario, cont);
       //     if (db.usuarioAlertado(dni)){
        //        view.mostrarAlerta();
        //    }else view.noMostrarAlerta();
        }catch (Error e){
            view.modoError("fallo: " + e.getMessage());
        }
    }

    @Override
    public void actualizarNombres(String nombres) {
        db.upDateUser(TUsuario.NOMBRES, nombres, dni);
        view.actualizarNombres(nombres);
    }

    @Override
    public void actualizarApePat(String apellidoPat) {
        db.upDateUser(TUsuario.APELLIDO_PAT, apellidoPat, dni);
        view.actualizarApePat(apellidoPat);
    }

    @Override
    public void actualizarApeMat(String apellidoMat) {
        db.upDateUser(TUsuario.APELLIDO_MAT, apellidoMat, dni);
        view.actualizarApeMat(apellidoMat);
    }

    @Override
    public void actualizarPhoto(String path) {
        db.upDateUser(TUsuario.URI, path, dni);
    }


}
