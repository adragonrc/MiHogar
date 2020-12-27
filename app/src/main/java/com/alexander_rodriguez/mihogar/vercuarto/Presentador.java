package com.alexander_rodriguez.mihogar.vercuarto;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;

import androidx.core.content.ContextCompat;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemRental;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemRoom;
import com.alexander_rodriguez.mihogar.DataBase.models.TMonthlyPayment;
import com.alexander_rodriguez.mihogar.DataBase.models.TPayment;
import com.alexander_rodriguez.mihogar.DataBase.models.TRental;
import com.alexander_rodriguez.mihogar.DataBase.models.TRoom;
import com.alexander_rodriguez.mihogar.MyAdminDate;
import com.alexander_rodriguez.mihogar.PDF;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.UTILIDADES.Mensualidad;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.UTILIDADES.TCuarto;
import com.alexander_rodriguez.mihogar.UTILIDADES.TPago;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.itextpdf.text.DocumentException;

import java.io.FileNotFoundException;
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
    //private ContentValues datosUsuario;
    //private ContentValues datosMensualidad;
    //private ContentValues datosAlquiler;
    private String dni;

    private MyAdminDate myDate;
    private String numeroCuarto;
    private Context mContext;

    public Presentador(Interface.view view, String numeroCuarto) {
        super(view);
        this.mContext = view.getContext();
        this.numeroCuarto = numeroCuarto;
        myDate = new MyAdminDate();
        myDate.setFormat(MyAdminDate.FORMAT_DATE_TIME);
    }

    @Override
    public void iniciarComandos(){
        mostrarDetalles();
    }

    @Override
    public void deshacerContrato(String motivo) {
        if(rental != null) {
            db.upDateAlquiler(mContext.getString(R.string.mdRentalDepartureDate), MyAdminDate.getFechaActual(), rental.getId());
            db.upDateAlquiler(mContext.getString(R.string.mdRentalReasonExit), motivo, rental.getId());
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
            room = (ItemRoom) documentSnapshot.toObject(TRoom.class);
            if(room != null) {
                room.setRoomNumber(documentSnapshot.getId());
                db.getRentalDR(room.getCurrentRentalId()).get()
                        .addOnSuccessListener(this::getAlquilerSuccess);
            }
        }
    }

    private void getAlquilerSuccess(DocumentSnapshot documentSnapshot) {

        if (documentSnapshot.exists()) {
            rental =(ItemRental) documentSnapshot.toObject(TRental.class);
            if(rental != null) {
                rental.setId(documentSnapshot.getId());
                rental.getCurrentMP().get().addOnSuccessListener(this::getRentalSuccess);
                return;
            }
        }
        view.showCuartolibre(room);
    }

    private void getRentalSuccess(DocumentSnapshot documentSnapshot) {

        int num = room.getNumberTenants();

        monthlyPayment = documentSnapshot.toObject(TMonthlyPayment.class);

        int pagosRealizados = rental.getPaymentsNumber();
        String fechaInicio = rental.getEntryDate();
        try {
            String paymentDate = MyAdminDate.adelantarPorMeses(fechaInicio, pagosRealizados);
            rental.setPaymentDate(paymentDate);
            if ((myDate.stringToDate(paymentDate)).before(new Date())) {
                view.noPago();
            } else {
                view.pago();
            }
        } catch (ParseException e) {
            e.printStackTrace();
            view.showMensaje("Error al actualizar la fecha de pago");
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
        DateFormat dateFormat = new SimpleDateFormat(MyAdminDate.FORMAT_DATE_TIME, Locale.getDefault());
        String fecha_i = dateFormat.format(new Date());
        monthlyPaymentAux = new TMonthlyPayment(mensualidad, fecha_i, rental.getId());
        db.agregarMensualidad(monthlyPaymentAux).addOnSuccessListener(this::addMonthlyPaymentSuccess);
    }

    private void addMonthlyPaymentSuccess(DocumentReference document) {
        monthlyPayment = monthlyPaymentAux;
        view.actualizarMensualidad(monthlyPayment.getAmount());
    }

    @Override
    public void actualizarDetalles(String detalles) {
        db.upDateCuarto(view.getContext().getString(R.string.mdRoomDescription), detalles, numeroCuarto);
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

        TPayment payment = new TPayment(fechaHoraActual,rental.getId(), room.getRoomNumber(), rental.getCurrentMP().getId(), monthlyPayment.getAmount());
        db.agregarPago(payment)
                .addOnSuccessListener(this::addPaymentSuccess)
                .addOnFailureListener(this::addPaymentFailure);
    }

    private void addPaymentFailure(Exception e) {
            view.showMensaje("Error al pagar");
    }

    private void addPaymentSuccess(DocumentReference documentReference) {

        int pagosRealizados = (rental.getPaymentsNumber() + 1);
        db.upDateAlquiler(mContext.getString(R.string.mdRentalPaymentsNumber), pagosRealizados, rental.getId())
                .addOnSuccessListener(aVoid->{
                    rental.setPaymentsNumber(pagosRealizados);
                    view.pago();
                });

        view.showMensaje("pago agregado");
        String entryDate = rental.getEntryDate();
        try {
            String nextPaymentDate = MyAdminDate.adelantarPorMeses(entryDate, pagosRealizados);
            rental.setPaymentDate(nextPaymentDate);
            view.actualizarFechaPago(nextPaymentDate);
        } catch (ParseException e) {
            e.printStackTrace();
            view.showMensaje("Error al actualizar la fecha de pago");
        }

        if(permisoPDF()){
            crearPDF();
        }else{
            view.solicitarPermiso();
        }
    }

    @Override
    public void actualizarNumTel(String numero) {
        db.upDateAlquiler(mContext.getString(R.string.mdRentalPhoneNumber), numero, rental.getId());
        rental.setPhoneNumber(numero);
        view.actualizarNumTel(numero);
    }

    @Override
    public void actualizarCorreo(String email) {
        db.upDateAlquiler(mContext.getString(R.string.mdRentalEmail), email, rental.getId());
        rental.setEmail(email);
        view.actualizarCorreo(email);
    }


    public void crearPDF(){
        Cursor pago;
        PDF pdf;

        String numeroDePago;
        String fecha;
        String costo;
        String direccion;
        String dni;

        pago = db.gePagosRealizados(Mensualidad.ID);

        if  (pago != null){
            if (pago.moveToLast()){
                numeroDePago = pago.getString(TPago.INT_ID);
                fecha = pago.getString(TPago.INT_FECHA);
                dni = pago.getString(TPago.INT_DNI);
                try {
                    pdf= new PDF();
                    costo = monthlyPayment.getAmount();
                    direccion = sp.getString(view.getContext().getString(R.string.direccion), "---");

                    pdf.crearVoucher(numeroCuarto, dni, numeroDePago, costo, direccion, fecha);
                    db.agregarVoucher(pdf.getPdfFile().getAbsolutePath(), pago.getString(TPago.INT_ID));
                    view.mostrarPDF(pdf.getPdfFile(), rental);

                } catch (FileNotFoundException ex) {
                    view.showMensaje("error al crear el archivo");
                    ex.printStackTrace();
                } catch (DocumentException ex) {
                    view.showMensaje("error al crear el documento");
                    ex.printStackTrace();
                }
            }
            pago.close();
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
    public void actualizarPhoto(String path) {
        db.upDateCuarto(TCuarto.URL, path, numeroCuarto);
    }


}
