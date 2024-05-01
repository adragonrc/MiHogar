package com.alexander_rodriguez.mihogar.adapters;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alexander_rodriguez.mihogar.DataBase.items.ItemTenant;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.Save;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class RvAdapterUser extends RecyclerView.Adapter<RvAdapterUser.Holder> {
    private final ArrayList<ItemTenant> list;
    private final AdapterInterface mInterface;
    private Holder contextHolderSelect;
    private Holder longHolderSelect;
    private Holder hMain;
    private final boolean showMain;

    public RvAdapterUser(AdapterInterface mInterface, ArrayList<ItemTenant> list, boolean showMain){
        this.mInterface = mInterface;
        this.list = list;
        this.showMain = showMain;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mInterface.getContext()).inflate(R.layout.view_user_detalles, viewGroup,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        final ItemTenant item = list.get(i);
        holder.onBind(item);
    }

    public void isMain(Holder h){
        makeNormal(hMain);
        makeMain(h);
    }

    private void makeNormal(Holder h){
        if(h != null) {
            Context c = mInterface.getContext();
            h.DNI.setTextColor(c.getResources().getColor(R.color.primaryTextColor));
            h.nombres.setTextColor(c.getResources().getColor(android.R.color.secondary_text_light));
        }
    }

    private void makeMain(Holder h){
        if (h != null) {
            hMain = h;
            Context c = mInterface.getContext();
            h.nombres.setTextColor(c.getResources().getColor(R.color.primaryColor));
            h.DNI.setTextColor(c.getResources().getColor(R.color.colorAccent));
        }
    }

    public void addItem(ItemTenant mu){
        list.add(mu);
        if(list.size() == 1){
            notifyDataSetChanged();
        }else{
            notifyItemInserted(list.size() -1);
        }
    }

    public Holder getLongHolderSelect() {
        return longHolderSelect;
    }

    public Holder getContextHolderSelect() {
        return contextHolderSelect;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setSelection(boolean f) {
        if (longHolderSelect != null) {
            longHolderSelect.setSelect(f);
            if (!f) longHolderSelect = null;
        }
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        private final TextView DNI;
        private final TextView nombres;
        private final ImageView ivPhoto;
        private final ImageView ivAlert;
        private final ImageView ivCheck;
        private ItemTenant model;

        public Holder(@NonNull View itemView) {
            super(itemView);
            DNI = itemView.findViewById(R.id.vuTvDNI);
            nombres = itemView.findViewById(R.id.vuTvNombres);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            ivAlert = itemView.findViewById(R.id.ivAlert);
            ivCheck = itemView.findViewById(R.id.ivCheck);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(v -> mInterface.onClickHolder(Holder.this));
            itemView.setOnLongClickListener(v->{
                if (longHolderSelect != null){
                    RvAdapterUser.this.setSelection(false);
                }else{

                }
                longHolderSelect = this;
                mInterface.onLongClick(this);
                return true;
            });
        }

        public void onBind(ItemTenant item){
            model = item;
            DNI.setText(item.getDni());
            nombres.setText(item.getFullName());
            File file;
            if (item.getPath() == null || item.getPath().isEmpty()) {
                 file = Save.createFile(mInterface.getContext(), mInterface.getContext().getString(R.string.cTenant), item.getDni());
            }else{
                file = new File(item.getPath());
            }
            if (file.exists()) Picasso.get().load(file).into(ivPhoto);
            if(item.isAlerted())
                ivAlert.setImageDrawable(ContextCompat.getDrawable(mInterface.getContext(), R.drawable.ic_add_alert_black_24dp));
            else ivAlert.setVisibility(View.GONE);

            if (showMain && item.isMain()){
                isMain(this);
            }
        }
        public void setSelect(boolean f){
            if (f){
                itemView.setBackgroundColor(mInterface.getContext().getResources().getColor(R.color.whiteOpacity));
                ivCheck.setVisibility(View.VISIBLE);
            }else{
                itemView.setBackgroundColor(mInterface.getContext().getResources().getColor(android.R.color.transparent));
                ivCheck.setVisibility(View.GONE);
            }
        }

        public ItemTenant getModel() {
            return model;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            contextHolderSelect = this;
          mInterface.onCreateContextMenu(menu, v, menuInfo, this);
        }
    }
}
