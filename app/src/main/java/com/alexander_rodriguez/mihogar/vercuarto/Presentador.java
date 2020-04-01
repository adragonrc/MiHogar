package com.alexander_rodriguez.mihogar.vercuarto;

import android.content.ContentValues;
import android.database.Cursor;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.MyAdminDate;
import com.alexander_rodriguez.mihogar.PDF;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.UTILIDADES.Mensualidad;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.UTILIDADES.TCuarto;
import com.alexander_rodriguez.mihogar.UTILIDADES.TPago;
import com.alexander_rodriguez.mihogar.UTILIDADES.TUsuario;
import com.itextpdf.text.DocumentException;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Presentador extends BasePresenter<Interface.view> implements Interface.Presenter {

    private ContentValues datosCuarto;
    private ContentValues datosUsuario;
    private ContentValues datosMensualidad;
    private ContentValues datosAlquiler;

    private MyAdminDate adminDate;
    private String numeroCuarto;

    public Presentador(Interface.view view, String numeroCuarto) {
        super(view);
        this.numeroCuarto = numeroCuarto;
        adminDate = new MyAdminDate();
        adminDate.setFormat(MyAdminDate.FORMAT_DATE_TIME);
    }

    @Override
    public void iniciarComandos(){
        mostrarDetalles();
    }

    @Override
    public void deshacerContrato(String motivo) {
        int id = datosAlquiler.getAsInteger(TAlquiler.ID);
        db.upDateAlquiler(TAlquiler.VAL, "0", id);
        db.upDateAlquiler(TAlquiler.MOTIVO, motivo, id);
        db.upDateAlquiler(TAlquiler.FECHA_C, adminDate.getFechaActual(), id);
        mostrarDetalles();
    }

    private void actualizarDatos(){
        datosAlquiler = db.getFilaAlquilerByCuartoOf("*", numeroCuarto);
        view.pago();
    }

    @Override
    public void mostrarDetalles(){
        datosCuarto = db.getFilaInCuarto("*", numeroCuarto);
        datosAlquiler = db.getFilaAlquilerByCuartoOf("*", numeroCuarto);
        if (datosAlquiler.size()!=0){
            datosMensualidad = db.getFilaInMensualidadActual("*",datosAlquiler.get(TAlquiler.ID));
            datosUsuario = db.getFilaInUsuariosOf("*",datosAlquiler.get(TAlquiler.DNI));
            if ((adminDate.stringToDate(datosAlquiler.getAsString(TAlquiler.FECHA_C))).before(new Date())) {
                view.noPago();
            }else {
                view.pago();
            }
            view.showCuartoAlquilado(datosCuarto, datosUsuario, datosMensualidad.getAsString(Mensualidad.COSTO));
        }else{
            view.showCuartolibre(datosCuarto);
        }
    }

    @Override
    public ContentValues getDatosAlquiler() {
        return datosAlquiler;
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
        DateFormat dateFormat = new SimpleDateFormat(MyAdminDate.FORMAT_DATE_TIME);
        String fecha_i = dateFormat.format(new Date());
        int id = datosAlquiler.getAsInteger(TAlquiler.ID);
        if(db.agregarMensualidad(Double.parseDouble(mensualidad), fecha_i, id)){
            datosMensualidad = db.getFilaInMensualidadActual("*",datosAlquiler.get(TAlquiler.ID));
            view.actualizarMensualidad(mensualidad);
        }
    }

    @Override
    public void actualizarDetalles(String detalles) {
        db.upDateCuarto(TCuarto.DETALLES, detalles, numeroCuarto);
        setValCV(datosCuarto, TCuarto.DETALLES, detalles);
        view.actualizarDetalles(detalles);
    }

    @Override
    public void realizarPago() {
        String s = adminDate.getFecha(datosAlquiler.getAsString(TAlquiler.FECHA_C));
        String numeroCuarto = datosCuarto.getAsString(TCuarto.NUMERO);
        String fecha;
        String numeroDePago;
        String costo = datosMensualidad.getAsString(Mensualidad.COSTO);
        String direccion = sp.getString(view.getContext().getString(R.string.direccion), "---");
        Cursor pago;

        if(db.agregarPago(adminDate.getDateFormat().format(new Date()),datosMensualidad.getAsLong(Mensualidad.ID), datosUsuario.getAsLong(TUsuario.DNI))){
            view.showMensaje("pago agregado");
            db.upDateAlquiler(TAlquiler.FECHA_C,s,datosAlquiler.getAsInteger(TAlquiler.ID));
            actualizarDatos();

            pago = db.getUltimoPago(Mensualidad.ID);
            PDF pdf = new PDF();

            if  (pago != null){
                if (pago.moveToLast()){
                    numeroDePago = pago.getString(TPago.INT_ID);
                    fecha = pago.getString(TPago.INT_FECHA);
                    try {
                        pdf.crearVoucher(numeroCuarto, numeroDePago, costo, direccion, fecha);
                        db.agregarVoucher(pdf.getPdfFile().getAbsolutePath(), pago.getString(TPago.INT_ID));
                        view.mostrarPDF(pdf.getPdfFile(), datosUsuario);
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
        }else{
            view.showMensaje("Error al pagar");
        }

    }


}
