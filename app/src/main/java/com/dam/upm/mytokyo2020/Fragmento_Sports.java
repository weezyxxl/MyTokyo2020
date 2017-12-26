package com.dam.upm.mytokyo2020;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class Fragmento_Sports extends Fragment {
    private RecyclerView reciclador2;
    private LinearLayoutManager layoutManager2;
    private AdaptadorSport adaptador;
    ArrayList<Sport> data = new ArrayList<>();

    public Fragmento_Sports() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragmento__sports, container, false);

        data.add(new Sport(0,"Archery",
                R.drawable.arqueria));
        data.add(new Sport(1,"Atletismo",
                R.drawable.atletismo));


        reciclador2 = (RecyclerView) view.findViewById(R.id.reciclador2);
        layoutManager2 = new LinearLayoutManager(getActivity());
        reciclador2.setLayoutManager(layoutManager2);

        adaptador = new AdaptadorSport(getActivity(), data, new CustomItemClickListener(){
            public void onItemClick(View v, int position) {
                Log.d("", "clicked position:" + position);

                //long postId = data.get(position).getIdDrawable();
                //int postId=data.get(position+1).getId();
                Fragment nuevoFragmento = new ArcheryDiscipline();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.app_bar_main, nuevoFragmento);
                transaction.addToBackStack(null);

                // Commit a la transacción
                transaction.commit();
                //Intent i = new Intent(getActivity(), ArcheryDiscipline.class);
                //startActivity(i);


            }
        });
        reciclador2.setAdapter(adaptador);

        return view;
    }

    public boolean onBackPressed(){
        return true;
    }


}
