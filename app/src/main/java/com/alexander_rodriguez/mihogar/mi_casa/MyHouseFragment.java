package com.alexander_rodriguez.mihogar.mi_casa;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.alexander_rodriguez.mihogar.adapters.RvAdapterUser;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemTenant;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.historial_casa.FragmentParent;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyHouseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyHouseFragment extends Fragment implements FragmentInterface.view {
    public static final String ARG_ROOM_NUMBER = "argRoomNumber";
    public static final String ARG_RENTAL_ID = "argRentalId";
    public static final String ARG_DNI = "argDni";

    public static final String TAG_MOSTRAR_PAGOS = "tag_table_pagos";

    private FragmentInterface.presenter presenter;
    private final FragmentParent.view parent;

    private RecyclerView rv;
    private View nothingToShow;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    private View view;

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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    public static MyHouseFragment newInstance(@NonNull FragmentParent.view parent, @Nullable String roomNumber, @Nullable String dni, @Nullable String rentalID) {
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
        presenter.onContextItemSelected(item);
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
        swipeRefreshLayout = view.findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setOnRefreshListener(() ->{
            presenter.refresh();
            swipeRefreshLayout.setRefreshing(false);
        });
        registerForContextMenu(rv);

        setProgressBarVisibility(View.GONE);
    }


    public void setProgressBarVisibility(int visibility) {
        if(progressBar == null) return;
        progressBar.setIndeterminate(visibility == View.VISIBLE);
        progressBar.setVisibility(visibility);
    }


    @Override
    public void showUsersList(ArrayList<ItemTenant> list, RecyclerView.LayoutManager manager, boolean showMain) {
        RvAdapterUser adapterUser = new RvAdapterUser(this, list, showMain);

        setProgressBarVisibility(View.GONE);
        nothingToShow.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapterUser);
    }

    @Override
    public void showList(RecyclerView.Adapter adapter, RecyclerView.LayoutManager manager){
        swipeRefreshLayout.setRefreshing(false);
        setProgressBarVisibility(View.GONE);
         nothingToShow.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);
    }


    @Override
    public void nothingHere() {
        swipeRefreshLayout.setRefreshing(false);
        setProgressBarVisibility(View.GONE);
        nothingToShow.setVisibility(View.VISIBLE);
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
    @Nullable
    public MenuInflater getMenuInflater() {
        return getActivity() == null ? null : getActivity().getMenuInflater();
    }

    @Override
    public void onClickHolder(RecyclerView.ViewHolder holder) {
        presenter.onClickHolder(holder);
    }

    @Override
    public void onLongClick(RecyclerView.ViewHolder holder) {

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo, RecyclerView.ViewHolder holder) {
        if(getMenuInflater() == null) return;
        getMenuInflater().inflate(R.menu.menu_room_options,menu);
    }

    @Override
    public void showDialog(AppCompatDialogFragment dialog) {
        if(getActivity() != null)
            dialog.show(getActivity().getSupportFragmentManager(), TAG_MOSTRAR_PAGOS);

    }
}