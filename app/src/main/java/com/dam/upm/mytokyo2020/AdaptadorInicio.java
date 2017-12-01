package com.dam.upm.mytokyo2020;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnItemClick;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by Alfonso on 24/11/2017.
 */
public class AdaptadorInicio
        extends RecyclerView.Adapter<AdaptadorInicio.ViewHolder> {

    private static Context mContext;
    private static CustomItemClickListener listener;
    ArrayList<Noticia> data;


    public AdaptadorInicio(Context mContext, ArrayList<Noticia> data, CustomItemClickListener listener) {
        this.mContext = mContext;
        this.data = data;
        this.listener = listener;

    }



    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        // Campos respectivos de un item
        public String mItem;
        public TextView nombre;
        public TextView fecha;
        public ImageView imagen;

        ViewHolder(View v) {
            super(v);
            nombre = (TextView) v.findViewById(R.id.nombre_noticia);
            fecha = (TextView) v.findViewById(R.id.fecha_noticia);
            imagen = (ImageView) v.findViewById(R.id.miniatura_noticia);
            itemView.setOnClickListener(this);

        }

        public void onClick(View v)
        {
            listener.onItemClick(v, this.getLayoutPosition());

        }

    }





    @Override
    public int getItemCount() {
        return Noticia.Noticias.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_inicio, viewGroup, false);

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
        Noticia item = Noticia.Noticias.get(i);
        Glide.with(viewHolder.itemView.getContext())
                .load(item.getIdDrawable())
                .centerCrop()
                .into(viewHolder.imagen);
       viewHolder.nombre.setText(item.getNombre());
        viewHolder.fecha.setText(item.getFecha().toString());

    }


}