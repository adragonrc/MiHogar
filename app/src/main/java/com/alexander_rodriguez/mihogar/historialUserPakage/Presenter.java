package com.alexander_rodriguez.mihogar.historialUserPakage;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.UTILIDADES.TUsuario;

public class Presenter extends BasePresenter<Interfaz.view> implements Interfaz.presenter {

    private String dni;
    private ContentValues datosUsuario;
    public Presenter(Interfaz.view view, String dni) {
        super(view);
        this.dni = dni;
    }

    @Override
    public void iniciarComandos() {
        if (dni !=null) {
            solicitarDatos();
        }else {
            view.showMensaje("DNI no encontrado");
            view.salir();
        }
    }
    private void setPic(ImageView imageView, String currentPhotoPath) {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }

    public void setImage(ImageView i, String path){
        if(path == null) return;
        if(path.equals("")) return;
       // setPic(i, path);
        Bitmap bm = BitmapFactory.decodeFile(path);
        i.setImageBitmap(bm);
    }


    private void solicitarDatos() {
        try {
            datosUsuario = db.getFilaInUsuariosOf("*", dni);
            String cont = db.contAlquileresOf(TAlquiler.DNI, dni);
            view.mostrarDatosUsuario(datosUsuario, cont);
            if (db.usuarioAlertado(dni)){
                view.mostrarAlerta();
            }else view.noMostrarAlerta();
        }catch (Error e){
            view.modoError("fallo: " + e.getMessage());
        }
    }

    @Override
    public void actualizarNombres(String nombres) {
        db.upDateUsuario(TUsuario.NOMBRES, nombres, dni);
        view.actualizarNombres(nombres);
    }

    @Override
    public void actualizarApePat(String apellidoPat) {
        db.upDateUsuario(TUsuario.APELLIDO_PAT, apellidoPat, dni);
        view.actualizarApePat(apellidoPat);
    }

    @Override
    public void actualizarApeMat(String apellidoMat) {
        db.upDateUsuario(TUsuario.APELLIDO_MAT, apellidoMat, dni);
        view.actualizarApeMat(apellidoMat);
    }

    @Override
    public void actualizarNumTel(String numero) {
        db.upDateUsuario(TUsuario.NUMERO_TEL, numero, dni);
        view.actualizarNumTel(numero);
    }

    @Override
    public void actualizarCorreo(String correo) {
        db.upDateUsuario(TUsuario.CORREO, correo, dni);
        view.actualizarCorreo(correo);
    }

}
