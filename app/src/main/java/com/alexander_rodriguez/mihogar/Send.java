package com.alexander_rodriguez.mihogar;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.telephony.PhoneNumberUtils;

import androidx.core.content.FileProvider;

import java.io.File;

public class Send {
    private  Context context;
    public Send(Context context){
        this.context = context;
    }

    public static void sendForWhatsapp(Context context, String name, String toNumber){
        File pdfPath = new File(Environment.getExternalStorageDirectory(), PDF.DIRECTORI_NAME);
        File newFile = new File(pdfPath, name);
        Uri uri = FileProvider.getUriForFile(context, "com.alexander_rodriguez.mihogar.android.fileprovider", newFile);
        //String toNumber = "+51 947 782 227";

        toNumber = toNumber.replace("+", "").replace(" ", "");
        toNumber = PhoneNumberUtils.stripSeparators(toNumber);
        Intent sendIntent = new Intent("android.intent.action.MAIN");
        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
        sendIntent.putExtra("jid", toNumber + "@s.whatsapp.net");
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setPackage("com.whatsapp");
        sendIntent.setType("application/pdf");
        context.startActivity(sendIntent);
    }
    public static void sendForGMail(Context context, String name, String correo){
        String []mailTo = {correo};
        File pdfPath = new File(Environment.getExternalStorageDirectory(), PDF.DIRECTORI_NAME);
        File newFile = new File(pdfPath, name);
        Uri uri = FileProvider.getUriForFile(context, "com.alexander_rodriguez.mihogar.android.fileprovider", newFile);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, mailTo);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Voucher de alquiler");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Reciba el voucher de su pago");
        emailIntent.setType("application/pdf");
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(Intent.createChooser(emailIntent, "Send email using: "));
    }

}
