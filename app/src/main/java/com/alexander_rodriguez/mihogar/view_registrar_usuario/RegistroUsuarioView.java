package com.alexander_rodriguez.mihogar.view_registrar_usuario;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.alexander_rodriguez.mihogar.DataBase.items.ItemTenant;
import com.alexander_rodriguez.mihogar.R;

public class RegistroUsuarioView extends ScrollView {
    private EditText etDNI;
    private EditText etNombre;
    private EditText etApellidoPat;
    private EditText etApellidoMat;

    private ImageView ivPhoto;

    private String path;

    private Button acept;

    private Button cancel;

    public RegistroUsuarioView(Context context) {
        super(context);
    }

    public RegistroUsuarioView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RegistroUsuarioView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RegistroUsuarioView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setPhoto(String path){
        if (path != null || path.equals("")){
            this.path = path;
            ivPhoto.setImageBitmap(BitmapFactory.decodeFile(path));
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        iniciarViews();
    }

    public ItemTenant getDatos(){
        //Save s = new Save();
        //currentImagePath = s.SaveImage(this, bmGuardar);
        return new ItemTenant(etDNI.getText().toString(),
                etNombre.getText().toString(),
                etApellidoPat.getText().toString(),
                etApellidoMat.getText().toString(),
                path,
                false);
    }

    protected void iniciarViews(){
        etDNI = findViewById(R.id.etDNI);
        etNombre = findViewById(R.id.etNombre);
        etApellidoPat = findViewById(R.id.etApellidoPat);
        etApellidoMat = findViewById(R.id.etApellidoMat);
        ivPhoto = findViewById(R.id.ivPhoto);
        acept = findViewById(R.id.positiveButton);
        cancel = findViewById(R.id.negativeButton);
        makeNotFocusable(etDNI, etNombre, etApellidoMat, etApellidoPat);
    }
    private void makeFocusable(View...view){
        for (View v: view){
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
        }
    }
    private void makeNotFocusable(View...view){
        for (View v: view){
            v.setFocusable(false);
        }
    }
    public EditText getEtDNI() {
        return etDNI;
    }

    public String getDniText(){
        return etDNI.getText().toString();
    }

    public void clear() {
        etApellidoMat.setText("");
        etApellidoPat.setText("");
        etDNI.setText("");
        etNombre.setText("");
        path = "";
        ivPhoto.setImageDrawable(getContext().getResources().getDrawable(R.drawable.images));

    }

    public void onExpanded() {
        makeFocusable(etDNI, etNombre, etApellidoMat, etApellidoPat);
        //etDNI.requestFocus();
    }

    public void onCollapsed() {
        //clear();
    }

    public Button getButtonPositive(){
        return acept;
    }
    public Button getButtonNegative(){
        return cancel;
    }
}