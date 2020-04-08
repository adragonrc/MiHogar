package com.alexander_rodriguez.mihogar.vercuarto.view_perfil_cuarto;

import android.view.View;

public interface perfilCuartoInterface {
    void onClickVermas(View view);

    void onClickEditarMensualidad(View view);

    void onClickEditarDetalles(View view);

    void ocEditarNumTel(View view);

    void ocEditarCorreo(View view);

    void onClickConfirMensualidad(View view);

    void onClickConfirDetalles(View view);

    void ocConfirNumTel(View view);

    void ocConfirCorreo(View view);

    void onClickPhoto(View view);

    void actualizarNumTel(String numTel);

    void actualizarCorreo(String correo);

    void onClickVerAlquileres(View view);
}
