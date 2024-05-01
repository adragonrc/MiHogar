package com.alexander_rodriguez.mihogar.historial_casa;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.adapters.RvAdapterUser;
import com.alexander_rodriguez.mihogar.AdminDate;
import com.alexander_rodriguez.mihogar.DataBase.DBInterface;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemTenant;
import com.alexander_rodriguez.mihogar.DataBase.models.TRentalTenant;
import com.alexander_rodriguez.mihogar.DataBase.parse.ParceTenant;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.history_user.HistorialUsuarioActivity;
import com.alexander_rodriguez.mihogar.mi_casa.FragmentInterface;
import com.alexander_rodriguez.mihogar.mi_casa.MyHouseFragment;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RentalUsersPresenter implements FragmentInterface.presenter {
    private AdminDate adminDate;

    private String modo;
    private String idAlquiler;
    private String dni;

    private int iMenu;
    private int idItemMenuSelected;

    private Menu menu;

    private boolean showMain;

    private final FragmentInterface.view view;
    private final FragmentParent.presenter parent;
    private final DBInterface db;
    private ArrayList<ItemTenant> list;

    private int cont;
    private int iCont;

    private LinearLayoutManager manager;
    public RentalUsersPresenter(FragmentInterface.view view, FragmentParent.presenter parent) {
        this.view = view;
        this.parent = parent;
        this.db = parent.getDB();
        adminDate = new AdminDate();
        idAlquiler = view.getArguments().getString(MyHouseFragment.ARG_RENTAL_ID);
        idItemMenuSelected = -1;
        manager = new LinearLayoutManager(view.getContext());
        list = new ArrayList<>();
    }

    private void showData(){

    }
    public void showList() {
        if(list.isEmpty()){
            refresh();
        }else
            view.showUsersList(list, manager, true);
    }


    private void getRentalTenantSuccess(QuerySnapshot queryDocumentSnapshots) {
        cont = queryDocumentSnapshots.size();
        iCont = 0;
        for (QueryDocumentSnapshot doc: queryDocumentSnapshots){
            TRentalTenant rentalTenant = doc.toObject(TRentalTenant.class);
            db.getUser(rentalTenant.getDNI()).addOnSuccessListener(this::getUserSuccess).addOnFailureListener(this::getUserFailure);
        }
    }

    private void getUserFailure(Exception e) {
        iCont++;
        if (iCont == cont){
            view.showUsersList(list, manager, true);
        }
    }

    private void getUserSuccess(DocumentSnapshot document) {
        ItemTenant user = ItemTenant.newInstance(document);
        if (user != null)
            list.add(user);
        iCont++;
        if (iCont == cont){
            view.showUsersList(list, manager, true);
        }
    }

    public void crearMenu(MenuInflater menuInflater, Menu menu) {
        this.menu = menu;
        if (iMenu != 0){
            menuInflater.inflate(this.iMenu, menu);
            if (idItemMenuSelected == R.id.iVerAlquileres) {
                menu.findItem(R.id.item_ordenar_numero).setVisible(true);
                menu.findItem(R.id.item_ordenar_nombre).setVisible(false);
            } else {
                menu.findItem(R.id.item_ordenar_numero).setVisible(false);
                menu.findItem(R.id.item_ordenar_nombre).setVisible(true);
            }
        }
    }

    @Override
    public void onClickHolder(RecyclerView.ViewHolder holder) {
        if(holder instanceof RvAdapterUser.Holder){
            ItemTenant itemTenant = list.get(holder.getAdapterPosition());
            ParceTenant parceTenant = new ParceTenant(itemTenant);
            Intent intent = new Intent(view.getContext(), HistorialUsuarioActivity.class);
            intent.putExtra(HistorialUsuarioActivity.USER_EXTRA, parceTenant);
            intent.putExtra(HistorialUsuarioActivity.DNI_EXTRA, parceTenant.getDni());
            view.goTo(intent);
        }
    }

    @Override
    public void onContextItemSelected(MenuItem item) {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {
        showList();

    }

    @Override
    public void refresh() {
        if(!list.isEmpty()) list.clear();
        view.setProgressBarVisibility(View.VISIBLE);
        if (idAlquiler!= null && !idAlquiler.isEmpty())
            db.getRentalTenant(view.getContext().getString(R.string.mdRTRentalId), idAlquiler)
                    .addOnSuccessListener(this::getRentalTenantSuccess);
    }
}
