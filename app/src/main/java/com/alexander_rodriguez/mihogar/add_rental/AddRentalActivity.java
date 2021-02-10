package com.alexander_rodriguez.mihogar.add_rental;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.adapters.AdapterInterface;
import com.alexander_rodriguez.mihogar.adapters.RvAdapterUser;
import com.alexander_rodriguez.mihogar.Base.BaseActivity;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemTenant;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.viewregistraralquiler.AgregarAlquilerView;
import com.alexander_rodriguez.mihogar.UTILIDADES.TCuarto;
import com.alexander_rodriguez.mihogar.UTILIDADES.TUsuario;
import com.alexander_rodriguez.mihogar.view_registrar_usuario.RegistroUsuarioView;
import com.alexander_rodriguez.mihogar.agregarcuarto.AgregarCuarto;
import com.alexander_rodriguez.mihogar.historialUserPakage.HistorialUsuarioActivity;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;

public class AddRentalActivity extends BaseActivity<Interfaz.presenter> implements Interfaz.view, AdapterInterface {
    static final int CODE_0 = 0;

    private RvAdapterUser adapterUser;

    private RecyclerView recyclerView;

    private ArrayList<ItemTenant> rvAdapter;

    private boolean cancelDialog;

    private RegistroUsuarioView ruv;

    private AgregarAlquilerView agregarAlquilerView;

    private SlidingUpPanelLayout sliding;

    private TextView tvAviso;

    private ImageButton btAddTenant;

    private DialogInterface.OnClickListener positivo = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Intent i = new Intent(getContext(), HistorialUsuarioActivity.class);
            i.putExtra(TUsuario.DNI, ruv.getDniText());
            getContext().startActivity(i);
        }
    };
    private DialogInterface.OnClickListener negativo = (dialog, which) -> {
        presenter.confirmar();

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ocultarTeclado(ruv.getEtDNI());
    }

    @Override
    protected void iniciarComandos() {
        rvAdapter = new ArrayList<>();
        adapterUser = new RvAdapterUser(this, rvAdapter, true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterUser);
        ocultarTeclado(ruv.getEtDNI());

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
        recyclerView = findViewById(R.id.recyclerView);
        ruv = findViewById(R.id.regCuarto);
        agregarAlquilerView = findViewById(R.id.agregarAlquiler);

        tvAviso = findViewById(R.id.tvVacio);

        sliding = findViewById(R.id.slide);
        //sliding.setAnchorPoint(0.5f);

        btAddTenant = findViewById(R.id.btAddTenant);

        agregarAlquilerView.setOnClickListener(v -> { });

        sliding.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {

            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                switch (newState){
                    case EXPANDED:{
                        if(ruv.getVisibility() == View.VISIBLE)
                            ruv.onExpanded();
                         else agregarAlquilerView.onExpanded();
                        break;
                    }
                    case COLLAPSED:{
                        if(ruv.getVisibility() == View.VISIBLE)
                            ruv.onCollapsed();
                        else agregarAlquilerView.onCollapsed();
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int option = item.getItemId();
        switch (option){
            case R.id.item_add_user:{
                presenter.onClickAddUser();
                break;
            }
            case BACK_PRESSED:{
                onBackPressed();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void startRegistroUsuario(){

    }

    private void exit(){
        super.onBackPressed();
    }
    @Override
    public void onBackPressed() {
        SlidingUpPanelLayout.PanelState mSlideState = sliding.getPanelState();
        if (mSlideState != SlidingUpPanelLayout.PanelState.EXPANDED && mSlideState != SlidingUpPanelLayout.PanelState.ANCHORED && mSlideState != SlidingUpPanelLayout.PanelState.DRAGGING) {
            if(presenter.saveChanges())
                super.onBackPressed();
            else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("¿Deseas salir?").
                        setMessage("Es posible que los cambios que implementaste no se puedan guardar.").
                        setPositiveButton("Aceptar", (dialogInterface, i) -> exit()).
                        setNegativeButton("Cancelar", (dialogInterface, i) -> {});
                builder.create().show();
            }
        } else {
            sliding.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            sliding.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        }
        if (mSlideState != SlidingUpPanelLayout.PanelState.EXPANDED && mSlideState != SlidingUpPanelLayout.PanelState.ANCHORED && mSlideState != SlidingUpPanelLayout.PanelState.DRAGGING){
            sliding.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            sliding.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                ruv.setPhoto(result.getUri().getPath());
            }else{
                if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    Exception e = result.getError();
                    Toast.makeText(this, "Posible Error es: "+ e, Toast.LENGTH_SHORT).show();
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
                .setPositiveButton("Agregar nuevo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cancelDialog = false;
                        startActivity(new Intent(AddRentalActivity.this, AgregarCuarto.class));
                    }
                }).setNegativeButton("Salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onBackPressed();
            }
        });
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
    public void close() {
        finish();
    }


    @Override
    public void onClickPositive(View v) {
        if (v == agregarAlquilerView.getPositive()) {
            presenter.agregarAlquilerNuevo(agregarAlquilerView.getData());
        }else{
            presenter.agregarUsuario(ruv.getDatos());
        }
    }

    @Override
    public void onClickNegative(View v) {
        onBackPressed();
    }

    public void mostrarNuevoUsuario(ItemTenant m){
        if (tvAviso.getVisibility() == View.VISIBLE){
            tvAviso.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        adapterUser.addItem(m);
        ruv.clear();
        sliding.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }

    public void doPrincipal(RvAdapterUser.Holder holder){
        adapterUser.isMain(holder);
    }

    public void onClickAddUser(View v){
        agregarAlquilerView.setVisibility(View.GONE);
        ruv.setVisibility(View.VISIBLE);
        sliding.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }
    public void onClickAvanzar(View v){

        String numCuarto = getIntent().getStringExtra(TCuarto.NUMERO);
        ArrayAdapter<String > adapter = presenter.getAdapterCuartos();
        agregarAlquilerView.prepararSpinenr(adapter, numCuarto);
        ruv.setVisibility(View.GONE);
        agregarAlquilerView.setVisibility(View.VISIBLE);

        sliding.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        presenter.avanzar();
    }

    @Override
    public void onClickHolder(RecyclerView.ViewHolder holder) {

        presenter.setMain(holder);
    }
}
