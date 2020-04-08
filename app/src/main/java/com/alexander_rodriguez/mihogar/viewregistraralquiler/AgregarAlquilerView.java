package com.alexander_rodriguez.mihogar.viewregistraralquiler;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import com.alexander_rodriguez.mihogar.MyAdminDate;
import com.alexander_rodriguez.mihogar.R;

import java.util.Date;

public class AgregarAlquilerView extends ScrollView {

    private EditText etPrecio;
    private EditText etCorreo;
    private EditText etNumeroTelef;

    private Spinner spNumCuarto;
    private Spinner spDia;
    private Spinner spMes;
    private Spinner spAnio;
    private Spinner spPlazo;

    private RadioGroup radioGroup;

    private Button positive;

    private Button negative;
    public AgregarAlquilerView(Context context) {
        super(context);
    }

    public AgregarAlquilerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AgregarAlquilerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AgregarAlquilerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        iniciarViews();
    }

    protected void iniciarViews() {

        etPrecio = findViewById(R.id.etPrecio);
        etNumeroTelef = findViewById(R.id.etNumeroTelefono);
        etCorreo = findViewById(R.id.etCorreo);

        spNumCuarto = findViewById(R.id.spNumCuartos);
        spDia = findViewById(R.id.spDia);
        spMes= findViewById(R.id.spMes);
        spAnio= findViewById(R.id.spAnio);
        spPlazo = findViewById(R.id.spPlazo);

        radioGroup = findViewById(R.id.radioGrup);

        positive = findViewById(R.id.positiveButton);
        negative = findViewById(R.id.negativeButton);
    }

    public ModelAA getList()  {

        String fecha =  MyAdminDate.buidFecha(spAnio.getSelectedItem().toString(), spMes.getSelectedItem().toString(), spDia.getSelectedItem().toString());
        boolean pago = radioGroup.getCheckedRadioButtonId() == R.id.rbCancelo;
        int pagosRealizados ;
        if  (pago) pagosRealizados  = 1;
        else pagosRealizados = 0;
        ModelAA model = new ModelAA(
                etPrecio.getText().toString(),
                etCorreo.getText().toString(),
                etNumeroTelef.getText().toString(),
                spNumCuarto.getSelectedItem().toString(),
                fecha,
                pagosRealizados,
                "0"
        );

        return model;
    }

    public void onExpanded(){
        etNumeroTelef.requestFocus();
    }

    public void onCollapsed(){
    }

    public Button getPositive() {
        return positive;
    }

    public Button getNegative() {
        return negative;
    }

    public void prepararSpinenr(ArrayAdapter<String> adapterCuartos, String cuartoSelected) {
        Date date = new Date();
        int posYear = date.getYear()-119;
        int posMes = date.getMonth();
        int posDia = date.getDate()-1;
        spDia.setSelection(posDia);
        spMes.setSelection(posMes);
        spAnio.setSelection(posYear);
        spNumCuarto.setAdapter(adapterCuartos);
        int pos = 0;
        if (cuartoSelected == null ) pos =  adapterCuartos.getPosition(cuartoSelected);
        if(pos == -1) pos = 0;
        spNumCuarto.setSelection(pos);
    }
}
