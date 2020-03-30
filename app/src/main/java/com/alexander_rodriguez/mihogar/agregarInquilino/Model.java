package com.alexander_rodriguez.mihogar.agregarInquilino;

import android.graphics.Bitmap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class Model {
    private EditText etDNI;
    private EditText etNombre;
    private EditText etApellidoPat;
    private EditText etApellidoMat;
    private EditText etPrecio;

    private ImageView ivPhoto;

    private Spinner spNumCuarto;
    private Spinner spDia;
    private Spinner spMes;
    private Spinner spAnio;

    private RadioGroup radioGroup;
    private String currentImagePath;
    private Bitmap bmGuardar;

}
