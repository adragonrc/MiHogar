package com.alexander_rodriguez.mihogar.agregarcuarto;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.DataBase.DBInterface;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemRoom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public class FPresenter extends BasePresenter<Interfaz.view> implements Interfaz.Presenter{
    private ItemRoom roomToSave;
    private OnCompleteListener<DocumentSnapshot> c1  = this::complete;
    public FPresenter(Interfaz.view view) {
        super(view);
    }

    private void complete(Task<DocumentSnapshot> task){
        if(task.isSuccessful()) {
            if (task.getResult().exists()) {
                view.showMensaje("Numero de cuarto ya existe");
            } else {
                db.agregarCuarto(roomToSave);
                view.showMensaje("Agregado");
                view.salir();
            }
        }
    }
    public void insertarCuarto(String numCuarto, String precio, String detalles, String path){
        if (path == null ) path = "";
        roomToSave = new ItemRoom(numCuarto, detalles, null, precio, path);

        if(roomToSave.getErrorIfExist() != -1){
            db.getCuartoDR(numCuarto).get().addOnCompleteListener(c1);
        }else{
            view.showMensaje("Campo vacio");
        }
    }

    @Override
    public void iniciarComandos() {
    }


    public class Iterator{
        DBInterface mdb;
        public Iterator(DBInterface mdb){
            this.mdb = mdb;
        }

        void existRoom(String num){

        }
    }
}
