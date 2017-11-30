package com.dam.upm.mytokyo2020;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;

import android.support.v7.app.ActionBar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity
     {


    private ViewFlipper flipper;
    private float lastX;
    private DrawerLayout drawerLayout;
    SharedPreferences sharedPreferences;
    String username;
    private static final int MENU_LOGOUT = Menu.FIRST + 4;
    private NavigationView navigationView;
    private static boolean logOutMarca = false;
    private String hola = "hola";


    private void agregarToolbar() {
             Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
             setSupportActionBar(toolbar);
             final ActionBar ab = getSupportActionBar();
             if (ab != null) {
                 // Poner ícono del drawer toggle
                 ab.setHomeAsUpIndicator(R.drawable.drawer_toggle);
                 ab.setDisplayHomeAsUpEnabled(true);
             }

         }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        agregarToolbar();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (navigationView != null) {
            prepararDrawer(navigationView);
            // Seleccionar item por defecto
            seleccionarItem(navigationView.getMenu().getItem(0));
        }

        sharedPreferences = getSharedPreferences("myPrefs",MODE_PRIVATE);
        if(sharedPreferences!=null){
            username = sharedPreferences.getString("username","");
        }

    }

         @Override
         protected void onResume() {
             super.onResume();
             System.out.println("#########################");
             System.out.println("En OnResume()");
             Intent aux = getIntent();
             String username = aux.getStringExtra("username");
             if(username!=null) {
                 if(username.equals("")){
                     System.out.println("NO HAY USUARIO");
                     navigationView.getMenu().removeItem(MENU_LOGOUT);
                 }else {
                     System.out.println("HAY USUARIO");
                     System.out.println("#################################");
                     System.out.println("Navigation vew count = " + navigationView.getChildCount());
                     if(navigationView.getChildCount() == 1 && !logOutMarca) {
                         logOutMarca = true;
                         navigationView.getMenu().add(0, MENU_LOGOUT, Menu.NONE, "Logout").setIcon(R.drawable.ic_profile);
                         navigationView.getMenu().getItem(0).setCheckable(true);
                     }
                     System.out.println("#################################");
                     System.out.println("Navigation vew count = " + navigationView.getChildCount());
                 }
             }else{
                 System.out.println("NO HAY USUARIO");
                 navigationView.getMenu().removeItem(MENU_LOGOUT);
             }
         }

    private void prepararDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        seleccionarItem(menuItem);

                        drawerLayout.closeDrawers();
                        return true;
                    }
                });

    }
    private void seleccionarItem(MenuItem itemDrawer) {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (itemDrawer.getItemId()) {
            case R.id.item_inicio:
                fragmentoGenerico = new Fragmento_Noticias();
                break;
            case R.id.My_profile:
                // Fragmento para la sección Cuenta
                if((getIntent().getBooleanExtra("dentro",false))==true){
                    Intent i = new Intent(this,ProfileActivity.class);
                    startActivity(i);
                }
                else{
                    Intent i = new Intent(this,LoginActivity.class);
                    startActivity(i);
                }
                break;
            case R.id.medal_table:
                // Fragmento para la sección Categorías
                /*Intent m = new Intent(this,MedalTableActivity.class);

                startActivity(m);*/
                fragmentoGenerico = new MedalTableActivity();

                break;
            case R.id.sports:
                fragmentoGenerico = new Fragmento_Sports();

                break;

            case R.id.twitter:
                Intent intent = null;
                try {
                    // get the Twitter app if possible
                    this.getPackageManager().getPackageInfo("com.twitter.android", 0);
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=USERID"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                } catch (Exception e) {
                    // no Twitter app, revert to browser
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/USERID_OR_PROFILENAME"));
                }
                this.startActivity(intent);

            case MENU_LOGOUT:
                doLogout();
                break;
        }
        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.app_bar_main, fragmentoGenerico)
                    .commit();
        }

        // Setear título actual
        setTitle(itemDrawer.getTitle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case MENU_LOGOUT:
                doLogout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

         private void doLogout() {
            SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            finish();
            Intent i = new Intent(this,MainActivity.class);
            i.putExtra("username","");
            startActivity(i);
         }
}
