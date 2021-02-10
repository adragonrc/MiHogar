package com.alexander_rodriguez.mihogar.mi_casa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.adapters.Models.ModelRoomView;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemRental;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemTenant;
import com.alexander_rodriguez.mihogar.viewUser.DialogDetallesAlquiler;

import java.util.ArrayList;

public interface FragmentInterface {
    interface presenter{

        void onCreate();

        void onResume();

        void onClickHolder(RecyclerView.ViewHolder holder);
    }
    interface view {
        Context getContext();

        Bundle getArguments();

        void showList(RecyclerView.Adapter adapter);

        void showRoomsList(ArrayList<ModelRoomView> list);

        void showUsersList(ArrayList<ItemTenant> list, RecyclerView.LayoutManager layout, boolean showMain);

        void showRentalsList(ArrayList<ItemRental> list, RecyclerView.LayoutManager layout);

        void nothingHere();

        void notifyChangedOn(int posList);

        void goTo(Intent intent);

        void showMessage(String get_tenants_failure);

        void showDialog(DialogDetallesAlquiler dialogDetallesAlquiler);

        void setProgressBarVisibility(int gone);
    }
}
