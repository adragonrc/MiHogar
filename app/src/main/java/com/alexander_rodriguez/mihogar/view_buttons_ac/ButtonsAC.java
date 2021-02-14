package com.alexander_rodriguez.mihogar.view_buttons_ac;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.alexander_rodriguez.mihogar.R;

public class ButtonsAC extends LinearLayout {
    private Button button0;
    private Button button1;

    public ButtonsAC(Context context) {
        super(context);
    }

    public ButtonsAC(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ButtonsAC(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ButtonsAC(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        button0 = findViewById(R.id.negativeButton);
        button1 = findViewById(R.id.positiveButton);
    }

    public void setListener(Listener listener) {
        button0.setOnClickListener(listener::ocNegative);
        button1.setOnClickListener(listener::ocPositive);
    }

    public void setTextButtons(@Nullable String negativeText,@Nullable String positiveText){
        if(negativeText != null)button0.setText(negativeText);
        if(positiveText != null)button1.setText(positiveText);
    }

    public Button getButton0() {
        return button0;
    }

    public Button getButton1() {
        return button1;
    }

    public interface Listener {
        void ocPositive(View view);
        void ocNegative(View view);
    }
}
