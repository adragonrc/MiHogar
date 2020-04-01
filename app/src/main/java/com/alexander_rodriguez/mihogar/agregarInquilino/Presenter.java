package com.alexander_rodriguez.mihogar.agregarInquilino;

import android.widget.ArrayAdapter;
import android.widget.RadioGroup;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.Modelos.ModelUsuario;
import com.alexander_rodriguez.mihogar.MyAdminDate;
import com.alexander_rodriguez.mihogar.R;

import java.text.ParseException;
import java.util.Date;

public class Presenter extends BasePresenter<Interfaz.View> implements Interfaz.Presenter{
    public static String ON_CREATE  = "onCreate";
    public static String ON_RESUME  = "onResume";
    private MyAdminDate myTime;
    private Boolean confirmacion;

    public Presenter(Interfaz.View view){
        super(view);
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
    public void agregarUsuario(ModelUsuario mu, String numCuarto, String mensualidad, String fecha, boolean pago) {

        if(mu.getUriPhoto() == null ||  mu.getUriPhoto().equals("")){
            view.showMensaje("agrega una foto");
            return;
        }
        if (validarImputs(mu.getDni(), mu.getNombres(), mu.getApellidoPat(), mu.getApellidoMat(), numCuarto, mensualidad)){
            String fecha_c = null;
            if (pago) {
                try {
                    Date date = myTime.getDateFormat().parse(fecha);
                    date.setMonth(date.getMonth() + 1);
                    fecha_c = myTime.getDateFormat().format(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                    view.showError("no se pudo completar la accion");
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
            ArrayAdapter<String> adapter= new ArrayAdapter<String>(view.getContext(), R.layout.support_simple_spinner_dropdown_item, numeroCuartos);
            view.prepararSpinsers(adapter);
        }
        confirmacion = false;
    }
}
