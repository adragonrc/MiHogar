package com.alexander_rodriguez.mihogar.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;

import com.alexander_rodriguez.mihogar.R;

public class DialogOptions extends AppCompatDialogFragment {
    private View mView;
    private Button b1;
    private Button b2;
    private Button b3;
    DialogInterfaz.DialogOptionPresenter dop;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        mView = getLayoutInflater().inflate(R.layout.option_dialog, null);
        b1 = mView.findViewById(R.id.btn1);
        b2 = mView.findViewById(R.id.btn2);
        b3 = mView.findViewById(R.id.btn3);
        setListener();
        builder.setView(mView);
        builder.setTitle("opciones");
        return builder.create();
    }
    private void setListener(){
        b1.setOnClickListener(option1);
        b2.setOnClickListener(option2);
        b3.setOnClickListener(option3);
    }
    public void showDiaglog(FragmentManager transaction, String tag, DialogInterfaz.DialogOptionPresenter dip){
        this.dop = dip;
        show(transaction,tag);
    }
    private View.OnClickListener option1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dop.OnClickOption1();
        }
    };
    private View.OnClickListener option2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dop.OnClickOption2();
        }
    };
    private View.OnClickListener option3 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dop.OnClickOption3();
        }
    };

}
