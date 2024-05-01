package com.alexander_rodriguez.mihogar.menu_main;

import android.app.Dialog;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.alexander_rodriguez.mihogar.Base.BaseView;

public interface Interface {
    interface View extends BaseView {
        void onClickHistorialCasa(android.view.View view);
        void onClickMasCuartos(android.view.View view);
        void onClickMasAlquiler(android.view.View view);
        void onClickMiCasa(android.view.View view);

        void showRegistrarCasa();

        void showDialog(AppCompatDialogFragment dialog, String tag);
    }
}
