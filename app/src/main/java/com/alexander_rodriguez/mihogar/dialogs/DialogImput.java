package com.alexander_rodriguez.mihogar.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;

import com.alexander_rodriguez.mihogar.R;

public class DialogImput extends AppCompatDialogFragment {
    private View mView;
    private EditText mEditText;
    private DialogInterfaz.DialogImputPresenter dip;
    private AlertDialog a;

    private View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus){
                a.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        }
    };
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        mView = dip.getmView();

        ((EditText)mView.findViewById(R.id.dialogEditText)).setHint(dip.getHint());
        mEditText = mView.findViewById(R.id.dialogEditText);
        mEditText.setOnFocusChangeListener(onFocusChangeListener);
        mEditText.setInputType(dip.getImputType());
        if (mView.getParent() == null) {
            builder.setView(mView);
        }
        builder.setTitle(dip.getTitle())
                .setPositiveButton("Aceptar", positiveListener)
                .setNegativeButton("Cancelar", negativeListener);
        a = builder.create();
        mEditText.requestFocus();
        return a;
    }

    public void showDiaglog(FragmentManager transaction, String tag, DialogInterfaz.DialogImputPresenter dip){
        this.dip = dip;
        show(transaction,tag);
    }

    private DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dip.positiveButtonListener(mEditText.getText().toString());
        }
    };

    private DialogInterface.OnClickListener negativeListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    };
}
