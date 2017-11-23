package com.dam.upm.mytokyo2020;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{


    private ViewFlipper flipper;
    private float lastX;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView bNav = (BottomNavigationView) findViewById(R.id.navigation);
        bNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Toast toast = Toast.makeText(getApplicationContext(),"Tocado",Toast.LENGTH_LONG);
                toast.show();
                return true;
            }
        });

        flipper = (ViewFlipper) findViewById(R.id.flipper);
        flipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN: //Que hacer cuando el usuario presiona la pantalla
                        lastX = motionEvent.getX(); //Unicamente obtengo la posición de donde ha tocado
                        //Toast toast = Toast.makeText(getApplicationContext(),"ActionDown",Toast.LENGTH_SHORT);
                        //toast.show();
                        break;
                    case MotionEvent.ACTION_UP: //Que hacer cuando el usuario deja de presionar
                        //Toast toast2 = Toast.makeText(getApplicationContext(),"ActionUp",Toast.LENGTH_SHORT);
                        //toast2.show();
                        float currentX = motionEvent.getX();
                        if(lastX < currentX){
                            if(flipper.getDisplayedChild() == 0){
                                break;
                            }
                            flipper.setInAnimation(getBaseContext(),R.anim.slide_in_from_left);
                            flipper.setOutAnimation(getBaseContext(),R.anim.slide_out_to_right);
                            flipper.showNext();
                        }
                        if(lastX > currentX){
                            if(flipper.getDisplayedChild() == 1){
                                break;
                            }
                            flipper.setInAnimation(getBaseContext(),R.anim.slide_in_from_right);
                            flipper.setOutAnimation(getBaseContext(),R.anim.slide_out_to_left);
                            flipper.showPrevious();
                        }
                        break;
                }
                return true;
            }
        });
        flipper.performClick();
       // String [] disciplinas = getResources().getStringArray(R.array.Disciplinas);
        ImageView archery = (ImageView)findViewById(R.id.imageView2);
        archery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(),"TocadoArchery",Toast.LENGTH_LONG);
                toast.show();
                Intent mainIntent = new Intent(MainActivity.this,Disciplina.class);
                mainIntent.putExtra("nombre","Archery");
                startActivity(mainIntent);
                finish();
            }
        });
    }





    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                float currentX = event.getX();
                if(lastX < currentX){
                    if(flipper.getDisplayedChild() == 0){
                        break;
                    }
                    flipper.setInAnimation(this,R.anim.slide_in_from_left);
                    flipper.setOutAnimation(this,R.anim.slide_out_to_right);
                    flipper.showNext();
                }
                if(lastX > currentX){
                    if(flipper.getDisplayedChild() == 1){
                        break;
                    }
                    flipper.setInAnimation(this,R.anim.slide_in_from_right);
                    flipper.setOutAnimation(this,R.anim.slide_out_to_left);
                    flipper.showPrevious();
                }

        }
        return super.onTouchEvent(event);
    }*/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_myTokyo2020) {
            Intent i = new Intent(this,TabActivity.class);
            startActivity(i);

        } else if (id == R.id.medal_table) {
            Intent i = new Intent(this,MedalTableActivity.class);
            startActivity(i);

        } else if (id == R.id.cityTokyo) {

        } else if (id == R.id.about) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
