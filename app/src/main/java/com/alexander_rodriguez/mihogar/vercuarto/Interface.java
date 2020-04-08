package com.alexander_rodriguez.mihogar.vercuarto;

import android.content.ContentValues;

import com.alexander_rodriguez.mihogar.Base.BaseView;
import com.alexander_rodriguez.mihogar.Base.IBasePresenter;
import com.alexander_rodriguez.mihogar.vercuarto.view_perfil_cuarto.perfilCuartoInterface;

import java.io.File;

public interface Interface {
    interface Presenter extends IBasePresenter {
        ContentValues getDatosAlquiler();

        void mostrarDetalles();

        void deshacerContrato(String motivo);

        void actualizarMensualidad(String mensualidad);

        void actualizarDetalles(String detalles);

        void realizarPago();

        void actualizarNumTel(String toString);

        void actualizarCorreo(String toString);

        void actualizarPhoto(String path);

        void crearPDF();

        String getResponsable();
    }
    interface view extends BaseView, perfilCuartoInterface {
        void noPago();

        void pago();

        void showCuartolibre(ContentValues cuarto);

        void showCuartoAlquilado(ContentValues cuarto, int numCuarto, String mensualidad);

        void mostrarPDF(File pdfFile, ContentValues datosUsuario);

        void actualizarMensualidad(String mensualidad);

        void actualizarDetalles(String detalles);

        void actualizarFechaPago(String fecha);
    }
}
