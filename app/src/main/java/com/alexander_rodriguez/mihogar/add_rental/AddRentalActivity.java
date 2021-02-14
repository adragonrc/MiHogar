package com.alexander_rodriguez.mihogar.add_rental;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.Base.BaseActivity;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemTenant;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.adapters.RvAdapterUser;
import com.alexander_rodriguez.mihogar.viewregistraralquiler.AddRentalView;
import com.alexander_rodriguez.mihogar.UTILIDADES.TCuarto;
import com.alexander_rodriguez.mihogar.UTILIDADES.TUsuario;
import com.alexander_rodriguez.mihogar.view_registrar_usuario.AddUserView;
import com.alexander_rodriguez.mihogar.add_room.AgregarCuarto;
import com.alexander_rodriguez.mihogar.historialUserPakage.HistorialUsuarioActivity;
import com.alexander_rodriguez.mihogar.viewregistraralquiler.ModelAA;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.theartofdev.edmodo.cropper.CropImage;

public class AddRentalActivity extends BaseActivity<Interfaz.presenter> implements Interfaz.view{


    private RecyclerView recyclerView;

    private boolean cancelDialog;

    private AddUserView addUserView;

    private AddRentalView addRentalView;

    private SlidingUpPanelLayout sliding;

    private TextView tvAviso;

