package com.alexander_rodriguez.mihogar.mydialog;

import android.view.View;

import androidx.annotation.Nullable;

public interface DialogInterfaz {
    interface DialogBaseView{
        String getTitle();
        String getMensaje();
        View getmView();
    }
    interface DialogConfirmPresenter{
        void positiveButtonListener();
    }
    interface DialogImputPresenter extends DialogBaseView{
        int getImputType();
        void setImputType(int imputTipe);
        void setHintView(String hint);
        String getHint();
        void positiveButtonListener(@Nullable String s);

    }
    interface DialogOptionPresenter {
        void OnClickOption1();
        void OnClickOption2();
        void OnClickOption3();
    }
}
