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
import com.alexander_rodriguez.mihogar.DataBase.parse.ParceTenant;
import com.alexander_rodriguez.mihogar.historialUserPakage.HistorialUsuarioActivity;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AllUsersPresenter extends BasePresenter<Interface.View> implements Interface.Presenter {
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
    public AllUsersPresenter(Interface.View view, Intent intent) {
        super(view);
        list = new ArrayList<>();
        manager = new LinearLayoutManager(view.getContext());
    }

    @Override
    public void showList() {
        if(list.isEmpty())
            loadUsersAndShow();
        else
            view.showUsersList(list, manager, false);
    }

    private void loadUsersAndShow(){
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
            view.showUsersList(list, manager, false);
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
