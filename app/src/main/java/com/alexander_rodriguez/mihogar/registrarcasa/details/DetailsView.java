package com.alexander_rodriguez.mihogar.registrarcasa.details;

import android.content.Context;

import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alexander_rodriguez.mihogar.DataBase.models.THouse;
import com.alexander_rodriguez.mihogar.R;

public class DetailsView extends LinearLayout {
    private EditText etNames;
    private EditText etLastNames;
    private EditText etDetails;

    public DetailsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public static DetailsView newInstance(Context context, AttributeSet attrs) {
        return new DetailsView(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        etNames = findViewById(R.id.etName);
        etLastNames = findViewById(R.id.etLastName);
        etDetails = findViewById(R.id.etDetails);
    }

    public String getNames() {
        return etNames.getText().toString();
    }
    public String getLastNames() {
        return etLastNames.getText().toString();
    }
    public String getDetails() {
        return etDetails.getText().toString();
    }
    public void setNames(String names) {
        etNames.setText(names);
    }
    public void setLastNames(String lastNames) {
        etLastNames.setText(lastNames);
    }
    public void setDetails(String details) {
        etDetails.setText(details);
    }

    private int getInteger(int id){
        return getResources().getInteger(id);
    }
    public String getError(){
        if(isIncorrect(getNames(), getInteger(R.integer.count_max_name))) return getContext().getString(R.string.name_error);
        if(isIncorrect(getLastNames(), getInteger(R.integer.count_max_last_name))) return getContext().getString(R.string.last_ame_error);
        if(isIncorrect(getDetails(), getInteger(R.integer.count_max_details))) return getContext().getString(R.string.details_error);
        return null;
    }

    private boolean isIncorrect(String s, int count){
        return s.equals("") || s.length() > count;
    }

    private String getString(EditText et) {
        return et.getText().toString();
    }

    public THouse toTHouse() {
        return new THouse(getNames(), getLastNames(), getDetails());
    }
}