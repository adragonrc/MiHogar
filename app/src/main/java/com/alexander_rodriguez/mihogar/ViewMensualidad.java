package com.alexander_rodriguez.mihogar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexander_rodriguez.mihogar.BaseView.BaseView;
import com.alexander_rodriguez.mihogar.BaseView.MvBasePresenter;

public class ViewMensualidad extends BaseView {
    private TextView tvTitulo;
    private TextView tvFecha;

    public ViewMensualidad(LayoutInflater inflater, Context mContext, ViewGroup viewGroup) {
        super(inflater, mContext, null,viewGroup);
    }

    @Override
    public View createView(String... attrs) {
        if (attrs.length>1) {
            tvTitulo.setText(attrs[0]);
            tvFecha.setText(attrs[1]);
        }
        return view;
    }

    @Override
    protected MvBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void iniciar() {
        tvTitulo = view.findViewById(R.id.tvMensualidad);
        tvFecha = view.findViewById(R.id.tvFecha);
    }


    @Override
    protected int getLayout() {
        return R.layout.view_mensualidad;
    }
}
