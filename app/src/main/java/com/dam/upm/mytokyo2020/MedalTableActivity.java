package com.dam.upm.mytokyo2020;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.util.ArrayList;

public  class MedalTableActivity extends Fragment {
    TabHost th;
    TextView disciplinaName;
    Bundle extras;
    TableLayout table;
    ImageView bell;
    FragmentManager fm;
    ArcheryDialogFragment adf;
    TextView arcInfo;
    private MapView mapView;

    private RecyclerView reciclador;
    private LinearLayoutManager layoutManager;
    private AdaptadorVisit adaptador;
    ArrayList<Experiencia> data = new ArrayList<>();



    public MedalTableActivity() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_medal_table ,container, false);


        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        th = (TabHost) getView().findViewById(R.id.tabHostTokyo);

        th.setup();

        TabHost.TabSpec tab1 = th.newTabSpec("tab1");
        TabHost.TabSpec tab2 = th.newTabSpec("tab2");

        tab1.setIndicator("Country");
        tab1.setContent(R.id.Country);

        tab2.setIndicator("Athlete");
        tab2.setContent(R.id.Athletes);



        th.addTab(tab1);
        th.addTab(tab2);




    }




}
