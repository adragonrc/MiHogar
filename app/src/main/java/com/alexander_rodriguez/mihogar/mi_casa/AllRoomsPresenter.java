package com.alexander_rodriguez.mihogar.mi_casa;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.adapters.Models.ModelRoomView;
import com.alexander_rodriguez.mihogar.adapters.RvAdapterRoom;
import com.alexander_rodriguez.mihogar.AdminDate;
import com.alexander_rodriguez.mihogar.DataBase.models.TRental;
import com.alexander_rodriguez.mihogar.DataBase.models.TRoom;
import com.alexander_rodriguez.mihogar.UTILIDADES.TCuarto;
import com.alexander_rodriguez.mihogar.historialcasa.FragmentParent;
import com.alexander_rodriguez.mihogar.vercuarto.ShowRoomActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class AllRoomsPresenter implements FragmentInterface.presenter {
    private final FragmentInterface.view view;
    private final FragmentParent.presenter parent;
    private final ArrayList<ModelRoomView> list;

    public AllRoomsPresenter(FragmentInterface.view view, FragmentParent.presenter parent){
        this.view = view;
        this.parent = parent;
        list = new ArrayList<>();
    }

    @Override
    public void onCreate() {

    }

    private void showData(){
        if(list.isEmpty()) refresh();
        else
            showRooms(list);
    }

    @Override
    public void onResume() {
        showData();
    }

    private void mostratCuartos(ArrayList<ModelRoomView> list) {

    }

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
        view.showRoomsList(list);
    }


    public void verCuartosLibres() {
        if(!list.isEmpty()) list.clear();
        //db.getEmptyRooms().addOnSuccessListener(this::getAllRoomsSuccess);
    }
    private void refresh() {
        if(!list.isEmpty()) list.clear();
        view.setProgressBarVisibility(View.VISIBLE);
        parent.getDB().getAllRoom().addOnSuccessListener(this::getAllRoomsSuccess);
    }

    private void getAllRoomsSuccess(QuerySnapshot queryDocumentSnapshots) {

        for (QueryDocumentSnapshot roomDoc : queryDocumentSnapshots) {
            ModelRoomView room = new ModelRoomView(roomDoc.toObject(TRoom.class));
            list.add(room);
            room.setPosList(list.indexOf(room));
            room.setRoomNumber(roomDoc.getId());
            if(room.getCurrentRentalId() != null && !room.getCurrentRentalId().isEmpty()) {
                MListener listener = new MListener(room);
                parent.getDB().getRental(room.getCurrentRentalId()).addOnSuccessListener(listener);
            }
        }
        showRooms(list);
    }
    private void showRooms(ArrayList<ModelRoomView> list) {
        if(list.isEmpty())
            view.nothingHere();
        else
            view.showRoomsList(list);
    }

    private class MListener  implements OnSuccessListener<DocumentSnapshot> {
        ModelRoomView modelRoomView;
        public MListener(ModelRoomView modelRoomView) {
            this.modelRoomView = modelRoomView;
        }

        @Override
        public void onSuccess(DocumentSnapshot t) {
            TRental rental = t.toObject(TRental.class);
            if (rental != null) {
                Date entryDate = rental.getEntryDate().toDate();
                try {
                    String nextPaymentDate = AdminDate.adelantarPorMeses(entryDate, rental.getPaymentsNumber());
                    modelRoomView.setPaymentDate(nextPaymentDate);
                } catch (ParseException e) {
                    modelRoomView.setPaymentDate(null);
                }
            }else{
                modelRoomView.setPaymentDate(null);
            }
            if(modelRoomView.isAlert()) view.notifyChangedOn(modelRoomView.getPosList());
        }
    }

    @Override
    public void onClickHolder(RecyclerView.ViewHolder holder) {
        if(holder instanceof RvAdapterRoom.Holder){
            RvAdapterRoom.Holder mHolder = (RvAdapterRoom.Holder) holder;
            String numero = mHolder.getTvTitle().getText().toString();
            Intent i = new Intent(view.getContext(), ShowRoomActivity.class);
            i.putExtra(TCuarto.NUMERO, numero);
            view.goTo(i);
        }
    }
}
