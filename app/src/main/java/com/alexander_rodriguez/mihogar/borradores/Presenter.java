package com.alexander_rodriguez.mihogar.borradores;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;

public class Presenter {
    /*
    public Presenter(interfaz.view view) {
        super(view);
    }

    @Override
    public void iniciarComandos() {

    }
    /*
    private MyAdminDate myTime;
    private Boolean confirmacion;
    private MyAdminDate adminDate;

    public Presenter(interfaz.view view){
        super(view);
        adminDate = new MyAdminDate();
        adminDate.setFormat(MyAdminDate.FORMAT_DATE_TIME);
    }
    private boolean validarImputs(String ...s){
        for (String s1 : s)
            if (s1.equals("")) {
                view.showError("Campo vacio");
                return false;
            }
        return true;
    }

    @Override
    public void agregarUsuario(ModelUsuario mu, String numCuarto, String mensualidad, int plazo, String fecha, boolean pago) {

        if(mu.getUriPhoto() == null ||  mu.getUriPhoto().equals("")){
            view.showMensaje("agrega una foto");
            return;
        }
        if (validarImputs(mu.getDni(), mu.getNombres(), mu.getApellidoPat(), mu.getApellidoMat(), numCuarto, mensualidad)){
            String fecha_c = null;
            if (pago) {
                try {
                    fecha_c = adminDate.getFechaSiguiente(fecha, 0);
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

    public void confirmar() {
        this.confirmacion = true;
    }

    @Override
    public void onClickAddUser() {

    }

    @Override
    public void agregarUsuario(Model datos) {

    }

    @Override
    public boolean doPago(RadioGroup radioGroup) {
        return radioGroup.getCheckedRadioButtonId() == R.id.rbCancelo;
    }

    @Override
    public void iniciarComandos() {
        myTime = new MyAdminDate();
        String []numeroCuartos = db.consultarNumerosDeCuartoDisponibles();

        if (numeroCuartos.length == 0){
            view.sinCuartos();
        }else{
            ArrayAdapter<String> adapter= new ArrayAdapter<>(view.getContext(), R.layout.support_simple_spinner_dropdown_item, numeroCuartos);
            view.prepararSpinsers(adapter);
        }
        confirmacion = false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == P_HORAS || position == P_MINUTOS){
            this.view.mostrarEtPlazo();
        }else{
            this.view.ocultarEtPlazo();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }*/
}
