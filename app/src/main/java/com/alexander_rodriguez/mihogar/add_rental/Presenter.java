package com.alexander_rodriguez.mihogar.add_rental;

import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.adapters.RvAdapterUser;
import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemTenant;
import com.alexander_rodriguez.mihogar.DataBase.models.TMonthlyPayment;
import com.alexander_rodriguez.mihogar.DataBase.models.TPayment;
import com.alexander_rodriguez.mihogar.AdminDate;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.viewregistraralquiler.ModelAA;
import com.alexander_rodriguez.mihogar.Save;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquilerUsuario;
import com.alexander_rodriguez.mihogar.UTILIDADES.TUsuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;

public class Presenter extends BasePresenter<Interfaz.view> implements Interfaz.presenter {
    static final String SIN_REGISTROS = "-1";

    private Boolean confirmacion;
    private AdminDate adminDate;
    private ArrayList<String> cuartosDisponibles;

    private ArrayList<ItemTenant> list;

    private ItemTenant modelSelect;

    private ModelAA modelToSave;

    private String rentalId;

    public Presenter(Interfaz.view view) {
        super(view);
        adminDate = new AdminDate();
        adminDate.setFormat(AdminDate.FORMAT_DATE_TIME);
        list = new ArrayList<>();
        confirmacion = false;
    }

    private boolean isNuevo(String dni){
        for (ItemTenant m: list ){
            if (m.getDni().equals(dni)){
                return false;
            }
        }
        return true;
    }

    private void getRoomAvailableComplete(QuerySnapshot task){

        cuartosDisponibles = new ArrayList<>();
        for (DocumentSnapshot doc: task){
            cuartosDisponibles.add(doc.getId());
        }
        if(cuartosDisponibles.isEmpty()){
            view.sinCuartos();
        }
    }
    @Override
    public void iniciarComandos() {
        /*cuartosDisponibles ;*/
        db.consultarNumerosDeCuartoDisponibles().addOnSuccessListener(this::getRoomAvailableComplete)
        .addOnFailureListener(this::getRoomAvailableFailure);

    }

    private void getRoomAvailableFailure(Exception e) {
        e.printStackTrace();
    }

    public ArrayAdapter<String> getAdapterCuartos () {
        return new ArrayAdapter<>(view.getContext(), R.layout.support_simple_spinner_dropdown_item, cuartosDisponibles);
    }

    @Override
    public void confirmar() {
        this.confirmacion = true;
    }

    @Override
    public void onClickAddUser() {
        view.startRegistroUsuario();
    }

    @Override
    public void agregarUsuario(ItemTenant m) {
        int err = m.getErrorIfExist();
        if (err != -1) {
            view.showMessage("Campo vacio en el campo: " + ItemTenant.getLabelName(err));
            return;
        }
        if (!isNuevo(m.getDni())){
            view.showError("Usuario ya esta en lista: DNI");
            return;
        }
        guardarModel(m);
/*
        db.getTenantHistory(m.getDni())
                .addOnSuccessListener(this::getTenantHistorySuccess)
                .addOnFailureListener(this::getTenantHistoryFailure);
*/
    }

    private void getTenantHistoryFailure(Exception e) {

    }

