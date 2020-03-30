package com.alexander_rodriguez.mihogar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.alexander_rodriguez.mihogar.UTILIDADES.TCuarto;
import com.alexander_rodriguez.mihogar.UTILIDADES.TUsuario;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;


public class ActivityShowImage extends AppCompatActivity {
    public static final String DATA_IMAGE = "BIT_MAP";
    public static final String IS_CUARTO_IMAGE = "photo_cuarto";
    public static final String IS_USER_IMAGE = "photo_user";
    public static final int BACK_PRESSED = 16908332;
    private Bitmap bmGuardar;
    private ImageView photoView;
    private String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        setTitle("Foto");
        ActionBar ab = getSupportActionBar();
        if  (ab!= null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        path = intent.getStringExtra(DATA_IMAGE);
        photoView =  findViewById(R.id.photo);
        if(path != null && !path.equals("")) {
            photoView.setImageBitmap(BitmapFactory.decodeFile(path));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_photo_show, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.app_bar_edit:{
                ocEdit(item.getActionView());
                break;
            }
            case R.id.app_bar_shared:{
                ocShared(item.getActionView());
                break;
            }
            case BACK_PRESSED:{
                onBackPressed();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void ocShared(View view){
        File dir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File newFile = new File(dir, new File(path).getName());

        Uri uri = FileProvider.getUriForFile(this, "com.alexander_rodriguez.mihogar.android.fileprovider", newFile);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("application/pdf");
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(emailIntent, "Enviar imagen con: "));
    }
    public void ocEdit(View view){
        CropImage.activity().setMaxCropResultSize(0,0)
                .setAllowFlipping(true)
                .setAspectRatio(15,10)
                .setMinCropResultSize(1000,750)
                .setMaxCropResultSize(3000,2250)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                bmGuardar = BitmapFactory.decodeFile(result.getUri().getPath());
                photoView.setImageURI(result.getUri());
                guardar();
            }else{
                if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    Exception e = result.getError();
                    Toast.makeText(this, "Posible Error es: "+ e, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private void guardar(){
        Save s = new Save();
        path =  s.SaveImage(this, bmGuardar);

        DataBaseAdmin db = new DataBaseAdmin(this, null, 1);
        if (getIntent().getBooleanExtra(IS_CUARTO_IMAGE, false)){
            String numCuarto =  getIntent().getStringExtra(TCuarto.NUMERO);
            db.upDateCuarto(TCuarto.URL, path, numCuarto);
        }else{
            if (getIntent().getBooleanExtra(IS_USER_IMAGE, false)){
                String numUsuario =  getIntent().getStringExtra(TUsuario.DNI);
                db.upDateUsuario(TUsuario.URI, path, numUsuario);
            }
        }
    }
}
