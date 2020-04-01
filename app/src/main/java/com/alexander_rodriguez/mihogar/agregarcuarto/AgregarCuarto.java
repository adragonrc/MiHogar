package com.alexander_rodriguez.mihogar.agregarcuarto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import  com.alexander_rodriguez.mihogar.AdministradorCamara;
import com.alexander_rodriguez.mihogar.Base.BaseActivity;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.Save;
import com.theartofdev.edmodo.cropper.CropImage;

public class AgregarCuarto extends BaseActivity<Interfaz.Presenter> implements Interfaz.View {
    private EditText numeroDeCuarto;
    private EditText precio;
    private EditText detalles;
    private ImageView ivPhoto;
    private AdministradorCamara adc;
    private String currentPhotoPath;
    private Bitmap bmGuardar;

    public void onClickAgregar(View view){
        String sNumCuarto = numeroDeCuarto.getText().toString();
        String sPrecio = precio.getText().toString();
        String sDetalles= detalles.getText().toString();
        Save s = new Save();
        //ivPhoto.buildDrawingCache();
        currentPhotoPath = s.SaveImage(this, bmGuardar);
        presenter.insertarCuarto(sNumCuarto,sPrecio,sDetalles, currentPhotoPath);
        Toast toast1 = Toast.makeText(getApplicationContext(), "Toast por defecto", Toast.LENGTH_SHORT);
        toast1.show();
    }

    @Override
    public void onClickCamara(View view) {
        Intent i = adc.dispatchTakePictureIntent();
        startActivityForResult(i, AdministradorCamara.REQUEST_TAKE_PHOTO);
    }

    @Override
    public void salir() {
        onBackPressed();
    }

    protected void iniciarViews(){
        numeroDeCuarto = findViewById(R.id.etNumeroCuarto);
        precio = findViewById(R.id.etPrecio);
        detalles = findViewById(R.id.etDetalles);
        ivPhoto = findViewById(R.id.ivPhoto);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                bmGuardar = BitmapFactory.decodeFile(result.getUri().getPath());
                ivPhoto.setImageURI(result.getUri());
            }else{
                if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    Exception e = result.getError();
                    Toast.makeText(this, "Posible Error es: "+ e, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void iniciarComandos() {
        setTitle("Agregar cuarto");
        ActionBar ab =  getSupportActionBar();
        if (ab != null){
            ab.setDisplayHomeAsUpEnabled(true);
        }
        numeroDeCuarto.requestFocus();
        adc = new AdministradorCamara(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if  (item.getItemId() == BACK_PRESSED) onBackPressed();
        return super.onContextItemSelected(item);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_agregar_cuarto;
    }

    @NonNull
    @Override
    protected Interfaz.Presenter createPresenter() {
        return new Presenter(this);
    }

    public void onChooseFile(View v){
        CropImage.activity().setMaxCropResultSize(0,0)
                .setAllowFlipping(true)
                .setAspectRatio(15,10)
                .setMinCropResultSize(1000,750)
                .setMaxCropResultSize(3000,2250)
                .start(this);
    }

}
