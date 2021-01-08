package com.alexander_rodriguez.mihogar.Adapters;

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
        ItemRental rental = list.get(position);
        holder.tvDepartureDate.setText(rental.getDepartureDateAsString());
        holder.tvEntryDate.setText(rental.getEntryDateAsString());
        holder.tvTenantsNumber.setText(String.valueOf(rental.getTenantsNumber()));
        holder.tvReason.setText(rental.getReasonExit());
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
        public Holder(@NonNull View itemView) {
            super(itemView);
            tvTenantsNumber = itemView.findViewById(R.id.tvTenantsNumber);
            tvEntryDate = itemView.findViewById(R.id.tvEntryDate);
            tvDepartureDate = itemView.findViewById(R.id.tvDepartureDate);
            tvReason = itemView.findViewById(R.id.tvReason);
            itemView.setOnClickListener(view1 -> {view.onClickHolder(Holder.this);});
        }
    }
}
