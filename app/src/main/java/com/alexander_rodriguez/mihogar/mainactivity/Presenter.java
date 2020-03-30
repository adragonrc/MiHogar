package com.alexander_rodriguez.mihogar.mainactivity;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.R;

public class Presenter extends BasePresenter<Interface.view> implements Interface.presenter {
    static final String SIN_REGISTROS = "-1";
    public Presenter(Interface.view view) {
        super(view);
    }

    @Override
    public void iniciarComandos(){
/*        if (!sp.getBoolean(DataBaseAdmin.KEY_START_SCRIPS,false)) {
            db.startScrips();
            SharedPreferences.Editor e = sp.edit();
            e.putBoolean(DataBaseAdmin.KEY_START_SCRIPS,true);
            e.commit();
        }
        */
        view.setID(getUser());
    }
    private String getUsuario(){
        return sp.getString(view.getContext().getString(R.string.id),"-1");
    }

    private String getContraseña(){
        return sp.getString(view.getContext().getString(R.string.password),"-1");
    }
    @Override
    public void ingresar(String usuario, String contraseña) {
        String id = getUsuario();
        String pass = getContraseña();
        if (id.equals("-1") || pass.equals("-1")) {
            view.showMensaje("no hay registros");
            return;
        }
        if (usuario.equals(id)) {
            if (contraseña.equals(pass)) {
                view.showMensaje("Bienvenido");
                view.ingresar();
            } else {
                view.negarIngreso();
                view.showMensaje("Contraseña Invalida");
            }
        } else {
            view.negarIngreso();
            view.showMensaje("Usuario Invalido");
        }
    }

    @Override
    public String getUser() {
        String user = getUsuario();
        if (user.equals(SIN_REGISTROS)){
            user = "";
        }
        return user;
    }
}
