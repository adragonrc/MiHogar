package com.alexander_rodriguez.mihogar.vercuarto;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import com.alexander_rodriguez.mihogar.ActivityShowImage;
import com.alexander_rodriguez.mihogar.Base.BaseActivity;
import com.alexander_rodriguez.mihogar.HeaderView;
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
import com.google.android.material.appbar.AppBarLayout;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VerCuartoActivity extends BaseActivity<Interface.Presenter> implements Interface.view {
    public static final String TAG_REALIZAR_PAGO = "confirmarPago";
    //private TextView tvNumeroCuarto;
    private TextView tvNombres;
    private TextView tvMensualidad;
    private TextView tvDni;
    private TextView tvDetalles;
    private TextView tvFechaC;

    private EditText etMensualidad;
    private EditText etDetalles;

    private ImageView ivAlert;
    private ImageView ivPerfil;

    private Button btPagarAlquiler;

    private CardView cvDetallesAlquiler;
    private CardView cvMensaje;

    private LinearLayout llBtns;

    private LinearLayout llPagoMensual;
    private LinearLayout llEditorMensualidad;
    private LinearLayout llShowMensualidad;
    private LinearLayout llEditorDetalles;
    private LinearLayout llShowDetalles;

    private DialogInterfaz.DialogImputPresenter dip;

    private DialogConfirmPago dialogConfirmPago;

    private View.OnClickListener listener;
    private View.OnClickListener listener2;

    private int iMenu;

    private String URIPerfil;

    private String numCuarto;

    private Menu menu;
    @BindView(R.id.toolbar_header_view)
    protected HeaderView toolbarHeaderView;

    @BindView(R.id.float_header_view)
    protected HeaderView floatHeaderView;

    @BindView(R.id.appbar)
    protected AppBarLayout appBarLayout;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    private boolean isHideToolbarView = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(this.iMenu, menu);
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
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle datos = new Bundle();
                datos.putString(TUsuario.DNI, tvDni.getText().toString());
                datos.putString(TCuarto.NUMERO, numCuarto);
                datos.putString(TAlquiler.FECHA_C, tvFechaC.getText().toString());
                datos.putString(Mensualidad.COSTO, tvMensualidad.getText().toString());

                dialogConfirmPago = new DialogConfirmPago(datos, VerCuartoActivity.this);

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
                        Toast.makeText(VerCuartoActivity.this, "transaccion canselada", Toast.LENGTH_SHORT).show();
                        dialogConfirmPago.dismiss();
                    }
                });
                dialogConfirmPago.show(getSupportFragmentManager(), TAG_REALIZAR_PAGO);
            }
        };
        listener2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.equals(btPagarAlquiler)) {
                    gotoTablePagos();
                }
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
        return R.layout.profile_app;
    }

    @NonNull
    @Override
    protected Presentador createPresenter() {
        numCuarto = getIntent().getStringExtra(TCuarto.NUMERO);
        return new Presentador(this, numCuarto);
    }

    @Override
    public void showCuartoAlquilado(ContentValues cuarto, ContentValues usuario, String mensualidad) {
        String nombes = usuario.getAsString(TUsuario.NOMBRES) + ", " + usuario.getAsString(TUsuario.APELLIDO_PAT) + " " + usuario.getAsString(TUsuario.APELLIDO_MAT) + ".";
        tvDni.setText(usuario.getAsString(TUsuario.DNI));
        tvNombres.setText(nombes);
        tvMensualidad.setText(mensualidad);
        //AdministradorCamara.setPic(ivPerfil, usuario.getAsString(TUsuario.URI));
        cvMensaje.setVisibility(View.GONE);
        tvFechaC.setText(presenter.getDatosAlquiler().getAsString(TAlquiler.FECHA_C));
        iMenu = R.menu.menu_cuarto;
        cvDetallesAlquiler.setVisibility(View.VISIBLE);
        detallesCuarto2(cuarto);
    }

    private void detallesCuarto(ContentValues cuarto){
        URIPerfil = cuarto.getAsString(TCuarto.URL);
        if (URIPerfil != null && !URIPerfil.equals("")) {
            Bitmap bm = BitmapFactory.decodeFile(URIPerfil);
            ivPerfil.setImageBitmap(bm);
        }
        //tvNumeroCuarto.setText(cuarto.getAsString(TCuarto.NUMERO));
      //
    }

    private void detallesCuarto2(ContentValues cuarto){
        URIPerfil = cuarto.getAsString(TCuarto.URL);
        if (URIPerfil != null && !URIPerfil.equals("")) {
            Bitmap bm = BitmapFactory.decodeFile(URIPerfil);
            ivPerfil.setImageBitmap(bm);
        }
        tvDetalles.setText(cuarto.getAsString(TCuarto.DETALLES));
        toolbarHeaderView.bindTo(cuarto.getAsString(TCuarto.NUMERO), cuarto.getAsString(TCuarto.DETALLES));
        floatHeaderView.bindTo(cuarto.getAsString(TCuarto.NUMERO), cuarto.getAsString(TCuarto.DETALLES));
    }
    public void showCuartolibre(ContentValues cuarto) {
        cvDetallesAlquiler.setVisibility(View.GONE);
        llBtns.setVisibility(View.GONE);
        cvMensaje.setVisibility(View.VISIBLE);
        iMenu = R.menu.menu_cuarto_no_alquilado;
        detallesCuarto2(cuarto);
        //AdministradorCamara.setPic(ivPerfil, cuarto.getAsString(TCuarto.URL));
    }

    @Override
    public void onClickVerInquilino(View view) {
        Intent i = new Intent(this, DialogConfirmPago.class);
        i.putExtra(TUsuario.DNI, Integer.parseInt(tvDni.getText().toString()));
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
        i.putExtra(TUsuario.DNI, tvDni.getText().toString());
        startActivity(i);
    }

    @Override
    public void onClickEditarMensualidad(View view) {
        llEditorMensualidad.setVisibility(View.VISIBLE);
        llShowMensualidad.setVisibility(View.GONE);
        etMensualidad.setText(tvMensualidad.getText().toString());
        etMensualidad.requestFocus();
    }

    @Override
    public void onClickEditarDetalles(View view) {
        llEditorDetalles.setVisibility(View.VISIBLE);
        llShowDetalles.setVisibility(View.GONE);
        etDetalles.setText(tvDetalles.getText().toString());
        etDetalles.requestFocus();
    }

    @Override
    public void onClickConfirMensualidad(View view) {
        presenter.actualizarMensualidad(etMensualidad.getText().toString());
    }

    @Override
    public void onClickConfirDetalles(View view) {
        presenter.actualizarDetalles(etDetalles.getText().toString());
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
        llEditorMensualidad.setVisibility(View.GONE);
        llShowMensualidad.setVisibility(View.VISIBLE);
        tvMensualidad.setText(mensualidad);
    }

    @Override
    public void actualizarDetalles(String detalles) {
        ocultarTeclado();
        llEditorDetalles.setVisibility(View.GONE);
        llShowDetalles.setVisibility(View.VISIBLE);

        tvDetalles.setText(detalles);
        toolbarHeaderView.setLastSeenText(detalles);
        floatHeaderView.setLastSeenText(detalles);
    }

    @Override
    public void onBackPressed() {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null)
            if (currentFocus.equals(etDetalles)) {
                llEditorDetalles.setVisibility(View.GONE);
                llShowDetalles.setVisibility(View.VISIBLE);
                return;
            } else {
                if (currentFocus.equals(etMensualidad)) {
                    llEditorMensualidad.setVisibility(View.GONE);
                    llShowMensualidad.setVisibility(View.VISIBLE);
                    return;
                }
            }
        super.onBackPressed();
    }

    @Override
    public void noPago() {
        ivAlert.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_alert_black_24dp));
        llPagoMensual.setOnClickListener(listener);
        btPagarAlquiler.setOnClickListener(listener);
        findViewById(R.id.tvRealizarPago).setVisibility(View.VISIBLE);
    }

    @Override
    public void pago() {
        findViewById(R.id.tvRealizarPago).setVisibility(View.GONE);
        ivAlert.setImageDrawable(getResources().getDrawable(R.drawable.ic_mood_black_24dp));
        btPagarAlquiler.setText("MOSTRAR PAGOS");
        llPagoMensual.setOnClickListener(listener2);
        btPagarAlquiler.setOnClickListener(listener2);
    }

    @Override
    protected void iniciarViews() {
        modificarTransicion();
      //  tvNumeroCuarto = findViewById(R.id.tvNumeroCuarto);
        tvNombres = findViewById(R.id.tvNombres);
        tvMensualidad = findViewById(R.id.vcTvMensualidad);
        tvDni = findViewById(R.id.tvDni);
        tvDetalles = findViewById(R.id.tvDetalles);
        tvFechaC = findViewById(R.id.tvFechaDePago);

        etDetalles = findViewById(R.id.etDetalles);
        etMensualidad = findViewById(R.id.etMensualidad);

        btPagarAlquiler = findViewById(R.id.button1);

        cvDetallesAlquiler = findViewById(R.id.cvDetallesAlquiler);
        cvMensaje = findViewById(R.id.cvMensaje);

        llBtns = findViewById(R.id.llBtns);
        llEditorDetalles = findViewById(R.id.llEditarDetalles);
        llEditorMensualidad = findViewById(R.id.llEditMensualidad);
        llShowDetalles = findViewById(R.id.llMostrarDetalles);
        llShowMensualidad = findViewById(R.id.llShowMensualidad);
        llPagoMensual = findViewById(R.id.llPagoMensual);

        ivAlert = findViewById(R.id.ivAlert);
        ivPerfil = findViewById(R.id.ivPerfil);
    }

    private void diseñoPrueba() {

        toolbarHeaderView = findViewById(R.id.toolbar_header_view);
        floatHeaderView = findViewById(R.id.float_header_view);
        appBarLayout = findViewById(R.id.appbar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab =  getSupportActionBar();
        if (ab != null){
            ab.setDisplayHomeAsUpEnabled(true);
        }
        appBarLayout.addOnOffsetChangedListener(new Offset(toolbarHeaderView));

        toolbarHeaderView.bindTo("Larry Page", "Last seen today at 7.00PM");
        floatHeaderView.bindTo("Larry Page", "Last seen today at 7.00PM");
    }
    public class Offset implements AppBarLayout.OnOffsetChangedListener {
        HeaderView toolbarHeaderView;
        public Offset(HeaderView toolbarHeaderView){
            this.toolbarHeaderView = toolbarHeaderView;
        }
        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
            int maxScroll = appBarLayout.getTotalScrollRange();
            float percentage = (float) Math.abs(offset) / (float) maxScroll;

            if (percentage == 1f && isHideToolbarView) {
                toolbarHeaderView.setVisibility(View.VISIBLE);
                isHideToolbarView = !isHideToolbarView;

            } else if (percentage < 1f && !isHideToolbarView) {
                toolbarHeaderView.setVisibility(View.GONE);
                isHideToolbarView = !isHideToolbarView;
            }
        }
    }
}