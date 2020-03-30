package com.alexander_rodriguez.mihogar.alquilerusuario;

import android.view.View;

import com.alexander_rodriguez.mihogar.Base.BaseView;
import com.alexander_rodriguez.mihogar.Base.IBasePresenter;

import java.util.ArrayList;

public interface Interface {
    interface Presentador extends IBasePresenter {

    }
    interface Vista extends BaseView {
        void mostrarRecycleView(ArrayList<Item> list);
        void onClickItemListener(View view);
    }
}
