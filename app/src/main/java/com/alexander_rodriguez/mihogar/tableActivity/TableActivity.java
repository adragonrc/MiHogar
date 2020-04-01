package com.alexander_rodriguez.mihogar.tableActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import androidx.annotation.NonNull;

import com.alexander_rodriguez.mihogar.Base.BaseActivity;
import com.alexander_rodriguez.mihogar.Base.IBasePresenter;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.UTILIDADES.TUsuario;
import com.alexander_rodriguez.mihogar.ViewMensualidad;
import com.alexander_rodriguez.mihogar.ViewPdfActivity;
import com.alexander_rodriguez.mihogar.viewForTable.ViewFilaOfPagos;


public class TableActivity extends BaseActivity<Presenter> implements Interfaz.view {
    private LinearLayout llPagos;

    @Override
    protected void iniciarComandos() {
        setTitle("Historial de pagos");
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_table;
    }

    @NonNull
    @Override
    protected Presenter createPresenter() {
        return new Presenter(this,getIntent().getStringExtra(TAlquiler.ID));
    }

    @Override
    protected void iniciarViews() {
        llPagos = findViewById(R.id.llPagos);
    }

    @Override
    public void addRow(String ...atts) {
        ViewFilaOfPagos vfp = new ViewFilaOfPagos(getLayoutInflater(),this, null);
        llPagos.addView(vfp.createView(atts));
    }

    @Override
    public void addTitleMensualidad(String s, String s1) {
        ViewMensualidad mtv = new ViewMensualidad(getLayoutInflater(),this,null);
        llPagos.addView(mtv.createView(s, s1));
    }

    @Override
    public void gotoShowPDF(String absolutePath, ContentValues datosUsuario) {
        Intent intent = new Intent(this, ViewPdfActivity.class);
        intent.putExtra(ViewPdfActivity.EXTRA_PATH_PDF, absolutePath);
        intent.putExtra(TUsuario.NUMERO_TEL, datosUsuario.getAsString(TUsuario.NUMERO_TEL));
        intent.putExtra(TUsuario.CORREO, datosUsuario.getAsString(TUsuario.CORREO));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void addTable(TableLayout tl) {
        llPagos.addView(tl);
    }

    @Override
    public ViewGroup getGrup() {
        return llPagos;
    }

    @Override
    public void showDialog(String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(mensaje)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.onPositive();
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    public void ocListo(View view){
        onBackPressed();
    }
}

