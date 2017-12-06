package com.dam.upm.mytokyo2020;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

public class ProfileActivity extends Fragment {

    TableLayout table;
    SharedPreferences sharedPreferences;
    //final Context context = this;

    private class GetEvents extends AsyncTask<String,Integer,JSONArray>{
        protected JSONArray doInBackground(String... params){
            JSONArray respuesta = null;

            try {
                URL url = new URL(ServerInfo.SERVER+"GetShoppingHistory?email="+params[0]);
                URLConnection connection = url.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = br.readLine();
                while(response!=null){
                    respuesta = new JSONArray(response);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return respuesta;
        }
    }


    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_profile, container, false);
    }


    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        sharedPreferences = this.getActivity().getSharedPreferences("myPrefs",Context.MODE_PRIVATE);

        TextView nombre_perfil = getView().findViewById(R.id.nombreFoto);
        nombre_perfil.setText(sharedPreferences.getString("username",""));
        final String email = sharedPreferences.getString("email","");

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONArray eventosUsuario = new GetEvents().execute(email).get();
                    int i = 0;
                    while(!eventosUsuario.isNull(i)){
                        JSONObject evento = eventosUsuario.getJSONObject(i);
                        JSONObject disciplina = evento.getJSONObject("disciplina");
                        i++;
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        TextView puntos = getView().findViewById(R.id.puntos);
        puntos.setText("500");

        //Buscar los eventos del usuario

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
                        alertDialogBuilder.setTitle("Event Info");
                        // set dialog message
                        alertDialogBuilder
                                .setMessage("27/02/2018 - 20:20")
                                .setCancelable(false)
                                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // if this button is clicked, close
                                        // current activity
                                        ProfileActivity.this.getActivity().finish();
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
                ev1.setText("Evento 1");
                ev1.setGravity(Gravity.CENTER);

                TextView hora1 = new TextView(getActivity().getApplicationContext());
                hora1.setText("18:00:10");
                hora1.setGravity(Gravity.CENTER);

                TextView lugar1 = new TextView(getActivity().getApplicationContext());
                lugar1.setText("Estadio Olimpico");
                lugar1.setGravity(Gravity.CENTER);

                row1.addView(ev1);
                row1.addView(hora1);
                row1.addView(lugar1);
                row1.setPadding(0,25,0,0);

                TextView ev2 = new TextView(getActivity().getApplicationContext());
                ev2.setText("Evento 2");
                ev2.setGravity(Gravity.CENTER);

                TextView hora2 = new TextView(getActivity().getApplicationContext());
                hora2.setText("19:00:10");
                hora2.setGravity(Gravity.CENTER);

                TextView lugar2 = new TextView(getActivity().getApplicationContext());
                lugar2.setText("Estadio de las golondrinas");
                lugar2.setGravity(Gravity.CENTER);

                row2.addView(ev2);
                row2.addView(hora2);
                row2.addView(lugar2);
                row2.setPadding(0,25,0,0);
                table.addView(row1,i);
                table.addView(row2,i+1);
                break;
            }
        }
    }

}




