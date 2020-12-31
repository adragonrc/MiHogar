package com.alexander_rodriguez.mihogar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Save {

    private Context context;

    public String SaveImage(Context context, String  path,  String parent, String name){
        if (path == null || path.equals("")) return "";
        Bitmap bm = BitmapFactory.decodeFile(path);
        return SaveImage(context, bm, parent,name);
    }
    public String SaveImage(Context context, Bitmap ImageToSave, String parent, String name) {
        if(ImageToSave == null ) return "";

        this.context = context;
        //  String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + NameOfFolder;

        //  File dir = new File(file_path);
        File dir = this.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (dir != null)
            if (!dir.exists()) {
                dir.mkdirs();
            }
        dir = new File(dir, parent);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, name + ".jpg");
        try {
            FileOutputStream fOut = new FileOutputStream(file);

            ImageToSave.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
            MakeSureFileWasCreatedThenMakeAvabile(file);
            AbleToSave();
            return file.getAbsolutePath();
        } catch(IOException e) {
            UnableToSave();
        }
        return "";
    }

    private void MakeSureFileWasCreatedThenMakeAvabile(File file){
        MediaScannerConnection.scanFile(context,
                new String[] { file.toString() } , null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                    }
                });
    }

    private String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-­ss");
        return df.format(c.getTime());
    }

    private void UnableToSave() {
        Toast.makeText(context, "¡No se ha podido guardar la imagen!", Toast.LENGTH_SHORT).show();
    }

    private void AbleToSave() {
        Toast.makeText(context, "Imagen guardada en la galería.", Toast.LENGTH_SHORT).show();
    }
}