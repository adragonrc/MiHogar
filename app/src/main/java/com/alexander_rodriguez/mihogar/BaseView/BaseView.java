package com.alexander_rodriguez.mihogar.BaseView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentManager;

public abstract class BaseView<P extends MvBasePresenter> implements MvBaseView {
    protected LayoutInflater inflater;
    protected Context mContext;
    protected FragmentManager transaction;
    protected View view;
    protected ViewGroup vg;
    P presenter;

    public BaseView(LayoutInflater inflater, Context mContext, FragmentManager transaction, ViewGroup vg){
        this.inflater = inflater;
        this.mContext = mContext;
        this.transaction = transaction;
        this.vg = vg;
        if (presenter!=null)
        presenter = getPresenter();
        view = inflater.inflate(getLayout(), vg, false);
        iniciar();
    }
    abstract public View createView(String... attrs);
    abstract protected P getPresenter();
    abstract protected void iniciar();
    abstract protected int getLayout();

}