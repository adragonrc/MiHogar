package com.alexander_rodriguez.mihogar.vercuarto;

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import com.alexander_rodriguez.mihogar.ActivityShowImage;
import com.alexander_rodriguez.mihogar.Base.BaseActivity;
import com.alexander_rodriguez.mihogar.view_buttons_ac.ButtonsAC;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemRental;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemRoom;
import com.alexander_rodriguez.mihogar.DataBase.parse.ParceRental;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.Save;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.UTILIDADES.TCuarto;
import com.alexander_rodriguez.mihogar.UTILIDADES.TUsuario;
import com.alexander_rodriguez.mihogar.ViewPdfActivity;
import com.alexander_rodriguez.mihogar.historialcasa.HistorialCasaActivity;
import com.alexander_rodriguez.mihogar.add_rental.AddRentalActivity;
import com.alexander_rodriguez.mihogar.menu_photo.MenuIterator;
import com.alexander_rodriguez.mihogar.menu_photo.interfazMenu;
import com.alexander_rodriguez.mihogar.mydialog.DialogAddAdvance;
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

public class ShowRoomActivity extends BaseActivity<Interface.Presenter> implements Interface.view, ButtonsAC.Listener, DialogAddAdvance.Interface {
    private static final String TAG_REALIZAR_PAGO = "confirmarPago";
    private static final String TAG_ADD_ADVANCE = "tag_add_advance";
    private static final int REQUEST_CODE_ADD_RENTAL = 2000;

    private PerfilCuarto perfilCuarto;

    private ProfileView profileCuarto;


    private ButtonsAC aceptarCancelar;

    private ImageView ivPerfil;

    private Button btPagarAlquiler;

    private DialogInterfaz.DialogImputPresenter dip;

    private DialogConfirmPago confirmPago;
    private DialogAddAdvance addAdvanced;

    private View.OnClickListener listener;
    private View.OnClickListener listener2;

    private String path;
    private String numCuarto;

    private Menu menu;

