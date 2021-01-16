package com.alexander_rodriguez.mihogar.mainactivity;

import android.view.View;

import com.alexander_rodriguez.mihogar.Base.BaseView;
import com.alexander_rodriguez.mihogar.Base.IBasePresenter;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface Interface {
    interface presenter extends IBasePresenter {

        String getUser();

        void signIn(String email, String pass);

        void signInWithGoogle(GoogleSignInAccount account);
    }
    interface view extends BaseView {
        void ingresar();

        void negarIngreso();

        void ocForgotPassword(View view);

        void setID(String s);
    }
}
