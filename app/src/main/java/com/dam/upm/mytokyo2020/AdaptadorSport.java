package com.dam.upm.mytokyo2020;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Alfonso on 26/11/2017.
 */

public class AdaptadorSport extends RecyclerView.Adapter<AdaptadorSport.ViewHolder> {

    private static Context mContext;
    CustomItemClickListener listener;
    ArrayList<Sport> data;


    public AdaptadorSport(Context mContext, ArrayList<Sport> data, CustomItemClickListener listener) {
        this.mContext = mContext;
        this.data = data;
        this.listener = listener;

    }



    public static class ViewHolder extends RecyclerView.ViewHolder  {
        // Campos respectivos de un item
        public String mItem;
        public TextView nombre;
        public ImageView imagen;

        ViewHolder(View v) {
            super(v);
            nombre = (TextView) v.findViewById(R.id.nombre_sport);
            imagen = (ImageView) v.findViewById(R.id.miniatura_sport);
            //itemView.setOnClickListener(this);

        }

    }





    @Override
    public int getItemCount() {
        return Sport.SPORTS.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_sports, viewGroup, false);

        final ViewHolder mViewHolder = new ViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, mViewHolder.getAdapterPosition());
            }
        });

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Sport item = Sport.SPORTS.get(i);
        Glide.with(viewHolder.itemView.getContext())
                .load(item.getIdDrawable())
                .centerCrop()
                .into(viewHolder.imagen);
        viewHolder.nombre.setText(item.getNombre());

    }
}
