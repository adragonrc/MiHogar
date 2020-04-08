package com.alexander_rodriguez.mihogar.menu_photo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.alexander_rodriguez.mihogar.ActivityShowImage;
import com.alexander_rodriguez.mihogar.Base.BaseActivity;
import com.alexander_rodriguez.mihogar.DataBaseAdmin;
import com.alexander_rodriguez.mihogar.Save;
import com.alexander_rodriguez.mihogar.UTILIDADES.TCuarto;
import com.alexander_rodriguez.mihogar.UTILIDADES.TUsuario;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.IOError;


public class MenuIterator implements interfazMenu{
    private BaseActivity mActivity;
    private DataBaseAdmin db ;

    public MenuIterator(BaseActivity mActivity){
        this.mActivity = mActivity;
    }

    @Override
    public void onShared(String path) {
        if (path == null || path.equals("") ) {
            mActivity.showMensaje("No hay foto");
            return;
        }
        File dir = mActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File newFile = new File(dir, new File(path).getName());

        Uri uri = FileProvider.getUriForFile(mActivity, BaseActivity.FILE_PROVIDER, newFile);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("application/pdf");
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        mActivity.startActivity(Intent.createChooser(emailIntent, "Enviar imagen con: "));

    }

    @Override
    public void onEdit() {
        mActivity.startCropImage();
    }



}
