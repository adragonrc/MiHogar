package com.alexander_rodriguez.mihogar.registrarcasa;

import android.content.Context;
import android.content.SharedPreferences;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.R;

public class Presentador extends BasePresenter<interfaz.view> implements interfaz.presentador {
    public Presentador(interfaz.view view) {
        super(view);
    }

    private boolean validarImputs(String ...s){
        for (String s1 : s)
            if (s1.equals("")) {
                view.showMessage("Campo vacio");
                return false;
            }
        return true;
    }
    @Override
    public void ingresar(String dir, String corr) {
        if(!validarImputs( dir, corr)) {
            view.showMessage("Campo vacio");
            return;
        };
        Context c = view.getContext();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(c.getString(R.string.direccion), dir);
        editor.putString(c.getString(R.string.correo), corr);
        editor.putBoolean(c.getString(R.string.start), true);
        editor.apply();
        view.avanzar();
    }

    @Override
    public void iniciarComandos() {
        Context c = view.getContext();
        if(sp.getBoolean(c.getString(R.string.start), false)){
            view.avanzar();
        }else {

        }
    }

    public void onResume(){
        Context c = view.getContext();
        if(sp.getBoolean(c.getString(R.string.start), false)){
            view.salir();
        }
    }
}
