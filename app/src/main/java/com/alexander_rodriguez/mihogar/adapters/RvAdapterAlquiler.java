package com.alexander_rodriguez.mihogar.adapters;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.adapters.Models.ModelAlquilerView;

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
        holder.onBind(list.get(i));

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
        private final TextView fecha;
        private final TextView numCuarto;
        private String mId;
        private final CardView cardView;
        private ModelAlquilerView model;

        public Holder(@NonNull View itemView) {
            super(itemView);
            fecha = itemView.findViewById(R.id.tvFecha);
            numCuarto = itemView.findViewById(R.id.tvNumCuarto);

            cardView = itemView.findViewById(R.id.idCardView);
            cardView.setOnCreateContextMenuListener(this);
            cardView.setOnClickListener(v -> view.onClickHolder(Holder.this));
        }

        public String getmId() {
            return mId;
        }

        public void onBind(ModelAlquilerView item){
            model = item;
            fecha.setText(item.getFecha());
            numCuarto.setText(item.getNumCuarto());
            mId  = item.getId();

            if (item.isAlert()){
                cardView.setBackgroundColor(view.getContext().getResources().getColor(R.color.primaryColorDark));
            }
        }

        public ModelAlquilerView getModel() {
            return model;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            if(view.getMenuInflater() != null) {
                view.getMenuInflater().inflate(R.menu.menu_room_options, menu);
                viewSelect = v;
                idAlquilerSelect = mId;
            }
        }
    }
}
