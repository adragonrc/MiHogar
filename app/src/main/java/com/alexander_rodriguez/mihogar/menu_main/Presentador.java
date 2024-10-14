package com.alexander_rodriguez.mihogar.menu_main;

import android.os.Bundle;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.DataBase.models.THouse;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.dialogs.ConfirmEmailDialog;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;


public class Presentador extends BasePresenter<Interface.View>  {
    public Presentador(Interface.View view) {
        super(view);
    }


    @Override
    public void iniciarComandos() {

    }

    @Override
    protected void userLogin() {
        FirebaseUser user = db.getCurrentUser();
        if (!user.isEmailVerified()){

            db.getHouseDR()
                    .addOnSuccessListener(this::getHouseSuccess);

        }
    }

    private void getHouseSuccess(DocumentSnapshot documentSnapshot) {
        FirebaseUser user = db.getCurrentUser();
        THouse house = documentSnapshot.toObject(THouse.class);
        if (house != null) {
            Bundle args = new Bundle();
            args.putString(ConfirmEmailDialog.ARG_NAME, house.getNames());
            args.putString(ConfirmEmailDialog.ARG_EMAIL, user.getEmail());

            ConfirmEmailDialog dialog = new ConfirmEmailDialog(view.getContext());
            dialog.setArguments(args);
            dialog.setSendButtonOCListener(v -> {
                db.getCurrentUser().
                        sendEmailVerification()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                dialog.dismiss();
                            } else {
                                view.showMessage(view.getContext().getString(R.string.s_error_performing_action));
                            }
                        });

            });
            view.showDialog(dialog, "confirm_email");
        }
    }
}
