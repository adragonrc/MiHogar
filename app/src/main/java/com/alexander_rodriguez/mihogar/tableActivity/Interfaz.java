package com.alexander_rodriguez.mihogar.tableActivity;

import android.view.ViewGroup;
import android.widget.TableLayout;

import com.alexander_rodriguez.mihogar.Base.BaseView;
import com.alexander_rodriguez.mihogar.Base.IBasePresenter;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemRental;
import com.alexander_rodriguez.mihogar.DataBase.parse.ParceRental;

public interface Interfaz {
    interface Presenter extends IBasePresenter {
        void onPositive();
        void crearPDF();
    }
    interface view extends BaseView {
        void addRow(String... atts);
        void addTitleMensualidad(String s, String s1);

        void gotoShowPDF(String string, ParceRental datoUsuario);

        void addTable(TableLayout tl);

        ViewGroup getGrup();

        void showDialog(String mensaje);

        void close();
    }
}
