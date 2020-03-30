package com.alexander_rodriguez.mihogar.tableActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.TableCursor;
import com.alexander_rodriguez.mihogar.UTILIDADES.Mensualidad;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.UTILIDADES.TPago;

public class Presenter extends BasePresenter<Interfaz.view> {
    private String idAlquiler;

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView tv = v.findViewById(R.id.tvId);
            if  (tv != null){
                Cursor c = db.getPago(tv.getText().toString());
                if (c != null){
                    ContentValues m = db.getFilaInMensualidadActual("*", c.getString(TPago.INT_ID_M));
                    m = db.getFilaAlquilerOf("*", m.getAsString(Mensualidad.ID_A));

                    ContentValues cv = db.getFilaInUsuariosOf("*", m.getAsString(TAlquiler.DNI));
                    view.gotoShowPDF(c.getString(TPago.INT_URI_VOUCHER), cv);
                }
            }
        }
    };

    public Presenter(Interfaz.view view, String idAlquiler) {
        super(view);
        this.idAlquiler = idAlquiler;
    }
    public void crearTabla(){
        ViewGroup vg = view.getGrup();
        String columnas = Mensualidad.ID+", "+Mensualidad.COSTO+ ", " +Mensualidad.FECHA_I;
        TableCursor tcMensualidad = db.getMensualidadesOfAlquiler(columnas, idAlquiler);
        Context c = view.getContext();
        for (int i = 0; i < tcMensualidad.getCount(); i++){
            TableLayout tl = (TableLayout) LayoutInflater.from(view.getContext()).inflate(R.layout.view_table_layout, vg, false);
            String idMensualidad = tcMensualidad.getValue(i,Mensualidad.ID);
            String costo = tcMensualidad.getValue(i,Mensualidad.COSTO);
            tl.addView(crateTitleMensualidad("Mensualidad: "+ idMensualidad, tcMensualidad.getValue(i,Mensualidad.FECHA_I), tl));
            TableCursor tcPagos = db.getPagosOf(TPago.ID+", "+ TPago.FECHA, idMensualidad);
            for (int j = 0; i < tcPagos.getCount(); i++){
                //tcPagos.getS()[j][0], tcPagos.getS()[j][1], costo
                View v =creatRowPago(tcPagos.getS()[j][0], tcPagos.getS()[j][1], costo, tl);
                tl.addView(v);
            }
            view.addTable(tl);
        }
    }

    private View creatRowPago(String numPago, String fecha, String monto, ViewGroup vg){
        View v = LayoutInflater.from(view.getContext()).inflate(R.layout.view_fila_of_pagos, vg, false);
        ((TextView)v.findViewById(R.id.tvId)).setText(numPago);
        ((TextView)v.findViewById(R.id.tvFecha)).setText(fecha);
        ((TextView)v.findViewById(R.id.tvMonto)).setText(monto);
        v.setOnClickListener(listener);
        return v;
    }


    private View crateTitleMensualidad(String mensualidad, String fecha, ViewGroup vg){
        View v = LayoutInflater.from(view.getContext()).inflate(R.layout.view_mensualidad, vg, false);
        ((TextView)v.findViewById(R.id.tvMensualidad)).setText(mensualidad);
        ((TextView)v.findViewById(R.id.tvFecha)).setText(fecha);
        return v;
    }

    @Override
    public void iniciarComandos() {
        crearTabla();
        /*
        String columnas = Mensualidad.ID+", "+Mensualidad.COSTO+ ", " +Mensualidad.FECHA_I;
        TableCursor tcMensualidad = db.getMensualidadesOfAlquiler(columnas, idAlquiler);

        for (int i = 0; i< tcMensualidad.getCount(); i++){
            String idMensualidad = tcMensualidad.getValue(i,Mensualidad.ID);
            String costo = tcMensualidad.getValue(i,Mensualidad.COSTO);
            view.addTitleMensualidad("Mensualidad: " +i, tcMensualidad.getValue(i,Mensualidad.FECHA_I));
            TableCursor tcPagos = db.getPagosOf(TPago.ID+", "+ TPago.FECHA, idMensualidad);

            for (int j = 0; j<tcPagos.getCount(); j++ ){
                view.addRow(tcPagos.getS()[j][0], tcPagos.getS()[j][1], costo);
            }
        }*/
    }
}
