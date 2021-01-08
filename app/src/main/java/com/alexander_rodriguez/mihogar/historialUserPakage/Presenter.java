package com.alexander_rodriguez.mihogar.historialUserPakage;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.alexander_rodriguez.mihogar.ActivityShowImage;
import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemTenant;
import com.alexander_rodriguez.mihogar.DataBase.parse.ParceTenant;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.Save;
import com.alexander_rodriguez.mihogar.UTILIDADES.TUsuario;
import com.alexander_rodriguez.mihogar.historialcasa.HistorialCasaActivity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FileDownloadTask;

import java.io.File;

public class Presenter extends BasePresenter<Interfaz.view> implements Interfaz.presenter {

    private String dni;
    private ItemTenant tenant;
    private final Intent mIntent;
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
        if(tenant == null)
            db.getUser(dni).addOnSuccessListener(this::getUserSuccess).addOnFailureListener(this::getUserFailure);
        else {
            view.mostrarDatosUsuario(tenant, "-1");
        }
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
            tenant = ItemTenant.newInstance(document);
            File f;
            if(tenant.getPath() != null && !tenant.getPath().isEmpty()) {
                f = new File(tenant.getPath());
            }else{
                f = Save.createFile(mContext, mContext.getString(R.string.cTenant), tenant.getDni() );
            }
            db.downloadRoomPhoto(tenant.getDni(), f)
                    .addOnSuccessListener(this::downloadRoomPhotoSuccess)
                    .addOnFailureListener(this::downloadRoomPhotoFailure);
            String cont = db.contAlquileresOf(TUsuario.DNI, dni);
            view.mostrarDatosUsuario(tenant, cont);

        }else {
            view.showMessage("El usuario: "+ dni + " no se ha encontrado");
        }
    }

    private void downloadRoomPhotoFailure(Exception e) {
    }

    private void downloadRoomPhotoSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
        view.reloadRoomPhoto();
    }

    @Override
    public void actualizarNombres(String nombres) {
        db.updateTenant(mContext.getString(R.string.mdTenantName), nombres, dni);
        view.actualizarNombres(nombres);
    }

    @Override
    public void actualizarApePat(String apellidoPat) {
        db.updateTenant(mContext.getString(R.string.mdTenantApellidoPat), apellidoPat, dni);
        view.actualizarApePat(apellidoPat);
    }

    @Override
    public void actualizarApeMat(String apellidoMat) {
        db.updateTenant(mContext.getString(R.string.mdTenantApellidoMat), apellidoMat, dni);
        view.actualizarApeMat(apellidoMat);
    }

    @Override
    public void updatePhoto(String path) {
        db.updateTenant(mContext.getString(R.string.mdTenantPath), path, dni);
        db.saveTenantPhoto(dni, path)
                .addOnFailureListener(exception -> view.showMessage("Photo upload failed"))
                .addOnSuccessListener(taskSnapshot -> view.showMessage("Photo upload was successful"));
    }

    @Override
    public void onClickPositive(View v) {
        if(tenant == null) return;
        Intent i = new Intent(mContext, HistorialCasaActivity.class);
        i.putExtra(HistorialCasaActivity.MODE, HistorialCasaActivity.RENTALS_OF_USER);
        i.putExtra(HistorialCasaActivity.EXTRA_DNI, tenant.getDni());
        mContext.startActivity(i);
    }

    @Override
    public void onClickPhoto(View v) {
        if(tenant == null) return;
        if(tenant.getPath().equals("")) {
            view.showMessage("Sin foto");
            return;
        }
        Intent intent = new Intent(mContext, ActivityShowImage.class);
        intent.putExtra(ActivityShowImage.IS_USER_IMAGE, true);
        intent.putExtra(ActivityShowImage.EXTRA_DNI, tenant.getDni());
        intent.putExtra(ActivityShowImage.DATA_IMAGE, tenant.getPath());
        view.showImage(intent);
    }
}
