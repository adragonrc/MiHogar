package com.alexander_rodriguez.mihogar.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.Adapters.Models.ModelCuartoView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class RvAdapterCuartos extends RecyclerView.Adapter<RvAdapterCuartos.Holder> {
    private ArrayList<ModelCuartoView> list;
    private Interface mInterface;
    private String valueSelect;
    public RvAdapterCuartos(Interface mInterface, ArrayList<ModelCuartoView> list){
        this.mInterface = mInterface;
        this.list = list;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater  = LayoutInflater.from(mInterface.getContext());
        View v = layoutInflater.inflate(R.layout.view_cuarto_descripcion, viewGroup,false);
        return new RvAdapterCuartos.Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        ModelCuartoView item = list.get(i);
        holder.tvTitle.setText(item.getRoomNumber());
        holder.tvDetalles.setText(item.getDetails());
        holder.id = item.getRoomNumber();
        String path = item.getPathImage();

        File f = new File(path);
        if (f.exists())       Picasso.get().load(f).into(holder.ivPhoto);

        if (item.isAlert()){
            holder.ivAlert.setImageDrawable(mInterface.getContext().getResources().getDrawable(R.drawable.ic_add_alert_black_24dp));
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
        private TextView tvTitle;
        private TextView tvDetalles;
        private ImageView ivPhoto;
        private ImageView ivAlert;
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
                    mInterface.onClickCuarto(tvTitle);
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            mInterface.getMenuInflater().inflate(R.menu.menu_opciones,menu);
            valueSelect = id;
        }
    }
    public interface Interface extends AdapterInterface{
        void onClickCuarto(View view);
    }
}
