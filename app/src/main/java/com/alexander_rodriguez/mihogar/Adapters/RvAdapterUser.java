package com.alexander_rodriguez.mihogar.Adapters;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.mi_casa.Models.ModelUserView;

import java.util.ArrayList;

public class RvAdapterUser extends RecyclerView.Adapter<RvAdapterUser.Holder> {
    private ArrayList<ModelUserView> list;
    private Interface mInterface;
    private View viewSelect;
    private String dniSelect;
    public RvAdapterUser(Interface mInterface, ArrayList<ModelUserView> list){
        this.mInterface = mInterface;
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mInterface.getContext()).inflate(R.layout.view_user_detalles,viewGroup,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        final ModelUserView item = list.get(i);
        holder.DNI.setText(item.getDni());
        holder.nombres.setText(item.getNombres());
        holder.letra.setText(item.getLetra());
        holder.letra.setBackgroundDrawable(item.getDrawable());
    }

    public View getViewSelect() {
        return viewSelect;
    }

    public String getDniSelect() {
        return dniSelect;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        private TextView letra;
        private TextView DNI;
        private TextView nombres;
        public Holder(@NonNull View itemView) {
            super(itemView);
            letra = itemView.findViewById(R.id.vutvLetra);
            DNI = itemView.findViewById(R.id.vuTvDNI);
            nombres = itemView.findViewById(R.id.vuTvNombres);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mInterface.onClickUsuario(DNI);
                }
            });
        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            mInterface.getMenuInflater().inflate(R.menu.menu_opciones,menu);
            viewSelect = v;
            dniSelect = DNI.getText().toString();
        }
    }
    public interface Interface extends AdapterInterface{
        void onClickUsuario(View view);
    }

}
