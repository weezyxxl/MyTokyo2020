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
        data.add(new Noticia(2,"24 NOV 2017", "AFRICA’S LONE MLB STAR SWINGING TO INSPIRE WHOLE CONTINENT IN TOKYO",
                R.drawable.foto_noticia3));
        data.add(new Noticia(3,"24 NOV 2017", "SOFTBALL’S GREATEST OLYMPIAN BERG TARGETS GOLD FOR THE NEXT GENERATION",
                R.drawable.foto_noticia4));
       data.add(new Noticia(4,"23 NOV 2017", "YOG STAR JADE JONES AIMING TO BECOME TAEKWONDO’S FIRST TRIPLE OLYMPIC CHAMPION",
                R.drawable.foto_noticia5));
        data.add(new Noticia(5,"22 NOV 2017", "TEENAGE BMX FREESTYLER ROBERTS READY TO TAKE OLYMPIC STAGE BY STORM",
                R.drawable.foto_noticia6));

        reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        layoutManager = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(layoutManager);

        adaptador = new AdaptadorInicio(getActivity(), data, new CustomItemClickListener(){
        public void onItemClick(View v, int position) {
            Log.d("", "clicked position:" + position);

            //long postId = data.get(position).getIdDrawable();
            int postId=data.get(position).getId();
            if(postId==0) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.olympic.org/news/softball-is-back-where-it-belongs-according-to-canada-coach-mark-smith"));
                startActivity(intent);
            }
            if(postId==1) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.olympic.org/news/fanning-targets-olympic-games-with-surfing-ready-to-tear-up-tokyo"));
                startActivity(intent);
            }
            if(postId==2) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.olympic.org/news/africa-s-lone-mlb-star-swinging-to-inspire-whole-continent-in-tokyo"));
                startActivity(intent);
            }
            if(postId==3) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.olympic.org/news/softball-s-greatest-olympian-berg-targets-gold-for-the-next-generation"));
                startActivity(intent);
            }
            if(postId==4) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.olympic.org/news/yog-star-jade-jones-aiming-to-become-taekwondo-s-first-triple-olympic-champion"));
                startActivity(intent);
            }
            if(postId==5) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.olympic.org/news/teenage-bmx-freestyler-roberts-ready-to-take-olympic-stage-by-storm"));
                startActivity(intent);
            }
            if(postId==6) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.es/"));
                startActivity(intent);
            }
    }
    });
        reciclador.setAdapter(adaptador);

        return view;
    }


}
