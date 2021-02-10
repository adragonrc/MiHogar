package com.alexander_rodriguez.mihogar.viewUser;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.UTILIDADES.Mensualidad;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.UTILIDADES.TCuarto;

import org.jetbrains.annotations.NotNull;

public class DialogConfirmPago extends AppCompatDialogFragment {
    public static final String ARG_ROOM_NUMBER = "roomNumber";
    public static final String ARG_PAYMENT_DATA = "paymentData";
    public static final String ARG_AMOUNT = "amount";

    private TextView tvDni;
    private TextView tvCuarto;
    private TextView tvFechaDePago;
    private TextView tvMonto;

    private Button aceptar;
    private Button cancelar;

    private View vista;

    private AlertDialog dialog;
    private Bundle datos;
    private LayoutInflater inflater;

    private Context context;

    public DialogConfirmPago(){

    }

    public DialogConfirmPago(Context context){
        this.context  = context;
        inflater = LayoutInflater.from(context);
        vista = inflater.inflate(R.layout.dialog_agregar_pago, null);
        iniciarViews();

    }

    @Override
    public @NotNull Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (vista != null)
            if  (vista.getParent() != null)
                ((ViewGroup)vista.getParent()).removeView(vista);

        builder.setView(vista);
        dialog = builder.create();
        this.datos = getArguments();
        if (datos!= null) {
            tvCuarto.setText(datos.getString(ARG_ROOM_NUMBER));
            tvFechaDePago.setText(datos.getString(ARG_PAYMENT_DATA));
            tvMonto.setText(datos.getString(ARG_AMOUNT));
        }
        return dialog;
    }


    public void setOnClickListenerAceptar(View.OnClickListener listenerAceptar){
        aceptar.setOnClickListener(listenerAceptar);
    }

    public void setOnClickListenerCancelar(View.OnClickListener listenerCancelar){
        cancelar.setOnClickListener(listenerCancelar);
    }

    private void iniciarViews() {
        tvDni = vista.findViewById(R.id.tvDni);
        tvCuarto = vista.findViewById(R.id.tvNumCuarto);
        tvFechaDePago = vista.findViewById(R.id.tvFechaDePago);
        tvMonto = vista.findViewById(R.id.tvPago);
        aceptar = vista.findViewById(R.id.btAceptar);
        cancelar = vista.findViewById(R.id.btCancelar);

    }
}


/*
    @Override
    public void modoEdicion(){
        tvNombres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dip.setImputType(EditorInfo.TYPE_TEXT_FLAG_CAP_SENTENCES);
                myDialog.showDiaglog(getSupportFragmentManager(),TUsuario.NOMBRES, dip);
            }
        });
        tvMonto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dip.setImputType(EditorInfo.TYPE_NUMBER_FLAG_DECIMAL);
                myDialog.showDiaglog(getSupportFragmentManager(), Mensualidad.COSTO, dip);
            }
        });
        tvCuarto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dip.setImputType(EditorInfo.TYPE_CLASS_NUMBER);
                myDialog.showDiaglog(getSupportFragmentManager(), TCuarto.NUMERO, dip);
            }
        });

    @Override
    public void positive(String value, String in) {
        switch (in){
            case TUsuario.DNI:{
            //    db.upDateUsuario(TUsuario.DNI,value,datosUser.getAsInteger(TUsuario.DNI));
                break;
            }
            case TUsuario.NOMBRES:{
                db.upDateUsuario(TUsuario.NOMBRES,value,datosUser.getAsInteger(TUsuario.DNI));
                actualizarDatos();
                iniciarComandos();
                break;
            }
            case TUsuario.APELLIDO_PAT:{
                db.upDateUsuario(TUsuario.APELLIDO_PAT,value,datosUser.getAsInteger(TUsuario.DNI));
                actualizarDatos();
                iniciarComandos();
                break;
            }
            case TUsuario.APELLIDO_MAT:{
                db.upDateUsuario(TUsuario.APELLIDO_MAT,value,datosUser.getAsInteger(TUsuario.DNI));
                actualizarDatos();
                iniciarComandos();
                break;
            }
            case Mensualidad.COSTO:{
                try{
                    Double d  = Double.parseDouble(value);
                    db.agregarMensualidad(d,adminDate.getDateFormat().format(new Date()),datosAlquiler.getAsLong(TAlquiler.ID));
                }catch (IOError error){
                    view.showMensaje(value+ ": no es double");
                }
                break;
            }
            default:
                view.showMensaje("no hay caso para :"+ in);
        }
    }
    }*/

