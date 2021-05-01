package com.alexander_rodriguez.mihogar.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.alexander_rodriguez.mihogar.R;

public class ConfirmEmailDialog extends AppCompatDialogFragment {
    public static final String ARG_NAME = "arg_name";
    public static final String ARG_EMAIL = "arg_email";
    private final Context mContext;
    private final View view;

    public ConfirmEmailDialog(Context c){
        mContext = c;
        view = LayoutInflater.from(mContext).inflate(R.layout.view_email_confirm, null);
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (view != null)
            if  (view.getParent() != null)
                ((ViewGroup) view.getParent()).removeView(view);


        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(view);
        Dialog dialog = builder.create();
        Bundle data = getArguments();
        if (data != null) {

            String name = data.getString(ARG_NAME);
            String email = data.getString(ARG_EMAIL);

            String title = mContext.getString(R.string.s_email_confirm_title).concat(" ").concat(name == null? "": name);
            String message =" ".concat(
                    mContext.getString(R.string.s_email_confirm_message_start))
                    .concat(" ")
                    .concat(email == null ? "": email)
                    .concat(" ")
                    .concat(mContext.getString(R.string.s_email_confirm_message_end));

            ((TextView) view.findViewById(R.id.tvTitle)).setText(title);
            ((TextView) view.findViewById(R.id.tvMessage)).setText(message);
        }
        return dialog;
    }

    public void setSendButtonOCListener(View.OnClickListener listener){
        if (view != null) view.findViewById(R.id.btSendEmail).setOnClickListener(listener);
    }
}
