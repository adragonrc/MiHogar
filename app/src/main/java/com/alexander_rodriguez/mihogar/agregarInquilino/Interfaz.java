package com.alexander_rodriguez.mihogar.agregarInquilino;

import android.widget.ArrayAdapter;
import android.widget.RadioGroup;

import com.alexander_rodriguez.mihogar.Base.BaseView;
import com.alexander_rodriguez.mihogar.Base.IBasePresenter;
import com.alexander_rodriguez.mihogar.modelos.ModelUsuario;

public interface Interfaz {
    interface Presenter  extends IBasePresenter {
        boolean doPago(RadioGroup radioGroup);
        void agregarUsuario(ModelUsuario mu, String numCuarto, String mensualidad, int plazo, String fecha, boolean pago);
         void confirmar();
    }
    interface View extends BaseView {
        void onClickAgregar(android.view.View view);
        void onClickTomarFoto(android.view.View view);
        void onClickCancel(android.view.View view);
        void showError(String error);
        void showDialog(String s);
        void prepararSpinsers(ArrayAdapter<String> a);
        void sinCuartos();
        void close();

        void mostrarEtPlazo();

        void ocultarEtPlazo();
    }
}
