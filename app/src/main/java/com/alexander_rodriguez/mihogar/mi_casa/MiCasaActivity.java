package com.alexander_rodriguez.mihogar.mi_casa;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import com.alexander_rodriguez.mihogar.Base.BaseActivity;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.historialcasa.AllUsersPresenter;
import com.alexander_rodriguez.mihogar.mydialog.DialogImput;
import com.alexander_rodriguez.mihogar.mydialog.DialogInterfaz;
import com.alexander_rodriguez.mihogar.mydialog.DialogOptions;
import com.alexander_rodriguez.mihogar.mydialog.PresenterDialogImput;
import com.alexander_rodriguez.mihogar.tableActivity.TableActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MiCasaActivity extends BaseActivity<Interface.Presenter> implements Interface.View {
    private DialogOptions dop;
    private DialogInterfaz.DialogOptionPresenter dialogOptionPresenter;
    private DialogImput imput ;
    private DialogInterfaz.DialogImputPresenter dialogImputPresenter;

    private MyHouseFragment roomFragment;
    private MyHouseFragment tenantFragment;
    private MyHouseFragment active;

    private FragmentManager fm;

    @Override
    protected void iniciarComandos() {
        setTitle("Mi Casa");
        dop = new DialogOptions();
        imput = new DialogImput();
        dialogOptionPresenter = new PresenterDialogOptions(dop);
        dialogImputPresenter =  new PresenterDialogImput(getContext(), "ALERTA") {
            @Override
            public void positiveButtonListener(@Nullable String s) {
                presenter.terminarAlquiler(s, imput.getTag());

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mi_casa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        presenter.onOptionItemSelected(item);
        
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mi_casa;
    }

    @NonNull
    @Override
    protected Interface.Presenter createPresenter() {
        return new Presenter(this);
    }

    @Override
    protected void iniciarViews() {

        roomFragment = new MyHouseFragment(this);
        tenantFragment = new MyHouseFragment(this);
        fm = getSupportFragmentManager();

        roomFragment.setPresenter(new AllRoomsPresenter(roomFragment, presenter));
        tenantFragment.setPresenter(new AllUsersPresenter(tenantFragment, presenter));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(presenter::onNavigationItemSelected);

        fm.beginTransaction().add(R.id.container, tenantFragment, "1").setMaxLifecycle(tenantFragment, Lifecycle.State.STARTED).commit();
        fm.beginTransaction().add(R.id.container, roomFragment, "2").setMaxLifecycle(roomFragment, Lifecycle.State.RESUMED).hide(tenantFragment).commit();
        active = roomFragment;
    }

    /*
        @Override
        public void mostrarUsuarios(ArrayList<ModelUserView> items) {
            adapterUsuarios = new RvAdapterUser(this, items);
            rv.setAdapter(adapterUsuarios);
            //registerForCoxtMenu(rv);
        }

        @Override
        public void mostrarAlquileres(ArrayList<ModelAlquilerView> alquilerViews) {
            adapterAlquiler = new RvAdapterAlquiler(this, alquilerViews);
            rv.setAdapter(adapterAlquiler);
            // registerForContextMenu(rv);
        }

        @Override
        public void onClickAlquiler(View view) {
            int dni  =Integer.valueOf(((TextView) view).getText().toString());
            Intent i = new Intent(this,VerUsuario.class);
            i.putExtra(TUsuario.DNI, dni);
            startActivity(i);
        }
        */

    @Override
    public void showDialogOptions(String idAlquiler) {
        dop.showDiaglog(getSupportFragmentManager(), idAlquiler, dialogOptionPresenter);
    }
    public void showDialogImput(final String idAlquiler){
        imput = new DialogImput();
        imput.showDiaglog(getSupportFragmentManager(), idAlquiler,dialogImputPresenter);
    }

    @Override
    public void nothingHere() {
    }

    @Override
    public void showRooms() {
        fm.beginTransaction().hide(active).setMaxLifecycle(active, Lifecycle.State.STARTED)
                .show(roomFragment).setMaxLifecycle(roomFragment, Lifecycle.State.RESUMED).commit();
        active = roomFragment;
    }

    @Override
    public void showTenants() {
        fm.beginTransaction().hide(active).setMaxLifecycle(active, Lifecycle.State.STARTED)
                .show(tenantFragment).setMaxLifecycle(tenantFragment, Lifecycle.State.RESUMED).commit();
        active = tenantFragment;
    }

    @Override
    public void goTo(Intent intent) {
        startActivity(intent);
    }


    private class PresenterDialogOptions implements DialogInterfaz.DialogOptionPresenter {
        private final DialogOptions dop;
        private PresenterDialogOptions(DialogOptions dop){
            this.dop = dop;
        }
        @Override
        public void OnClickOption1() {
            Toast.makeText(getContext(), "Op1", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void OnClickOption2() {
            Intent i = new Intent(getContext(), TableActivity.class);
            i.putExtra(TAlquiler.ID, dop.getTag());
            getContext().startActivity(i);
        }

        @Override
        public void OnClickOption3() {
            showDialogImput(dop.getTag());
        }
    }
}
