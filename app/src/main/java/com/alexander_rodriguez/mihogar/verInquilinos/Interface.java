package com.alexander_rodriguez.mihogar.verInquilinos;

import com.alexander_rodriguez.mihogar.Base.BaseView;
import com.alexander_rodriguez.mihogar.Base.IBasePresenter;

public interface Interface {
    interface Presenter extends IBasePresenter {
    }
    interface View extends BaseView {
        void agregarFragmento(String idAlquiler);
        void showError();
    }
}
