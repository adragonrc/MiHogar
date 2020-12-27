package com.alexander_rodriguez.mihogar.agregarcuarto;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.DataBase.DBInterface;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemRoom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public class Presenter extends BasePresenter<Interfaz.view> implements Interfaz.Presenter{
    private ItemRoom roomToSave;
    private OnCompleteListener<DocumentSnapshot> oclGetRoom  = this::getRoomComplete;
    private OnCompleteListener<Void> oclAddRoom  = this::addRoomComplete;
    public Presenter(Interfaz.view view) {
        super(view);
    }

    private void getRoomComplete(Task<DocumentSnapshot> task){
        if(task.isSuccessful()) {
            if (task.getResult().exists()) {
                view.showMensaje("Numero de cuarto ya existe");
            } else {
                db.agregarCuarto(roomToSave).addOnCompleteListener(oclAddRoom);
            }
        }
    }
    private void addRoomComplete(Task<Void> task){
        if(task.isSuccessful()){
            view.showMensaje("Agregado");
            view.salir();
        }
    }
    public void insertarCuarto(String numCuarto, String precio, String detalles, String path){
        if (path == null ) path = "";
        roomToSave = new ItemRoom(numCuarto, detalles, null, precio, path);
        int err = roomToSave.getErrorIfExist();
        if(err == -1){
            db.getCuartoDR(numCuarto).get().addOnCompleteListener(oclGetRoom);
        }else{
            view.showMensaje("Campo vacio: " + ItemRoom.getLabelName(err));
        }
    }

    @Override
    public void iniciarComandos() { }
}
