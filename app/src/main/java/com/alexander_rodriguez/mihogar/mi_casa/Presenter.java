package com.alexander_rodriguez.mihogar.mi_casa;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.DataBase.models.TRoom;
import com.alexander_rodriguez.mihogar.MyAdminDate;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.Adapters.Models.ModelCuartoView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Presenter extends BasePresenter<Interface.View> implements Interface.Presenter, MListener.CallBack {
    private ArrayList<ModelCuartoView> list;
    private Context mContext;
    private int contCallBack;

    public Presenter(Interface.View view) {
        super(view);
        mContext = view.getContext();
        list = new ArrayList<>();
    }

    @Override
    public void iniciarComandos() {
        //View.mostratCuartos(getListCuartos());
        //View.mostrarAlquileres(getListAlquileres());
        view.mostratCuartos(getListCuartos());
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
        list = getListCuartos();
        view.mostratCuartos(list);
    }

    @Override
    public void verCuartosAlquilados() {
        list = getListCuartosAlquilados();
        view.mostratCuartos(list);
    }

    @Override
    public void verCuartosLibres() {
        if(!list.isEmpty()) list.clear();
        db.getEmptyRooms().addOnSuccessListener(this::getAllRoomsSuccess);

        view.mostratCuartos(list);}

    @Override
    public void ordenarPorFecha() {
        Collections.sort(list, new Comparator<ModelCuartoView>() {
            @Override
            public int compare(ModelCuartoView o1, ModelCuartoView o2) {
                try {
                    return MyAdminDate.comparar(o1.getFechaCancelar(), o2.getFechaCancelar());
                } catch (ParseException e) {
                    e.printStackTrace();
                    view.showMensaje("Error con la fecha");
                    return 0;
                }
            }
        });
        view.mostratCuartos(list);
    }

    private ArrayList<ModelCuartoView> getListCuartosAlquilados(){
        String columnas = "*";
        Cursor c = db.getCuartosAlquilados(columnas);
        return list;
    }
    private ArrayList<ModelCuartoView> getListCuartos() {
        if(!list.isEmpty()) list.clear();
        db.getAllRoom().addOnSuccessListener(this::getAllRoomsSuccess);
        return list;
    }

    private void getAllRoomsSuccess(QuerySnapshot queryDocumentSnapshots) {

        DownloadRentalsTask task = new DownloadRentalsTask();
        task.execute(queryDocumentSnapshots);
    }

    @Override
    public void onComplete() {

    }

    private void getRentSuccess(DocumentSnapshot documentSnapshot) {

    }
    private class DownloadRentalsTask extends AsyncTask<QuerySnapshot, Void, Void> {

        @Override
        protected Void doInBackground(QuerySnapshot... voids) {
            QuerySnapshot queryDocumentSnapshots = voids[0];
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                ModelCuartoView room = (ModelCuartoView) document.toObject(TRoom.class);
                list.add(room);
                room.setRoomNumber(document.getId());
                db.getRentalDR(room.getCurrentRentalId()).get().addOnSuccessListener(new MListener<>(room));

            }
            return null;
        }

        protected void onPostExecute(Void result) {
            view.mostratCuartos(list);
        }
    }
}
