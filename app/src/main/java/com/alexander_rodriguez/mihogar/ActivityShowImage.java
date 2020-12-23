package com.alexander_rodriguez.mihogar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.alexander_rodriguez.mihogar.Base.BaseActivity;
import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.Base.IBasePresenter;
import com.alexander_rodriguez.mihogar.DataBase.DataBaseAdmin;
import com.alexander_rodriguez.mihogar.UTILIDADES.TCuarto;
import com.alexander_rodriguez.mihogar.UTILIDADES.TUsuario;
import com.alexander_rodriguez.mihogar.menu_photo.MenuIterator;
import com.alexander_rodriguez.mihogar.menu_photo.interfazMenu;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOError;


public class ActivityShowImage extends BaseActivity<IBasePresenter> {
    public static final String DATA_IMAGE = "BIT_MAP";
    public static final String IS_CUARTO_IMAGE = "photo_cuarto";
    public static final String IS_USER_IMAGE = "photo_user";
    public static final int BACK_PRESSED = 16908332;
    private Bitmap bmGuardar;
    private ImageView photoView;
    private String path;
    private interfazMenu interfazMenu;
    private DataBaseAdmin db ;

    @Override
    protected void iniciarComandos() {

        setTitle("Foto");
        ActionBar ab = getSupportActionBar();
        if  (ab!= null)  ab.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        path = intent.getStringExtra(DATA_IMAGE);
        photoView =  findViewById(R.id.photo);
        if(path != null && !path.equals("")) {
            photoView.setImageBitmap(BitmapFactory.decodeFile(path));
        }
        interfazMenu = new MenuIterator(this);

        db = new DataBaseAdmin(this, null, 1);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_show_image;
    }

    @NonNull
    @Override
    protected IBasePresenter createPresenter() {
        return new BasePresenter<ActivityShowImage>(this) {
            @Override
            public void iniciarComandos() {

            }
        };
    }

    @Override
    protected void iniciarViews() {

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
                interfazMenu.onEdit();
                break;
            }
            case R.id.app_bar_shared:{
                interfazMenu.onShared(path);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String path = "";
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == Activity.RESULT_OK){
                Bitmap bmGuardar = BitmapFactory.decodeFile(result.getUri().getPath());
                photoView.setImageBitmap(bmGuardar);
                guardar(bmGuardar);
            }else{
                if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    assert result != null;
                    Exception e = result.getError();
                    Toast.makeText(this, "Posible Error es: "+ e, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void guardar(Bitmap bmGuardar) {
        try {
            Save s = new Save();
            String path = s.SaveImage(this, bmGuardar);
            if (getIntent().getBooleanExtra(ActivityShowImage.IS_CUARTO_IMAGE, false)) {
                String numCuarto = getIntent().getStringExtra(TCuarto.NUMERO);
                db.upDateCuarto(TCuarto.URL, path, numCuarto);
            } else {
                if (getIntent().getBooleanExtra(ActivityShowImage.IS_USER_IMAGE, false)) {
                    String numUsuario = getIntent().getStringExtra(TUsuario.DNI);
                    db.upDateUsuario(TUsuario.URI, path, numUsuario);
                }
            }
        }catch (IOError e){
            showMensaje("No se pudo guardar");
        }
    }
}

    /*

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
*/
