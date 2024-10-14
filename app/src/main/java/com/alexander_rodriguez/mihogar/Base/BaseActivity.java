package com.alexander_rodriguez.mihogar.Base;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.mainactivity.MainActivity;
import com.juliodigital.crooperimage.CropImage;


public abstract class BaseActivity<P extends IBasePresenter> extends AppCompatActivity implements BaseView {
    protected P presenter;

    public static final String FILE_PROVIDER = "com.alexander_rodriguez.mihogar.android.fileprovider";
    public static final int BACK_PRESSED = 16908332;
    public static String[] PERMISOS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    protected final static int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    protected ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        presenter = createPresenter();
        iniciarViews();
        iniciarComandos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.iniciarComandos();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    public Context getContext() {
        return this;
    }

    protected abstract void iniciarComandos();

    protected abstract int getLayout();

    @NonNull
    protected abstract P createPresenter();

    protected abstract void iniciarViews();

    @Override
    public void showMessage(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();

    }

    public void modificarTransicion() {

    }

    protected void ocultarTeclado() {
        View view = getCurrentFocus();
        if (view != null) {
            view.clearFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected void ocultarTeclado(TextView tv) {
        if (tv != null) {
            tv.clearFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(tv.getWindowToken(), 0);
        }
    }

    public void startCropImage() {
        CropImage.activity().setMaxCropResultSize(0, 0)
                .setAllowFlipping(true)
                .setAspectRatio(15, 10)
                .setMinCropResultSize(1000, 750)
                .setMaxCropResultSize(3000, 2250)
                .start(this);
    }

    public void solicitarPermiso() {
        ActivityCompat.requestPermissions(this, BaseActivity.PERMISOS, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
    }

    @Override
    public void setProgressBarVisibility(int visibility) {
        if(progressBar == null) return;
        progressBar.setIndeterminate(visibility == View.VISIBLE);
        progressBar.setVisibility(visibility);
    }

    @Override
    public void goToLogin() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
