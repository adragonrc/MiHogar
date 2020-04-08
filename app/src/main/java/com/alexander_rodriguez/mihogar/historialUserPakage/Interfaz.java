package com.alexander_rodriguez.mihogar.historialUserPakage;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.alexander_rodriguez.mihogar.Base.BaseView;
import com.alexander_rodriguez.mihogar.Base.IBasePresenter;
import com.alexander_rodriguez.mihogar.ButtonsAC.interfazAC;

public interface Interfaz {
    interface presenter extends IBasePresenter {
        void actualizarNombres(String nombres);
        void actualizarApePat(String apellidoPat);
        void actualizarApeMat(String apellidoMat);


        void actualizarPhoto(String path);
    }
    interface view extends BaseView, interfazAC {

        void mostrarAlerta();
        void noMostrarAlerta();
        void mostrarDatosUsuario(ContentValues datos, String i);
        void modoError(String error);


        void ocEditarNombres(View view);
        void ocEditarApePat(View view);
        void ocEditarApeMat(View view);

        void ocConfirNombres(View view);
        void ocConfirApePat(View view);
        void ocConfirApeMat(View view);

        void actualizarNombres(String nombres);
        void actualizarApePat(String apellidoPaterno);
        void actualizarApeMat(String apellidoMaterno);

        void salir();
    }
}
