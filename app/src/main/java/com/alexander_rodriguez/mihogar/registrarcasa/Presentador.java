package com.alexander_rodriguez.mihogar.registrarcasa;

import android.content.Intent;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.listalquileres.Interface;
import com.alexander_rodriguez.mihogar.registrarcasa.details.DetailsView;
import com.alexander_rodriguez.mihogar.registrarcasa.register.RegisterView;
import com.google.firebase.auth.AuthResult;

public class Presentador extends BasePresenter<interfaz.view> implements interfaz.presentador {
    private final Intent intent;
    private String mode;
    private Interface anInterface = new Interface() {
        @Override
        public void ingresar(RegisterView register, DetailsView details) {

        }

        @Override
        public void initViews() {

        }
    };

    @Override
    protected void userNotLogin() {

    }

    public Presentador(interfaz.view view, Intent intent) {
        super(view);
        this.intent = intent;
    }

    @Override
    public void ingresar(RegisterView register, DetailsView details) {
        anInterface.ingresar(register, details);
    }

    public String getMode() {
        if(mode == null) mode = intent.getStringExtra(RegistrarCasaActivity.EXTRA_MODE);
        mode = mode == null? "": mode;
        return mode;
    }

    @Override
    public void iniciarComandos() {
        switch (getMode()){
            case RegistrarCasaActivity.EXTRA_ONLY_DETAILS:{
                anInterface = new OnlyDetails();
                break;
            }
            case RegistrarCasaActivity.EXTRA_NEW_USER: {
                anInterface = new NewUser();
                break;
            }
        }
        anInterface.initViews();
    }

    public void onResume(){

    }
    public interface Interface{
        void ingresar(RegisterView register, DetailsView details);
        void initViews();
    }

    public class OnlyDetails implements  Presentador.Interface{
        public OnlyDetails() {
        }

        public void ingresar(RegisterView register,DetailsView details){
            String detailsError = details.getError();
            if (detailsError != null) {
                view.showMessage(detailsError);
                return;
            }
            db.updateHouseDetails(details.toTHouse())
                .addOnSuccessListener(this::updateHouseSuccess)
                .addOnFailureListener(this::updateHouseFailure);
        }

        private void updateHouseFailure(Exception e) {
            e.printStackTrace();
            view.showMessage("Fallo al guardar");
            view.avanzar();
        }

        private void updateHouseSuccess(Void aVoid) {
            view.avanzar();
        }

        public void initViews(){
            view.onlyDetailsMode();
        }

    }

    public class NewUser implements Presentador.Interface{
        public NewUser() {
        }

        public void ingresar(RegisterView register, DetailsView details){
            String registerError = register.getError();
            if (registerError != null) {
                view.showMessage(registerError);
                return;
            }
            db.createUser(register.getEmail(), register.getPass())
                    .addOnSuccessListener(this::createUserSuccess)
                    .addOnFailureListener(this::createUserFailure);
        }
        public void initViews(){
            view.newUserMode();
        }

        private void createUserSuccess(AuthResult authResultTask) {
            db.initData();
            anInterface = new OnlyDetails();
            anInterface.initViews();
        }

        private void createUserFailure(Exception e) {
            e.printStackTrace();
            view.showMessage("No se pudo crear el usuario");
        }
    }
}