    private Menu mMenu;
    private final DialogInterface.OnClickListener positivo = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Intent i = new Intent(getContext(), HistorialUsuarioActivity.class);
            i.putExtra(TUsuario.DNI, addUserView.getDniText());
            getContext().startActivity(i);
        }
    };
    private final DialogInterface.OnClickListener negativo = (dialog, which) -> presenter.confirmar();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ocultarTeclado(addUserView.getEtDNI());
    }



    @Override
    protected void iniciarComandos() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        ocultarTeclado(addUserView.getEtDNI());

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_add_list_usuarios;
    }

    @NonNull
    @Override
    protected Interfaz.presenter createPresenter() {
        return new Presenter(this);
    }

    @Override
    protected void iniciarViews() {
        ActionBar ab = getSupportActionBar();
        if (ab != null) ab.setTitle(getString(R.string.title_add_rental));
        recyclerView = findViewById(R.id.recyclerView);
        addUserView = findViewById(R.id.regCuarto);
        addRentalView = findViewById(R.id.agregarAlquiler);
        tvAviso = findViewById(R.id.tvVacio);
        sliding = findViewById(R.id.slide);
        //sliding.setAnchorPoint(0.5f);

        addRentalView.setParent(this);
        addUserView.setParent(this);
        addRentalView.setOnClickListener(v -> { });

        sliding.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {

            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                switch (newState){
                    case EXPANDED:{
                        if(addUserView.getVisibility() == View.VISIBLE)
                            addUserView.onExpanded();
                         else addRentalView.onExpanded();
                        break;
                    }
                    case COLLAPSED:{
                        if(addUserView.getVisibility() == View.VISIBLE)
                            addUserView.onCollapsed();
                        else addRentalView.onCollapsed();
                            AddRentalActivity.this.ocultarTeclado();
                        break;
                    }
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_user, menu);
        this.mMenu  = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int option = item.getItemId();
        if (option == R.id.item_add_user) {
            presenter.onClickAddUser();
        } else if (option == BACK_PRESSED) {
            onBackPressed();
        }

        presenter.onOptionItemSelected(option);
        return super.onOptionsItemSelected(item);
    }

    public void startRegistroUsuario(){

    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (result != null) {
                if (resultCode == RESULT_OK) {
                    addUserView.setPhoto(result.getUri().getPath());
                } else {
                    if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Exception e = result.getError();
                        Toast.makeText(this, "Posible Error es: " + e, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClickTomarFoto(View view) {
        CropImage.activity().setMaxCropResultSize(0,0)
                .setAllowFlipping(true)
                .setAspectRatio(15,10)
                .setMinCropResultSize(1000,750)
                .setMaxCropResultSize(3000,2250)
                .start(this);
    }

    @Override
    public void ocPositiveAddUser(View view) {
        /*if (view == agregarAlquilerView.getPositive()) {

        }else*/
        presenter.agregarUsuario(addUserView.getDatos());
    }

    @Override
    public void ocNegativeAdd(View view) {
        onBackPressed();
    }

    @Override
    public void ocPositiveEdit(View view) {
        presenter.saveUserChanges(addUserView.getModelEdit());
    }

    @Override
    public void ocNegativeEdit(View view) {
        editUserComplete();
        onBackPressed();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, "Campo vacio: "+ error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDialog(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(s).
                setMessage("Usuario ya existe").
                setPositiveButton("ver historia", positivo).
                setNegativeButton("Agregar", negativo);
        builder.create().show();
    }

    public void sinCuartos() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mensaje").setMessage("No hay cuartos disponibles")
                .setPositiveButton("Agregar nuevo", (dialog, which) -> {
                    cancelDialog = false;
                    startActivity(new Intent(AddRentalActivity.this, AgregarCuarto.class));
                }).setNegativeButton("Salir", (dialog, which) -> onBackPressed());
        AlertDialog a =builder.create();
        a.setOnDismissListener(dialog -> {
            if (cancelDialog) {
                AddRentalActivity.this.onBackPressed();
            }
        });
        a.setOnCancelListener(dialog -> cancelDialog = true);
        a.show();
    }

    @Override
    public void close(int result_code) {
        setResult(result_code);
        finish();
    }

    public void addUserComplete(){
        if (tvAviso.getVisibility() == View.VISIBLE){
            tvAviso.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        addUserView.clear();
        sliding.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }

    @Override
    public void editUserComplete() {
        addUserView.clear();
        sliding.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        addUserView.finishEdit();
    }

    @Override
    public void addMenu(int menu_add_user_options) {
        getMenuInflater().inflate(menu_add_user_options, mMenu);
    }

    @Override
    public void removeMenuItem(int item) {
        mMenu.removeItem(item);
    }

    public void onClickAddUser(View v){
        addRentalView.setVisibility(View.GONE);
        addUserView.setVisibility(View.VISIBLE);
        sliding.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }
    public void onClickAvanzar(View v){

        String numCuarto = getIntent().getStringExtra(TCuarto.NUMERO);
        ArrayAdapter<String > adapter = presenter.getAdapterCuartos();
        addRentalView.prepararSpinenr(adapter, numCuarto);
        addUserView.setVisibility(View.GONE);
        addRentalView.setVisibility(View.VISIBLE);

        sliding.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        presenter.avanzar();
    }



    @Override
    public void onClickHolder(RecyclerView.ViewHolder holder) {
        presenter.ocHolder(holder);
    }

    @Override
    public void onLongClick(RecyclerView.ViewHolder holder) {
        presenter.onLongClick(holder);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo, RecyclerView.ViewHolder holder) {

    }

    @Override
    public SlidingUpPanelLayout.PanelState getPanelState() {
        return  sliding.getPanelState();
    }

    @Override
    public void panelCollapsed() {
        sliding.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }

    @Override
    public void setTitle(String title, boolean f) {
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
            ab.setDisplayHomeAsUpEnabled(f);
        }
    }

    @Override
    public void editUser(ItemTenant model) {
        addUserView.onUpdateUser(model);
        addRentalView.setVisibility(View.GONE);
        addUserView.setVisibility(View.VISIBLE);
        sliding.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }

    @Override
    public void setAdapter(RvAdapterUser adapterUser) {
        recyclerView.setAdapter(adapterUser);
    }

    @Override
    public void saveRental(ModelAA model) {
        presenter.agregarAlquilerNuevo(model);
    }

    @Override
    public void cancelAddRental() {
        onBackPressed();
    }
}
