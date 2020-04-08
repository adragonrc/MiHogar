package com.alexander_rodriguez.mihogar.registrarcasa;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
        permisos();
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

    protected void permisos(){
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED){
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                ActivityCompat. requestPermissions(this, PERMISOS, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
            Toast.makeText(this, "Los vouchers no seran crados", Toast.LENGTH_SHORT).show();
        }
    }
}
