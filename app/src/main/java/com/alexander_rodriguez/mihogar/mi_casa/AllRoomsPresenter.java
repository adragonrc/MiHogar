package com.alexander_rodriguez.mihogar.mi_casa;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.R;
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
    private final RecyclerView.LayoutManager manager;
    private RvAdapterRoom adapterRoom;

    public AllRoomsPresenter(FragmentInterface.view view, FragmentParent.presenter parent){
        this.view = view;
        this.parent = parent;
        list = new ArrayList<>();
        manager = new LinearLayoutManager(view.getContext());
    }

    @Override
    public void onCreate() {

    }

    private void showData(){
        if(list.isEmpty()) refresh();
        else {
            showRooms(list);
            chargeRentals();
        }
    }

    @Override
    public void onResume() {
        showData();
    }

    public void ordenarPorFecha() {
        Collections.sort(list, (o1, o2) -> {
            try {
                return AdminDate.comparar(o1.getPaymentDateAsString(), o2.getPaymentDateAsString());
            } catch (ParseException e) {
                e.printStackTrace();
                view.showMessage("Error con la fecha");
                return 0;
            }
        });
        showRooms(list);
    }


    public void verCuartosLibres() {
        if(!list.isEmpty()) list.clear();
        //db.getEmptyRooms().addOnSuccessListener(this::getAllRoomsSuccess);
    }

    public void refresh() {
        if(!list.isEmpty()) list.clear();
        view.setProgressBarVisibility(View.VISIBLE);
        parent.getDB().getAllRoom().addOnSuccessListener(this::getAllRoomsSuccess);
    }

    private void getAllRoomsSuccess(QuerySnapshot queryDocumentSnapshots) {
        for (QueryDocumentSnapshot roomDoc : queryDocumentSnapshots) {
            ModelRoomView room = new ModelRoomView(roomDoc.toObject(TRoom.class));
            if (!room.isHide()) {
                list.add(room);
                room.setPosList(list.indexOf(room));
                room.setRoomNumber(roomDoc.getId());

            }
        }
        showRooms(list);
        chargeRentals();
    }

    private void chargeRentals(){
        for (ModelRoomView room : list) {
            if (room.getCurrentRentalId() != null && !room.getCurrentRentalId().isEmpty()) {
                MListener listener = new MListener(room);
                parent.getDB().getRental(room.getCurrentRentalId()).addOnSuccessListener(listener);
            }
        }
    }

    private void showRooms(ArrayList<ModelRoomView> list) {
        if(list.isEmpty())
            view.nothingHere();
        else {
            adapterRoom = new RvAdapterRoom(view, list);
            view.showList(adapterRoom, manager);
        }
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
                Date nextPaymentDate = AdminDate.adelantarPorMeses(entryDate, rental.getPaymentsNumber());
                modelRoomView.setPaymentDate(nextPaymentDate);
                if(modelRoomView.isAlert()) {
                    adapterRoom.notifyItemChanged(modelRoomView.getPosList());
                }
            }else{
                modelRoomView.setPaymentDate(new Date(0));
            }
        }
    }

    @Override
    public void onClickHolder(RecyclerView.ViewHolder holder) {
        if(holder instanceof RvAdapterRoom.Holder){
            RvAdapterRoom.Holder mHolder = (RvAdapterRoom.Holder) holder;
            ModelRoomView model = mHolder.getModel();
            Intent i = new Intent(view.getContext(), ShowRoomActivity.class);
            i.putExtra(TCuarto.NUMERO, model.getRoomNumber());
            view.goTo(i);
        }
    }

    @Override
    public void onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        /*
            case R.id.opVerPagos: {
                Intent i = new Intent(getContext(), TableActivity.class);
                i.putExtra(TAlquiler.ID, adapterUsuarios.getDniSelect());
                getContext().startActivity(i);
                break;
            }
            case R.id.opTerminarA: {
                showDialogImput(adapterUsuarios.getDniSelect());
                break;
            }
            default:
                Toast.makeText(this, "Caso no encontrado", Toast.LENGTH_SHORT).show();*/
        if (id == R.id.opDeleteRoom) {
            deleteRoom();
        }
    }

    private void deleteRoom() {
        ModelRoomView model = adapterRoom.getHolderSelect().getModel();
        if (model.getNumberOfRentals()>0){
            parent.getDB().updateRoom(view.getContext().getString(R.string.mdRoomIsHide), true, model.getRoomNumber())
                .addOnSuccessListener(this::roomHideSuccess);
        }else{
            parent.getDB().getCuartoDR(model.getRoomNumber()).delete().addOnSuccessListener(this::roomDeleteSuccess);
        }
    }

    private void roomHideSuccess(Void aVoid) {
        adapterRoom.removeItem(adapterRoom.getHolderSelect().getLayoutPosition());
    }

    private void roomDeleteSuccess(Void aVoid) {
        adapterRoom.removeItem(adapterRoom.getHolderSelect().getLayoutPosition());
    }
}
