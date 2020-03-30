package com.alexander_rodriguez.mihogar.historialUserPakage;

import android.content.ContentValues;
import android.view.View;
import android.widget.ImageView;

import com.alexander_rodriguez.mihogar.Base.BaseView;
import com.alexander_rodriguez.mihogar.Base.IBasePresenter;

public interface Interfaz {
    interface presenter extends IBasePresenter {
        void actualizarNombres(String nombres);
        void actualizarApePat(String apellidoPat);
        void actualizarApeMat(String apellidoMat);
        void setImage(ImageView i, String path);

        void actualizarNumTel(String toString);

        void actualizarCorreo(String toString);
    }
    interface view extends BaseView {
        void mostrarAlerta();
        void noMostrarAlerta();
        void mostrarDatosUsuario(ContentValues datos, String i);
        void modoError(String error);

        void ocEditarNombres(View view);
        void ocEditarApePat(View view);
        void ocEditarApeMat(View view);
        void ocEditarNumTel(View view);
        void ocEditarCorreo(View view);

        void ocConfirNombres(View view);
        void ocConfirApePat(View view);
        void ocConfirApeMat(View view);
        void ocConfirNumTel(View view);
        void ocConfirCorreo(View view);

        void ocVerMas(View view);
        void actualizarNombres(String nombres);
        void actualizarApePat(String apellidoPaterno);
        void actualizarApeMat(String apellidoMaterno);
        void actualizarNumTel(String numTel);
        void actualizarCorreo(String correo);

        void salir();
    }
}
