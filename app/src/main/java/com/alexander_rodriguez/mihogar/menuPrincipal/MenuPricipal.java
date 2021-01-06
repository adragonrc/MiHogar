package com.alexander_rodriguez.mihogar.menuPrincipal;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.alexander_rodriguez.mihogar.Base.BaseActivity;
import com.alexander_rodriguez.mihogar.Base.IBasePresenter;
import com.alexander_rodriguez.mihogar.Preferencias;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.agregarcuarto.AgregarCuarto;
import com.alexander_rodriguez.mihogar.historialcasa.HistorialCasaActivity;
import com.alexander_rodriguez.mihogar.add_rental.AddRentalActivity;
import com.alexander_rodriguez.mihogar.mi_casa.MiCasaActivity;
import com.alexander_rodriguez.mihogar.registrarcasa.RegistrarCasaActivity;

public class MenuPricipal extends BaseActivity<IBasePresenter> implements Interface.View {

    @Override
    protected void iniciarComandos() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_menu_principal;
    }

    @NonNull
    @Override
    protected IBasePresenter createPresenter() {
        return new Presentador(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_configuraciones, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        int config = R.id.item_Ajustes;
        if (id == config) {
            startActivity(new Intent(this, Preferencias.class));
        }else{
            /*if (id == R.id.about){
                alertDialog().show();
            }*/
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void iniciarViews() {
        setTitle("Menu Principal");
    }

    public void onClickMasAlquiler(View view) {
        startActivity(new Intent(this, AddRentalActivity.class));
    }

    @Override
    public void onClickMiCasa(View view) {
        startActivity(new Intent(this, MiCasaActivity.class));
    }

    @Override
    public void showRegistrarCasa() {
        startActivity(new Intent(this, RegistrarCasaActivity.class));
    }

    public void onClickMasCuartos(View view) {
        startActivity(new Intent(this, AgregarCuarto.class));
    }

    public void onClickHistorialCasa(View view) {
        Intent i = new Intent(this, HistorialCasaActivity.class);
        i.putExtra(HistorialCasaActivity.MODE, HistorialCasaActivity.ALL_RENTALS);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        alertDialog().show();
    }

    public AlertDialog alertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mensaje").
                setMessage("¿Desea salir?").
                setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        MenuPricipal.this.finish();
                       /* Intent i = new Intent(MenuPricipal.this, RegistrarCasaActivity.class);

                        i.putExtra(RegistrarCasaActivity.ON_EXIT, true);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
*/
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }
}
