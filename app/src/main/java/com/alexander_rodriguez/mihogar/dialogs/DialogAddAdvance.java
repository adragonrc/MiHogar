package com.alexander_rodriguez.mihogar.mydialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.alexander_rodriguez.mihogar.R;
import com.google.android.material.textfield.TextInputLayout;

public class DialogAddAdvance extends AppCompatDialogFragment {

    private Dialog dialog;
    private final View view;

    private final Button btCancel;
    private final Button btAccept;

    private final TextView tvPaymentAmount;
    private final EditText etAdvance;
    private final TextInputLayout til;
    private final Context mContext;

    private double amount;

    private Interface parent;

    public DialogAddAdvance(Interface parent){
        this.mContext = parent.getContext();
        this.parent = parent;
        view = LayoutInflater.from(mContext).inflate(R.layout.view_advance_payment, null, false);
        btAccept = view.findViewById(R.id.btAccept);
        btCancel = view.findViewById(R.id.btCancel);
        tvPaymentAmount = view.findViewById(R.id.tvPaymentAmount);
        etAdvance = view.findViewById(R.id.etAdvance);
        til = view.findViewById(R.id.til);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if (view != null)
            if  (view.getParent() != null)
                ((ViewGroup) view.getParent()).removeView(view);
        builder.setView(view);
        dialog = builder.create();
        etAdvance.setText("");

        if (getArguments() != null) {
            amount= getArguments().getDouble(mContext.getString(R.string.mdPaymentAmount));
            String sAmount = "S/. " + amount;
            tvPaymentAmount.setText(sAmount);
            btAccept.setOnClickListener(v->{
                try {
                    double auxAmount = Double.parseDouble(etAdvance.getText().toString());
                    if (auxAmount > amount) {
                        til.setError("El valor no puede ser mayor a " + sAmount);
                    } else {
                        til.setError(null);
                        parent.onAccept(dialog, auxAmount);
                    }
                }catch (NumberFormatException e){
                    e.printStackTrace();
                    parent.showError(mContext.getString(R.string.sError));
                }
            });
            btCancel.setOnClickListener(v->{
                parent.onCancel(dialog);
            });
        }
        return dialog;
    }


    public interface Interface {
        Context getContext();
        void onAccept(Dialog dialog, Double amount);
        void onCancel(Dialog dialog);

        void showError(String string);
    }
}
