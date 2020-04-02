package com.alexander_rodriguez.mihogar.vercuarto;

import android.content.ContentValues;

import com.alexander_rodriguez.mihogar.Base.BaseView;
import com.alexander_rodriguez.mihogar.Base.IBasePresenter;

import java.io.File;

public interface Interface {
    interface Presenter extends IBasePresenter {
        ContentValues getDatosAlquiler();
        void mostrarDetalles();
        void deshacerContrato(String motivo);
        void actualizarMensualidad(String mensualidad);
        void actualizarDetalles(String detalles);
        void realizarPago();
    }
    interface view extends BaseView, perfilCuartoInterface {
        void noPago();
        void pago();
        void showCuartolibre(ContentValues cuarto);
        void showCuartoAlquilado(ContentValues cuarto, ContentValues Usuario, String mensualidad);
        void mostrarPDF(File pdfFile, ContentValues datosUsuario);
        void actualizarMensualidad(String mensualidad);
        void actualizarDetalles(String detalles);

    }
}
