package com.alexander_rodriguez.mihogar.vercuarto;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import com.alexander_rodriguez.mihogar.ActivityShowImage;
import com.alexander_rodriguez.mihogar.Base.BaseActivity;
import com.alexander_rodriguez.mihogar.ButtonsAceptarCancelar;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.UTILIDADES.Mensualidad;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.UTILIDADES.TCuarto;
import com.alexander_rodriguez.mihogar.UTILIDADES.TUsuario;
import com.alexander_rodriguez.mihogar.ViewPdfActivity;
import com.alexander_rodriguez.mihogar.agregarInquilino.AgregarInquilino;
import com.alexander_rodriguez.mihogar.historialUserPakage.HistorialUsuarioActivity;
import com.alexander_rodriguez.mihogar.listalquileres.ListAlquileresActivity;
import com.alexander_rodriguez.mihogar.mydialog.DialogImput;
import com.alexander_rodriguez.mihogar.mydialog.DialogInterfaz;
import com.alexander_rodriguez.mihogar.mydialog.PresenterDialogImput;
import com.alexander_rodriguez.mihogar.tableActivity.TableActivity;
import com.alexander_rodriguez.mihogar.verusuario.DialogConfirmPago;

import java.io.File;

import butterknife.ButterKnife;

public class VerCuarto2 extends BaseActivity<Interface.Presenter> implements Interface.view {
    public static final String TAG_REALIZAR_PAGO = "confirmarPago";
    //private TextView tvNumeroCuarto;

    private PerfilCuarto perfilCuarto;

    private ProfileView profileCuarto;


    private ButtonsAceptarCancelar aceptarCancelar;

    private ImageView ivPerfil;

    private Button btPagarAlquiler;

    private DialogInterfaz.DialogImputPresenter dip;

    private DialogConfirmPago dialogConfirmPago;

    private View.OnClickListener listener;
    private View.OnClickListener listener2;

    private int iMenu;

    private String URIPerfil;

    private String numCuarto;

