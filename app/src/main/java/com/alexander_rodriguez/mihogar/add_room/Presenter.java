package com.alexander_rodriguez.mihogar.add_room;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemRoom;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

public class Presenter extends BasePresenter<Interfaz.view> implements Interfaz.Presenter{
    private ItemRoom roomToSave;
    private OnSuccessListener<DocumentSnapshot> oclGetRoom  = this::getRoomComplete;
    private OnSuccessListener<Void> oclAddRoom  = this::addRoomComplete;
    public Presenter(Interfaz.view view) {
        super(view);
    }

    private void getRoomComplete(DocumentSnapshot task){
        if (task.exists()) {
            view.showMessage("Numero de cuarto ya existe");
        } else {
            db.agregarCuarto(roomToSave).addOnSuccessListener(oclAddRoom);
        }
    }
    private void addRoomComplete(Void task){
        view.showMessage("Agregado");
        view.salir();
    }
    public void insertarCuarto(String numCuarto, String precio, String detalles, String path){
        if (path == null ) path = "";
        roomToSave = new ItemRoom(detalles, path, precio, numCuarto);
        int err = roomToSave.getErrorIfExist();
        if(err == -1){
            db.getCuartoDR(numCuarto).get().addOnSuccessListener(oclGetRoom);
        }else{
            view.showMessage("Campo vacio: " + ItemRoom.getLabelName(err));
        }
    }

    @Override
    public void iniciarComandos() { }
}
