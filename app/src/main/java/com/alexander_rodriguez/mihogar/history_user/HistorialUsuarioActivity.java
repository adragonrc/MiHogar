package com.alexander_rodriguez.mihogar.history_user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import com.alexander_rodriguez.mihogar.Base.BaseActivity;
import com.alexander_rodriguez.mihogar.view_buttons_ac.ButtonsAC;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemTenant;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.Save;
import com.alexander_rodriguez.mihogar.menu_photo.MenuIterator;
import com.alexander_rodriguez.mihogar.menu_photo.interfazMenu;
import com.alexander_rodriguez.mihogar.vercuarto.view_perfil_cuarto.ProfileView;
import com.juliodigital.crooperimage.CropImage;


public class HistorialUsuarioActivity extends BaseActivity<Interfaz.presenter> implements Interfaz.view {

    public static final String USER_EXTRA = "userExtra";
    public static final String DNI_EXTRA = "dni";
    private TextView tvNombres;
    private TextView tvApellidoPat;
    private TextView tvApellidoMat;
    private TextView tvNumAlquiler;

    private EditText etNombres;
    private EditText etApellidoPat;
    private EditText etApellidoMat;

    private LinearLayout llEditarNombres;
    private LinearLayout llEditarApePat;
    private LinearLayout llEditarApeMat;

    private LinearLayout llConfirNombres;
    private LinearLayout llConfirApePat;
    private LinearLayout llConfirApeMat;

    private ProfileView profileView;

    private String path;

    private ItemTenant tenant;

    private interfazMenu interfazMenu;

    private ButtonsAC ac;

    @Override
    protected void iniciarComandos() {

        //setTitle("Inquilino");
        setSupportActionBar(profileView.getToolbar());
        interfazMenu = new MenuIterator(this);
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
        return new Presenter(this, getIntent());
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_photo_show, menu);
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK && result != null){
                resultOk(result);
            }else{
                if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    Exception e = result.getError();
                    Toast.makeText(this, "Posible Error es: "+ e, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private void resultOk(CropImage.ActivityResult result){
        Save s = new Save();
        Bitmap bm = BitmapFactory.decodeFile(result.getUri().getPath());
        path = s.SaveImage(this, bm, getString(R.string.cTenant),  tenant.getDni());
        presenter.updatePhoto(path);
        profileView.reloadPhoto(path);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.app_bar_edit) {
            interfazMenu.onEdit();
        } else if (id == R.id.app_bar_shared) {
            interfazMenu.onShared(path);
        } else if (id == BACK_PRESSED) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void mostrarDatosUsuario(ItemTenant datos, String i) {
        tenant = datos;
        profileView.setSubTitle(datos.getDni());
        profileView.setTitle(datos.getName());
        tvNombres.setText(datos.getName());
        tvApellidoPat.setText(datos.getApellidoPat());
        tvApellidoMat.setText(datos.getApellidoMat());
        //tvNumeroTel.setText(datos.getAsString(TUsuario.NUMERO_TEL));
        //tvCorreo.setText(datos.getAsString(TUsuario.CORREO));

        path = datos.getPath();

        profileView.reloadPhoto(path);
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

    public void onClickPhoto(View view){
        presenter.onClickPhoto(view);
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
    public void salir(){
        onBackPressed();
    }

    @Override
    public void reloadRoomPhoto() {
        profileView.reloadPhoto(Save.createFile(this, getString(R.string.cTenant), presenter.getDni()).getAbsolutePath());
    }

    @Override
    public void showImage(Intent intent) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, profileView.getIvPhoto(), ViewCompat.getTransitionName(profileView.getIvPhoto()));
        startActivity(intent, options.toBundle());
    }

    @Override
    protected void iniciarViews() {
        modificarTransicion();

        profileView = findViewById(R.id.profileAppid);

        ScrollView cuerpo = (ScrollView) LayoutInflater.from(this).inflate(R.layout.view_perfil_usuario_cuerpo, null);
        profileView.addToCuerpo(cuerpo);

        tvNombres = findViewById(R.id.hutvNombres);
        tvApellidoPat = findViewById(R.id.hutvApePat);
        tvApellidoMat = findViewById(R.id.hutvApeMat);
        tvNumAlquiler = findViewById(R.id.tvNumAlquiler);

        etNombres = findViewById(R.id.etNombres);
        etApellidoPat = findViewById(R.id.etApePat);
        etApellidoMat = findViewById(R.id.etApeMat);

        llEditarNombres = findViewById(R.id.llEditarNombres);
        llEditarApePat = findViewById(R.id.llEditarApellidoPat);
        llEditarApeMat = findViewById(R.id.llEditarMat);

        llConfirNombres = findViewById(R.id.llConfirmNombres);
        llConfirApePat = findViewById(R.id.llConfirmApePat);
        llConfirApeMat = findViewById(R.id.llConfirmApeMat);

        ac = findViewById(R.id.llBtns);

        ac.setTextButtons("SALIR", "VER ALQUILERES");
    }

    @Override
    public void ocPositive(View view) {
        presenter.onClickPositive(view);
    }

    @Override
    public void ocNegative(View view) {
        salir();
    }
}
