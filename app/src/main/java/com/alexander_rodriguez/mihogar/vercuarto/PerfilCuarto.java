package com.alexander_rodriguez.mihogar.vercuarto;

import android.content.ContentValues;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.alexander_rodriguez.mihogar.ButtonsAceptarCancelar;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.UTILIDADES.TUsuario;

public class PerfilCuarto extends ScrollView{
    private TextView tvNombres;
    private TextView tvMensualidad;
    private TextView tvDni;
    private TextView tvDetalles;
    private TextView tvFechaC;

    private EditText etMensualidad;
    private EditText etDetalles;

    private ImageView ivAlert;


    private CardView cvDetallesAlquiler;
    private CardView cvMensaje;


    private LinearLayout llPagoMensual;
    private LinearLayout llEditorMensualidad;
    private LinearLayout llShowMensualidad;
    private LinearLayout llEditorDetalles;
    private LinearLayout llShowDetalles;

    private ListenerPerfilCuarto lpc;
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

    public void showCuartoAlquilado(ContentValues usuario, String mensualidad, ContentValues alquiler) {
        String nombes = usuario.getAsString(TUsuario.NOMBRES) + ", " + usuario.getAsString(TUsuario.APELLIDO_PAT) + " " + usuario.getAsString(TUsuario.APELLIDO_MAT) + ".";

        tvDni.setText(usuario.getAsString(TUsuario.DNI));
        tvNombres.setText(nombes);
        tvMensualidad.setText(mensualidad);
        tvFechaC.setText(alquiler.getAsString(TAlquiler.FECHA_C));

        cvMensaje.setVisibility(View.GONE);
        cvDetallesAlquiler.setVisibility(View.VISIBLE);
    }

    public void showCuartolibre() {
        cvDetallesAlquiler.setVisibility(View.GONE);
        cvMensaje.setVisibility(View.VISIBLE);
    }

    public void onClickVerInquilino(View view) {
        lpc.onClickVerInquilino(view);
    }

    public void onClickTerminarAlquiler(View view) {
        lpc.onClickTerminarAlquiler(view);
    }

    public void onClickVermas(View view) {
        lpc.onClickVermas(view);
    }

    public void onClickEditarMensualidad(View view) {
        lpc.onClickEditarMensualidad(view);
    }

    public void modoEditaMensualidad(){
        llEditorMensualidad.setVisibility(View.VISIBLE);
        llShowMensualidad.setVisibility(View.GONE);
        etMensualidad.setText(tvMensualidad.getText().toString());
        etMensualidad.requestFocus();
    }

    public void onClickEditarDetalles(View view) {
        lpc.onClickEditarDetalles(view);
    }

    public void modoEditarDetalles() {
        llEditorDetalles.setVisibility(View.VISIBLE);
        llShowDetalles.setVisibility(View.GONE);
        etDetalles.setText(tvDetalles.getText().toString());
        etDetalles.requestFocus();
    }

    public void onClickConfirMensualidad(View view) {
        lpc.onClickConfirMensualidad(view);
    }

    public void modoConfirMensualidad(View view) {
        lpc.onClickConfirMensualidad(view);
    }
    public void onClickConfirDetalles(View view) {
        lpc.onClickConfirDetalles(view);
    }

    public void onClickPhoto(View view) {
        lpc.onClickPhoto(view);
    }

    public void onClickVerAlquileres(View view) {
        lpc.onClickVerAlquileres(view);
    }

    public void actualizarMensualidad(String mensualidad) {
    //   ocultarTeclado();
        llEditorMensualidad.setVisibility(View.GONE);
        llShowMensualidad.setVisibility(View.VISIBLE);
        tvMensualidad.setText(mensualidad);
    }

    public void actualizarDetalles(String detalles) {
    //   ocultarTeclado();
        llEditorDetalles.setVisibility(View.GONE);
        llShowDetalles.setVisibility(View.VISIBLE);

        tvDetalles.setText(detalles);
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
        ivAlert.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_alert_black_24dp));
        llPagoMensual.setOnClickListener(listener);
        findViewById(R.id.tvRealizarPago).setVisibility(View.VISIBLE);
    }

    public void pago(OnClickListener listener2) {
        findViewById(R.id.tvRealizarPago).setVisibility(View.GONE);
        ivAlert.setImageDrawable(getResources().getDrawable(R.drawable.ic_mood_black_24dp));
        llPagoMensual.setOnClickListener(listener2);
    }

    protected void iniciarViews() {
        //  tvNumeroCuarto = findViewById(R.id.tvNumeroCuarto);
        tvNombres = findViewById(R.id.tvNombres);
        tvMensualidad = findViewById(R.id.vcTvMensualidad);
        tvDni = findViewById(R.id.tvDni);
        tvDetalles = findViewById(R.id.tvDetalles);
        tvFechaC = findViewById(R.id.tvFechaDePago);

        etDetalles = findViewById(R.id.etDetalles);
        etMensualidad = findViewById(R.id.etMensualidad);


        cvDetallesAlquiler = findViewById(R.id.cvDetallesAlquiler);
        cvMensaje = findViewById(R.id.cvMensaje);

        llEditorDetalles = findViewById(R.id.llEditarDetalles);
        llEditorMensualidad = findViewById(R.id.llEditMensualidad);
        llShowDetalles = findViewById(R.id.llMostrarDetalles);
        llShowMensualidad = findViewById(R.id.llShowMensualidad);
        llPagoMensual = findViewById(R.id.llPagoMensual);

        ivAlert = findViewById(R.id.ivAlert);
    }



    public EditText getEtDetalles() {
        return etDetalles;
    }

    public EditText getEtMensualidad() {
        return etMensualidad;
    }

    public void setTvDetalles(TextView tvDetalles) {
        this.tvDetalles = tvDetalles;
    }

    public void setDetallesText(String detallesText){
        tvDetalles.setText(detallesText);
    }
    public void setTvMensualidad(TextView tvMensualidad) {
        this.tvMensualidad = tvMensualidad;
    }

    public TextView getTvDni() {
        return tvDni;
    }

    public String getDniText(){
        return tvDni.getText().toString();
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
        return tvDetalles.getText().toString();
    }
    public String getEtDetallesText(){
        return etDetalles.getText().toString();
    }
    public boolean checkBack(View currentFocus){
        if (currentFocus.equals(etDetalles)) {
            llEditorDetalles.setVisibility(View.GONE);
            llShowDetalles.setVisibility(View.VISIBLE);
            return true;
        } else {
            if (currentFocus.equals(etMensualidad)) {
                llEditorMensualidad.setVisibility(View.GONE);
                llShowMensualidad.setVisibility(View.VISIBLE);
                return true;
            }
        }
        return false;
    }
}
