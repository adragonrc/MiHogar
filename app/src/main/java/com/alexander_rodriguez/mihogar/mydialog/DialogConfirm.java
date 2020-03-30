package com.alexander_rodriguez.mihogar.mydialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;

public  class DialogConfirm extends AppCompatDialogFragment {
    DialogInterfaz.DialogConfirmPresenter dcp;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("AALERTA")
                .setMessage("¿Seguro de realizar el pago?")
                .setPositiveButton("SI", positiveListener)
                .setNegativeButton("NO", negativeListener);
        return builder.create();

    }
    public void  showDiaglog(FragmentManager transaction, String tag, DialogInterfaz.DialogConfirmPresenter dcp){
        this.dcp = dcp;
        show(transaction,tag);
    }
    private DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dcp.positiveButtonListener();
        }
    };
    public DialogInterface.OnClickListener negativeListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    };
}
