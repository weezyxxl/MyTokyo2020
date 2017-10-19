package com.dam.upm.mytokyo2020;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String[] opciones;
    private DrawerLayout menuLayout;
    private Resources res;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TEST
        final ImageView im1 = (ImageView)findViewById(R.id.imageView15);
        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(),"Has apretado " + im1.getId(),Toast.LENGTH_LONG);
                toast.show();
            }
        });
        res = getResources();
        opciones = res.getStringArray(R.array.array_names) ;
        menuLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        listView = (ListView)findViewById(R.id.left_drawer);

        listView.setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,opciones));
        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent in = new Intent(getApplicationContext(),MainActivity_2.class);
                    startActivity(in);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }
}
