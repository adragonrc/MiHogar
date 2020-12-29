package com.alexander_rodriguez.mihogar.mi_casa;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemRoom;
import com.alexander_rodriguez.mihogar.DataBase.models.TRoom;
import com.alexander_rodriguez.mihogar.MyAdminDate;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.Adapters.Models.ModelCuartoView;
import com.alexander_rodriguez.mihogar.modelos.ModelCuarto;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Presenter extends BasePresenter<Interface.View> implements Interface.Presenter{
    private ArrayList<ModelCuartoView> list;
    private Context mContext;
    private int contCallBack;

    public Presenter(Interface.View view) {
        super(view);
        mContext = view.getContext();
    }

    @Override
    public void iniciarComandos() {
    }

    @Override
    public void terminarAlquiler(String motivo, String id) {
        db.upDateAlquiler(TAlquiler.FECHA_SALIDA, MyAdminDate.getFechaActual(), id);
        db.upDateAlquiler(TAlquiler.MOTIVO, motivo, id);
        //view.mostrarAlquileres(getListAlquileres());
        verTodos();
    }

    @Override
    public void verTodos() {
        if(list == null ) refresh();
        else
            mostratCuartos(list);
    }

    @Override
    public void verCuartosAlquilados() {
        list = getListCuartosAlquilados();
    }

    @Override
    public void verCuartosLibres() {
        if(!list.isEmpty()) list.clear();
        db.getEmptyRooms().addOnSuccessListener(this::getAllRoomsSuccess);
    }

    @Override
    public void ordenarPorFecha() {
        Collections.sort(list, (o1, o2) -> {
            try {
                return MyAdminDate.comparar(o1.getPaymentDate(), o2.getPaymentDate());
            } catch (ParseException e) {
                e.printStackTrace();
                view.showMensaje("Error con la fecha");
                return 0;
            }
        });
        view.mostratCuartos(list);
    }

    @Override
    public Task<DocumentSnapshot> getRental(String currentRentalId) {
        return db.getRental(currentRentalId);
    }

    @Override
    public void mostratCuartos(ArrayList<ModelCuartoView> list) {
        view.mostratCuartos(list);
    }

    private ArrayList<ModelCuartoView> getListCuartosAlquilados(){
        String columnas = "*";
        Cursor c = db.getCuartosAlquilados(columnas);
        return list;
    }

    @Override
    public ArrayList<ModelCuartoView> getList() {
        return list;
    }

    public void refresh(){
        if(list == null) list = new ArrayList<>();
        else if(!list.isEmpty()) list.clear();

        db.getAllRoom().addOnSuccessListener(this::getAllRoomsSuccess);
    }

    private void getAllRoomsSuccess(QuerySnapshot queryDocumentSnapshots) {
        new DownloadRentalsTask(this).execute(queryDocumentSnapshots);
    }

    static  class DownloadRentalsTask extends AsyncTask<QuerySnapshot, Void, Void> {

        ArrayList<ModelCuartoView> list;
        Interface.Presenter presenter;

        public DownloadRentalsTask(Interface.Presenter presenter) {
            this.list = presenter.getList();
            this.presenter = presenter;
        }

        @Override
        protected Void doInBackground(QuerySnapshot... voids) {
            QuerySnapshot queryDocumentSnapshots = voids[0];
            for (QueryDocumentSnapshot roomDoc : queryDocumentSnapshots) {
                ModelCuartoView room = new  ModelCuartoView (roomDoc.toObject(TRoom.class));
                list.add(room);
                room.setRoomNumber(roomDoc.getId());
                if(room.getCurrentRentalId() != null && !room.getCurrentRentalId().isEmpty())
                    presenter.getRental(room.getCurrentRentalId()).addOnSuccessListener(new MListener<>(room));
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            presenter.mostratCuartos(list);
        }
    }
}
