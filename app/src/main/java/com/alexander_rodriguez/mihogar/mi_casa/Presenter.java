package com.alexander_rodriguez.mihogar.mi_casa;

import android.content.Context;
import android.database.Cursor;
import android.view.View;

import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.DataBase.models.TRental;
import com.alexander_rodriguez.mihogar.DataBase.models.TRoom;
import com.alexander_rodriguez.mihogar.AdminDate;
import com.alexander_rodriguez.mihogar.Adapters.Models.ModelCuartoView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

public class Presenter extends BasePresenter<Interface.View> implements Interface.Presenter{
    private final ArrayList<ModelCuartoView> list;
    private Context mContext;
    private int contCallBack;

    public Presenter(Interface.View view) {
        super(view);
        mContext = view.getContext();
        list = new ArrayList<>();
    }

    @Override
    public void iniciarComandos() {
    }

    @Override
    public void terminarAlquiler(String motivo, String id) {
        /*
        db.updateRental(mContext.getString(R.string.mdRentalDepartureDate), MyAdminDate.getFechaActual(), id);
        db.updateRental(mContext.getString(R.string.mdRentalReasonExit), motivo, id);
        db.upDateRoom(mContext.getString(R.string.mdroomCurrentRentalId), null)
        verTodos();*/

        view.showMessage("Funcion aun no implementada");
    }

    @Override
    public void verTodos() {
        if(list.isEmpty()) refresh();
        else
            mostratCuartos(list);
    }

    @Override
    public void verCuartosAlquilados() {
        /*list = getListCuartosAlquilados();*/
        view.showMessage("Funcion aun no implementada");
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
                return AdminDate.comparar(o1.getPaymentDate(), o2.getPaymentDate());
            } catch (ParseException e) {
                e.printStackTrace();
                view.showMessage("Error con la fecha");
                return 0;
            }
        });
        view.mostratCuartos(list);
    }

    private void mostratCuartos(ArrayList<ModelCuartoView> list) {
        view.setProgressBarVisibility(View.GONE);
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
        if(!list.isEmpty()) list.clear();

        db.getAllRoom().addOnSuccessListener(this::getAllRoomsSuccess);
    }

    private void getAllRoomsSuccess(QuerySnapshot queryDocumentSnapshots) {

        for (QueryDocumentSnapshot roomDoc : queryDocumentSnapshots) {
            ModelCuartoView room = new  ModelCuartoView (roomDoc.toObject(TRoom.class));
            list.add(room);
            room.setPosList(list.indexOf(room));
            room.setRoomNumber(roomDoc.getId());
            if(room.getCurrentRentalId() != null && !room.getCurrentRentalId().isEmpty()) {
                MListener listener = new MListener(room);
                db.getRental(room.getCurrentRentalId()).addOnSuccessListener(listener);
            }
        }
        if(list.isEmpty())
            view.nothingHere();
        mostratCuartos(list);
        //new DownloadRentalsTask(this).execute(queryDocumentSnapshots);
    }

    private class MListener  implements OnSuccessListener<DocumentSnapshot>{
        ModelCuartoView modelCuartoView;
        public MListener(ModelCuartoView modelCuartoView) {
            this.modelCuartoView = modelCuartoView;
        }

        @Override
        public void onSuccess(DocumentSnapshot t) {
            TRental rental = t.toObject(TRental.class);
            if (rental != null) {
                String entryDate = AdminDate.dateToString(rental.getEntryDate().toDate());;
                try {
                    String nextPaymentDate = AdminDate.adelantarPorMeses(entryDate, rental.getPaymentsNumber());
                    modelCuartoView.setPaymentDate(nextPaymentDate);
                } catch (ParseException e) {
                    modelCuartoView.setPaymentDate(null);
                }
            }else{
                modelCuartoView.setPaymentDate(null);
            }
            if(modelCuartoView.isAlert()) view.notifyChangedOn(modelCuartoView.getPosList());
        }
    }
    /*
    private class DownloadRentalsTask extends AsyncTask<QuerySnapshot, Void, Void> {

        ArrayList<ModelCuartoView> list;
        Interface.Presenter presenter;

        public DownloadRentalsTask(Interface.Presenter presenter) {
            this.list = presenter.getList();
            this.presenter = presenter;
        }

        @Override
        protected Void doInBackground(QuerySnapshot... voids) {

        }

        protected void onPostExecute(Void result) {
        }
    }*/

}
