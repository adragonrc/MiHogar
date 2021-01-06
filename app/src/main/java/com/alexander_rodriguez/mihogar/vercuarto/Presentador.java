package com.alexander_rodriguez.mihogar.vercuarto;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;

import androidx.core.content.ContextCompat;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemPayment;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemRental;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemRoom;
import com.alexander_rodriguez.mihogar.DataBase.models.TMonthlyPayment;
import com.alexander_rodriguez.mihogar.DataBase.models.TRoom;
import com.alexander_rodriguez.mihogar.AdminDate;
import com.alexander_rodriguez.mihogar.PDF;
import com.alexander_rodriguez.mihogar.R;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.itextpdf.text.DocumentException;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Presentador extends BasePresenter<Interface.view> implements Interface.Presenter {

    private ItemRoom room;
    private ItemRental rental;
    private TMonthlyPayment monthlyPayment;
    private TMonthlyPayment monthlyPaymentAux;
    private ItemPayment payment;

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
            db.updateRental(mContext.getString(R.string.mdRentalDepartureDate), AdminDate.getFechaActual(), rental.getId());
            db.updateRental(mContext.getString(R.string.mdRentalReasonExit), motivo, rental.getId());
            db.updateRoom(mContext.getString(R.string.mdRoomCurrentRentalId), null, numeroCuarto);
            mostrarDetalles();
            rental = null;
        }
    }

    private void actualizarDatos(){
        db.getRentalDR(room.getCurrentRentalId()).get();
        view.pago();
    }

    @Override
    public void mostrarDetalles(){
        db.getRoom(numeroCuarto).addOnSuccessListener(this::getRoomSucces);
    }

    private void getRoomSucces(DocumentSnapshot documentSnapshot) {
        if(documentSnapshot.exists()) {
            TRoom tRoom = documentSnapshot.toObject(TRoom.class);
            if(tRoom != null) {
                room = new ItemRoom(tRoom);
                room.setRoomNumber(documentSnapshot.getId());
                if(room.getPathImage() != null && !room.getPathImage().isEmpty()) {
                    File f = new File(room.getPathImage());
                    if (!f.exists()) {
                        File parent = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                        if (parent != null){
                            if (!parent.exists()) {
                                parent.mkdirs();
                            }
                            parent = new File(parent, mContext.getString(R.string.cRoom));
                            if (!parent.exists()) {
                                parent.mkdirs();
                            }
                        }
                    }
                    db.downloadRoomPhoto(room.getRoomNumber(), f)
                            .addOnSuccessListener(this::downloadRoomPhotoSuccess)
                            .addOnFailureListener(this::downloadRoomPhotoFailure);
                }
                if(room.getCurrentRentalId() != null && !room.getCurrentRentalId().isEmpty()){
                    db.getRental(room.getCurrentRentalId())
                            .addOnSuccessListener(this::getAlquilerSuccess);
                }else{
                    view.showCuartolibre(room);
                }
            }
        }
    }

    private void downloadRoomPhotoFailure(@NotNull Exception e) {
        e.printStackTrace();
    }

    private void downloadRoomPhotoSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
        view.reloadRoomPhoto();
    }

    private void getAlquilerSuccess(DocumentSnapshot documentSnapshot) {
        if (documentSnapshot.exists()) {
            rental = ItemRental.newInstance(documentSnapshot);
            if(rental != null) {
                rental.setId(documentSnapshot.getId());
                DocumentReference mp = rental.getCurrentMP();
                if(mp != null) mp.get().addOnSuccessListener(this::getCurrentMPSuccess);
                else view.showMessage("Current Monthly Payment wasn't found");
                return;
            }
        }
        view.showCuartolibre(room);
    }

    private void getCurrentMPSuccess(DocumentSnapshot documentSnapshot) {
        if(!documentSnapshot.exists()){
            view.showMessage("Current Monthly Payment wasn't found");
            return;
        }
        int num = room.getTenantsNumber();

        monthlyPayment = documentSnapshot.toObject(TMonthlyPayment.class);

        int pagosRealizados = rental.getPaymentsNumber();
        Timestamp fechaInicio = rental.getEntryDate();
        try {
            String paymentDate = AdminDate.adelantarPorMeses(fechaInicio.toDate(), pagosRealizados);
            rental.setPaymentDate(paymentDate);
            if ((myDate.stringToDate(paymentDate)).before(new Date())) {
                view.noPago();
            } else {
                view.pago();
            }
        } catch (ParseException e) {
            e.printStackTrace();
            view.showMessage("Error al actualizar la fecha de pago");
        }

        view.showCuartoAlquilado(room, num, monthlyPayment.getAmount());
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
    public void actualizarMensualidad(String mensualidad) {
        DateFormat dateFormat = new SimpleDateFormat(AdminDate.FORMAT_DATE_TIME, Locale.getDefault());

        monthlyPaymentAux = new TMonthlyPayment(mensualidad, new Timestamp(new Date()), rental.getId());
        db.agregarMensualidad(monthlyPaymentAux).addOnSuccessListener(this::addMonthlyPaymentSuccess);
    }

    private void addMonthlyPaymentSuccess(DocumentReference document) {
        monthlyPayment = monthlyPaymentAux;
        view.actualizarMensualidad(monthlyPayment.getAmount());
    }

    @Override
    public void actualizarDetalles(String detalles) {
        db.updateRoom(view.getContext().getString(R.string.mdRoomDescription), detalles, numeroCuarto);
        room.setDetails(detalles);
        view.actualizarDetalles(detalles);
    }

    @Override
    public void realizarPago() {
        String s_modo;
        int modo = 0;

        s_modo = sp.getString("list_tiempo", "0");
        if (s_modo!= null) modo = Integer.parseInt(s_modo);

        //fechaNueva = myDate.adelantarUnMes(datosAlquiler.getAsString(TAlquiler.EXTRA_FECHA_PAGO), modo);
        String fechaHoraActual = myDate.getDateFormat().format(new Date());

        payment = new ItemPayment(Timestamp.now(),rental.getId(), room.getRoomNumber(), rental.getCurrentMP().getId(), monthlyPayment.getAmount(), rental.getMainTenant());
        db.addPayment(payment.getRoot())
                .addOnSuccessListener(this::addPaymentSuccess)
                .addOnFailureListener(this::addPaymentFailure);
    }

    private void addPaymentFailure(Exception e) {
            view.showMessage("Error al pagar");
    }

    private void addPaymentSuccess(DocumentReference documentReference) {
        payment.setId(documentReference.getId());
        int pagosRealizados = (rental.getPaymentsNumber() + 1);
        db.updateRental(mContext.getString(R.string.mdRentalPaymentsNumber), pagosRealizados, rental.getId())
                .addOnSuccessListener(aVoid->{
                    rental.setPaymentsNumber(pagosRealizados);
                    view.pago();
                });

        view.showMessage("pago agregado");

        try {
            String nextPaymentDate = AdminDate.adelantarPorMeses(rental.getEntryDate().toDate(), pagosRealizados);
            rental.setPaymentDate(nextPaymentDate);
            view.actualizarFechaPago(nextPaymentDate);
        } catch (ParseException e) {
            e.printStackTrace();
            view.showMessage("Error al actualizar la fecha de pago");
        }

        if(permisoPDF()){
            crearPDF();
        }else{
            view.solicitarPermiso();
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


    public void crearPDF(){
        PDF pdf;
        String direccion;

        if  (payment != null){
                try {
                    pdf= new PDF();
                    direccion = sp.getString(view.getContext().getString(R.string.direccion), "---");

                    pdf.crearVoucher(numeroCuarto, payment.getDni(), payment.getId(), payment.getAmount(), direccion, AdminDate.dateToString(payment.getDate().toDate()));
                    db.agregarVoucher(pdf.getPdfFile().getAbsolutePath(), payment.getId());
                    view.mostrarPDF(pdf.getPdfFile(), rental);
                } catch (FileNotFoundException ex) {
                    view.showMessage("error al crear el archivo");
                    ex.printStackTrace();
                } catch (DocumentException ex) {
                    view.showMessage("error al crear el documento");
                    ex.printStackTrace();
                }
        }
    }

    @Override
    public String getResponsable() {
        return rental.getMainTenant();
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


}
