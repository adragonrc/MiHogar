package com.alexander_rodriguez.mihogar.tableActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.alexander_rodriguez.mihogar.AdminDate;
import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemPayment;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemRental;
import com.alexander_rodriguez.mihogar.DataBase.models.TMonthlyPayment;
import com.alexander_rodriguez.mihogar.DataBase.parse.ParceRental;
import com.alexander_rodriguez.mihogar.PDF;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.itextpdf.text.DocumentException;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;

public class Presenter extends BasePresenter<Interfaz.view> implements Interfaz.Presenter{
    private ParceRental rental;

    private  String idAlquiler;
    private boolean tablaFueCreada;
    private TableRowView viewClicked;
    private ItemPayment paymentAux;

    private final Context mContext;

    public Presenter(Interfaz.view view, Intent intent) {
        super(view);
        mContext = view.getContext();

/*
        this.idAlquiler = intent.getStringExtra(TableActivity.RENTAL_ID);
        this.email = intent.getStringExtra(TableActivity.EMAIL);
        this.phoneNumber = intent.getStringExtra(TableActivity.PHONE_NUM);
        this.existMD = intent.getBooleanExtra(TableActivity.SEND_MT, false);
*/
        rental = (ParceRental) intent.getParcelableExtra("parce");
        if(rental == null){
            this.idAlquiler = intent.getStringExtra(TableActivity.RENTAL_ID);
        }
        tablaFueCreada = false;
        Intent i;
    }

    public void crearTabla(){
        db.getMonthlyPaymentCR(idAlquiler).get()
                .addOnSuccessListener(this::getMonthlyPaymentsSuccess)
                .addOnFailureListener(this::getPaymentsFailure);
    }

    private void getMonthlyPaymentsSuccess(QuerySnapshot queryDocumentSnapshots) {
        ViewGroup vg = view.getGrup();
        for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
            TMonthlyPayment payment = doc.toObject(TMonthlyPayment.class);
            TableLayout tl = (TableLayout) LayoutInflater.from(view.getContext()).inflate(R.layout.view_table_layout, vg, false);
            String idMensualidad = doc.getId();
            String costo = String.valueOf(payment.getAmount());

            String date = AdminDate.dateToString(payment.getDateInit().toDate());
            tl.addView(crateTitleMensualidad("Mensualidad: "+ idMensualidad, date, tl));
            GetPaymentAdmin getPayment = new GetPaymentAdmin(tl, costo);
            db.getPayments(mContext.getString(R.string.mdPaymentMonthlyPaymentId), idMensualidad).addOnSuccessListener(getPayment).addOnFailureListener(this::getPaymentsFailure);
        }
    }

    private void getPaymentsFailure(Exception e) {
        e.printStackTrace();
    }

    private class GetPaymentAdmin implements OnSuccessListener<QuerySnapshot> {
        TableLayout root;
        String amout;

        public GetPaymentAdmin(TableLayout root, String amout) {
            this.root = root;
            this.amout = amout;
        }

        @Override
        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
            for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
                ItemPayment itemPayment = ItemPayment.getInstance(doc);
                View v =creatRowPago(itemPayment, root);
                root.addView(v);
            }
            view.addTable(root);
        }
    }

    private View creatRowPago(ItemPayment payment, ViewGroup vg){
        TableRowView v =(TableRowView) LayoutInflater.from(view.getContext()).inflate(R.layout.view_fila_of_pagos, vg, false);
        v.setText(payment.getId(), AdminDate.dateToString(payment.getDate().toDate()), String.valueOf(payment.getAmount()));
        v.setOnClickListener(new PaymentListener(payment));
        return v;
    }

    private class PaymentListener implements View.OnClickListener{
        ItemPayment payment;

        public PaymentListener(ItemPayment payment) {
            this.payment = payment;
        }

        @Override
        public void onClick(@NotNull View v) {
            viewClicked = (TableRowView) v;
            TextView tv = v.findViewById(R.id.tvId);
            if  (tv != null){
                if (payment != null){
                    File pdf = new File(PDF.getFolder(), PDF.parseName(payment.getId()));
                    if (pdf.exists()) {
                            view.gotoShowPDF(pdf.getAbsolutePath(), rental);
                    }else{
                        paymentAux = payment;
                        view.showDialog("No se encontro el archivo\n¿Desea Crearlo?");
                    }
                }
            }
        }
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
        if (rental != null) {
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
        PDF.Model pdfModel = new PDF.Model(rental.getRoomNumber(), paymentAux.getDni(), viewClicked.getTextId(), viewClicked.getTextMonto(), direccion, viewClicked.getTextFecha());
        try {
            pdf.crearVoucher(pdfModel);
            db.agregarVoucher(pdf.getPdfFile().getAbsolutePath(), viewClicked.getTextId());
            view.gotoShowPDF(pdf.getPdfFile().getAbsolutePath(), rental);
        } catch (FileNotFoundException e) {
            view.showMessage("error al crear el archivo");
            e.printStackTrace();
        } catch (DocumentException e) {
            view.showMessage("error al crear el documento");
            e.printStackTrace();
        }
    }
}
