package com.alexander_rodriguez.mihogar.mainactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.DataBase.DBInterface;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.dialogs.ConfirmEmailDialog;
import com.alexander_rodriguez.mihogar.forgot_password.ForgotPasswordFragment;
import com.alexander_rodriguez.mihogar.registrarcasa.RegistrarCasaActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

public class Presenter extends BasePresenter<Interface.view> implements Interface.presenter {
    static final String SIN_REGISTROS = "-1";

    public Presenter(Interface.view view) {
        super(view);
    }

    @Override
    public void iniciarComandos(){

    }

    @Override
    protected void userLogin() {
        db.getHouseDR()
                .addOnSuccessListener(this::getHouseSuccess)
                .addOnFailureListener(this::getHouseFailure);
    }

    @Override
    protected void userNotLogin() {
        view.showLogin();
    }

    private void getHouseFailure(Exception e) {

    }

    private void getHouseSuccess(DocumentSnapshot documentSnapshot) {
        if(documentSnapshot.exists()){
            view.ingresar();

        }else{
            view.goToRegister(RegistrarCasaActivity.EXTRA_ONLY_DETAILS);
            view.finish();
        }
    }

    private String getUsuario(){
        return sp.getString(view.getContext().getString(R.string.email),"-1");
    }

    private String getContraseña(){
        return sp.getString(view.getContext().getString(R.string.password),"-1");
    }

    @Override
    public String getUser() {
        String user = getUsuario();
        return user.equals(SIN_REGISTROS) ? "" : user;
    }

    @Override
    public void signIn(String email, String pass) {
        db.sigIn(email, pass).addOnSuccessListener(this::signInSuccess).addOnFailureListener(this::signInFailure);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(view.getContext().getString(R.string.email), email);
        edit.apply();
    }

    @Override
    public void signInWithGoogle(GoogleSignInAccount account) {
        db.sigInWithGoogle(account).addOnSuccessListener(this::signInWithGoogleSuccess).addOnFailureListener(this::signInWithGoogleFailure);
    }

    @Override
    public void ocForgotPassword() {
        ForgotPasswordFragment forgotPass = new ForgotPasswordFragment();
        view.showFragment(forgotPass, "ForgotPass");
    }

    private void signInWithGoogleFailure(Exception e) {
        // If sign in fails, display a message to the user.
        e.printStackTrace();
        view.negarIngreso();
    }

    private void signInWithGoogleSuccess(AuthResult task) {

    }

    private void signInFailure(Exception e) {
        view.negarIngreso();
    }

    private void signInSuccess(AuthResult authResult) {
        view.ingresar();
    }
}
