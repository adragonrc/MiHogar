package com.alexander_rodriguez.mihogar.agregarInquilino;

import android.widget.ArrayAdapter;

import com.alexander_rodriguez.mihogar.Adapters.RvAdapterUser;
import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.MyAdminDate;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.viewregistraralquiler.ModelAA;
import com.alexander_rodriguez.mihogar.Save;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquilerUsuario;
import com.alexander_rodriguez.mihogar.UTILIDADES.TUsuario;
import com.alexander_rodriguez.mihogar.modelos.ModelUsuario;

import java.util.ArrayList;

public class Presenter extends BasePresenter<Interfaz.view> implements Interfaz.presenter {
    static final String SIN_REGISTROS = "-1";

    private Boolean confirmacion;
    private MyAdminDate adminDate;
    private String [] cuartosDisponibles;

    private ArrayList<ModelUsuario> list;

    private ModelUsuario modelSelect;

    private RvAdapterUser.Holder holderSelect;

    public Presenter(Interfaz.view view) {
        super(view);
        adminDate = new MyAdminDate();
        adminDate.setFormat(MyAdminDate.FORMAT_DATE_TIME);
        list = new ArrayList<>();
        confirmacion = false;
    }

    private boolean isNuevo(String dni){
        for (ModelUsuario m: list ){
            if (m.getDni().equals(dni)){
                return false;
            }
        }
        return true;
    }

    @Override
    public void iniciarComandos() {
        cuartosDisponibles = db.consultarNumerosDeCuartoDisponibles();
        if (cuartosDisponibles.length == 0) {
            view.sinCuartos();
        }
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
    public void agregarUsuario(ModelUsuario m) {
        if (!validarStrings(m.getDni(), m.getNombre(), m.getApellidoPat(), m.getApellidoMat())) {
            view.showError("Campo vacio");
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

    private ModelUsuario  reviewUsersOld(){
        for (ModelUsuario m: list ) {
            if(db.existeUsuario(m.getDni())){
                return m;
            };
        }
        return null;
    }
    @Override
    public void agregarAlquilerNuevo(ModelAA model) {
        if  (list.isEmpty()){
            view.showMensaje("No hay usuarios en la lista");
            return;
        }

        if(modelSelect == null || holderSelect == null ){
            view.showMensaje("Seleccione un usuario responsable");
            return;
        }

        if(reviewUsersOld() != null){

            return;
        }

        if(model.isCorrect()){
            if (db.agregarAlquiler(model)) {
                long idAlquiler = db.getIdMaxAlquiler();
                Save s = new Save();
                int cont = 0;
                for (ModelUsuario m : list) {
                    m.setPath(s.SaveImage(view.getContext(), m.getPath()));
                    if(!db.agregarInquilino(m)) break;
                    if (!db.agregarAlquilerUsuario(idAlquiler, m.getDni(), m.isMain())) break;
                    cont ++ ;
                }

                if (cont == list.size()) {
                    if (db.agregarMensualidad(Double.parseDouble(model.getPrecio()), model.getFecha(), idAlquiler)) {
                        if (model.pago()) {
                            long idMax = db.getIDMaxMensualidad();
                            if(!db.agregarPago(model.getFecha(), idMax, Integer.parseInt(modelSelect.getDni()))){
                                view.showError("No se pudo agregar el pago");
                            }else {
                                view.close();
                            }
                        }else view.close();
                    }else{
                        view.showError("No se pudo agregar la mensualidad.");
                        db.revertir(TAlquiler.T_NOMBRE, TAlquiler.ID, String.valueOf(idAlquiler));
                    }
                }else{
                    db.revertir(TAlquilerUsuario.T_NOMBRE, TAlquilerUsuario.ID_AL, idAlquiler);
                    for (int i = 0; i < cont; i++) {
                        ModelUsuario m = list.get(i);
                        db.revertir(TUsuario.T_NOMBRE, TUsuario.DNI, m.getDni());
                    }
                    view.showError("No se pudo agregar los usuarios");
                }
            }else{
                view.showError("No se pudo agregar el alquiler, intente con nuevos datos");
            }
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
            ModelUsuario m  = list.get(holder.getAdapterPosition());
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

    private void guardarModel(ModelUsuario m){
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