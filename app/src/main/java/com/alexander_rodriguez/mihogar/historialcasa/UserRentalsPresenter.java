package com.alexander_rodriguez.mihogar.historialcasa;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.Adapters.RvAdapterAlquiler;
import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemRental;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.tableActivity.TableActivity;
import com.alexander_rodriguez.mihogar.viewUser.DialogDetallesAlquiler;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UserRentalsPresenter extends BasePresenter<Interface.View> implements Interface.Presenter {
    private String rentalId;
    private String roomNumber;

    private int iMenu;
    private int idItemMenuSelected;

    private Menu menu;

    private boolean showMain;

    private ArrayList<ItemRental> list;
    private Intent mIntent;

    private int cont;
    private int iCont;
    public UserRentalsPresenter(Interface.View view, Intent intent) {
        super(view);
        rentalId = intent.getStringExtra(HistorialCasaActivity.EXTRA_RENTAL_ID);
        roomNumber = intent.getStringExtra(HistorialCasaActivity.EXTRA_ROOM_NUMBER);
        list = new ArrayList<>();
        if(roomNumber == null || roomNumber.isEmpty()){
            view.showMessage("lost data");
        }
    }

    @Override
    public void showList() {
        if(list == null ) list = new ArrayList<>();
        if(list.isEmpty())
            if(roomNumber != null && !roomNumber.isEmpty()) {
                db.getRentalsOfRoom(roomNumber).addOnSuccessListener(this::getRentalsSuccess)
                        .addOnFailureListener(this::getRentalsFailure);
            }
        else
            view.showRentalsList(list, new LinearLayoutManager(view.getContext()));
    }

    private void getRentalsFailure(Exception e) {
        view.showMessage("No se pudo descargar los datos");
        e.printStackTrace();
    }

    private void getRentalsSuccess(QuerySnapshot queryDocumentSnapshots) {
        if(rentalId == null) rentalId = "";
        for (DocumentSnapshot doc : queryDocumentSnapshots){
            ItemRental rental = ItemRental.newInstance(doc);
            if(!rental.getId().equals(rentalId)){
                list.add(rental);
            }
        }
        if(!list.isEmpty()){
            view.showRentalsList(list, new LinearLayoutManager(view.getContext()));
        }
    }

    @Override
    public void crearMenu(MenuInflater menuInflater, Menu menu) {

    }

    @Override
    public ContentValues getDetails(String id) {
        return null;
    }

    @Override
    public void ordenarPorNombre() {

    }

    @Override
    public void ordenarPorNumero() {

    }

    @Override
    public void itemSelected(MenuItem item) {

    }

    @Override
    public void onClickHolder(RecyclerView.ViewHolder holder) {
        if(holder instanceof RvAdapterAlquiler.Holder){
            RvAdapterAlquiler.Holder mHolder = (RvAdapterAlquiler.Holder) holder;

            Bundle bundle = new Bundle();
            ContentValues alquiler = getDetails(mHolder.getmId());

            DialogDetallesAlquiler dialogDetallesAlquiler = new DialogDetallesAlquiler(view.getContext(), alquiler);
            dialogDetallesAlquiler.setOnClickListenerVerCuarto(v -> {
                Intent i = new Intent(view.getContext(), HistorialCasaActivity.class);
                i.putExtra(HistorialCasaActivity.MODE, HistorialCasaActivity.ALL_USERS);
                i.putExtra(TAlquiler.ID, mHolder.getmId());
                view.goTo(i);
            });
            dialogDetallesAlquiler.setOnClickListenerVerPagos(v -> {
                Intent intent = new Intent(view.getContext(), TableActivity.class);
                intent.putExtra(TAlquiler.ID, mHolder.getmId());
                view.goTo(intent);
            });

            view.showDialog(dialogDetallesAlquiler);

        }
    }

    @Override
    public void iniciarComandos() {
        showList();
    }
}
