package com.alexander_rodriguez.mihogar.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.File;

public class LogadImageTask extends AsyncTask<String , Void, Bitmap> {
    private ImageView imageView;
    public LogadImageTask(ImageView imageView){
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap bitmap = null;
        Bitmap resizedImageBitmap = null;
        File imgFile = new File(strings[0]);
        if (imgFile.exists()){
            bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            resizedImageBitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, false);
        }
        return resizedImageBitmap;
    }

    protected void onPostExecute(Bitmap result){
        if (result != null && imageView!= null){
            imageView.setImageBitmap(result);
        }
    }

}
