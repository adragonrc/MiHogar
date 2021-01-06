package com.alexander_rodriguez.mihogar.historialUserPakage;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemUser;
import com.alexander_rodriguez.mihogar.DataBase.parse.ParceTenant;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.UTILIDADES.TUsuario;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.File;

public class Presenter extends BasePresenter<Interfaz.view> implements Interfaz.presenter {

    private String dni;
    private ContentValues datosUsuario;
    private final ItemUser tenant;
    private Intent mIntent;
    private final Context mContext;
    public Presenter(Interfaz.view view, Intent intent) {
        super(view);
        this.mIntent = intent;
        mContext = view.getContext();
        tenant =(ParceTenant) mIntent.getParcelableExtra(HistorialUsuarioActivity.USER_EXTRA);
        dni = mIntent.getStringExtra(HistorialUsuarioActivity.DNI_EXTRA);
        if(tenant == null && dni == null) {
            view.showMessage("Data is missing");
        }
    }

    @Override
    public void iniciarComandos() {
        if(tenant == null || tenant.getDni() == null || tenant.getDni().isEmpty()){
            view.mostrarDatosUsuario(tenant, "0");
        }else{
            if (dni != null && !dni.isEmpty()) {
                solicitarDatos();
            }else {
                view.showMessage("DNI no encontrado");
                view.salir();
            }
        }


    }

    private void solicitarDatos() {
        db.getUser(dni).addOnSuccessListener(this::getUserSuccess).addOnFailureListener(this::getUserFailure);
//     if (db.usuarioAlertado(dni)){
//         view.mostrarAlerta();
//    }else view.noMostrarAlerta();

    }

    private void getUserFailure(Exception e) {
        view.showMessage("La busqueda del usuario: "+ dni + " ha fallado");
        e.printStackTrace();
    }

    private void getUserSuccess(DocumentSnapshot document) {
        if(document.exists()){
            String cont = db.contAlquileresOf(TUsuario.DNI, dni);
            view.mostrarDatosUsuario(tenant, cont);
        }else {
            view.showMessage("El usuario: "+ dni + " no se ha encontrado");
        }
    }

    @Override
    public void actualizarNombres(String nombres) {
        db.upDateUser(mContext.getString(R.string.mdTenantName), nombres, dni);
        view.actualizarNombres(nombres);
    }

    @Override
    public void actualizarApePat(String apellidoPat) {
        db.upDateUser(mContext.getString(R.string.mdTenantApellidoPat), apellidoPat, dni);
        view.actualizarApePat(apellidoPat);
    }

    @Override
    public void actualizarApeMat(String apellidoMat) {
        db.upDateUser(mContext.getString(R.string.mdTenantApellidoMat), apellidoMat, dni);
        view.actualizarApeMat(apellidoMat);
    }

    @Override
    public void actualizarPhoto(String path) {
        db.upDateUser(mContext.getString(R.string.mdTenantPath), path, dni);
    }


}
