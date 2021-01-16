package com.alexander_rodriguez.mihogar.Base;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alexander_rodriguez.mihogar.DataBase.DBInterface;
import com.alexander_rodriguez.mihogar.DataBase.DataBaseAdmin;
import com.alexander_rodriguez.mihogar.DataBase.FDAdministrator;
import com.alexander_rodriguez.mihogar.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public abstract class BasePresenter<V extends BaseView> implements IBasePresenter{
    public static int P_MENSUAL = 0;
    public static int P_DIARIO = 1;
    public static int P_HORAS = 2;
    public static int P_MINUTOS = 3;
    private V mMvpView;
    protected V view;
    protected DBInterface db;
    protected SharedPreferences sp;
    public BasePresenter(V view){
        this.view = view;
/*
        db = new DataBaseAdmin(view.getContext(),null,1);
        db.getWritableDatabase();
*/
        db = new FDAdministrator(view.getContext());
        sp = PreferenceManager.getDefaultSharedPreferences(view.getContext());

        if(db.getCurrentUser() == null ) {
            userNotLogin();
        }
        else{
            db.initData();
            userLogin();
        }
    }

    protected void userLogin() {

    }

    protected void userNotLogin(){

    }

    public void attachView(V mvpView) {
        mMvpView = mvpView;
    }

    public void detachView() {
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public static boolean validarStrings(String ...s){
        for (String s1: s) {
            if (s1 == null || s1.equals("")) return false;
        }
        return true;
    }

    @Override
    public void signOut() {
        db.signOut();
        view.goToLogin();
    }
}
