package com.alexander_rodriguez.mihogar.vercuarto;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;

import androidx.core.content.ContextCompat;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemMonthlyPayment;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemPayment;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemRental;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemRoom;
import com.alexander_rodriguez.mihogar.DataBase.models.TAdvance;
import com.alexander_rodriguez.mihogar.DataBase.models.TRoom;
import com.alexander_rodriguez.mihogar.AdminDate;
import com.alexander_rodriguez.mihogar.PDF;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.Save;
import com.alexander_rodriguez.mihogar.table_activity.TableRowView;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.itextpdf.text.DocumentException;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

public class Presentador extends BasePresenter<Interface.view> implements Interface.Presenter {

    private ItemRoom room;
    private ItemRental rental;
    private ItemMonthlyPayment monthlyPayment;
    private ItemMonthlyPayment monthlyPaymentAux;
    private ItemPayment auxPayment;
    private ItemPayment lastPayment;

    private PDF.Model model;

    private final AdminDate myDate;
    private final String numeroCuarto;
    private final Context mContext;


    public Presentador(Interface.view view, String numeroCuarto) {
        super(view);
        this.mContext = view.getContext();
        this.numeroCuarto = numeroCuarto;
        myDate = new AdminDate();
        myDate.setFormat(AdminDate.FORMAT_DATE_TIME);
    }

    @Override
    public void iniciarComandos(){
        mostrarDetalles();
    }

    @Override
    public void deshacerContrato(String motivo) {
        if(rental != null) {
            db.terminateContract(rental.getId(), motivo, numeroCuarto)
                    .addOnSuccessListener(this::terminateContractSuccess)
                    .addOnFailureListener(this::terminateContractFailure);
        }
    }

    private void terminateContractFailure(@NotNull Exception e) {
        view.showMessage("No se pudo anular el contrato");
        e.printStackTrace();
    }

    private void terminateContractSuccess(Void aVoid) {
        rental = null;
        room.setCurrentRentalId(null);
        mostrarDetalles();
    }

    @Override
    public void mostrarDetalles(){
        if(room == null) {
            view.setProgressBarVisibility(View.VISIBLE);
            db.getRoom(numeroCuarto).addOnSuccessListener(this::getRoomSucces);
        }
        else
            getRental();
    }

    private void getRoomSucces(DocumentSnapshot documentSnapshot) {
        if(documentSnapshot.exists()) {
            TRoom tRoom = documentSnapshot.toObject(TRoom.class);
            if(tRoom != null) {
                room = new ItemRoom(tRoom);
                room.setRoomNumber(documentSnapshot.getId());
                File f = Save.createFile(mContext, mContext.getString(R.string.cRoom), room.getRoomNumber());
                room.setPathImage(f.getAbsolutePath());

                if(room.getPathImageStorage() != null && !room.getPathImageStorage().isEmpty()){
                    db.downloadRoomPhoto(room.getRoomNumber(), f)
                            .addOnSuccessListener(this::downloadRoomPhotoSuccess)
                            .addOnFailureListener(this::downloadRoomPhotoFailure);
                }

                getRental();
            }
        }
    }

    private void getRental() {
        if(room.getCurrentRentalId() != null && !room.getCurrentRentalId().isEmpty()){
            if(rental == null){
                db.getRental(room.getCurrentRentalId())
                        .addOnSuccessListener(this::getRentalSuccess);
            }else{
                getMP();
            }
        }else{
            view.showCuartolibre(room);
        }
    }

    private void getMP() {
        DocumentReference mp = rental.getCurrentMP();
        if(mp != null) mp.get().addOnSuccessListener(this::getCurrentMPSuccess);
        else view.showMessage(mContext.getString(R.string.smpNotFound));
    }

    private void downloadRoomPhotoFailure(@NotNull Exception e) {
        e.printStackTrace();
    }

