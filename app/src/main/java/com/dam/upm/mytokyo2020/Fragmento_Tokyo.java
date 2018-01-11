package com.dam.upm.mytokyo2020;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.services.android.telemetry.location.LocationEngine;
import com.mapbox.services.android.telemetry.permissions.PermissionsManager;


import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Alfonso on 02/01/2018.
 */

public  class Fragmento_Tokyo extends Fragment {
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



    String[] itemname = {
            "Shibuya Granbell Hotel",
            "Park Hyatt Tokyo",
            "Hoshinoya Tokyo",
            "Keio Plaza Hotel",
            "Asakusa View Hotel"

    };

    Integer[] imgid = {
            R.drawable.hotel1,
            R.drawable.hotel2,
            R.drawable.hotel3,
            R.drawable.hotel4,
            R.drawable.hotel5

    };

    String[] listviewShortDescription = new String[]{
            "15-17 Sakuragaoka-cho, Shibuya-ku, Tokyo 150-0031",
            "3-7-1-2 Nishi Shinjuku, Shinjuku-Ku, Tokyo 163-1055",
            "1-9-1 Otemachi, Chiyoda-ku, Tokyo 100-0004",
            "2-2-1 Nishi-Shinjuku, Shinjuku-Ku, Tokyo 160-8330",
            "3-17-1 Nishiasakusa Taito Tokyo-to, Tokyo 111-8765"};

    public Fragmento_Tokyo() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_tokyo, container, false);

        data.add(new Experiencia(0,"SHINJUKU NIGHTLIFE", "Shinjuku & Northwest Tokyo",
                R.drawable.experiencia_1));
        data.add(new Experiencia(1,"TSUKIJI MARKET", "Ginza & Tsukiji",
                R.drawable.experiencia_2));
        data.add(new Experiencia(2,"TOKYO CITYSCAPE", "Shinjuku & Northwest Tokyo",
                R.drawable.experiencia_3));
        data.add(new Experiencia(3,"SHOPPING IN HARAJUKU", "Harajuku & Aoyama",
                R.drawable.experiencia_4));

        reciclador = (RecyclerView) view.findViewById(R.id.reciclador2);
        layoutManager = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(layoutManager);

        adaptador = new AdaptadorVisit(getActivity(), data, new CustomItemClickListener(){
            public void onItemClick(View v, int position) {
                Log.d("", "clicked position:" + position);

                //long postId = data.get(position).getIdDrawable();
                int postId=data.get(position).getId();
                if(postId==0) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.lonelyplanet.com/japan/tokyo/shinjuku-and-west-tokyo"));
                    startActivity(intent);
                }
                if(postId==1) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.lonelyplanet.com/japan/tokyo/ginza-tsukiji/attractions/tsukiji-outer-market/a/poi-sig/1315243/356817"));
                    startActivity(intent);
                }
                if(postId==2) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.lonelyplanet.com/japan/tokyo/shinjuku-and-west-tokyo/attractions/tokyo-metropolitan-government-building/a/poi-sig/396717/356817"));
                    startActivity(intent);
                }
                if(postId==3) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.lonelyplanet.com/japan/tokyo/harajuku-and-aoyama"));
                    startActivity(intent);
                }

            }
        });
        reciclador.setAdapter(adaptador);
        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        Mapbox.getInstance(getActivity(), "pk.eyJ1IjoiYWZlcm5hbmRlenMiLCJhIjoiY2pjMGUycGJiMDAzNTJ3cXl6NTY3NW5seiJ9.S5M0PIW4dMD5Lede8dgp0Q");
        mapView = (MapView) getView().findViewById(R.id.mapView);
        mapView.onCreate(state);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {


                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(35.6780929, 139.71489429999997))
                        .title("New National Stadium")
                        .setSnippet("Opening and Closing Ceremonies, Athletics, Football (Final)"));

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(35.6672534, 139.69993650000004))
                        .title("Yoyogi National Gymnasium")
                        .setSnippet("Handball"));

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(35.6672534, 139.71253130000002))
                        .title("Tokyo Metropolitan Gymnasium")
                        .setSnippet("Table tennis"));

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(35.6933175, 139.74988499999995))
                        .title("Nippon Budokan")
                        .setSnippet("Judo, Karate"));

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(35.67694669999999, 139.7635034))
                        .title("Tokyo International Forum")
                        .setSnippet("Weightlifting"));

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(35.685175, 139.75279950000004))
                        .title("Imperial Palace Garden")
                        .setSnippet("Cycling (Road)"));

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(35.6969758, 139.79328629999998))
                        .title("Kokugikan Arena")
                        .setSnippet("Boxing"));


            }
        });
        th = (TabHost) getView().findViewById(R.id.tabHostTokyo);

        th.setup();

        TabHost.TabSpec tab1 = th.newTabSpec("tab1");
        TabHost.TabSpec tab2 = th.newTabSpec("tab2");
        TabHost.TabSpec tab3 = th.newTabSpec("tab3");

        tab1.setIndicator("Hotels");
        tab1.setContent(R.id.Hotels);

        tab2.setIndicator("Visit");
        tab2.setContent(R.id.Visit);

        tab3.setIndicator("Map");
        tab3.setContent(R.id.Map);

        th.addTab(tab1);
        th.addTab(tab2);
        th.addTab(tab3);

        ListView list;
        CustomListAdapter adapter = new CustomListAdapter(getActivity(), itemname, imgid,listviewShortDescription);
        list = (ListView) getView().findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String Slecteditem = itemname[+position];

                if(Slecteditem.equals("Shibuya Granbell Hotel")){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.granbellhotel.jp/en/shibuya/"));
                    startActivity(intent);
                }

                if(Slecteditem.equals("Park Hyatt Tokyo")){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://tokyo.park.hyatt.com/en/hotel/home.html"));
                    startActivity(intent);
                }

                if(Slecteditem.equals("Hoshinoya Tokyo")){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://hoshinoya.com/tokyo/en/"));
                    startActivity(intent);
                }

                if(Slecteditem.equals("Keio Plaza Hotel")){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.keioplaza.com/"));
                    startActivity(intent);
                }

                if(Slecteditem.equals("Asakusa View Hotel")){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.viewhotels.co.jp/asakusa/english/"));
                    startActivity(intent);
                }

                Toast.makeText(getContext().getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();

            }
        });

    }


    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        //  newFragment.show(getClgetSupportFragmentManager(), "timePicker");
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

}
