package com.alexander_rodriguez.mihogar.adapters;

import android.content.Context;
import android.view.MenuInflater;

import androidx.recyclerview.widget.RecyclerView;

public interface AdapterInterface {
    MenuInflater getMenuInflater();
    Context getContext();
    void onClickHolder(RecyclerView.ViewHolder holder);
}