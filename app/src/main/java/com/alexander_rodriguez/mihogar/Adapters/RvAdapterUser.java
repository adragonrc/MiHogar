package com.alexander_rodriguez.mihogar.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.Adapters.Models.ModelUserView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import java.io.File;
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
        String path = item.getPath();

        File f = new File(path);
        if (f.exists())       Picasso.get().load(f).into(holder.ivPhoto);

        if(item.getAlert())
            holder.ivAlert.setImageDrawable(mInterface.getContext().getResources().getDrawable(R.drawable.ic_add_alert_black_24dp));
        else holder.ivAlert.setVisibility(View.GONE);

        if (item.isMain()){
            isMain(holder);
        }
    }

    public void isMain(Holder h){
        if (h != null) {
            Context c = mInterface.getContext();
            h.nombres.setTextColor(c.getResources().getColor(R.color.colorPrimary));
            h.DNI.setTextColor(c.getResources().getColor(R.color.colorAccent));
        }
    }

    public void removeMain(Holder h){
        if  (h != null ){
            Context c = mInterface.getContext();
            h.DNI.setTextColor(c.getResources().getColor(R.color.primaryTextColor));
            h.nombres.setTextColor(c.getResources().getColor(android.R.color.secondary_text_light));
        }
    }

    public void addItem(ModelUserView mu){
        list.add(mu);
        if(list.size() == 1){
            notifyDataSetChanged();
        }else{
            notifyItemInserted(list.size() -1);
        }
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
        private TextView DNI;
        private TextView nombres;
        private ImageView ivPhoto;
        private ImageView ivAlert;
        public Holder(@NonNull View itemView) {
            super(itemView);
            DNI = itemView.findViewById(R.id.vuTvDNI);
            nombres = itemView.findViewById(R.id.vuTvNombres);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            ivAlert = itemView.findViewById(R.id.ivAlert);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(v -> mInterface.onClickUsuario(Holder.this));
        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            mInterface.getMenuInflater().inflate(R.menu.menu_opciones,menu);
            viewSelect = v;
            dniSelect = DNI.getText().toString();
        }

        public TextView getDNI() {
            return DNI;
        }
    }
    public interface Interface extends AdapterInterface{
        void onClickUsuario(Holder holder);
    }

}
