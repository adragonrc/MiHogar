package com.alexander_rodriguez.mihogar.history_user;

import android.content.Intent;
import android.view.View;

import com.alexander_rodriguez.mihogar.Base.BaseView;
import com.alexander_rodriguez.mihogar.Base.IBasePresenter;
import com.alexander_rodriguez.mihogar.view_buttons_ac.ButtonsAC;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemTenant;

public interface Interfaz {
    interface presenter extends IBasePresenter {
        void actualizarNombres(String nombres);
        void actualizarApePat(String apellidoPat);
        void actualizarApeMat(String apellidoMat);


        void updatePhoto(String path);

        void onClickPositive(View v);

        void onClickPhoto(View view);

        String getDni();
    }
    interface view extends BaseView, ButtonsAC.Listener {

        void mostrarAlerta();
        void noMostrarAlerta();
        void mostrarDatosUsuario(ItemTenant datos, String i);
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

        void reloadRoomPhoto();

        void showImage(Intent intent);
    }
}
