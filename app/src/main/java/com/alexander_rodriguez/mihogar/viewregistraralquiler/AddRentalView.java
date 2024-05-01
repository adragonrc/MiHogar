package com.alexander_rodriguez.mihogar.viewregistraralquiler;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import com.alexander_rodriguez.mihogar.AdminDate;
import com.alexander_rodriguez.mihogar.view_buttons_ac.ButtonsAC;
import com.alexander_rodriguez.mihogar.R;
import com.google.firebase.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddRentalView extends ScrollView {

    private EditText etPrecio;
    private EditText etCorreo;
    private EditText etNumeroTelef;

    private Spinner spNumCuarto;
    private Spinner spDia;
    private Spinner spMes;
    private Spinner spAnio;
    private Spinner spPlazo;

    private RadioGroup radioGroup;

    private ButtonsAC buttonsAC;

    private AddRentalInterface parent;

    public AddRentalView(Context context) {
        super(context);
    }

    public AddRentalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AddRentalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AddRentalView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        iniciarViews();
    }

    public void setParent(AddRentalInterface parent) {
        this.parent = parent;
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
        buttonsAC = findViewById(R.id.llBtns);
        makeNotFocusable(etCorreo, etNumeroTelef, etPrecio);
        buttonsAC.setListener(new ButtonsAC.Listener() {
            @Override
            public void ocPositive(View view) {
                parent.saveRental(getData());
            }

            @Override
            public void ocNegative(View view) {
                parent.cancelAddRental();
            }
        });
    }
    private void makeFocusable(View...view){
        for (View v: view){
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
        }
    }
    private void makeNotFocusable(View...view){
        for (View v: view){
            v.setFocusable(false);
        }
    }

    public ModelAA getData() {

        String fecha =  AdminDate.buidFecha(spAnio.getSelectedItem().toString(), spMes.getSelectedItem().toString(), spDia.getSelectedItem().toString());
        boolean pago = radioGroup.getCheckedRadioButtonId() == R.id.rbCancelo;
        int pagosRealizados ;
        if  (pago) pagosRealizados  = 1;
        else pagosRealizados = 0;
        Timestamp timestamp = null;
        try {
            timestamp = new Timestamp((new SimpleDateFormat(AdminDate.FORMAT_DATE, Locale.getDefault())).parse(fecha));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new ModelAA(
                timestamp,
                spNumCuarto.getSelectedItem().toString(),
                pagosRealizados,
                etCorreo.getText().toString(),
                etNumeroTelef.getText().toString(),
                etPrecio.getText().toString()
        );
    }

    public void onExpanded(){
        makeFocusable(etCorreo, etNumeroTelef, etPrecio);
        etNumeroTelef.requestFocus();
    }

    public void onCollapsed(){
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
