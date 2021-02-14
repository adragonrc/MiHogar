package com.alexander_rodriguez.mihogar.historialcasa;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.FragmentManager;

import com.alexander_rodriguez.mihogar.Base.BaseActivity;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.mi_casa.MyHouseFragment;
import com.alexander_rodriguez.mihogar.viewUser.DialogDetallesAlquiler;

public class HistorialCasaActivity extends BaseActivity<Interface.Presenter> implements Interface.View{

    public static final String ALL_USERS = "allUsers";

    public static final String USERS_OF_RENTAL = "usersOfRental";

    public static final String RENTALS_OF_USER = "rentalOfUsers";

    public static final String RENTALS_OF_ROOM = "rentalsOfRoom";

    public static final String ALL_RENTALS = "allRentals";

    public static final String MODE = "mode";

    public static final String EXTRA_ROOM_NUMBER = "extra_room_number";
    public static final String EXTRA_RENTAL_ID = "extra_rental_id";

    public static final String EXTRA_RENTAL_ID_IGNORE = "extraRentalIdToIgnore";
    public static final String EXTRA_DNI = "dni";

    @Override
    protected void iniciarComandos() {
        setTitle("Historial");
        ActionBar ab =  getSupportActionBar();
        if (ab != null){
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    @NonNull
    @Override
    protected Interface.Presenter createPresenter() {
        return new Presenter(this, getIntent());
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_historial_casa;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        presenter.crearMenu(getMenuInflater(), menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        presenter.itemSelected(item);

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void iniciarViews() {

    }


    @Override
    public void salir() {
        onBackPressed();
    }

    @Override
    public void goTo(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void showDialog(DialogDetallesAlquiler dialogDetallesAlquiler) {
    //    dialogDetallesAlquiler.show(getSupportFragmentManager(), TAG_MOSTRAR_PAGOS);
    }

    @Override
    public void showFragment(MyHouseFragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, fragment).commit();
    }
}
