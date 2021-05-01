package com.alexander_rodriguez.mihogar.dialogs;

import android.view.View;
import android.widget.EditText;

public class ModelDialogImput extends DialogModel{
    private View view;
    private EditText imput;
    public ModelDialogImput(String title, String mensaje) {
        super(title, mensaje);

    }

    public EditText getImput() {
        return imput;
    }

    public View getView() {
        return view;
    }
}
