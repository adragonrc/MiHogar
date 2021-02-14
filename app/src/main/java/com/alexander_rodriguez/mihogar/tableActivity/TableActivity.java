package com.alexander_rodriguez.mihogar.tableActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import androidx.annotation.NonNull;

import com.alexander_rodriguez.mihogar.Base.BaseActivity;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemRental;
import com.alexander_rodriguez.mihogar.DataBase.parse.ParceRental;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.ViewMensualidad;
import com.alexander_rodriguez.mihogar.ViewPdfActivity;
import com.alexander_rodriguez.mihogar.viewForTable.ViewFilaOfPagos;


public class TableActivity extends BaseActivity<Interfaz.Presenter> implements Interfaz.view {
    public static final String EXTRA_RENTAL = "rental";

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
    protected Interfaz.Presenter createPresenter() {
        return new Presenter(this, getIntent());
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            presenter.crearPDF();
        }
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
    public void gotoShowPDF(String absolutePath, ParceRental datosAlquiler) {
        Intent intent = new Intent(this, ViewPdfActivity.class);

        intent.putExtra(ViewPdfActivity.EXTRA_PATH_PDF, absolutePath);
        intent.putExtra(TAlquiler.NUMERO_TEL, datosAlquiler.getPhoneNumber());
        intent.putExtra(TAlquiler.CORREO, datosAlquiler.getEmail());
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
                .setPositiveButton("Aceptar", (dialog, which) -> presenter.onPositive()).setNegativeButton("Cancelar", (dialog, which) -> {});
        builder.create().show();
    }

    public void ocListo(View view){
        onBackPressed();
    }

    @Override
    public void close() {
        finish();
    }
}

