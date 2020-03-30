package com.alexander_rodriguez.mihogar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdministradorCamara {
    public static final int REQUEST_TAKE_PHOTO = 1;
    private String currentPhotoPath;
    private Context context;

    public AdministradorCamara(Context context){
        this.context = context;
        currentPhotoPath = "";
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
    public Intent dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                //Toast.makeText(this, "Error al crear el archivo", Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context,
                        "com.alquilerapp.myapplication.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                return takePictureIntent;
                //activity.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
        return null;
    }

    public void setPic(ImageView imageView){
        if(currentPhotoPath == null)
            return;

        if(currentPhotoPath.equals("")) return;
        //obtener las dimensiones de la vista
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();
        //Obtener las dimenciones de el mapa de bits BitMap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        //determina el factor de escala de la imagen
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

        imageView.setImageBitmap(bitmap);
    }
    public static void setPic(final ImageView imageView, final String currentPhotoPath){
        if(currentPhotoPath == null)
            return;

        if(currentPhotoPath.equals("")) return;
        //obtener las dimensiones de la vista
        ViewTreeObserver observer = imageView.getViewTreeObserver();

        final int[] targetW = new int[1];
        final int[] targetH = new int[1];
        if(observer.isAlive()){
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onGlobalLayout() {
                    targetW[0] =  imageView.getWidth();
                    targetH[0] = imageView.getHeight();
                    if(targetH[0] != 0 && targetW[0] != 0){
                        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                        bmOptions.inJustDecodeBounds = true;
                        int photoW = bmOptions.outWidth;
                        int photoH = bmOptions.outHeight;
                        //determina el factor de escala de la imagen
                        int scaleFactor = Math.min(photoW / targetW[0], photoH / targetH[0]);

                        bmOptions.inJustDecodeBounds = false;
                        bmOptions.inSampleSize = scaleFactor;
                        bmOptions.inPurgeable = true;

                        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

                        imageView.setImageBitmap(bitmap);
                        imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            });
        }
    }
    public String getCurrentPhotoPath() {
        return currentPhotoPath;
    }
}
