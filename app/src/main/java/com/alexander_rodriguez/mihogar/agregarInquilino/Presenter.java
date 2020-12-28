package com.alexander_rodriguez.mihogar.agregarInquilino;

import android.widget.ArrayAdapter;

import com.alexander_rodriguez.mihogar.Adapters.RvAdapterUser;
import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.DataBase.FDAdministrator;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemUser;
import com.alexander_rodriguez.mihogar.DataBase.models.TMonthlyPayment;
import com.alexander_rodriguez.mihogar.DataBase.models.TPayment;
import com.alexander_rodriguez.mihogar.MyAdminDate;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.viewregistraralquiler.ModelAA;
import com.alexander_rodriguez.mihogar.Save;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquilerUsuario;
import com.alexander_rodriguez.mihogar.UTILIDADES.TUsuario;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Iterator;

public class Presenter extends BasePresenter<Interfaz.view> implements Interfaz.presenter {
    static final String SIN_REGISTROS = "-1";

    private Boolean confirmacion;
    private MyAdminDate adminDate;
    private ArrayList<String> cuartosDisponibles;

    private ArrayList<ItemUser> list;

    private ItemUser modelSelect;

    private RvAdapterUser.Holder holderSelect;

    private ModelAA modelToSave;

    private String rentalId;
    public Presenter(Interfaz.view view) {
        super(view);
        adminDate = new MyAdminDate();
        adminDate.setFormat(MyAdminDate.FORMAT_DATE_TIME);
        list = new ArrayList<>();
        confirmacion = false;
    }

    private boolean isNuevo(String dni){
        for (ItemUser m: list ){
            if (m.getDni().equals(dni)){
                return false;
            }
        }
        return true;
    }

