package com.alexander_rodriguez.mihogar.viewUser;

import android.content.ContentValues;

import com.alexander_rodriguez.mihogar.DataBase.DataBaseAdmin;
import com.alexander_rodriguez.mihogar.AdminDate;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;

import java.text.ParseException;
import java.util.Date;

public class Presenter implements Interfaz.Presenter{
    private String idAlquiler;
    private Interfaz.View view;
    private DataBaseAdmin db;
    private ContentValues datosAlquiler;
    private AdminDate adminDate;
    public Presenter(Interfaz.View view, String idAlquiler ){
        this.view = view;
        this.idAlquiler = idAlquiler;
        adminDate = new AdminDate();
        db = new DataBaseAdmin(view.getContext(),null,1);
    }
    @Override
    public void iniciar(){
        try {
            datosAlquiler = db.getFilaAlquilerOf("*", idAlquiler);
            String fechac = datosAlquiler.getAsString(TAlquiler.EXTRA_FECHA_PAGO);
            Date d = adminDate.getDateFormat().parse(fechac);
            if (d.before(new Date()))
                view.doNoPago();
            else
                view.doPago();
          //  view.setAttributes(datosAlquiler.getAsString(TAlquiler.DNI), fechac);
        } catch (ParseException e) {
            view.setAttributes("error", "en paseToDate");
        }

    }

    @Override
    public void terminarAlquiler() {
        db.upDateAlquiler(TAlquiler.ID,"0",idAlquiler);
    }

}
