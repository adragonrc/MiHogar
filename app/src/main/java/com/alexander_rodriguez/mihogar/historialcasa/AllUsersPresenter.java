package com.alexander_rodriguez.mihogar.historialcasa;

import android.content.ContentValues;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.adapters.RvAdapterUser;
import com.alexander_rodriguez.mihogar.AdminDate;
import com.alexander_rodriguez.mihogar.DataBase.DBInterface;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemTenant;
import com.alexander_rodriguez.mihogar.DataBase.parse.ParceTenant;
import com.alexander_rodriguez.mihogar.historialUserPakage.HistorialUsuarioActivity;
import com.alexander_rodriguez.mihogar.mi_casa.FragmentInterface;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AllUsersPresenter  implements FragmentInterface.presenter{

    private final FragmentInterface.view view;
    private final FragmentParent.presenter parent;
    private final DBInterface db;

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
    public AllUsersPresenter(FragmentInterface.view view, FragmentParent.presenter parent) {
        this.view = view;
        this.parent = parent;
        this.db = parent.getDB();
        list = new ArrayList<>();
        manager = new LinearLayoutManager(view.getContext());
    }

    public void showData() {
        if(list.isEmpty())
            refresh();
        else {
            showTenants(list);
        }
    }

    public void refresh(){
        if(!list.isEmpty()) list.clear();
        view.setProgressBarVisibility(View.VISIBLE);
        db.getUserCR().get().addOnSuccessListener(this::getUsersSuccess).addOnFailureListener(this::getUsersFailure);
    }

    private void getUsersFailure(Exception e) {
        view.showMessage("Get Tenants Failure");
        e.printStackTrace();
    }

    private void getUsersSuccess(QuerySnapshot queryDocumentSnapshots) {
        for (QueryDocumentSnapshot doc: queryDocumentSnapshots){
            ItemTenant user = ItemTenant.newInstance(doc);
            if(user != null){
                list.add(user);
            }
        }
        showTenants(list);
    }
    private void showTenants(ArrayList<ItemTenant> list) {
        if(list.isEmpty())
            view.nothingHere();
        else {
            RvAdapterUser adapterUser = new RvAdapterUser(view, list, false);
            view.showList(adapterUser, manager);
        }
    }

    public ContentValues getDetails(String id) {
        return null;
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {
        iniciarComandos();
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

    public void iniciarComandos() {
        showData();
    }
}
