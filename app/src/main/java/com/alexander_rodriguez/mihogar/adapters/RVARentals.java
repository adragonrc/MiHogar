package com.alexander_rodriguez.mihogar.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.DataBase.items.ItemRental;
import com.alexander_rodriguez.mihogar.R;

import java.util.ArrayList;

public class RVARentals extends RecyclerView.Adapter<RVARentals.Holder> {

    private final AdapterInterface view;
    private final ArrayList<ItemRental> list;

    public RVARentals(AdapterInterface view, ArrayList<ItemRental> list){
        this.view = view;
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(view.getContext()).inflate(R.layout.view_rental_room, parent,false);
        return new RVARentals.Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.onBind(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        private final TextView tvTenantsNumber;
        private final TextView tvEntryDate;
        private final TextView tvDepartureDate;
        private final TextView tvReason;
        private ItemRental model;
        public Holder(@NonNull View itemView) {
            super(itemView);
            tvTenantsNumber = itemView.findViewById(R.id.tvTenantsNumber);
            tvEntryDate = itemView.findViewById(R.id.tvEntryDate);
            tvDepartureDate = itemView.findViewById(R.id.tvDepartureDate);
            tvReason = itemView.findViewById(R.id.tvReason);
            itemView.setOnClickListener(view1 -> {view.onClickHolder(Holder.this);});
        }

        public void onBind(ItemRental rental){
            this.model = rental;
            tvDepartureDate.setText(rental.getDepartureDateAsString());
            tvEntryDate.setText(rental.getEntryDateAsString());
            tvTenantsNumber.setText(String.valueOf(rental.getTenantsNumber()));
            tvReason.setText(rental.getReasonExit());
        }

        public ItemRental getModel() {
            return model;
        }
    }
}
