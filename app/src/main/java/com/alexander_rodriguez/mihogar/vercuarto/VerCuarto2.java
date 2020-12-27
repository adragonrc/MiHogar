package com.alexander_rodriguez.mihogar.vercuarto;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.alexander_rodriguez.mihogar.ButtonsAC.ButtonsAceptarCancelar;
import com.alexander_rodriguez.mihogar.ButtonsAC.interfazAC;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemRental;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemRoom;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.Save;
import com.alexander_rodriguez.mihogar.UTILIDADES.Mensualidad;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.UTILIDADES.TCuarto;
import com.alexander_rodriguez.mihogar.UTILIDADES.TUsuario;
import com.alexander_rodriguez.mihogar.ViewPdfActivity;
import com.alexander_rodriguez.mihogar.historialcasa.HistorialCasaActivity;
import com.alexander_rodriguez.mihogar.listalquileres.ListAlquileresActivity;
import com.alexander_rodriguez.mihogar.agregarInquilino.AgregarAlquilerActivity;
import com.alexander_rodriguez.mihogar.menu_photo.MenuIterator;
import com.alexander_rodriguez.mihogar.menu_photo.interfazMenu;
import com.alexander_rodriguez.mihogar.mydialog.DialogImput;
import com.alexander_rodriguez.mihogar.mydialog.DialogInterfaz;
import com.alexander_rodriguez.mihogar.mydialog.PresenterDialogImput;
import com.alexander_rodriguez.mihogar.tableActivity.TableActivity;
import com.alexander_rodriguez.mihogar.vercuarto.view_perfil_cuarto.PerfilCuarto;
import com.alexander_rodriguez.mihogar.vercuarto.view_perfil_cuarto.ProfileView;
import com.alexander_rodriguez.mihogar.viewUser.DialogConfirmPago;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.Objects;

import butterknife.ButterKnife;

public class VerCuarto2 extends BaseActivity<Interface.Presenter> implements Interface.view, interfazAC {
    public static final String TAG_REALIZAR_PAGO = "confirmarPago";

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

    private String path;
    private String numCuarto;

    private Menu menu;

