package com.alexander_rodriguez.mihogar.historialUserPakage;

import android.content.ContentValues;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import com.alexander_rodriguez.mihogar.ActivityShowImage;
import com.alexander_rodriguez.mihogar.Base.BaseActivity;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.UTILIDADES.TUsuario;
import com.alexander_rodriguez.mihogar.alquilerusuario.AlquilerUsuarioActivity;
import com.alexander_rodriguez.mihogar.vercuarto.ProfileView;

import java.util.Objects;

import javax.xml.validation.Validator;


public class HistorialUsuarioActivity extends BaseActivity<Interfaz.presenter> implements Interfaz.view {

    private TextView tvNombres;
    private TextView tvApellidoPat;
    private TextView tvApellidoMat;
    private TextView tvNumeroTel;
    private TextView tvCorreo;
    private TextView tvNumAlquiler;

    private EditText etNombres;
    private EditText etApellidoPat;
    private EditText etApellidoMat;
    private EditText etNumeroTel;
    private EditText etCorreo;

    private LinearLayout llEditarNombres;
    private LinearLayout llEditarApePat;
    private LinearLayout llEditarApeMat;
    private LinearLayout llEditarNumTel;
    private LinearLayout llEditarCorreo;

    private LinearLayout llConfirNombres;
    private LinearLayout llConfirApePat;
    private LinearLayout llConfirApeMat;
    private LinearLayout llConfirNumTel;
    private LinearLayout llConfirCorreo;


    private ProfileView profileView;

    private String uriPhoto;

    private String dni;

    private Button verPagos;
    private Button salir;

