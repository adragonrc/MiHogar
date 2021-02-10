package com.alexander_rodriguez.mihogar.vercuarto.view_perfil_cuarto;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.alexander_rodriguez.mihogar.DataBase.items.ItemRental;
import com.alexander_rodriguez.mihogar.R;

public class PerfilCuarto extends ScrollView{
    private TextView tvNumeroInquilinos;
    private TextView tvMensualidad;
    private TextView tvAdvanceTotal;
    private TextView tvDetalles;
    private TextView tvFechaI;
    private TextView tvFechaC;
    private TextView tvNumeroTel;
    private TextView tvCorreo;

    private EditText etMensualidad;
    private EditText etDetalles;
    private EditText etNumeroTel;
    private EditText etCorreo;

    private ImageView ivAlert;

    private CardView cvDetallesAlquiler;
    private CardView cvMPDetails;
    private CardView cvMessage;

    private LinearLayout llPagoMensual;
    private LinearLayout llAdvance;
    private LinearLayout llEditorMensualidad;
    private LinearLayout llShowMensualidad;
    private LinearLayout llEditorDetalles;
    private LinearLayout llShowDetalles;
    private LinearLayout llEditarNumTel;
    private LinearLayout llEditarCorreo;


    private LinearLayout llConfirNumTel;
    private LinearLayout llConfirCorreo;

    public PerfilCuarto(Context context) {
        super(context);
    }

    public PerfilCuarto(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PerfilCuarto(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PerfilCuarto(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        iniciarViews();
    }

    public void showCuartoAlquilado(int cantidadDeUsuarios, String mensualidad, ItemRental rental) {
        //String nombes = usuario.getAsString(TUsuario.NOMBRES) + ", " + usuario.getAsString(TUsuario.APELLIDO_PAT) + " " + usuario.getAsString(TUsuario.APELLIDO_MAT) + ".";

        tvNumeroInquilinos.setText(String.valueOf(cantidadDeUsuarios));
        tvMensualidad.setText(mensualidad);
        tvFechaC.setText(rental.getPaymentDateAsString());
        tvFechaI.setText(rental.getEntryDateAsString());
        tvNumeroTel.setText(rental.getPhoneNumber());
        tvCorreo.setText(rental.getEmail());

        cvMessage.setVisibility(GONE);
        cvDetallesAlquiler.setVisibility(VISIBLE);
        cvMPDetails.setVisibility(VISIBLE);
    }

    public void showCuartolibre() {
        cvDetallesAlquiler.setVisibility(GONE);
        cvMPDetails.setVisibility(GONE);
        cvMessage.setVisibility(VISIBLE);
    }

    public void modoEditaMensualidad(){
        llEditorMensualidad.setVisibility(View.VISIBLE);
        llShowMensualidad.setVisibility(View.GONE);
        etMensualidad.setText(tvMensualidad.getText().toString());
        etMensualidad.requestFocus();
    }

    public void modoEditarDetalles() {
        llEditorDetalles.setVisibility(View.VISIBLE);
        llShowDetalles.setVisibility(View.GONE);
        etDetalles.setText(tvDetalles.getText().toString());
        etDetalles.requestFocus();
    }

    public void actualizarMensualidad(String mensualidad) {
        llEditorMensualidad.setVisibility(View.GONE);
        llShowMensualidad.setVisibility(View.VISIBLE);
        tvMensualidad.setText(mensualidad);
    }

    public void actualizarDetalles(String detalles) {
        llEditorDetalles.setVisibility(View.GONE);
        llShowDetalles.setVisibility(View.VISIBLE);

        tvDetalles.setText(detalles);
    }

    public void modEditarNumTel() {
        etNumeroTel.setText(tvNumeroTel.getText().toString());
        llEditarNumTel.setVisibility(View.GONE);
        llConfirNumTel.setVisibility(View.VISIBLE);
        etNumeroTel.requestFocus();
    }

    public void modEditarCorreo() {
        etCorreo.setText(tvCorreo.getText().toString());
        llEditarCorreo.setVisibility(View.GONE);
        llConfirCorreo.setVisibility(View.VISIBLE);
        etCorreo.requestFocus();
    }

    public void mostrarNumeroTelefonico(String numTel) {
        llEditarNumTel.setVisibility(View.VISIBLE);
        llConfirNumTel.setVisibility(View.GONE);
        tvNumeroTel.setText(numTel);
    }
    public void mostrarCorreo(String correo) {
        llEditarCorreo.setVisibility(View.VISIBLE);
        llConfirCorreo.setVisibility(View.GONE);
        tvCorreo.setText(correo);
    }

    public void backDetalles(){
        llEditorDetalles.setVisibility(View.GONE);
        llShowDetalles.setVisibility(View.VISIBLE);
    }
    public void backMensualidad(){
        llEditorMensualidad.setVisibility(View.GONE);
        llShowMensualidad.setVisibility(View.VISIBLE);
    }

    public void noPago(OnClickListener listener) {
        ivAlert.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_add_alert_black_24dp));
        llPagoMensual.setOnClickListener(listener);
        findViewById(R.id.tvRealizarPago).setVisibility(View.VISIBLE);
    }