    private interfazMenu interfazMenu;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_photo_show, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.iVerPagos) {
            ParceRental rental = new ParceRental(presenter.getDatosAlquiler());
            Intent i = new Intent(this, TableActivity.class);
            i.putExtra(TableActivity.EXTRA_RENTAL, rental);
            startActivity(i);
        } else if (id == R.id.iAddRental) {
            Intent i = new Intent(this, AddRentalActivity.class);
            i.putExtra(TCuarto.NUMERO, numCuarto);
            startActivityForResult(i, REQUEST_CODE_ADD_RENTAL);
        } else if (id == R.id.app_bar_edit) {
            interfazMenu.onEdit();
        } else if (id == R.id.app_bar_shared) {
            interfazMenu.onShared(path);
        } else if (id == BACK_PRESSED) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK && result != null) {
                    resultOk(result);
                } else {
                    if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Exception e = Objects.requireNonNull(result).getError();
                        e.printStackTrace();
                        //Toast.makeText(this, "Posible Error es: "+ e, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case REQUEST_CODE_ADD_RENTAL:{
                if (resultCode == RESULT_OK){
                    presenter.refresh();
                }
            }
        }
    }

    private void resultOk(CropImage.ActivityResult result){
        Save s = new Save();
        Bitmap bm = BitmapFactory.decodeFile(result.getUri().getPath());
        path = s.SaveImage(this, bm, getString(R.string.cRoom), numCuarto);
        presenter.updatePhoto(path);
        profileCuarto.reloadPhoto(path);
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
            }
        };

        createListener();

        listener2 = v -> {
            if (v.equals(btPagarAlquiler)) {
                gotoTablePagos();
            }
        };
    }

    private void gotoTablePagos() {
        Intent intent = new Intent(this, TableActivity.class);
        ParceRental rental = new ParceRental(presenter.getDatosAlquiler());
        intent.putExtra(TableActivity.EXTRA_RENTAL, rental);
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
        if (menu != null) {
            menu.removeItem(R.id.iAddRental);
            getMenuInflater().inflate(R.menu.menu_cuarto, menu);
        }
        detallesCuarto2(cuarto);
    }


    private void detallesCuarto2(ItemRoom room){
        path = room.getPathImage();
        profileCuarto.reloadPhoto(path);
        perfilCuarto.setDetallesText(room.getDetails());
        profileCuarto.setTitle(room.getRoomNumber());
        profileCuarto.setSubTitle(room.getDetails());
    }

    @Override
    public void reloadRoomPhoto() {
        profileCuarto.reloadPhoto(Save.createFile(this, getString(R.string.cRoom), numCuarto).getAbsolutePath());
    }

    @Override
    public void hideAdvance() {
        perfilCuarto.hideAdvance();
    }

    @Override
    public void showAdvance(Double amount) {
        perfilCuarto.showAdvance(amount);
    }

    @Override
    public void showAllAdvances(TableLayout tl) {

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setView(tl);
        b.show();
    }

    public void showCuartolibre(ItemRoom cuarto) {
        perfilCuarto.showCuartolibre();
        if (menu !=null) {
            menu.removeItem(R.id.iVerPagos);
            getMenuInflater().inflate(R.menu.menu_cuarto_no_alquilado, menu);
        }
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
        i.putExtra(HistorialCasaActivity.MODE, HistorialCasaActivity.USERS_OF_RENTAL);
        i.putExtra(HistorialCasaActivity.EXTRA_RENTAL_ID, presenter.getRoom().getCurrentRentalId());
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
        if(path == null || path.equals("")) {
            Toast.makeText(this, "Sin foto", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, ActivityShowImage.class);
        String transitionName = ViewCompat.getTransitionName(ivPerfil);
        intent.putExtra(ActivityShowImage.IS_CUARTO_IMAGE, true);
        intent.putExtra(TCuarto.NUMERO, numCuarto);

        intent.putExtra(ActivityShowImage.DATA_IMAGE, path);
        if(transitionName != null) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, ivPerfil, transitionName);
            startActivity(intent, options.toBundle());
        }else{
            startActivity(intent);
        }
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
        Intent i = new Intent(this, HistorialCasaActivity.class);
        i.putExtra(HistorialCasaActivity.MODE, HistorialCasaActivity.RENTALS_OF_ROOM);
        i.putExtra(HistorialCasaActivity.EXTRA_ROOM_NUMBER, numCuarto);
        i.putExtra(HistorialCasaActivity.EXTRA_RENTAL_ID_IGNORE, presenter.getRoom().getCurrentRentalId());
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
        btPagarAlquiler.setText(R.string.sPayRent);
        btPagarAlquiler.setOnClickListener(listener);
    }

    @Override
    public void pago() {
        perfilCuarto.pago(listener2);
        btPagarAlquiler.setText(R.string.sMostrarPago);
        btPagarAlquiler.setOnClickListener(listener2);
    }

    private DialogAddAdvance createAddAdvanced(){
        if (addAdvanced == null) addAdvanced =  new DialogAddAdvance(this);
        return addAdvanced;
    }

    private DialogConfirmPago createConfirmPago(){
        if (confirmPago == null) confirmPago = new DialogConfirmPago(this);
        return confirmPago;
    }


    @Override
    protected void iniciarViews() {
        modificarTransicion();
        profileCuarto = findViewById(R.id.profileAppid);

        aceptarCancelar = findViewById(R.id.llBtns);
        aceptarCancelar.setTextButtons("QUITAR ALQUILER", "PAGAR ALQUILER");

        btPagarAlquiler = aceptarCancelar.getButton1();
        aceptarCancelar.setListener(this);
        ivPerfil = findViewById(R.id.ivPerfil);

        perfilCuarto = (PerfilCuarto)LayoutInflater.from(this).inflate(R.layout.view_perfil_cuarto, profileCuarto, false);
        profileCuarto.addToCuerpo(perfilCuarto);

    }

    private void createListener(){
        listener = v -> {
            Bundle data = new Bundle();
            data.putString(TUsuario.DNI, presenter.getResponsable());
            data.putString(DialogConfirmPago.ARG_ROOM_NUMBER, numCuarto);
            data.putString(DialogConfirmPago.ARG_PAYMENT_DATA, presenter.getDatosAlquiler().getPaymentDateAsString());
            data.putString(DialogConfirmPago.ARG_AMOUNT, String.valueOf(presenter.getAmount()));

            createConfirmPago();
            confirmPago.setArguments(data);
            confirmPago.setOnClickListenerAceptar(v12 -> {
                presenter.realizarPago();
                confirmPago.dismiss();
            });
            confirmPago.setOnClickListenerCancelar(v1 -> {
                createAddAdvanced();
                Bundle advancedData = new Bundle();
                advancedData.putDouble(getString(R.string.mdPaymentAmount), presenter.getRemainingPayment());
                addAdvanced.setArguments(advancedData);
                confirmPago.dismiss();
                addAdvanced.show(getSupportFragmentManager(), TAG_ADD_ADVANCE);
            });
            confirmPago.show(getSupportFragmentManager(), TAG_REALIZAR_PAGO);
        };
    }

    @Override
    public void onAccept(Dialog dialog, Double amount) {
        presenter.addAdvance(amount);
        addAdvanced.dismiss();
    }

    @Override
    public void onCancel(Dialog dialog) {
        addAdvanced.dismiss();
    }

    @Override
    public void showError(String string) {
        showMessage(string);
    }

    public void ocShowDetailsAdvance(View view) {
        presenter.ocShowDetailsAdvance();
    }

    @Override
    public void ocPositive(View view) {

    }

    @Override
    public void ocNegative(View view) {
        DialogImput imput = new DialogImput();
        imput.showDiaglog(getSupportFragmentManager(), "d", dip);
        dip.setHintView("Motivo");
    }
}