    @Override
    protected void iniciarComandos() {

        //setTitle("Inquilino");
        setSupportActionBar(profileView.getToolbar());
        ActionBar ab =  getSupportActionBar();
        if (ab != null){
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_perfil_usuario;
    }

    @NonNull
    @Override
    protected Interfaz.presenter createPresenter() {
        dni = getIntent().getStringExtra(TUsuario.DNI);
        return new Presenter(this, dni);
    }

    @Override
    public void mostrarAlerta() {
       // imCalificativo.setImageDrawable(getResources().getDrawable(R.drawable.ic_warning_black_24dp));
    }

    @Override
    public void noMostrarAlerta() {
       // imCalificativo.setImageDrawable(getResources().getDrawable(R.drawable.ic_offline_pin_black_24dp));
    }

    @Override
    public void mostrarDatosUsuario(ContentValues datos, String i) {
        profileView.setSubTitle(datos.getAsString(TUsuario.DNI));
        profileView.setTitle(datos.getAsString(TUsuario.NOMBRES));

        tvNombres.setText(datos.getAsString(TUsuario.NOMBRES));
        tvApellidoPat.setText(datos.getAsString(TUsuario.APELLIDO_PAT));
        tvApellidoMat.setText(datos.getAsString(TUsuario.APELLIDO_MAT));
        tvNumeroTel.setText(datos.getAsString(TUsuario.NUMERO_TEL));
        tvCorreo.setText(datos.getAsString(TUsuario.CORREO));
        uriPhoto = datos.getAsString(TUsuario.URI);
        presenter.setImage(profileView.getIvPhoto(), uriPhoto);
        tvNumAlquiler.setText(i);
    }

    @Override
    public void modoError(String error){
        profileView.setTitle(error);
        profileView.setSubTitle(error);
        tvNombres.setText(error);
        tvApellidoPat.setText(error);
        tvApellidoMat.setText(error);
    }

    @Override
    public void onBackPressed() {
        View view = getCurrentFocus();
        if (view != null)
        if (view.equals(etApellidoMat)){
            llConfirApeMat.setVisibility(View.GONE);
            llEditarApeMat.setVisibility(View.VISIBLE);
            return;
        }else {
            if (view.equals(etApellidoPat)){
                llConfirApePat.setVisibility(View.GONE);
                llEditarApePat.setVisibility(View.VISIBLE);
                return;
            }else{
                if (view.equals(etNombres)){
                    llConfirNombres.setVisibility(View.GONE);
                    llEditarNombres.setVisibility(View.VISIBLE);
                    return;
                }
            }
        }
        super.onBackPressed();
    }

    @Override
    public void ocEditarNombres(View view) {
        etNombres.setText(tvNombres.getText().toString());
        llEditarNombres.setVisibility(View.GONE);
        llConfirNombres.setVisibility(View.VISIBLE);
        etNombres.requestFocus();
    }

    @Override
    public void ocEditarApePat(View view) {
        etApellidoPat.setText(tvApellidoPat.getText().toString());
        llEditarApePat.setVisibility(View.GONE);
        llConfirApePat.setVisibility(View.VISIBLE);
        etApellidoPat.requestFocus();
    }

    @Override
    public void ocEditarApeMat(View view) {
        etApellidoMat.setText(tvApellidoMat.getText().toString());
        llEditarApeMat.setVisibility(View.GONE);
        llConfirApeMat.setVisibility(View.VISIBLE);
        etApellidoMat.requestFocus();
    }

    @Override
    public void ocEditarNumTel(View view) {
        etNumeroTel.setText(tvNumeroTel.getText().toString());
        llEditarNumTel.setVisibility(View.GONE);
        llConfirNumTel.setVisibility(View.VISIBLE);
        etNumeroTel.requestFocus();
    }

    @Override
    public void ocEditarCorreo(View view) {
        etCorreo.setText(tvCorreo.getText().toString());
        llEditarCorreo.setVisibility(View.GONE);
        llConfirCorreo.setVisibility(View.VISIBLE);
        etCorreo.requestFocus();
    }

    @Override
    public void ocConfirNombres(View view) {
        presenter.actualizarNombres(etNombres.getText().toString());
    }

    @Override
    public void ocConfirApePat(View view) {
        presenter.actualizarApePat(etApellidoPat.getText().toString());
    }

    @Override
    public void ocConfirApeMat(View view) {
        presenter.actualizarApeMat(etApellidoMat.getText().toString());
    }

    @Override
    public void ocConfirNumTel(View view) {
        presenter.actualizarNumTel(etNumeroTel.getText().toString());
    }

    @Override
    public void ocConfirCorreo(View view) {
        presenter.actualizarCorreo(etCorreo.getText().toString());
    }
    public void onClickPhoto(View view){
        if(Objects.requireNonNull(uriPhoto).equals("")) {
            Toast.makeText(this, "Sin foto", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, ActivityShowImage.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, profileView.getIvPhoto(), ViewCompat.getTransitionName(profileView.getIvPhoto()));
        intent.putExtra(ActivityShowImage.IS_USER_IMAGE, true);
        intent.putExtra(TUsuario.DNI, dni);
        intent.putExtra(ActivityShowImage.DATA_IMAGE, uriPhoto);
        startActivity(intent, options.toBundle());


    }

    @Override
    public void ocVerMas(View view){
        Intent i = new Intent(this, AlquilerUsuarioActivity.class);
        i.putExtra(AlquilerUsuarioActivity.EXTRA_DNI, Integer.valueOf(dni));
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id ==BACK_PRESSED){
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void actualizarNombres(String nombres) {
        ocultarTeclado();
        llEditarNombres.setVisibility(View.VISIBLE);
        llConfirNombres.setVisibility(View.GONE);
        tvNombres.setText(nombres);
    }

    @Override
    public void actualizarApePat(String apellidPaterno) {
        ocultarTeclado();
        llEditarApePat.setVisibility(View.VISIBLE);
        llConfirApePat.setVisibility(View.GONE);
        tvApellidoPat.setText(apellidPaterno);
    }

    @Override
    public void actualizarApeMat(String apellidMaterno) {
        ocultarTeclado();
        llEditarApeMat.setVisibility(View.VISIBLE);
        llConfirApeMat.setVisibility(View.GONE);
        tvApellidoMat.setText(apellidMaterno);
    }

    @Override
    public void actualizarNumTel(String numTel) {
        ocultarTeclado();
        llEditarNumTel.setVisibility(View.VISIBLE);
        llConfirNumTel.setVisibility(View.GONE);
        tvNumeroTel.setText(numTel);
    }
    @Override
    public void actualizarCorreo(String correo) {
        ocultarTeclado();
        llEditarCorreo.setVisibility(View.VISIBLE);
        llConfirCorreo.setVisibility(View.GONE);
        tvCorreo.setText(correo);
    }

    @Override
    public void salir(){
        onBackPressed();
    }

    @Override
    protected void iniciarViews() {
        modificarTransicion();

        profileView = findViewById(R.id.profileAppid);

        ScrollView cuerpo = (ScrollView) LayoutInflater.from(this).inflate(R.layout.perfil_usuario_cuerpo, null);
        profileView.addToCuerpo(cuerpo);

        tvNombres = findViewById(R.id.hutvNombres);
        tvApellidoPat = findViewById(R.id.hutvApePat);
        tvApellidoMat = findViewById(R.id.hutvApeMat);
        tvNumeroTel = findViewById(R.id.hutvNumTel);
        tvCorreo = findViewById(R.id.hutvCorreo);
        tvNumAlquiler = findViewById(R.id.tvNumAlquiler);

        etNombres = findViewById(R.id.etNombres);
        etApellidoPat = findViewById(R.id.etApePat);
        etApellidoMat = findViewById(R.id.etApeMat);
        etNumeroTel = findViewById(R.id.etNumTel);
        etCorreo = findViewById(R.id.etCorreo);

        llEditarNombres = findViewById(R.id.llEditarNombres);
        llEditarApePat = findViewById(R.id.llEditarApellidoPat);
        llEditarApeMat = findViewById(R.id.llEditarMat);
        llEditarNumTel = findViewById(R.id.llEditarNumTel);
        llEditarCorreo = findViewById(R.id.llEditarCorreo);

        llConfirNombres = findViewById(R.id.llConfirmNombres);
        llConfirApePat = findViewById(R.id.llConfirmApePat);
        llConfirApeMat = findViewById(R.id.llConfirmApeMat);
        llConfirNumTel = findViewById(R.id.llConfirmNumTel);
        llConfirCorreo = findViewById(R.id.llConfirmCorreo);

        salir = findViewById(R.id.button0);
        verPagos = findViewById(R.id.button1);
        salir.setText("SALIR");
        verPagos.setText("VER PAGOS");
    }
}