    private void getRoomAvailableComplete(Task<QuerySnapshot> task){
        if(task.isSuccessful()){
            Iterator<QueryDocumentSnapshot> iterator = FDAdministrator.getTasksIterator(task);
            cuartosDisponibles = new ArrayList<>();
            if(iterator != null)
                while (iterator.hasNext()){
                    QueryDocumentSnapshot documentSnapshot = iterator.next();
                    cuartosDisponibles.add(documentSnapshot.getId());
                }
        }
    }
    @Override
    public void iniciarComandos() {
        /*cuartosDisponibles ;*/
        db.consultarNumerosDeCuartoDisponibles().addOnCompleteListener(this::getRoomAvailableComplete);

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
    public void agregarUsuario(ItemUser m) {
        int err = m.getErrorIfExist();
        if (err != -1) {
            view.showMensaje("Campo vacio en el campo: " + ItemUser.getLabelName(err));
            return;
        }
        if (!isNuevo(m.getDni())){
            view.showError("Usuario ya esta en lista: DNI");
            return;
        }
        if (db.existeUsuario(m.getDni())) {
            if (confirmacion){
                guardarModel(m);
            }else{
                if (db.esUsuarioAntiguo(m.getDni())){
                    view.showMensaje("número DNI ya esta registrado");
                    view.showDialog("Usuario Antiguo");
                }else {
                    if (db.esUsuarioInterno(m.getDni())) {
                        view.showMensaje("Usuario ya se encuentra en casa ;v");
                    }
                }
            }
        }else {
            guardarModel(m);
        }
    }

    @Override
    public void avanzar() {

    }

    private ItemUser reviewUsersOld(){
        for (ItemUser m: list ) {
            if(db.existeUsuario(m.getDni())){
                return m;
            };
        }
        return null;
    }

    private void addTenantSuccess(Void v){
        TMonthlyPayment monthlyPayment = new TMonthlyPayment(modelToSave.getPrice(), modelToSave.getEntryDate(), rentalId);
        db.agregarMensualidad(monthlyPayment)
                .addOnSuccessListener(this::addMonthlyPaymentSuccess)
                .addOnFailureListener(this::addMonthlyPaymentFailure);

    }

    private void addMonthlyPaymentSuccess(DocumentReference document) {
        if (modelToSave.wasPaid()) {
            TPayment payment = new TPayment(modelToSave.getEntryDate(), rentalId, modelToSave.getRoomNumber(), document.getId(), modelToSave.getPrice());
            db.agregarPago(payment)
                    .addOnSuccessListener(this::addPaymentSuccess)
                    .addOnFailureListener(this::addPaymentFailure);

            db.updateCurrentRentMP(rentalId, document);
        } else {
            view.close();
        }
    }

    private void addMonthlyPaymentFailure(Exception e) {
        view.showError("No se pudo agregar la mensualidad.");
        db.revertir(TAlquiler.T_NOMBRE, TAlquiler.ID, String.valueOf(rentalId));
    }
    private void addPaymentSuccess(DocumentReference documentReference) {
        db.updateCurrentRoomRent(modelToSave.getRoomNumber(), rentalId);
        db.updateTenantRoomNum(modelToSave.getRoomNumber(), list.size());

        view.showMensaje("OK");
        view.close();
    }
    private void addPaymentFailure(Exception e) {
        view.showError("No se pudo agregar el pago");
        e.printStackTrace();
    }

    private void addTentalFailure(Exception e){
        view.showMensaje("Could not add");
        e.printStackTrace();
    }
    private void revertir(int cont, int idAlquiler){
        if (cont < list.size()-1) {
            db.revertir(TAlquilerUsuario.T_NOMBRE, TAlquilerUsuario.ID_AL, idAlquiler);
            for (int i = 0; i < cont; i++) {
                ItemUser m = list.get(i);
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

        for (ItemUser m : list) {
            m.setPath(s.SaveImage(view.getContext(), m.getPath()));
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
        if  (list.isEmpty()){
            view.showMensaje("No hay usuarios en la lista");
            return;
        }

        if(modelSelect == null || holderSelect == null ){
            view.showMensaje("Seleccione un usuario responsable");
            return;
        }
/*
        if(reviewUsersOld() != null){

            return;
        }*/

        if(modelToSave.isCorrect()){
            db.agregarAlquiler(modelToSave.getRoot())
                    .addOnSuccessListener(this::addRentalWasSuccess)
                    .addOnFailureListener(this::addRentalWasFailure);

        }else{
            view.showError("Campos vacios");
        }
    }

    @Override
    public void doMain(RvAdapterUser.Holder holder) {
        if (holderSelect == null || modelSelect == null){
            holderSelect = holder;
            modelSelect = list.get(holder.getAdapterPosition());
            view.doPrincipal(holder);
            modelSelect.setMain(true);
        }else{
            modelSelect.setMain(false);
            ItemUser m  = list.get(holder.getAdapterPosition());
            view.cambiarPrincipal(holderSelect, holder);
            m.setMain(true);

            holderSelect = holder;
            modelSelect = m;
        }
    }

    @Override
    public boolean saveChanges() {
        return list.isEmpty();
    }

    private void guardarModel(ItemUser m){
        list.add(m);
        view.mostrarNuevoUsuario(m);
    }
}
/*
    @Override
    public void agregarUsuario(ModelUsuario mu, String numCuarto, String mensualidad, int plazo, String fecha, boolean pago) {

            if (validarImputs(mu.getDni(), mu.getNombres(), mu.getApellidoPat(), mu.getApellidoMat(), numCuarto, mensualidad)){

            String fecha_c = null;
            if (pago) {
                try {
                    fecha_c = adminDate.adelantarUnMes(fecha, 0);
                } catch (ParseException e) {
                    e.printStackTrace();
                    view.showError("fecha no disponible");
                    return;
                }
            }
            if (db.existeUsuario(mu.getDni())){
                if (confirmacion){
                    try {
                        db.agregarInquilinoExist(mu.getDni(), numCuarto, Double.parseDouble(mensualidad), fecha, fecha_c);
                        view.showMensaje("Alquiler Agregado");
                        view.close();
                    }catch (IllegalAccessError error){
                        view.showError("error, agregar usuario");
                    }
                }else{
                    if (db.esUsuarioAntiguo(mu.getDni())){
                        view.showMensaje("número DNI ya esta registrado");
                        view.showDialog("Usuario Antiguo");
                    }else {
                        if (db.esUsuarioInterno(mu.getDni())) {
                            view.showMensaje("Usuario ya se encuentra en casa ;v");
                        }
                    }
                }
            }else{
                try {
                    db.agregarNuevoInquilino(mu, numCuarto, Double.parseDouble(mensualidad), fecha, fecha_c);
                    view.showMensaje("Usuario Agregado");
                    view.close();
                }catch (IllegalAccessError error){
                    view.showError("error, agregar usuario");
                }
            }
        }
    }
*/