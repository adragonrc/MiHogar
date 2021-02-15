package com.alexander_rodriguez.mihogar.mi_casa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.adapters.AdapterInterface;
import com.alexander_rodriguez.mihogar.adapters.Models.ModelRoomView;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemRental;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemTenant;
import com.alexander_rodriguez.mihogar.viewUser.DialogDetallesAlquiler;

import java.util.ArrayList;

public interface FragmentInterface {
    interface presenter{

        void onCreate();

        void onResume();

        void refresh();

        void onClickHolder(RecyclerView.ViewHolder holder);

        void onContextItemSelected(MenuItem item);
    }
    interface view extends AdapterInterface {
        Context getContext();

        Bundle getArguments();

        void showList(RecyclerView.Adapter adapter, RecyclerView.LayoutManager manager);

        void showUsersList(ArrayList<ItemTenant> list, RecyclerView.LayoutManager layout, boolean showMain);

        void nothingHere();

        void goTo(Intent intent);

        void showMessage(String get_tenants_failure);

        void showDialog(AppCompatDialogFragment dialogDetallesAlquiler);

        void setProgressBarVisibility(int gone);
    }
}
