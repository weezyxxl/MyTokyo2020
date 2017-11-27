package com.dam.upm.mytokyo2020;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class Fragmento_Noticias extends Fragment {
    private RecyclerView reciclador;
    private LinearLayoutManager layoutManager;
    private AdaptadorInicio adaptador;
    ArrayList<Noticia> data = new ArrayList<>();

    public Fragmento_Noticias() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragmento_prueba, container, false);

        data.add(new Noticia(0,"26 NOV 2017", "SOFTBALL IS ‘BACK WHERE IT BELONGS’ ACCORDING TO CANADA COACH MARK SMITH",
                R.drawable.foto_noticia1));
        data.add(new Noticia(1,"25 NOV 2017", "FANNING TARGETS OLYMPIC GAMES WITH SURFING READY TO TEAR UP TOKYO",
                R.drawable.foto_noticia2));

        reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        layoutManager = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(layoutManager);

        adaptador = new AdaptadorInicio(getActivity(), data, new CustomItemClickListener(){
        public void onItemClick(View v, int position) {
            Log.d("", "clicked position:" + position);

            //long postId = data.get(position).getIdDrawable();
            int postId=data.get(position+1).getId();
            if(postId==0) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.olympic.org/news/make-2020-tokyo-model-for-sustainable-games"));
                startActivity(intent);
            }
            if(postId==1) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.es/"));
                startActivity(intent);
            }
    }
    });
        reciclador.setAdapter(adaptador);

        return view;
    }


}
