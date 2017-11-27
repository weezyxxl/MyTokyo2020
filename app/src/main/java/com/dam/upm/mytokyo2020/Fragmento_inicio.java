package com.dam.upm.mytokyo2020;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Date;

public class Fragmento_inicio extends Fragment {
    ListView listView;
    //String[] elementos = {"jose", "pedro", "maria", "miguel", "luis", "daniel", "elena", "Laura", "Sofia"};
    String[] noticias = {new Noticia(0,"26 NOV 2017","Noticia 1",R.mipmap.foto1).toString(),
                        new Noticia(1,"26 NOV 2017","Noticia 2",R.mipmap.foto1).toString(),
                        new Noticia(2,"26 NOV 2017","Noticia 3",R.mipmap.foto1).toString()};



    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragmento_inicio, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        listView = (ListView)getView().findViewById(R.id.listview);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_expandable_list_item_1,noticias);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity().getApplicationContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();

                if(position==0) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.olympic.org/news/make-2020-tokyo-model-for-sustainable-games"));
                    startActivity(intent);
                }
                if(position==1){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.olympic.org/news/australia-s-mr-baseball-has-set-his-sights-on-more-olympic-glory"));
                    startActivity(intent);
                }
            }
        });
    }
}