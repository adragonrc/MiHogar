package com.alexander_rodriguez.mihogar.historialcasa;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.DataBase.parse.ParceRental;
import com.alexander_rodriguez.mihogar.adapters.Models.ModelAlquilerView;
import com.alexander_rodriguez.mihogar.adapters.RVARentals;
import com.alexander_rodriguez.mihogar.adapters.RvAdapterAlquiler;
import com.alexander_rodriguez.mihogar.DataBase.DBInterface;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemRental;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.adapters.RvAdapterRoom;
import com.alexander_rodriguez.mihogar.mi_casa.FragmentInterface;
import com.alexander_rodriguez.mihogar.mi_casa.MyHouseFragment;
import com.alexander_rodriguez.mihogar.tableActivity.TableActivity;
import com.alexander_rodriguez.mihogar.viewUser.DialogDetallesAlquiler;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UserRentalsPresenter implements FragmentInterface.presenter {
    private final FragmentInterface.view view;
    private final FragmentParent.presenter parent;
    private final DBInterface db;

    private String rentalId;
    private String roomNumber;

    private int iMenu;
    private int idItemMenuSelected;

    private Menu menu;

    private boolean showMain;

    private ArrayList<ItemRental> list;
    private Intent mIntent;
    private RVARentals adapter;

    private int cont;
    private int iCont;
    public UserRentalsPresenter(FragmentInterface.view view, FragmentParent.presenter parent) {
        this.view = view;
        this.parent = parent;
        this.db = parent.getDB();
        rentalId = view.getArguments().getString(MyHouseFragment.ARG_RENTAL_ID);
        roomNumber = view.getArguments().getString(MyHouseFragment.ARG_ROOM_NUMBER);
        list = new ArrayList<>();
        if(roomNumber == null || roomNumber.isEmpty()){
            view.showMessage("lost data");
        }
    }

    private void showData(){
        if(list.isEmpty()) refresh();
        else {
            showList();
        }
    }

    public void showList() {
        if(list.isEmpty())
            view.nothingHere();
        else {
            adapter = new RVARentals(view, list);
            view.showList(adapter, new LinearLayoutManager(view.getContext()));
        }
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
        showList();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {
        iniciarComandos();
    }

    @Override
    public void refresh() {
        if(roomNumber != null && !roomNumber.isEmpty()) {
            db.getRentalsOfRoom(roomNumber).addOnSuccessListener(this::getRentalsSuccess)
                    .addOnFailureListener(this::getRentalsFailure);
        }
    }

    @Override
    public void onClickHolder(RecyclerView.ViewHolder holder) {
        if(holder instanceof RVARentals.Holder){
            RVARentals.Holder mHolder = (RVARentals.Holder) holder;
            ItemRental model = mHolder.getModel();

            DialogDetallesAlquiler dialogDetallesAlquiler = new DialogDetallesAlquiler(view.getContext(), model);
            dialogDetallesAlquiler.setOnClickListenerVerCuarto(v -> {
                Intent i = new Intent(view.getContext(), HistorialCasaActivity.class);
                i.putExtra(HistorialCasaActivity.MODE, HistorialCasaActivity.USERS_OF_RENTAL);
                i.putExtra(HistorialCasaActivity.EXTRA_RENTAL_ID, model.getId());
                view.goTo(i);
            });
            dialogDetallesAlquiler.setOnClickListenerVerPagos(v -> {
                ParceRental parceRental = new ParceRental(model);
                Intent intent = new Intent(view.getContext(), TableActivity.class);
                intent.putExtra(TableActivity.EXTRA_RENTAL, parceRental);
                view.goTo(intent);
            });
            view.showDialog(dialogDetallesAlquiler);
        }
    }

    @Override
    public void onContextItemSelected(MenuItem item) {

    }


    private ContentValues getDetails(String getmId) {
        return null;
    }

    public void iniciarComandos() {
        showData();
    }
}
