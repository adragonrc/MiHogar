package com.alexander_rodriguez.mihogar.Base;

import android.content.Context;

public interface BaseView{
    Context getContext();
    void showMessage(String mensaje);
    void solicitarPermiso();
}