    public void pago(OnClickListener listener2) {
        findViewById(R.id.tvRealizarPago).setVisibility(View.GONE);
        ivAlert.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_mood_black_24dp));
        llPagoMensual.setOnClickListener(listener2);
    }

    protected void iniciarViews() {
        tvNumeroInquilinos = findViewById(R.id.tvTenantsNumber);
        tvMensualidad = findViewById(R.id.vcTvMensualidad);
        tvAdvanceTotal = findViewById(R.id.tvAdvanceTotal);
        tvDetalles = findViewById(R.id.tvDetalles);
        tvFechaI = findViewById(R.id.tvEntryDate);
        tvFechaC = findViewById(R.id.tvFechaDePago);
        tvNumeroTel = findViewById(R.id.hutvNumTel);
        tvCorreo = findViewById(R.id.hutvCorreo);

        etDetalles = findViewById(R.id.etDetalles);
        etMensualidad = findViewById(R.id.etMensualidad);
        etNumeroTel = findViewById(R.id.etNumTel);
        etCorreo = findViewById(R.id.etCorreo);

        cvDetallesAlquiler = findViewById(R.id.cvDetallesAlquiler);
        cvMPDetails = findViewById(R.id.cvMPDetails);
        cvMessage = findViewById(R.id.cvMensaje);

        llEditarNumTel = findViewById(R.id.llEditarNumTel);
        llEditarCorreo = findViewById(R.id.llEditarCorreo);
        llEditorDetalles = findViewById(R.id.llEditarDetalles);
        llEditorMensualidad = findViewById(R.id.llEditMensualidad);
        llShowDetalles = findViewById(R.id.llMostrarDetalles);
        llShowMensualidad = findViewById(R.id.llShowMensualidad);
        llPagoMensual = findViewById(R.id.llPagoMensual);
        llConfirNumTel = findViewById(R.id.llConfirmNumTel);
        llConfirCorreo = findViewById(R.id.llConfirmCorreo);
        llAdvance = findViewById(R.id.llAdvance);

        ivAlert = findViewById(R.id.ivAlert);
    }

    public void setDetallesText(String detallesText){
        tvDetalles.setText(detallesText);
    }

    public TextView getTvFechaC() {
        return tvFechaC;
    }

    public TextView getTvMensualidad() {
        return tvMensualidad;
    }

    public String getMensualidadText(){
        return tvMensualidad.getText().toString();
    }

    public String getDetallesText(){
        return etDetalles.getText().toString();
    }

    public boolean checkBack(View currentFocus){
        if (currentFocus.equals(etDetalles)) {
            backDetalles();
            return true;
        } else {
            if (currentFocus.equals(etMensualidad)) {
                backMensualidad();
                return true;
            }
        }
        return false;
    }

    public void setFechaCText(String fecha) {
        tvFechaC.setText(fecha);
    }

    public String  getCorreoText(){
        return etCorreo.getText().toString();
    }
    public String  getTelefonoText(){
        return etNumeroTel.getText().toString();
    }

    public void showAdvance(Double amount) {
        llAdvance.setVisibility(VISIBLE);
        tvAdvanceTotal.setText(String.valueOf(amount));
    }

    public void hideAdvance() {
        llAdvance.setVisibility(GONE);
    }
}
