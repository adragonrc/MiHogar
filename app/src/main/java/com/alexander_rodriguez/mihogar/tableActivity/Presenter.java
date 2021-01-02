package com.alexander_rodriguez.mihogar.tableActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.DataBase.DataBaseInterface;
import com.alexander_rodriguez.mihogar.PDF;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.TableCursor;
import com.alexander_rodriguez.mihogar.UTILIDADES.Mensualidad;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.UTILIDADES.TPago;
import com.itextpdf.text.DocumentException;

import java.io.File;
import java.io.FileNotFoundException;

public class Presenter extends BasePresenter<Interfaz.view> implements Interfaz.Presenter{
    private ContentValues alquiler;

    private String idAlquiler;
    private boolean tablaFueCreada;
    private TableRowView viewClicked;
    private ContentValues pago;

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            viewClicked = (TableRowView) v;
            TextView tv = v.findViewById(R.id.tvId);
            if  (tv != null){
                Cursor p = db.getPago(tv.getText().toString());
                pago = DataBaseInterface.cursorToCV(p);
                p.close();
                if (pago != null){

                    alquiler = db.getFilaAlquilerOf("*", idAlquiler);
                    String path =  pago.getAsString(TPago.URI_VOUCHER);

                    if (path != null) {
                        if (!path.equals("") && (new File(path)).exists()) {
                            view.gotoShowPDF(path, alquiler);
                        } else {
                            view.showDialog("No se encontro el archivo\n¿Desea Crearlo?");
                        }
                    }else{
                        view.showDialog("No se encontro el archivo\n¿Desea Crearlo?");
                    }
                }
            }
        }
    };

    public Presenter(Interfaz.view view, String idAlquiler) {
        super(view);
        this.idAlquiler = idAlquiler;
        tablaFueCreada = false;
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
                View v =creatRowPago(tcPagos.getS()[j][0], tcPagos.getS()[j][1], costo, tl);
                tl.addView(v);
            }
            view.addTable(tl);
        }
    }

    private View creatRowPago(String numPago, String fecha, String monto, ViewGroup vg){
        TableRowView v =(TableRowView) LayoutInflater.from(view.getContext()).inflate(R.layout.view_fila_of_pagos, vg, false);
        v.setText(numPago, fecha, monto);
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
        if (!tablaFueCreada) {
            crearTabla();
            tablaFueCreada = true;
        }
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

    @Override
    public void onPositive() {
        if (alquiler != null) {
            if (permisoPDF()){
                crearPDF();
            }else{
                view.solicitarPermiso();
            }
        }
    }
    public boolean permisoPDF(){
        int permissionCheck = ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    public void crearPDF(){
        PDF pdf = new PDF();
        String direccion = sp.getString(view.getContext().getString(R.string.direccion), "---");
        try {
            pdf.crearVoucher(alquiler.getAsString(TAlquiler.NUMERO_C), pago.getAsString(TPago.DNI_RESPONSABLE), viewClicked.getTextId(), viewClicked.getTextMonto(), direccion, viewClicked.getTextFecha());
            db.agregarVoucher(pdf.getPdfFile().getAbsolutePath(), viewClicked.getTextId());
            view.gotoShowPDF(pdf.getPdfFile().getAbsolutePath(), alquiler);
        } catch (FileNotFoundException e) {
            view.showMessage("error al crear el archivo");
            e.printStackTrace();
        } catch (DocumentException e) {
            view.showMessage("error al crear el documento");
            e.printStackTrace();
        }
    }
}