    private interfazMenu interfazMenu;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(this.iMenu, menu);
        getMenuInflater().inflate(R.menu.menu_photo_show, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.iVerPagos: {
                String idAlquiler = presenter.getDatosAlquiler().getId();
                Intent i = new Intent(this, TableActivity.class);
                i.putExtra(TAlquiler.ID, idAlquiler);
                startActivity(i);
                break;
            }
            case R.id.iAgregarInquilino: {
                Intent i = new Intent(this, AgregarAlquilerActivity.class);
                i.putExtra(TCuarto.NUMERO, numCuarto);
                startActivity(i);
                break;
            }
            case R.id.app_bar_edit:{
                interfazMenu.onEdit();
                break;
            }
            case R.id.app_bar_shared:{
                interfazMenu.onShared(path);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK && result != null){
                resultOk(result);
            }else{
                if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    Exception e = Objects.requireNonNull(result).getError();
                    Toast.makeText(this, "Posible Error es: "+ e, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void resultOk(CropImage.ActivityResult result){
        Save s = new Save();
        Bitmap bm = BitmapFactory.decodeFile(result.getUri().getPath());
        path = s.SaveImage(this, bm);
        presenter.actualizarPhoto(path);
        profileCuarto.setPhotoImage(path);
    }

    @Override
    protected void iniciarComandos() {
        interfazMenu = new MenuIterator(this);
        ButterKnife.bind(this);

        setSupportActionBar(profileCuarto.getToolbar());
        ActionBar ab =  getSupportActionBar();
        if (ab != null){
            ab.setDisplayHomeAsUpEnabled(true);
        }

        dip = new PresenterDialogImput(this, "ALERTA") {
            @Override
            public void positiveButtonListener(@Nullable String s) {
                presenter.deshacerContrato(s);
                removerMenuCuartoSinAlquiler();
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
        intent.putExtra(TAlquiler.ID, presenter.getDatosAlquiler().getId());
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
    public void showCuartoAlquilado(ItemRoom cuarto, int usuario, String mensualidad) {
        perfilCuarto.showCuartoAlquilado(usuario, mensualidad, presenter.getDatosAlquiler());

        aceptarCancelar.setVisibility(View.VISIBLE);
        iMenu = R.menu.menu_cuarto;
        if (menu != null) {
            removerMenuCuartoSinAlquiler();
            getMenuInflater().inflate(iMenu, menu);
        }
        detallesCuarto2(cuarto);
    }

    private void removerMenuCuartoSinAlquiler(){
        menu.removeItem(R.id.iVerPagos);
    }

    private void removerMenuCuartoAlquilado(){
        menu.removeItem(R.id.iVerPagos);
    }

    private void detallesCuarto2(ItemRoom room){
        path = room.getPathImage();
        profileCuarto.setPhotoImage(path);
        perfilCuarto.setDetallesText(room.getDetails());
        profileCuarto.setTitle(room.getRoomNumber());
        profileCuarto.setSubTitle(room.getDetails());

    }
    public void showCuartolibre(ItemRoom cuarto) {
        perfilCuarto.showCuartolibre();
        iMenu = R.menu.menu_cuarto_no_alquilado;

        detallesCuarto2(cuarto);
        aceptarCancelar.setVisibility(View.GONE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            presenter.crearPDF();
        }
    }


    @Override
    public void onClickVermas(View view) {
        //startActivity(new Intent(this, HistorialCasaActivity.class));
        Intent i = new Intent(this, HistorialCasaActivity.class);
        i.putExtra(HistorialCasaActivity.TYPE_MODE, HistorialCasaActivity.MODO_SOLO_USUARIOS);
        i.putExtra(TAlquiler.ID, presenter.getDatosAlquiler().getId());
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
    public void ocEditarNumTel(View view) {
        perfilCuarto.modEditarNumTel();
    }

    @Override
    public void ocEditarCorreo(View view) {
        perfilCuarto.modEditarCorreo();
    }

    @Override
    public void onClickConfirMensualidad(View view) {
        presenter.actualizarMensualidad(perfilCuarto.getMensualidadText());
    }

    @Override
    public void onClickConfirDetalles(View view) {
        presenter.actualizarDetalles(perfilCuarto.getDetallesText());
    }

    @Override
    public void ocConfirNumTel(View view) {
        presenter.actualizarNumTel(perfilCuarto.getTelefonoText());
    }

    @Override
    public void ocConfirCorreo(View view) {
        presenter.actualizarCorreo(perfilCuarto.getCorreoText());
    }

    @Override
    public void mostrarPDF(File pdfFile, ItemRental datosAlquiler) {
        Intent intent = new Intent(this, ViewPdfActivity.class);
        intent.putExtra(ViewPdfActivity.EXTRA_PATH_PDF, pdfFile.getAbsolutePath());
        intent.putExtra(TAlquiler.NUMERO_TEL, datosAlquiler.getPhoneNumber());
        intent.putExtra(TAlquiler.CORREO, datosAlquiler.getEmail());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onClickPhoto(View view) {
        if(Objects.requireNonNull(path).equals("")) {
            Toast.makeText(this, "Sin foto", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, ActivityShowImage.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, ivPerfil, ViewCompat.getTransitionName(ivPerfil));
        intent.putExtra(ActivityShowImage.IS_CUARTO_IMAGE, true);
        intent.putExtra(TCuarto.NUMERO, numCuarto);
        intent.putExtra(ActivityShowImage.DATA_IMAGE, path);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void actualizarNumTel(String numTel) {
        ocultarTeclado();
        perfilCuarto.mostrarNumeroTelefonico(numTel);
    }

    @Override
    public void actualizarCorreo(String correo) {
        ocultarTeclado();
        perfilCuarto.mostrarCorreo(correo);
    }

    @Override
    public void onClickVerAlquileres(View view) {
        Intent i = new Intent(this, ListAlquileresActivity.class);
        i.putExtra(TCuarto.NUMERO, numCuarto);
        i.putExtra(TAlquiler.ID, presenter.getDatosAlquiler().getId());
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
    public void actualizarFechaPago(String fecha) {
        perfilCuarto.setFechaCText(fecha);
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
        btPagarAlquiler.setText(R.string.sMostrarPago);
        btPagarAlquiler.setOnClickListener(listener2);
    }

    @Override
    protected void iniciarViews() {
        modificarTransicion();
        profileCuarto = findViewById(R.id.profileAppid);

        aceptarCancelar = findViewById(R.id.llBtns);
        aceptarCancelar.setTextButtons("QUITAR ALQUILER", "PAGAR ALQUILER");

        btPagarAlquiler = aceptarCancelar.getButton1();
        ivPerfil = findViewById(R.id.ivPerfil);

        perfilCuarto = (PerfilCuarto)LayoutInflater.from(this).inflate(R.layout.view_perfil_cuarto, profileCuarto, false);
        profileCuarto.addToCuerpo(perfilCuarto);
    }

    private void createListener(){
        listener = v -> {
            Bundle datos = new Bundle();
            datos.putString(TUsuario.DNI, presenter.getResponsable());
            datos.putString(TCuarto.NUMERO, numCuarto);
            datos.putString(TAlquiler.EXTRA_FECHA_PAGO, perfilCuarto.getTvFechaC().getText().toString());
            datos.putString(Mensualidad.COSTO, perfilCuarto.getTvMensualidad().getText().toString());

            dialogConfirmPago = new DialogConfirmPago(datos, VerCuarto2.this);

            dialogConfirmPago.setOnClickListenerAceptar(v12 -> {
                presenter.realizarPago();
                dialogConfirmPago.dismiss();
            });
            dialogConfirmPago.setOnClickListenerCancelar(v1 -> {
                Toast.makeText(VerCuarto2.this, "transaccion canselada", Toast.LENGTH_SHORT).show();
                dialogConfirmPago.dismiss();
            });
            dialogConfirmPago.show(getSupportFragmentManager(), TAG_REALIZAR_PAGO);
        };
    }

    @Override
    public void onClickPositive(View v) {
    }

    @Override
    public void onClickNegative(View v) {
        DialogImput imput = new DialogImput();
        imput.showDiaglog(getSupportFragmentManager(), "d", dip);
        dip.setHintView("Motivo");
    }
}