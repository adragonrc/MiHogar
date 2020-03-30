package com.alexander_rodriguez.mihogar.verInquilinos;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;

public class Presenter extends BasePresenter<Interface.View> implements Interface.Presenter {
    private String idAlquileres[];

    public Presenter(Interface.View view) {
        super(view);
    }

    @Override
    public void iniciarComandos(){
    }
}
