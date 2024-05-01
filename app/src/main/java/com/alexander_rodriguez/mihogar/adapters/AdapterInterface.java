package com.alexander_rodriguez.mihogar.adapters;

import android.content.Context;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public interface AdapterInterface {
    @Nullable
    MenuInflater getMenuInflater();
    Context getContext();
    void onClickHolder(RecyclerView.ViewHolder holder);
    void onLongClick(RecyclerView.ViewHolder holder);

    void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo, RecyclerView.ViewHolder holder);
}
