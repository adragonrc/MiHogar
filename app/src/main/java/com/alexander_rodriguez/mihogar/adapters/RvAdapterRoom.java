package com.alexander_rodriguez.mihogar.adapters;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.adapters.Models.ModelRoomView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class RvAdapterRoom extends RecyclerView.Adapter<RvAdapterRoom.Holder> {
    private ArrayList<ModelRoomView> list;
    private AdapterInterface mInterface;
    private String valueSelect;
    public RvAdapterRoom(AdapterInterface mInterface, ArrayList<ModelRoomView> list){
        this.mInterface = mInterface;
        this.list = list;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater  = LayoutInflater.from(mInterface.getContext());
        View v = layoutInflater.inflate(R.layout.view_cuarto_descripcion, viewGroup,false);
        return new RvAdapterRoom.Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        ModelRoomView item = list.get(i);
        holder.tvTitle.setText(item.getRoomNumber());
        holder.tvDetalles.setText(item.getDetails());
        holder.id = item.getRoomNumber();
        String path = item.getPathImage();

        File f = new File(path);
        if (f.exists())       Picasso.get().load(f).into(holder.ivPhoto);

        if (item.isAlert()){
                holder.ivAlert.setImageDrawable(ContextCompat.getDrawable(mInterface.getContext(), R.drawable.ic_add_alert_black_24dp));
        }else{
            holder.ivAlert.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public String getValueSelect() {
        return valueSelect;
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        private final TextView tvTitle;
        private final TextView tvDetalles;
        private final ImageView ivPhoto;
        private final ImageView ivAlert;
        private String id;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDetalles = itemView.findViewById(R.id.tvDetalles);
            ivAlert = itemView.findViewById(R.id.ivAlert);

            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mInterface.onClickHolder(Holder.this);
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            mInterface.getMenuInflater().inflate(R.menu.menu_opciones,menu);
            valueSelect = id;
        }

        public TextView getTvTitle() {
            return tvTitle;
        }
    }
}
