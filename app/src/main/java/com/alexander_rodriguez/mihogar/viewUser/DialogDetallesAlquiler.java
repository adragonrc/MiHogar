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

import com.alexander_rodriguez.mihogar.DataBase.items.ItemRental;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.adapters.Models.ModelAlquilerView;

import org.jetbrains.annotations.NotNull;

public class DialogDetallesAlquiler extends AppCompatDialogFragment {
    public static final String NUM_USUARIOS = "nUsuarios";
    private LayoutInflater inflater;
    private View view;
    private Context mContext;
    private ItemRental datos;
    private Dialog dialog;

    private TextView tvIdAlquiler;
    private TextView tvNumUsuarios;
    private TextView tvNumCuarto;
    private TextView tvFechaInicio;
    private TextView tvFechaFin;
    private TextView tvMotivoDeSalida;

    private Button btVerCuarto;
    private Button btVerPago;

    public DialogDetallesAlquiler(Context context, ItemRental datos){
        super();
        this.datos = datos;
        this.mContext  = context;
        inflater = getLayoutInflater();
        view = inflater.inflate(R.layout.alquiler_description, null, false);
        iniciarViews();
    }


    public @NotNull Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        if (view != null)
            if  (view.getParent() != null)
                ((ViewGroup) view.getParent()).removeView(view);

        builder.setView(view);
        dialog = builder.create();
        return dialog;
    }

    private void iniciarViews(){
        tvIdAlquiler = view.findViewById(R.id.tvIdAlquiler);
        tvNumUsuarios = view.findViewById(R.id.tvTenantsNumber);
        tvNumCuarto = view.findViewById(R.id.tvNumCuarto);
        tvFechaInicio = view.findViewById(R.id.tvEntryDate);
        tvFechaFin = view.findViewById(R.id.tvDepartureDate);
        tvMotivoDeSalida = view.findViewById(R.id.tvReason);

        btVerCuarto = view.findViewById(R.id.btVerCuarto);
        btVerPago = view.findViewById(R.id.btVerPagos);


        tvIdAlquiler.setText(datos.getId());
        tvNumCuarto.setText(datos.getRoomNumber());
        tvNumUsuarios.setText(String.valueOf(datos.getTenantsNumber()));
        tvFechaInicio.setText(datos.getPaymentDateAsString());
        tvFechaFin.setText(datos.getDepartureDateAsString());
        tvMotivoDeSalida.setText(datos.getReasonExit());
    }

    public void setOnClickListenerVerCuarto(View.OnClickListener listener){
        btVerCuarto.setOnClickListener(listener);
    }

    public void setOnClickListenerVerPagos(View.OnClickListener listener){
        btVerPago.setOnClickListener(listener);
    }
}
