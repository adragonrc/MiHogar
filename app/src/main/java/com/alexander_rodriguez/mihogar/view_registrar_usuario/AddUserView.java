package com.alexander_rodriguez.mihogar.view_registrar_usuario;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.alexander_rodriguez.mihogar.view_buttons_ac.ButtonsAC;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemTenant;
import com.alexander_rodriguez.mihogar.R;

public class AddUserView extends ScrollView {
    private InterfaceUserView parent;
    private EditText etDNI;
    private EditText etNombre;
    private EditText etApellidoPat;
    private EditText etApellidoMat;

    private ImageView ivPhoto;

    private String path;

    private ItemTenant modelEdit;

    private ButtonsAC buttonsAC;

    private ButtonsAC.Listener addUserListener;
    private ButtonsAC.Listener editUserListener;

    public AddUserView(Context context) {
        super(context);
    }

    public AddUserView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AddUserView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AddUserView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setParent(InterfaceUserView newParent){
        this.parent = newParent;
    }

    public void setPhoto(String path){
        if (path != null && !path.isEmpty()){
            this.path = path;
            ivPhoto.setImageBitmap(BitmapFactory.decodeFile(path));
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        iniciarViews();
    }

    public ItemTenant getModelEdit() {
        modelEdit.setData(getDatos()) ;
        return modelEdit;
    }

    @NonNull
    public ItemTenant getDatos(){
        return  new ItemTenant(etDNI.getText().toString(),
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
        buttonsAC = findViewById(R.id.llBtns);
        addUserListener = new ButtonsAC.Listener() {
            @Override
            public void ocPositive(View view) {
                parent.ocPositiveAddUser(view);
            }

            @Override
            public void ocNegative(View view) {
                parent.ocNegativeAdd(view);
            }
        };
        editUserListener = new ButtonsAC.Listener() {
            @Override
            public void ocPositive(View view) {
                parent.ocPositiveEdit(view);
            }

            @Override
            public void ocNegative(View view) {
                parent.ocNegativeEdit(view);
            }
        };


        makeNotFocusable(etDNI, etNombre, etApellidoMat, etApellidoPat);
        buttonsAC.setListener(addUserListener);
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
        ivPhoto.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.images));
    }

    public void onExpanded() {
        makeFocusable(etDNI, etNombre, etApellidoMat, etApellidoPat);
        //etDNI.requestFocus();
    }

    public void onCollapsed() {
        //clear();
    }

    public void onUpdateUser(ItemTenant model) {
        modelEdit = model;
        etApellidoMat.setText(model.getApellidoMat());
        etApellidoPat.setText(model.getApellidoPat());
        etDNI.setText(model.getDni());
        etNombre.setText(model.getName());
        path = model.getPath();
        setPhoto(path);
        buttonsAC.setListener(editUserListener);
        buttonsAC.setTextButtons(null, "Guardar");
    }

    public void finishEdit() {
        buttonsAC.setListener(addUserListener);
        modelEdit = null;
        buttonsAC.setTextButtons(null, "Agregar");
    }
}