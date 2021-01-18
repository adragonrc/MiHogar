package com.alexander_rodriguez.mihogar.registrarcasa.register;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.AttributeSet;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.registrarcasa.details.DetailsView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterView extends LinearLayout {
    private EditText etEmail;
    private EditText etPass;
    private EditText etConfirmPass;

    public RegisterView(Context context) {
        super(context);
    }

    public RegisterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RegisterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static DetailsView newInstance(Context context, AttributeSet attrs) {
        return new DetailsView(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);
        etConfirmPass = findViewById(R.id.etConfirmPass);
    }

    public String getEmail() {
        return etEmail.getText().toString();
    }

    public String getPass() {
        return etPass.getText().toString();
    }

    public String getConfirmPass() {
        return etConfirmPass.getText().toString();
    }

    public void setEmail(String names) {
        etEmail.setText(names);
    }
    public void setPass(String lastNames) {
        etPass.setText(lastNames);
    }
    public void setConfirmPass(String details) {
        etConfirmPass.setText(details);
    }

    public String getError(){
        if(getEmail().equals("") || !Patterns.EMAIL_ADDRESS.matcher(getEmail().trim()).matches()) return getContext().getString(R.string.email_error);
        if(isIncorrect(getPass(), getResources().getInteger(R.integer.min_length_pass))) return getContext().getString(R.string.pass_error);
        if(isIncorrect(getConfirmPass(), getResources().getInteger(R.integer.min_length_pass)) || !getPass().equals(getConfirmPass())) return getContext().getString(R.string.confirm_pass_error);
        return null;
    }

    private boolean isIncorrect(String s, int count){
        return s.equals("") || s.length() < count;
    }
}