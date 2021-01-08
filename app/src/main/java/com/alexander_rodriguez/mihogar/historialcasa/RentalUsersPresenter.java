package com.alexander_rodriguez.mihogar.historialcasa;

import android.content.ContentValues;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.Adapters.RvAdapterUser;
import com.alexander_rodriguez.mihogar.AdminDate;
import com.alexander_rodriguez.mihogar.Base.BasePresenter;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemTenant;
import com.alexander_rodriguez.mihogar.DataBase.models.TRentalTenant;
import com.alexander_rodriguez.mihogar.DataBase.parse.ParceTenant;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;
import com.alexander_rodriguez.mihogar.historialUserPakage.HistorialUsuarioActivity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RentalUsersPresenter extends BasePresenter<Interface.View> implements Interface.Presenter {
    private AdminDate adminDate;

    private String modo;
    private String idAlquiler;
    private String dni;

    private int iMenu;
    private int idItemMenuSelected;

    private Menu menu;

    private boolean showMain;

    private ArrayList<ItemTenant> list;
    private Intent mIntent;

    private int cont;
    private int iCont;

    private LinearLayoutManager manager;
    public RentalUsersPresenter(Interface.View view, Intent i) {
        super(view);
        adminDate = new AdminDate();
        idAlquiler = i.getStringExtra(TAlquiler.ID);
        idItemMenuSelected = -1;
        mIntent = i;
        manager = new LinearLayoutManager(view.getContext());
        list = new ArrayList<>();
    }

    @Override
    public void showList() {
        if(list.isEmpty()){
            loadUsersAndShow();
        }else
            view.showUsersList(list, manager, true);
    }

    private void loadUsersAndShow(){
        db.getRentalTenant(view.getContext().getString(R.string.mdRTRentalId), idAlquiler)
                .addOnSuccessListener(this::getRentalTenantSuccess);
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

    @Override
    public void crearMenu(MenuInflater menuInflater, Menu menu) {
        this.menu = menu;
        if (iMenu != 0){
            menuInflater.inflate(this.iMenu, menu);
            switch (idItemMenuSelected) {
                case R.id.iVerAlquileres: {
                    menu.findItem(R.id.item_ordenar_numero).setVisible(true);
                    menu.findItem(R.id.item_ordenar_nombre).setVisible(false);
                    break;
                }
                default: {
                    menu.findItem(R.id.item_ordenar_numero).setVisible(false);
                    menu.findItem(R.id.item_ordenar_nombre).setVisible(true);
                    break;
                }
            }
        }
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
    public void iniciarComandos() {
        showList();
    }
}
