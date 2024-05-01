package com.alexander_rodriguez.mihogar.menu_photo;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import androidx.core.content.FileProvider;

import com.alexander_rodriguez.mihogar.Base.BaseActivity;
import com.alexander_rodriguez.mihogar.DataBase.DataBaseInterface;

import java.io.File;


public class MenuIterator implements interfazMenu{
    private BaseActivity mActivity;
    private DataBaseInterface db ;

    public MenuIterator(BaseActivity mActivity){
        this.mActivity = mActivity;
    }

    @Override
    public void onShared(String path) {
        if (path == null || path.equals("") ) {
            mActivity.showMessage("No hay foto");
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
