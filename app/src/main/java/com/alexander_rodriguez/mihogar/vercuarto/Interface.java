package com.alexander_rodriguez.mihogar.vercuarto;

import com.alexander_rodriguez.mihogar.Base.BaseView;
import com.alexander_rodriguez.mihogar.Base.IBasePresenter;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemRental;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemRoom;
import com.alexander_rodriguez.mihogar.vercuarto.view_perfil_cuarto.perfilCuartoInterface;

import java.io.File;

public interface Interface {
    interface Presenter extends IBasePresenter {
        ItemRental getDatosAlquiler();

        ItemRoom getRoom();

        void mostrarDetalles();

        void deshacerContrato(String motivo);

        void actualizarMensualidad(String mensualidad);

        void actualizarDetalles(String detalles);

        void realizarPago();

        void actualizarNumTel(String toString);

        void actualizarCorreo(String toString);

        void updatePhoto(String path);

        void crearPDF();

        String getResponsable();
    }
    interface view extends BaseView, perfilCuartoInterface {
        void noPago();

        void pago();

        void showCuartolibre(ItemRoom cuarto);

        void showCuartoAlquilado(ItemRoom cuarto, int numCuarto, String mensualidad);

        void mostrarPDF(File pdfFile, ItemRental datosUsuario);

        void actualizarMensualidad(String mensualidad);

        void actualizarDetalles(String detalles);

        void actualizarFechaPago(String fecha);

        void reloadRoomPhoto();
    }
}
