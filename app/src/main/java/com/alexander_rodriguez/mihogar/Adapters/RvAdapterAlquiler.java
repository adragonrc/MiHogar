package com.alexander_rodriguez.mihogar.Adapters;

import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.Adapters.Models.ModelAlquilerView;

import java.util.ArrayList;

public class RvAdapterAlquiler extends RecyclerView.Adapter<RvAdapterAlquiler.Holder>{
    private ArrayList<ModelAlquilerView> list;
    private Interface view;
    private View viewSelect;
    private String idAlquilerSelect;
    public RvAdapterAlquiler(Interface view, ArrayList<ModelAlquilerView> list){
        this.view = view;
        this.list = list;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(view.getContext()).inflate(R.layout.view_alquiler_detalles,viewGroup,false);
        return new RvAdapterAlquiler.Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        ModelAlquilerView item = list.get(i);

        holder.dni.setText(item.getDni());
        holder.fecha.setText(item.getFecha());
        if(!item.getPath().equals(""))holder.ivPhoto.setImageBitmap(BitmapFactory.decodeFile(item.getPath()));
        holder.numCuarto.setText(item.getNumCuarto());
        holder.nombres.setText(item.getNombres());
        holder.mId  = item.getId();

        if (item.isAlert()){
            holder.nombres.setTextColor(view.getContext().getResources().getColor(R.color.colorPrimary));
            holder.dni.setTextColor(view.getContext().getResources().getColor(R.color.colorAccent));
            holder.fecha.setTextColor(view.getContext().getResources().getColor(R.color.colorAccent));
            holder.numCuarto.setTextColor(view.getContext().getResources().getColor(R.color.colorAccent));
        }
    }
    public View getViewSelect() {
        return viewSelect;
    }

    public String getIdAlquilerSelect() {
        return idAlquilerSelect;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        private ImageView ivPhoto;
        private TextView dni;
        private TextView nombres;
        private TextView fecha;
        private TextView numCuarto;
        private String mId;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            dni = itemView.findViewById(R.id.vatvDni);
            fecha = itemView.findViewById(R.id.vatvFecha);
            nombres = itemView.findViewById(R.id.vatvNombre);
            numCuarto = itemView.findViewById(R.id.vatvNumeroCuarto);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.onClickAlquiler(mId);
                }
            });
        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            view.getMenuInflater().inflate(R.menu.menu_opciones,menu);
            viewSelect = v;
            idAlquilerSelect = mId;
        }
    }

    public interface Interface extends  AdapterInterface{
        void onClickAlquiler(String idAlquiler);
    }
}