    private void downloadRoomPhotoSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
        view.reloadRoomPhoto();
    }

    private void getRentalSuccess(DocumentSnapshot documentSnapshot) {
        if (documentSnapshot.exists()) {
            rental = ItemRental.newInstance(documentSnapshot);
            getMP();
        }else
            view.showCuartolibre(room);
    }

    private void getCurrentMPSuccess(DocumentSnapshot documentSnapshot) {
        if(!documentSnapshot.exists()){
            view.showMessage(mContext.getString(R.string.smpNotFound));
            view.showCuartoAlquilado(room, rental.getTenantsNumber(), "00.00");
            return;
        }

        monthlyPayment = ItemMonthlyPayment.newInstance(documentSnapshot);

        if (monthlyPayment.getLastPaymentId() != null && !monthlyPayment.getLastPaymentId().isEmpty())
            db.getPayment(monthlyPayment.getLastPaymentId()).addOnSuccessListener(this::getLastPaymentSuccess);
        else {
            showUserPaymentStatus();
            view.showCuartoAlquilado(room, rental.getTenantsNumber(), String.valueOf(monthlyPayment.getAmount()));
        }
    }


    private void getLastPaymentSuccess(DocumentSnapshot documentSnapshot) {
        if (documentSnapshot.exists()){
            lastPayment = ItemPayment.getInstance(documentSnapshot);
        }
        showUserPaymentStatus();
        view.showCuartoAlquilado(room, rental.getTenantsNumber(), String.valueOf(monthlyPayment.getAmount()));
    }
    private void showStatus(boolean f){
        if (f){
            view.pago();
            view.hideAdvance();
        }else{
            view.noPago();
            if (0 < lastPayment.getAmount()) view.showAdvance(getRemainingPayment());
        }
    }

    private void showUserPaymentStatus(){
        if (lastPayment==null) lastPayment = new ItemPayment();
        int paymentsNumber = rental.getPaymentsNumber();
        Date entryDate = rental.getEntryDate().toDate();
        Date paymentDate = AdminDate.adelantarPorMeses(entryDate, paymentsNumber);

        rental.setPaymentDate(paymentDate);
        boolean itsOnTime = paymentDate.after(new Date());
        boolean fullPayment = lastPayment.getAmount() >= monthlyPayment.getAmount();
        showStatus(itsOnTime&&fullPayment);

    }

    @Override
    public ItemRental getDatosAlquiler() {
        return rental;
    }

    //private boolean consultarConfirmacion(String s){
    //    return true;
    //}
    private void setValCV(ContentValues cv, String  key, String newValue){
        cv.remove(key);
        cv.put(key, newValue);
    }


    @Override
    public void actualizarMensualidad(String sMonthlyPayment) {
        try {
            Double monthlyPayment = Double.parseDouble(sMonthlyPayment);
            monthlyPaymentAux = new ItemMonthlyPayment(monthlyPayment, new Timestamp(new Date()), rental.getId(), lastPayment.getId());
            db.agregarMensualidad(monthlyPaymentAux).addOnSuccessListener(this::addMonthlyPaymentSuccess);
        }catch (NumberFormatException e){
            e.printStackTrace();
            view.showMessage(mContext.getString(R.string.sMPError));
        }
    }

    private void addMonthlyPaymentSuccess(DocumentReference document) {
        monthlyPayment = monthlyPaymentAux;
        monthlyPayment.setId(document.getId());
        lastPayment.setMonthlyPaymentId(document.getId());
        db.updatePayment(lastPayment.getId(), mContext.getString(R.string.mdPaymentMonthlyPaymentId), lastPayment.getMonthlyPaymentId());
        view.actualizarMensualidad(String.valueOf(monthlyPayment.getAmount()));
    }

    @Override
    public void actualizarDetalles(String detalles) {
        db.updateRoom(view.getContext().getString(R.string.mdRoomDetails), detalles, numeroCuarto);
        room.setDetails(detalles);
        view.actualizarDetalles(detalles);
    }

    @Override
    public void addAdvance(Double amount) {
        double remaining =getRemainingPayment();
        if (amount >= remaining) {
            realizarPago();
        }
        else {
            if (monthlyPayment.getLastPaymentId() == null || monthlyPayment.getLastPaymentId().isEmpty() || getRemainingPayment() == 0) {
                auxPayment = new ItemPayment(Timestamp.now(), rental.getId(), room.getRoomNumber(), monthlyPayment.getId(), amount, rental.getMainTenant(), lastPayment.getId());
                db.addPayment(auxPayment.getRoot()).addOnSuccessListener(this::addPaymentSuccess).continueWith(
                        task -> {
                            if (task.isSuccessful()) {
                                if (task.getResult() != null) {
                                    TAdvance advance = new TAdvance(amount, Timestamp.now());
                                    db.addAdvanced(task.getResult().getId(), advance).addOnSuccessListener(doc -> {
                                        addAdvanceFinish(advance, doc.getId());
                                    });
                                }
                            }
                            return null;
                        });
            } else {
                TAdvance advance = new TAdvance(amount, Timestamp.now());
                db.addAdvanced(lastPayment.getId(), advance).addOnSuccessListener(doc -> {
                    updatePaymentAmount(advance.getAmount());
                    addAdvanceFinish(advance, doc.getId());
                });
            }
        }
    }

    private void updatePaymentAmount(double amount){
        Double sum = lastPayment.getAmount() + amount;
        db.updatePayment(lastPayment.getId(), mContext.getString(R.string.mdPaymentAmount), sum);
        lastPayment.setAmount(sum);
    }
    private void updatePaymentNumber(){
        int paymentsNumber = (rental.getPaymentsNumber() + 1);
        db.updateRental(mContext.getString(R.string.mdRentalPaymentsNumber), paymentsNumber, rental.getId())
                .addOnFailureListener(Throwable::printStackTrace);
        rental.setPaymentsNumber(paymentsNumber);
    }
    private void addAdvanceFinish(TAdvance advance, String id){
        String direccion = sp.getString(view.getContext().getString(R.string.direccion), "---");
        model = new PDF.Model(numeroCuarto, lastPayment.getDni(), id, String.valueOf(advance.getAmount()), direccion, AdminDate.dateToString(advance.getDate().toDate()));
        crearPDF();
    }

    @Override
    public void realizarPago() {
        String s_modo;
        int modo = 0;

        s_modo = sp.getString("list_tiempo", "0");
        if (s_modo!= null) modo = Integer.parseInt(s_modo);

        double remaining = getRemainingPayment();
        if (remaining == monthlyPayment.getAmount()){
            auxPayment = new ItemPayment(Timestamp.now(),rental.getId(), room.getRoomNumber(), rental.getCurrentMP().getId(), monthlyPayment.getAmount(), rental.getMainTenant(), null);
            db.addPayment(auxPayment.getRoot())
                    .addOnSuccessListener(this::addPaymentSuccess)
                    .addOnFailureListener(this::addPaymentFailure);
        }else{
            TAdvance advance = new TAdvance(remaining, Timestamp.now());
            db.addAdvanced(lastPayment.getId(), advance).addOnSuccessListener(doc -> {
                updatePaymentAmount(advance.getAmount());
                updatePaymentNumber();
                paymentFinished();
                addAdvanceFinish(advance, doc.getId());
                //view.addAvanceSuccess();
            });
        }
    }
    private void paymentFinished(){
        Date nextPaymentDate = AdminDate.adelantarPorMeses(rental.getEntryDate().toDate(), rental.getPaymentsNumber());
        rental.setPaymentDate(nextPaymentDate);
        view.pago();
        view.actualizarFechaPago(AdminDate.dateToString(nextPaymentDate));
    }

    private void addPaymentFailure(Exception e) {
            view.showMessage("Error al pagar");
    }

    private void addPaymentSuccess(DocumentReference documentReference) {
        lastPayment = auxPayment;
        lastPayment.setId(documentReference.getId());
        db.updateMonthlyPayment(monthlyPayment.getRentalId(), monthlyPayment.getId(), mContext.getString(R.string.mdmpLastPayment), lastPayment.getId())
                .addOnFailureListener(Throwable::printStackTrace);

        monthlyPayment.setLastPaymentId(documentReference.getId());

        view.showMessage("pago agregado");

        if (lastPayment.getAmount().equals(monthlyPayment.getAmount())) {
            int paymentsNumber = (rental.getPaymentsNumber() + 1);
            db.updateRental(mContext.getString(R.string.mdRentalPaymentsNumber), paymentsNumber, rental.getId())
                    .addOnFailureListener(Throwable::printStackTrace);
            rental.setPaymentsNumber(paymentsNumber);
            paymentFinished();
            createPdfModel(lastPayment.getAmount());
            crearPDF();
        }else {
            view.noPago();
        }

    }

    @Override
    public void actualizarNumTel(String numero) {
        db.updateRental(mContext.getString(R.string.mdRentalPhoneNumber), numero, rental.getId());
        rental.setPhoneNumber(numero);
        view.actualizarNumTel(numero);
    }

    @Override
    public void actualizarCorreo(String email) {
        db.updateRental(mContext.getString(R.string.mdRentalEmail), email, rental.getId());
        rental.setEmail(email);
        view.actualizarCorreo(email);
    }

    private void createPdfModel(double amount){
        String direccion;
        direccion = sp.getString(view.getContext().getString(R.string.direccion), "---");
        if (model == null)
            model = new PDF.Model(numeroCuarto, lastPayment.getDni(), lastPayment.getId(), String.valueOf(amount), direccion, AdminDate.dateToString(lastPayment.getDate().toDate()));
        else model.setAmount(String.valueOf(amount));
    }
    public void crearPDF(){
        if (permisoPDF()) {
            PDF pdf;

            if (lastPayment != null) {
                try {
                    pdf = new PDF();
                    pdf.crearVoucher(model);
                    db.agregarVoucher(pdf.getPdfFile().getAbsolutePath(), lastPayment.getId());
                    view.mostrarPDF(pdf.getPdfFile(), rental);
                } catch (FileNotFoundException ex) {
                    view.showMessage("error al crear el archivo");
                    ex.printStackTrace();
                } catch (DocumentException ex) {
                    view.showMessage("error al crear el documento");
                    ex.printStackTrace();
                }
            }
        }else {
            view.solicitarPermiso();
        }
    }

    @Override
    public String getResponsable() {
        return rental.getMainTenant();
    }

    private void addPayment(double amount){
    }

    public double getAmount(){
        return monthlyPayment == null ? 0: monthlyPayment.getAmount();
    }
    @Override
    public double getRemainingPayment() {
        double remaining = monthlyPayment.getAmount() - (lastPayment == null ? 0d: lastPayment.getAmount());
        return remaining < 0? 0: remaining;
    }

    @Override
    public void ocShowDetailsAdvance() {
        db.getAllAdvance(lastPayment.getId()).addOnSuccessListener(this::getAllAdvanceSuccess);
    }

    @Override
    public void refresh() {
        room = null;
        rental = null;
        monthlyPayment = null;
        monthlyPaymentAux = null;
        auxPayment = null;
        lastPayment = null;
        iniciarComandos();
    }


    private void getAllAdvanceSuccess(QuerySnapshot queryDocumentSnapshots) {
        TableLayout tl = (TableLayout) LayoutInflater.from(view.getContext()).inflate(R.layout.view_table_layout, null, false);
        for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
            TAdvance advance = doc.toObject(TAdvance.class);
            TableRowView v =(TableRowView) LayoutInflater.from(view.getContext()).inflate(R.layout.view_fila_of_pagos, tl, false);
            v.setText(doc.getId(), AdminDate.dateToString(advance.getDate().toDate()), String.valueOf(advance.getAmount()));
            tl.addView(v);
        }
        view.showAllAdvances(tl);
    }

    public boolean permisoPDF(){
        int permissionCheck = ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void updatePhoto(String path) {
        db.updateRoom(mContext.getString(R.string.mdRoomPathImage), path, numeroCuarto);
        db.saveRoomPhoto(numeroCuarto, path).addOnFailureListener(exception -> {
            // Handle unsuccessful uploads
            view.showMessage("Photo upload failed");
        }).addOnSuccessListener(taskSnapshot -> {
            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
            // ...
            String pathStorage;
            if(taskSnapshot.getMetadata() != null){
                pathStorage = taskSnapshot.getMetadata().getPath();
            }else{
                pathStorage = db.getRoomPhotoStoregeAsString(numeroCuarto);
            }
            db.updateRoom(mContext.getString(R.string.mdRoomPathImageStorage), pathStorage, numeroCuarto);
        });;
    }

    @Override
    public ItemRoom getRoom() {
        return room;
    }
}
