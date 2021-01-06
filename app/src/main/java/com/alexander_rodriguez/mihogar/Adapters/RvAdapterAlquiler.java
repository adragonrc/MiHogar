package com.alexander_rodriguez.mihogar.Adapters;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.Adapters.Models.ModelAlquilerView;

import java.util.ArrayList;

public class RvAdapterAlquiler extends RecyclerView.Adapter<RvAdapterAlquiler.Holder>{

    private ArrayList<ModelAlquilerView> list;
    private AdapterInterface view;
    private View viewSelect;
    private String idAlquilerSelect;

    public RvAdapterAlquiler(AdapterInterface view, ArrayList<ModelAlquilerView> list){
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

       // holder.dni.setText(item.getDni());
        holder.fecha.setText(item.getFecha());
        holder.numCuarto.setText(item.getNumCuarto());
        holder.mId  = item.getId();

        if (item.isAlert()){
            holder.cardView.setBackgroundColor(view.getContext().getResources().getColor(R.color.colorPrimaryDark));
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
        private TextView fecha;
        private TextView numCuarto;
        private String mId;
        private CardView cardView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            fecha = itemView.findViewById(R.id.tvFecha);
            numCuarto = itemView.findViewById(R.id.tvNumCuarto);

            cardView = itemView.findViewById(R.id.idCardView);
            cardView.setOnCreateContextMenuListener(this);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.onClickHolder(Holder.this);
                }
            });
        }

        public String getmId() {
            return mId;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            view.getMenuInflater().inflate(R.menu.menu_opciones,menu);
            viewSelect = v;
            idAlquilerSelect = mId;
        }
    }
}
