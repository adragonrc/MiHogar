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
import com.alexander_rodriguez.mihogar.Save;
import com.alexander_rodriguez.mihogar.adapters.Models.ModelRoomView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class RvAdapterRoom extends RecyclerView.Adapter<RvAdapterRoom.Holder> {
    private ArrayList<ModelRoomView> list;
    private AdapterInterface mInterface;
    private Holder holderSelect;
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
        holder.bind(item);
    }

    public void removeItem(int position){
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Holder getHolderSelect() {
        return holderSelect;
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, View.OnClickListener, View.OnLongClickListener{
        private final TextView tvTitle;
        private final TextView tvDetalles;
        private final ImageView ivPhoto;
        private final ImageView ivAlert;
        private String id;
        private ModelRoomView model;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDetalles = itemView.findViewById(R.id.tvDetalles);
            ivAlert = itemView.findViewById(R.id.ivAlert);

            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(this);
            //itemView.setOnLongClickListener(this);
        }

        private void bind(ModelRoomView item){
            this.model = item;
            tvTitle.setText(item.getRoomNumber());
            tvDetalles.setText(item.getDetails());
            id = item.getRoomNumber();

            File f = Save.createFile(mInterface.getContext(), mInterface.getContext().getString(R.string.cRoom), model.getRoomNumber());
            if (f.exists()) Picasso.get().load(f).into(ivPhoto);

            if (item.isAlert()){
                ivAlert.setImageDrawable(ContextCompat.getDrawable(mInterface.getContext(), R.drawable.ic_add_alert_black_24dp));
            }else{
                ivAlert.setVisibility(View.GONE);
            }
        }

        public ModelRoomView getModel() {
            return model;
        }

        @Override
        public void onClick(View view) {
            mInterface.onClickHolder(Holder.this);
        }

        @Override
        public boolean onLongClick(View view) {
            mInterface.onLongClick(Holder.this);
            return false;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            mInterface.onCreateContextMenu(menu, v, menuInfo, Holder.this);
            holderSelect = this;
        }

        public TextView getTvTitle() {
            return tvTitle;
        }
    }
}
