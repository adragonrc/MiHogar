package com.alexander_rodriguez.mihogar.mi_casa;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.alexander_rodriguez.mihogar.adapters.AdapterInterface;
import com.alexander_rodriguez.mihogar.adapters.Models.ModelRoomView;
import com.alexander_rodriguez.mihogar.adapters.RVARentals;
import com.alexander_rodriguez.mihogar.adapters.RvAdapterRoom;
import com.alexander_rodriguez.mihogar.adapters.RvAdapterUser;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemRental;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemTenant;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.historialcasa.FragmentParent;
import com.alexander_rodriguez.mihogar.viewUser.DialogDetallesAlquiler;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyHouseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyHouseFragment extends Fragment implements FragmentInterface.view, AdapterInterface {
    public static final String ARG_ROOM_NUMBER = "argRoomNumber";
    public static final String ARG_RENTAL_ID = "argRentalId";
    public static final String ARG_DNI = "argDni";

    public static final String TAG_MOSTRAR_PAGOS = "tag_table_pagos";

    private FragmentInterface.presenter presenter;
    private final FragmentParent.view parent;

    private RvAdapterRoom adapterCuartos;
    private RecyclerView.LayoutManager manager;
    private RecyclerView rv;
    private View nothingToShow;
    private ProgressBar progressBar;

    private View view;
    private RvAdapterUser adapterUser;

    private boolean isShow;

    public MyHouseFragment(FragmentParent.view parent) {
        // Required empty public constructor
        this.parent = parent;
    }

    public void setPresenter(FragmentInterface.presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onResume()
    {
        super.onResume();
         presenter.onResume();
    }

    public static MyHouseFragment newInstance(@NonNull FragmentParent.view parent, @Nullable String roomNumber, @Nullable String rentalID) {
        Bundle args = new Bundle();
        args.putString(ARG_ROOM_NUMBER, roomNumber);
        args.putString(ARG_RENTAL_ID, rentalID);
        MyHouseFragment fragment = new MyHouseFragment(parent);
        fragment.setArguments(args);
        return  fragment;
    }

    public static MyHouseFragment newInstance(@NonNull FragmentParent.view parent, @Nullable String dni) {
        Bundle args = new Bundle();
        args.putString(ARG_DNI, dni);
        MyHouseFragment fragment = new MyHouseFragment(parent);
        fragment.setArguments(args);
        return  fragment;
    }

    public static MyHouseFragment newInstance(@NonNull FragmentParent.view parent) {
        return new MyHouseFragment(parent);
    }

    public static MyHouseFragment newInstance(@NonNull FragmentParent.view parent, @Nullable String roomNumber,@Nullable String dni,@Nullable String rentalID) {
        Bundle args = new Bundle();
        args.putString(ARG_ROOM_NUMBER, roomNumber);
        args.putString(ARG_DNI, dni);
        args.putString(ARG_RENTAL_ID, rentalID);
        MyHouseFragment fragment = new MyHouseFragment(parent);
        fragment.setArguments(args);
        return  fragment;
    }


    @Override
    public boolean onContextItemSelected(@NotNull MenuItem item) {
        /*if (adapterUsuarios.getViewSelect().equals(item.getActionView())){
            Toast.makeText(this, "Usar getAction", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "No Usar getAction", Toast.LENGTH_SHORT).show();
        }
        int id = item.getItemId();
        switch (id){
            case R.id.opVerUsuario: {
                break;
            }
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
                Toast.makeText(this, "Caso no encontrado", Toast.LENGTH_SHORT).show();
        }
        */
        return super.onContextItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_house, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rv = view.findViewById(R.id.recyclerView);
        nothingToShow = view.findViewById(R.id.nothingToShow);
        progressBar = view.findViewById(R.id.progressBar);

        manager = new LinearLayoutManager(getContext());
        setProgressBarVisibility(View.GONE);
        rv.setLayoutManager(manager);
    }

    public void setProgressBarVisibility(int visibility) {
        if(progressBar == null) return;
        progressBar.setIndeterminate(visibility == View.VISIBLE);
        progressBar.setVisibility(visibility);
    }

    @Override
    public void showRoomsList(ArrayList<ModelRoomView> list) {
        adapterCuartos = new RvAdapterRoom(this, list);
        showList(adapterCuartos);
/*
        setProgressBarVisibility(View.GONE);
        nothingToShow.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);
        rv.setAdapter(adapterCuartos);*/
    }

    @Override
    public void showUsersList(ArrayList<ItemTenant> list, RecyclerView.LayoutManager manager, boolean showMain) {
        adapterUser = new RvAdapterUser(this, list, showMain);

        setProgressBarVisibility(View.GONE);
        nothingToShow.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapterUser);
    }

    @Override
    public void showList(RecyclerView.Adapter adapter){
        setProgressBarVisibility(View.GONE);
        nothingToShow.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);
    }

    @Override
    public void showRentalsList(ArrayList<ItemRental> list, RecyclerView.LayoutManager manager) {
        rv.setVisibility(View.VISIBLE);
        nothingToShow.setVisibility(View.GONE);
        RVARentals adapter = new RVARentals(this, list);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);
    }

    @Override
    public void nothingHere() {
        setProgressBarVisibility(View.GONE);
        nothingToShow.setVisibility(View.VISIBLE);
    }

    @Override
    public void notifyChangedOn(int posList) {
        adapterCuartos.notifyItemChanged(posList);
    }

    @Override
    public void goTo(Intent intent) {
        parent.goTo(intent);
    }

    @Override
    public void showMessage(String message) {
        parent.showMessage(message);
    }


    @Override
    public MenuInflater getMenuInflater() {
        return getActivity() == null ? null : getActivity().getMenuInflater();
    }

    @Override
    public void onClickHolder(RecyclerView.ViewHolder holder) {
        presenter.onClickHolder(holder);
    }

    @Override
    public void showDialog(DialogDetallesAlquiler dialogDetallesAlquiler) {
        if(getActivity() != null)
            dialogDetallesAlquiler.show(getActivity().getSupportFragmentManager(), TAG_MOSTRAR_PAGOS);

    }
}