    private void getTenantHistorySuccess(QuerySnapshot queryDocumentSnapshots) {
        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
            if (doc.exists())
                if (db.esUsuarioAntiguo(doc.getId())) {
                    view.showMessage("El usuario con DNI " + doc.getId() + " ya ha sido registrado");
                    view.showDialog("Usuario Antiguo");
                } else {
                    if (db.esUsuarioInterno(doc.getId())) {
                        view.showMessage("Usuario ya se encuentra en casa ;v");
                    }
                }
        }
    }

    @Override
    public void avanzar() {

    }

    private void addTenantSuccess(Void v){
        TMonthlyPayment monthlyPayment = new TMonthlyPayment(Double.parseDouble(modelToSave.getPrice()), modelToSave.getEntryDate(), rentalId);
        db.agregarMensualidad(monthlyPayment)
                .addOnSuccessListener(this::addMonthlyPaymentSuccess)
                .addOnFailureListener(this::addMonthlyPaymentFailure);

    }

    private void addMonthlyPaymentSuccess(DocumentReference document) {
        db.updateCurrentRentMP(rentalId, document);
        if (modelToSave.wasPaid()) {
            TPayment payment = new TPayment(modelToSave.getEntryDate(), rentalId, modelToSave.getRoomNumber(), document.getId(), Double.parseDouble(modelToSave.getPrice()), modelSelect.getDni(), null);
            db.addPayment(payment)
                    .addOnSuccessListener(this::addPaymentSuccess)
                    .addOnFailureListener(this::addPaymentFailure);
        } else {
            finish();
            view.close();
        }
    }

    private void finish(){
        db.updateCurrentRoomRent(modelToSave.getRoomNumber(), rentalId);
        view.showMessage("OK");
        view.close();
    }

    private void addMonthlyPaymentFailure(Exception e) {
        view.showError("No se pudo agregar la mensualidad.");
        db.revertir(TAlquiler.T_NOMBRE, TAlquiler.ID, String.valueOf(rentalId));
    }
    private void addPaymentSuccess(DocumentReference documentReference) {
        finish();
    }
    private void addPaymentFailure(Exception e) {
        view.showError("No se pudo agregar el pago");
        e.printStackTrace();
    }

    private void addTentalFailure(Exception e){

        view.showMessage("Could not add");
        e.printStackTrace();
    }
    private void revertir(int cont, int idAlquiler){
        if (cont < list.size()-1) {
            db.revertir(TAlquilerUsuario.T_NOMBRE, TAlquilerUsuario.ID_AL, idAlquiler);
            for (int i = 0; i < cont; i++) {
                ItemTenant m = list.get(i);
                db.revertir(TUsuario.T_NOMBRE, TUsuario.DNI, m.getDni());
            }
            view.showError("No se pudo agregar los usuarios");
        }
    }
    private void addRentalWasSuccess(DocumentReference document){
        /*long idAlquiler = db.getIdMaxAlquiler();*/
        rentalId = document.getId();
        Save s = new Save();
        int cont = 0;
        for (ItemTenant m : list) {
            m.setPath(s.SaveImage(view.getContext(), m.getPath(), view.getContext().getString(R.string.cTenant), m.getDni()));
        }
        db.agregarInquilinos(list, rentalId)
                .addOnSuccessListener(this::addTenantSuccess)
                .addOnFailureListener(this::addTentalFailure);

    }
    private void addRentalWasFailure(Exception e){
        view.showError("No se pudo agregar el alquiler, intente con nuevos datos");
    }

    @Override
    public void agregarAlquilerNuevo(ModelAA model) {
        modelToSave = model;
        if(model == null) {
            view.showMessage("Datos no validos");
            return;
        }
        if  (list.isEmpty()){
            view.showMessage("No hay usuarios en la lista");
            return;
        }

        if(modelSelect == null){
            view.showMessage("Seleccione un usuario responsable");
            return;
        }
/*
        if(reviewUsersOld() != null){

            return;
        }*/
        modelToSave.setTenantsNumber(list.size());
        if(modelToSave.isCorrect()){
            db.agregarAlquiler(modelToSave.getRoot())
                    .addOnSuccessListener(this::addRentalWasSuccess)
                    .addOnFailureListener(this::addRentalWasFailure);

        }else{
            view.showError("Campos vacios o invalidos");
        }
    }

    @Override
    public void setMain(RecyclerView.ViewHolder holder) {
        if(holder instanceof RvAdapterUser.Holder){
            RvAdapterUser.Holder mHolder = (RvAdapterUser.Holder) holder;
            if(modelSelect != null){
                modelSelect.setMain(false);
            }
            modelSelect = list.get(mHolder.getAdapterPosition());
            view.doPrincipal(mHolder);
            modelSelect.setMain(true);
        }
    }

    @Override
    public boolean saveChanges() {
        return list.isEmpty();
    }

    private void guardarModel(ItemTenant m){
        if(list.isEmpty()) {
            m.setMain(true);
            modelSelect = m;
        }
        list.add(m);
        view.mostrarNuevoUsuario(m);
    }

    class SaveUser implements OnSuccessListener<DocumentSnapshot>, OnFailureListener{
        public SaveUser(){

        }

        @Override
        public void onFailure(@NonNull Exception e) {

        }

        @Override
        public void onSuccess(@NotNull DocumentSnapshot documentSnapshot) {

        }

        private void userNotExists(Exception e) {
        }

        private void userExists(Task<DocumentSnapshot> task) {

        }
    }
}