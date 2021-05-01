package com.alexander_rodriguez.mihogar.forgot_password;

import android.util.Log;

import androidx.annotation.NonNull;

import com.alexander_rodriguez.mihogar.historial_casa.FragmentParent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Presenter implements FragmentInterface.presenter {
    private FragmentInterface.view view;
    public Presenter( FragmentInterface.view view){
        this.view= view;
    }

    @Override
    public void ocNext(String emailAddress) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            view.salir();
                        }else{
                            view.showMessage("No se pudo concluir la accion");
                        }
                    }
                });
    }
}
