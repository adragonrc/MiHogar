package com.alexander_rodriguez.mihogar.mydialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.alexander_rodriguez.mihogar.R;

public abstract class PresenterDialogImput implements DialogInterfaz.DialogImputPresenter {
    private int imputType;
    private String title;
    private View view;
    private Context mContext;
    private String hint;
    public PresenterDialogImput(Context context, String title){
        imputType = EditorInfo.TYPE_TEXT_FLAG_CAP_SENTENCES;
        this.title = title;
        mContext = context;
        hint = "valor: ";
    }
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getMensaje() {
        return null;
    }

    public void setImputType(int imputTipe){
        this.imputType = imputTipe;
    }

    public int getImputType() {
        return imputType;
    }

    @Override
    public View getmView() {
        view = LayoutInflater.from(mContext).inflate(R.layout.my_alert_dialog,null, false);
        return view;
    }
    @Override
    public void setHintView(String hint){
        this.hint = hint;
    }
    @Override
    public String getHint() {
        return hint;
    }
}
