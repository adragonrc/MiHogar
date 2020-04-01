package com.alexander_rodriguez.mihogar.registrarcasa;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;

import com.alexander_rodriguez.mihogar.Base.BaseActivity;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.menuPrincipal.MenuPricipal;
import com.google.android.material.textfield.TextInputEditText;

public class RegistrarCasaActivity extends BaseActivity<interfaz.presentador> implements interfaz.view {
    public static final int ON_BACK_PRESED = -1;
    public static final String ON_EXIT = "exit";
    private TextInputEditText etDireccion;
    private TextInputEditText etCorreo;

    @Override
    protected void iniciarComandos() {
        setTitle("Registro");
        if(getIntent().getBooleanExtra(ON_EXIT, false)){
            finish();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_registrar_casa;
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @NonNull
    @Override
    protected interfaz.presentador createPresenter() {
        return new Presentador(this);
    }

    @Override
    protected void iniciarViews() {
        etDireccion = findViewById(R.id.etDireccion);
        etCorreo = findViewById(R.id.etCorreo);
    }

    @Override
    public void ocAceptar(View view){
        String direccion = etDireccion.getText().toString();
        String correo = etCorreo.getText().toString();
        presenter.ingresar(direccion, correo);
    }
    @Override
    public void salir(){
        finish();
    }

    @Override
    public void avanzar() {
        startActivity(new Intent(this, MenuPricipal.class));
    }

}
