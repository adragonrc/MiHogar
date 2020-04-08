package com.alexander_rodriguez.mihogar.viewUser;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;

public class DialogDetallesAlquiler extends AppCompatDialogFragment {
    public static final String NUM_USUARIOS = "nUsuarios";
    private LayoutInflater inflater;
    private View vista;
    private Context mContext;
    private ContentValues datos;
    private Dialog dialog;

    private TextView tvIdAlquiler;
    private TextView tvNumUsuarios;
    private TextView tvNumCuarto;
    private TextView tvFechaInicio;
    private TextView tvFechaFin;
    private TextView tvMotivoDeSalida;

    private Button btVerCuarto;
    private Button btVerPago;

    public DialogDetallesAlquiler(Context context, ContentValues datos){
        super();
        this.datos = datos;
        this.mContext  = context;
        inflater = getLayoutInflater();
        vista = inflater.inflate(R.layout.alquiler_description, null, false);
        iniciarViews();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        if (vista != null)
            if  (vista.getParent() != null)
                ((ViewGroup)vista.getParent()).removeView(vista);

        builder.setView(vista);
        dialog = builder.create();
        return dialog;
    }

    private void iniciarViews(){
        tvIdAlquiler = vista.findViewById(R.id.tvIdAlquiler);
        tvNumUsuarios = vista.findViewById(R.id.tvNumeroInquilinos);
        tvNumCuarto = vista.findViewById(R.id.tvNumCuarto);
        tvFechaInicio = vista.findViewById(R.id.tvFechaIngreso);
        tvFechaFin = vista.findViewById(R.id.tvFechaSalida);
        tvMotivoDeSalida = vista.findViewById(R.id.tvMotivoSalida);

        btVerCuarto = vista.findViewById(R.id.btVerCuarto);
        btVerPago = vista.findViewById(R.id.btVerPagos);

        tvIdAlquiler.setText(datos.getAsString(TAlquiler.ID));
        tvNumCuarto.setText(datos.getAsString(TAlquiler.NUMERO_C));
        tvNumUsuarios.setText(datos.getAsString(NUM_USUARIOS));
        tvFechaInicio.setText(datos.getAsString(TAlquiler.FECHA_INICIO));
        tvFechaFin.setText(datos.getAsString(TAlquiler.FECHA_SALIDA));
        tvMotivoDeSalida.setText(datos.getAsString(TAlquiler.MOTIVO));
    }

    public void setOnClickListenerVerCuarto(View.OnClickListener listener){
        btVerCuarto.setOnClickListener(listener);
    }

    public void setOnClickListenerVerPagos(View.OnClickListener listener){
        btVerPago.setOnClickListener(listener);
    }
}
