package com.dam.upm.mytokyo2020;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

public class ArcheryDiscipline extends Fragment {
    TabHost th;
    TextView disciplinaName;
    Bundle extras;
    TableLayout table;
    ImageView bell;
    FragmentManager fm;
    ArcheryDialogFragment adf;
    TextView arcInfo;
    SharedPreferences sharedPreferences;
    String username;
    String email;
    //final Context context = this;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_disciplina, container, false);
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        //sharedPreferences = this.getActivity().getSharedPreferences("myPrefs",Context.MODE_PRIVATE);

        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        fm = getFragmentManager();
        adf = new ArcheryDialogFragment();

        disciplinaName = (TextView)getView().findViewById(R.id.disciplinaTitle);

        arcInfo = (TextView)getView().findViewById(R.id.arc_info);
        arcInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adf.show(getActivity().getFragmentManager(),"Archery Info");
            }
        });

        extras = getActivity().getIntent().getExtras();
        /*if(extras != null){
            disciplinaName.setText(extras.getString("nombre"));
        }*/

        th = (TabHost)getView().findViewById(R.id.tabHost);

        th.setup();

        TabHost.TabSpec tab1 = th.newTabSpec("tab1");
        TabHost.TabSpec tab2 = th.newTabSpec("tab2");
        TabHost.TabSpec tab3 = th.newTabSpec("tab3");

        Bundle bundle = this.getArguments();
        if(bundle!=null){
            username = bundle.getString("username");
            email = bundle.getString("email");
        }
        bell = (ImageView) getView().findViewById(R.id.notification);
        if(username!=null && email !=null) {
            bell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Caso en el que la notificación este apagada
                    if (bell.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_notifications_none_black_18dp).getConstantState()) {
                        Toast t = Toast.makeText(view.getContext(), "Notifications On", Toast.LENGTH_SHORT);
                        t.show();
                        bell.setImageResource(R.drawable.ic_notifications_black_18dp);
                        //Esto es para probar
                        FirebaseMessaging.getInstance().subscribeToTopic("archery-male");
                    }//Caso en el que la notificacion esté encendida
                    else {
                        if (bell.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_notifications_black_18dp).getConstantState()) {
                            Toast t = Toast.makeText(view.getContext(), "Notifications Off", Toast.LENGTH_SHORT);
                            t.show();
                            bell.setImageResource(R.drawable.ic_notifications_none_black_18dp);
                            FirebaseMessaging.getInstance().unsubscribeFromTopic("archery-male");
                        }
                    }
                }
            });
        }else{
            bell.setVisibility(View.GONE);
        }

        tab1.setIndicator("Events");
        tab1.setContent(R.id.Eventos);

        tab2.setIndicator("Schedule");
        tab2.setContent(R.id.Schedule);

        table = getView().findViewById(R.id.table);
        System.out.println("Numero de hijos de la tabla");
        System.out.println(table.getChildCount());


        for(int i = 0 ; i < 2 ; i++){
            //Al inicio solo tiene un hijo (una row) que es el encabezado
            if(i>0) {
                //Aqui crear tantas filas como eventos haya
                TableRow row1 = new TableRow(getActivity().getApplicationContext());
                TableRow row2 = new TableRow(getActivity().getApplicationContext());
                TextView ev1 = new TextView(getActivity().getApplicationContext());
                ev1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

                        // set title
                        alertDialogBuilder.setTitle("Buy Ticket");

                        // set dialog message
                        alertDialogBuilder
                                .setMessage("Do you want to buy a ticket?")
                                .setCancelable(false)
                                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // if this button is clicked, close
                                        // current activity
                                        //ArcheryDiscipline.this.getActivity().finish();
                                        //String username = getActivity().getSharedPreferences("myPrefs",Context.MODE_PRIVATE).getString("username","");

                                        if(username!=null && !username.equals("")) {
                                            System.out.println("#####################");
                                            System.out.println("En ARCHERY");
                                            System.out.println(username);
                                            Intent buyTicket = new Intent(getActivity(), BuyActivity.class);
                                            buyTicket.putExtra("disciplina","archery");
                                            buyTicket.putExtra("email",sharedPreferences.getString("email",""));
                                            startActivity(buyTicket);
                                        }else{
                                            System.out.println("INICIANDO LOGIN DESDE ARCHERY");
                                            Intent login = new Intent(getActivity(),LoginActivity.class);
                                            startActivityForResult(login,1);
                                        }

                                    }
                                })
                                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // if this button is clicked, just close
                                        // the dialog box and do nothing
                                        dialog.cancel();
                                    }
                                });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();

                    }
                });
                ev1.setText("Event 1");
                ev1.setGravity(Gravity.CENTER);

                TextView dia1 = new TextView(getActivity().getApplicationContext());
                dia1.setText("25/07/2020");
                dia1.setGravity(Gravity.CENTER);

                TextView hora1 = new TextView(getActivity().getApplicationContext());
                hora1.setText("12:00");
                hora1.setGravity(Gravity.CENTER);

                TextView lugar1 = new TextView(getActivity().getApplicationContext());
                lugar1.setText("Dream Island Archery Field");
                lugar1.setGravity(Gravity.CENTER);
                lugar1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

                        // set title
                        alertDialogBuilder.setTitle("Dream Island Archery Field");
                        // set dialog message
                        alertDialogBuilder
                                .setMessage("How to go")
                                .setCancelable(false)
                                .setPositiveButton("Guide me",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // if this button is clicked, close
                                        // current activity
                                        //Primero engañar al gps diciendo que estamos en esta posicion por ejemplo 35.701117, 139.736656
                                        //Comprobar si está instalada o no la aplicacion de Gooogle Maps
                                        String packageName = "com.google.android.apps.maps";
                                        int flag = 0;
                                        ApplicationInfo appInfo = null;
                                        try {
                                            appInfo = getActivity().getPackageManager().getApplicationInfo(packageName,flag);
                                        } catch (PackageManager.NameNotFoundException e) {
                                            //e.printStackTrace();
                                        }
                                        if(appInfo!=null) {
                                            boolean isAppDisabled = appInfo.enabled;
                                            //Uri gmmIntentUri = Uri.parse("google.navigation:q=Taronga+Zoo,+Sydney+Australia");
                                            Uri gmmIntentUri = Uri.parse("google.navigation:q=35.64825,139.83071");
                                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                            mapIntent.setPackage("com.google.android.apps.maps");
                                            if(mapIntent.resolveActivity(getActivity().getPackageManager())!=null) {
                                                startActivity(mapIntent);
                                            }
                                        }else{
                                            //Iniciar navegador web
                                            Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("http://maps.google.co.in/maps?q=35.64825,139.83071"));
                                            startActivity(i);
                                        }

                                    }
                                })
                                .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // if this button is clicked, just close
                                        // the dialog box and do nothing
                                        dialog.cancel();
                                    }
                                });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();
                    }
                });

                row1.addView(ev1);
                row1.addView(dia1);
                row1.addView(hora1);
                row1.addView(lugar1);
                row1.setPadding(0,25,0,0);

                TextView ev2 = new TextView(getActivity().getApplicationContext());
                ev2.setText("Event 2");
                ev2.setGravity(Gravity.CENTER);

                TextView dia2 = new TextView(getActivity().getApplicationContext());
                dia2.setText("26/07/2020");
                dia2.setGravity(Gravity.CENTER);


                TextView hora2 = new TextView(getActivity().getApplicationContext());
                hora2.setText("09:00");
                hora2.setGravity(Gravity.CENTER);

                TextView lugar2 = new TextView(getActivity().getApplicationContext());
                lugar2.setText("Dream Island Archery Field");
                lugar2.setGravity(Gravity.CENTER);

                row2.addView(ev2);
                row2.addView(dia2);
                row2.addView(hora2);
                row2.addView(lugar2);
                row2.setPadding(0,25,0,0);
                table.addView(row1,i);
                table.addView(row2,i+1);
                break;
            }
        }

        tab3.setIndicator("Results");
        tab3.setContent(R.id.Resultados);

        th.addTab(tab1);
        th.addTab(tab2);
        th.addTab(tab3);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        System.out.println("EN onActivityResult ARCHERYDISCIPLINE");
        if(requestCode == 1){
            System.out.println("EN REQUEST_code");
            if(resultCode == 1){
                System.out.println("EN RESULT CODE");
                //sharedPreferences = this.getContext().getApplicationContext().getSharedPreferences("myPrefs",Context.MODE_PRIVATE);
                sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                username = sharedPreferences.getString("username","");

                Intent buyTicket = new Intent(getActivity(), BuyActivity.class);
                buyTicket.putExtra("username",username);
                buyTicket.putExtra("email",email);
                //buyTicket.putExtra("disciplina","archery");
                startActivity(buyTicket);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("#################");
        System.out.println("EN ON RESUME DE ARCHERY DISCIPLINE");
        //sharedPreferences = this.getContext().getApplicationContext().getSharedPreferences("myPrefs",Context.MODE_PRIVATE);
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username","");
        System.out.println("Nombre = > " + username);
    }
}


