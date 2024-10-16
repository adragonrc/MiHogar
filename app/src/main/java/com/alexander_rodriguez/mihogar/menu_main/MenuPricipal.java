package com.alexander_rodriguez.mihogar.menu_main;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.alexander_rodriguez.mihogar.Base.BaseActivity;
import com.alexander_rodriguez.mihogar.Base.IBasePresenter;
import com.alexander_rodriguez.mihogar.Preferencias;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.add_room.AgregarCuarto;
import com.alexander_rodriguez.mihogar.historial_casa.HistorialCasaActivity;
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
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        int config = R.id.imPreferences;
        if (id == config) {
            startActivity(new Intent(this, Preferencias.class));
        }else{
            if (id == R.id.imSignOut){
                alertDialog().show();
            }
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

    @Override
    public void showDialog(AppCompatDialogFragment dialog, String tag) {
        dialog.show(getSupportFragmentManager(), tag);
    }

    public void onClickMasCuartos(View view) {
        startActivity(new Intent(this, AgregarCuarto.class));
    }

    public void onClickHistorialCasa(View view) {
        Intent i = new Intent(this, HistorialCasaActivity.class);
        i.putExtra(HistorialCasaActivity.MODE, HistorialCasaActivity.ALL_USERS);
        startActivity(i);
    }

    public AlertDialog alertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mensaje").
                setMessage("¿Cerrar sesión?").
                setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.signOut();
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
