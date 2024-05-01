package com.alexander_rodriguez.mihogar.mainactivity;

import android.content.Intent;
import android.view.View;

import com.alexander_rodriguez.mihogar.Base.BaseView;
import com.alexander_rodriguez.mihogar.Base.IBasePresenter;
import com.alexander_rodriguez.mihogar.forgot_password.ForgotPasswordFragment;
import com.alexander_rodriguez.mihogar.forgot_password.FragmentInterface;
import com.alexander_rodriguez.mihogar.historial_casa.FragmentParent;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface Interface {
    interface presenter extends IBasePresenter{

        String getUser();

        void signIn(String email, String pass);

        void signInWithGoogle(GoogleSignInAccount account);

        void ocForgotPassword();
    }
    interface view extends BaseView {
        void ingresar();

        void showLogin();

        void negarIngreso();

        void ocForgotPassword(View view);

        void setID(String s);

        void goToRegister(String mode);

        void finish();

        void showFragment(ForgotPasswordFragment forgotPass, String name);
    }
}