    private Menu menu;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(this.iMenu, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.iVerPagos: {
                String idAlquiler = presenter.getDatosAlquiler().getAsString(TAlquiler.ID);
                Intent i = new Intent(this, TableActivity.class);
                i.putExtra(TAlquiler.ID, idAlquiler);
                startActivity(i);
                break;
            }
            case R.id.iAgregarInquilino: {
                Intent i = new Intent(this, AgregarInquilino.class);
                i.putExtra(TCuarto.NUMERO, numCuarto);
                startActivity(i);
                break;
            }
            case BACK_PRESSED:{
                onBackPressed();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.mostrarDetalles();
    }

    @Override
    protected void iniciarComandos() {
        ButterKnife.bind(this);
        diseñoPrueba();
        dip = new PresenterDialogImput(this, "ALERTA") {
            @Override
            public void positiveButtonListener(@Nullable String s) {
                presenter.deshacerContrato(s);
                menu.removeItem(R.id.iVerPagos);
                getMenuInflater().inflate(R.menu.menu_cuarto_no_alquilado, menu);
            }
        };

        createListener();

        listener2 = v -> {
            if (v.equals(btPagarAlquiler)) {
                gotoTablePagos();
            }
        };
        iMenu = R.menu.menu_cuarto_no_alquilado;
    }

    private void gotoTablePagos() {
        Intent intent = new Intent(this, TableActivity.class);
        intent.putExtra(TAlquiler.ID, presenter.getDatosAlquiler().getAsString(TAlquiler.ID));
        startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main2;
    }

    @NonNull
    @Override
    protected Presentador createPresenter() {
        numCuarto = getIntent().getStringExtra(TCuarto.NUMERO);
        return new Presentador(this, numCuarto);
    }

    @Override
    public void showCuartoAlquilado(ContentValues cuarto, ContentValues usuario, String mensualidad) {
        perfilCuarto.showCuartoAlquilado(usuario, mensualidad, presenter.getDatosAlquiler());

        aceptarCancelar.setVisibility(View.VISIBLE);
        iMenu = R.menu.menu_cuarto;
        if (menu != null) {
            menu.clear();
            getMenuInflater().inflate(iMenu, menu);
        }
        detallesCuarto2(cuarto);
    }

    private void detallesCuarto2(ContentValues cuarto){
        URIPerfil = cuarto.getAsString(TCuarto.URL);

        profileCuarto.setPhotoImage(URIPerfil);

        perfilCuarto.setDetallesText(cuarto.getAsString(TCuarto.DETALLES));
        profileCuarto.setTitle(cuarto.getAsString(TCuarto.NUMERO));
        profileCuarto.setSubTitle(cuarto.getAsString(TCuarto.DETALLES));

    }
    public void showCuartolibre(ContentValues cuarto) {
        perfilCuarto.showCuartolibre();
        iMenu = R.menu.menu_cuarto_no_alquilado;

        detallesCuarto2(cuarto);
        aceptarCancelar.setVisibility(View.GONE);
    }

    @Override
    public void onClickVerInquilino(View view) {
        Intent i = new Intent(this, DialogConfirmPago.class);
        i.putExtra(TUsuario.DNI, Integer.parseInt(perfilCuarto.getDniText()));
        startActivity(i);
    }

    @Override
    public void onClickTerminarAlquiler(View view) {
        DialogImput imput = new DialogImput();
        imput.showDiaglog(getSupportFragmentManager(), "d", dip);
        dip.setHintView("Motivo");
    }

    @Override
    public void onClickVermas(View view) {
        Intent i = new Intent(this, HistorialUsuarioActivity.class);
        i.putExtra(TUsuario.DNI, perfilCuarto.getDniText());
        startActivity(i);
    }

    @Override
    public void onClickEditarMensualidad(View view) {
        perfilCuarto.modoEditaMensualidad();
    }

    @Override
    public void onClickEditarDetalles(View view) {
        perfilCuarto.modoEditarDetalles();
    }

    @Override
    public void onClickConfirMensualidad(View view) {
        presenter.actualizarMensualidad(perfilCuarto.getMensualidadText());
    }

    @Override
    public void onClickConfirDetalles(View view) {
        presenter.actualizarDetalles(perfilCuarto.getEtDetallesText());
    }

    @Override
    public void mostrarPDF(File pdfFile, ContentValues datosUsuario) {
        Intent intent = new Intent(this, ViewPdfActivity.class);
        intent.putExtra(ViewPdfActivity.EXTRA_PATH_PDF, pdfFile.getAbsolutePath());
        intent.putExtra(TUsuario.NUMERO_TEL, datosUsuario.getAsString(TUsuario.NUMERO_TEL));
        intent.putExtra(TUsuario.CORREO, datosUsuario.getAsString(TUsuario.CORREO));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onClickPhoto(View view) {
        Intent intent = new Intent(this, ActivityShowImage.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, ivPerfil, ViewCompat.getTransitionName(ivPerfil));
        intent.putExtra(ActivityShowImage.IS_CUARTO_IMAGE, true);
        intent.putExtra(TCuarto.NUMERO, numCuarto);
        intent.putExtra(ActivityShowImage.DATA_IMAGE, URIPerfil);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onClickVerAlquileres(View view) {
        Intent i = new Intent(this, ListAlquileresActivity.class);
        i.putExtra(TCuarto.NUMERO, numCuarto);
        startActivity(i);
    }

    @Override
    public void actualizarMensualidad(String mensualidad) {
        ocultarTeclado();
        perfilCuarto.actualizarMensualidad(mensualidad);
    }

    @Override
    public void actualizarDetalles(String detalles) {
        ocultarTeclado();
        perfilCuarto.actualizarDetalles(detalles);
        profileCuarto.setSubTitle(detalles);
    }

    @Override
    public void onBackPressed() {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            if (!perfilCuarto.checkBack(currentFocus))
                super.onBackPressed();
        }else
            super.onBackPressed();

    }

    @Override
    public void noPago() {
        perfilCuarto.noPago(listener);
        btPagarAlquiler.setOnClickListener(listener);

    }

    @Override
    public void pago() {
        perfilCuarto.pago(listener2);
        btPagarAlquiler.setText("MOSTRAR PAGOS");
        btPagarAlquiler.setOnClickListener(listener2);
    }

    @Override
    protected void iniciarViews() {
        modificarTransicion();
        profileCuarto = findViewById(R.id.profileAppid);
        aceptarCancelar = findViewById(R.id.btAceptarCancelar);
        btPagarAlquiler = findViewById(R.id.button1);
        ivPerfil = findViewById(R.id.ivPerfil);

        perfilCuarto = (PerfilCuarto)LayoutInflater.from(this).inflate(R.layout.activity_perfil_cuarto, null);
        profileCuarto.addToCuerpo(perfilCuarto);
    }

    private void createListener(){
        listener = v -> {
            Bundle datos = new Bundle();
            datos.putString(TUsuario.DNI, perfilCuarto.getTvDni().getText().toString());
            datos.putString(TCuarto.NUMERO, numCuarto);
            datos.putString(TAlquiler.FECHA_C, perfilCuarto.getTvFechaC().getText().toString());
            datos.putString(Mensualidad.COSTO, perfilCuarto.getTvMensualidad().getText().toString());

            dialogConfirmPago = new DialogConfirmPago(datos, VerCuarto2.this);

            dialogConfirmPago.setOnClickListenerAceptar(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.realizarPago();
                    dialogConfirmPago.dismiss();
                }
            });
            dialogConfirmPago.setOnClickListenerCancelar(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(VerCuarto2.this, "transaccion canselada", Toast.LENGTH_SHORT).show();
                    dialogConfirmPago.dismiss();
                }
            });
            dialogConfirmPago.show(getSupportFragmentManager(), TAG_REALIZAR_PAGO);
        };
    }
    private void diseñoPrueba() {

        setSupportActionBar(profileCuarto.getToolbar());
        ActionBar ab =  getSupportActionBar();
        if (ab != null){
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }
}