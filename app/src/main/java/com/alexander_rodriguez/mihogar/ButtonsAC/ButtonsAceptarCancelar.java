package com.alexander_rodriguez.mihogar.ButtonsAC;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.alexander_rodriguez.mihogar.R;

public class ButtonsAceptarCancelar extends LinearLayout {
    private Button button0;
    private Button button1;

    public ButtonsAceptarCancelar(Context context) {
        super(context);
    }

    public ButtonsAceptarCancelar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ButtonsAceptarCancelar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ButtonsAceptarCancelar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        button0 = findViewById(R.id.negativeButton);
        button1 = findViewById(R.id.positiveButton);
    }

    public void setOnClickListenerButtonN(OnClickListener listener){
        button0.setOnClickListener(listener);
    }

    public void setOnClickListenerButtonP(OnClickListener listener){
        button1.setOnClickListener(listener);
    }

    public void setTextButtons(String negativeText, String positiveText){
        button0.setText(negativeText);
        button1.setText(positiveText);
    }

    public Button getButton0() {
        return button0;
    }

    public Button getButton1() {
        return button1;
    }

}
