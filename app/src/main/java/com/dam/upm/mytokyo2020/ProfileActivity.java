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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

public class ProfileActivity extends Fragment {

    TableLayout table;
    SharedPreferences sharedPreferences;
    JSONObject [] eventos;
    JSONObject [] disciplinas;
    Double [] precios;
    String email;
    String username;
    TextView tvNombre;
    //final Context context = this;

    private class GetEvents extends AsyncTask<String,Integer,JSONArray>{
        protected JSONArray doInBackground(String... params){
            JSONArray respuesta = null;

            System.out.println("Parametro 0: " + params[0]);

            try {
                URL url = new URL(ServerInfo.SERVER+"GetShoppingHistory?email="+params[0]);
                System.out.println("Llamando a host : " + url.getHost());
                System.out.println("Llamando a query: " + url.getQuery());
                System.out.println("Llamando a path: " + url.getPath());
                URLConnection connection = url.openConnection();
                System.out.println("Despues del URLConnection");
                connection.setDoOutput(false);
                System.out.println("Despues del SetDoOutput");
                connection.setDoInput(true);
                System.out.println("Despues del SetDoInput");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                System.out.println("Despues del bufferReader");
                String response = br.readLine();
                System.out.println("Despues del br.readline");
                if(response!=null){
                    respuesta = new JSONArray(response);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                //e.printStackTrace();
                respuesta = null;
            }
            return respuesta;
        }

        @Override
        protected void onPostExecute(JSONArray eventosUsuario) {
            try {
                System.out.println("En el POST-Execute");
                //JSONArray eventosUsuario = new GetEvents().execute(email).get();
                if (eventosUsuario != null) {
                    System.out.println("Hay eventos");
                    eventos = new JSONObject[eventosUsuario.length()];
                    disciplinas = new JSONObject[eventosUsuario.length()];
                    precios = new Double[eventosUsuario.length()];
                    int i = 0;
                    while (!eventosUsuario.isNull(i)) {
                        JSONObject eventoAux = eventosUsuario.getJSONObject(i);
                        JSONObject evento = eventoAux.getJSONObject("ev");
                        JSONObject uCE =  eventoAux.getJSONObject("uCE");
                        System.out.println(uCE.toString());
                        Double precio = (Double)uCE.get("precio");
                        Integer idEvento = evento.getInt("idEvento");
                        JSONObject disciplina = (JSONObject) evento.get("disciplina");
                        eventos[i] = evento;
                        disciplinas[i] = disciplina;
                        precios[i] = precio;
                        i++;
                    }
                    TextView puntos = getView().findViewById(R.id.puntos);
                    puntos.setText("500");

                    table = getView().findViewById(R.id.table);
                    System.out.println("Numero de hijos de la tabla");
                    System.out.println(table.getChildCount());
                    System.out.println("Numero de eventos");
                    System.out.println(eventos.length);

                    for (i = 0; i < eventos.length; i++) {
                        System.out.println("En el bucle de PostExecute");
                        System.out.print("Longitud " + eventos.length);
                        //Al inicio solo tiene un hijo (una row) que es el encabezado
                        //if (i >= 0) {
                            System.out.println("Entrado cuando i >= 0");
                            final int j = i;
                            //Aqui crear tantas filas como eventos haya
                            TableRow row1 = new TableRow(getActivity().getApplicationContext());
                            TableRow row2 = new TableRow(getActivity().getApplicationContext());
                            TextView ev1 = new TextView(getActivity().getApplicationContext());
                            SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");
                            String horaString = hora.format(eventos[j].get("hora"));
                            try {
                                String nombreDisciplina = disciplinas[i].getString("nombre");
                                System.out.println("Nombre Disciplina = " + nombreDisciplina);
                                System.out.println("Tipo del evento = " + eventos[j].get("tipo"));
                                ev1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                                        // set title
                                        try {
                                            alertDialogBuilder.setTitle(disciplinas[j].get("nombre").toString());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        // set dialog message
                                        try {
                                            SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
                                            SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");
                                            String fechaString = fecha.format(eventos[j].get("fecha"));
                                            String horaString = hora.format(eventos[j].get("hora"));
                                            Double precioAux = precios[j];
                                            DecimalFormat df = new DecimalFormat("#.##");
                                            alertDialogBuilder
                                                    .setMessage("Prueba\n\t" + "> "+ eventos[j].getString("tipo")+"\n"+ "Fecha\n\t" + "> " + fechaString + "\n" + "Hora\n\t" + "> "+ horaString + "\n" + "Precio Pagado\n\t"+ "> " + df.format(precioAux) + " €\n")
                                                    .setCancelable(false)
                                                    .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                          dialogInterface.dismiss();
                                                        }
                                                    });
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        AlertDialog alertDialog = alertDialogBuilder.create();
                                        // show it
                                        alertDialog.show();
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //System.out.println("Añadiendo Evento 1");
                            ev1.setText("Evento " + (i+1));
                            ev1.setGravity(Gravity.CENTER);

                            TextView hora1 = new TextView(getActivity().getApplicationContext());
                            hora1.setText(horaString);
                            hora1.setGravity(Gravity.CENTER);

                            TextView lugar1 = new TextView(getActivity().getApplicationContext());
                            lugar1.setText(eventos[i].getString("lugar"));
                            lugar1.setGravity(Gravity.CENTER);

                            row1.addView(ev1);
                            row1.addView(hora1);
                            row1.addView(lugar1);
                            row1.setPadding(0, 25, 0, 0);

                            table.addView(row1, i + 1);
                        //}
                    }//for

                } else {
                    System.out.println("No hay eventos");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("OnCreateView");
        return inflater.inflate(R.layout.activity_profile, container, false);
    }


    public void onActivityCreated(Bundle state) {
        System.out.println("On Activity Created");
        super.onActivityCreated(state);

        Bundle bundle = this.getArguments();
        if(bundle!=null){
            email = bundle.getString("email");
            username = bundle.getString("username");
            System.out.println(email);
            System.out.println(username);
            tvNombre = getView().findViewById(R.id.nombreFoto);
            tvNombre.setText(username);
        }
        /*sharedPreferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        TextView nombre_perfil = getView().findViewById(R.id.nombreFoto);
        nombre_perfil.setText(sharedPreferences.getString("username", ""));
        final String email = sharedPreferences.getString("email", "");

        System.out.println("Despues O");
        System.out.println("Username es: " + sharedPreferences.getString("username",""));
        System.out.println("Email es : " + email);*/



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("En el POST");
                    JSONArray eventosUsuario = new GetEvents().execute(email).get();
                    /*if(eventosUsuario!=null) {
                        eventos = new JSONObject[eventosUsuario.length()];
                        disciplinas = new JSONObject[eventosUsuario.length()];
                        int i = 0;
                        while (!eventosUsuario.isNull(i)) {
                            JSONObject eventoAux = eventosUsuario.getJSONObject(i);
                            JSONObject evento = eventoAux.getJSONObject("ev");
                            Integer idEvento = evento.getInt("idEvento");
                            JSONObject disciplina = (JSONObject) evento.get("disciplina");
                            eventos[i] = evento;
                            disciplinas[i] = disciplina;
                            i++;
                        }
                        TextView puntos = getView().findViewById(R.id.puntos);
                        puntos.setText("500");

                        table = getView().findViewById(R.id.table);
                        System.out.println("Numero de hijos de la tabla");
                        System.out.println(table.getChildCount());
                        System.out.println("Numero de eventos");
                        System.out.println(eventos.length);

                        for (i = 0; i < eventos.length; i++) {
                            //Al inicio solo tiene un hijo (una row) que es el encabezado
                            if (i > 0) {
                                final int j = i;
                                //Aqui crear tantas filas como eventos haya
                                TableRow row1 = new TableRow(getActivity().getApplicationContext());
                                TableRow row2 = new TableRow(getActivity().getApplicationContext());
                                TextView ev1 = new TextView(getActivity().getApplicationContext());
                                try {
                                    String nombreDisciplina = disciplinas[i].getString("nombre");
                                    ev1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                                            // set title
                                            alertDialogBuilder.setTitle("Event Info");
                                            // set dialog message
                                            try {
                                                alertDialogBuilder
                                                        .setMessage(eventos[j].getString("Tipo"))
                                                        .setCancelable(false)
                                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                // if this button is clicked, close
                                                                // current activity
                                                                ProfileActivity.this.getActivity().finish();
                                                            }
                                                        })
                                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                // if this button is clicked, just close
                                                                // the dialog box and do nothing
                                                                dialog.cancel();
                                                            }
                                                        });
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            // create alert dialog

                                            AlertDialog alertDialog = alertDialogBuilder.create();
                                            // show it
                                            alertDialog.show();

                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
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
                                row1.setPadding(0, 25, 0, 0);

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
                                row2.setPadding(0, 25, 0, 0);
                                table.addView(row1, i);
                                table.addView(row2, i + 1);
                                break;
                            }
                        }//for

                    }else{
                        System.out.println("No hay eventos");
                    }*/

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        },2000);

        if ((eventos == null) && (disciplinas == null)) {
            System.out.println("!!!!!!!!!!!!");
        }  //Cuidao que si no hay eventos se queda aqui la aplicacion y se cuelga
        //Aqui ya tengo los eventos y disciplinas
        //Buscar los eventos del usuario
//        else {
//            TextView puntos = getView().findViewById(R.id.puntos);
//            puntos.setText("500");
//
//            table = getView().findViewById(R.id.table);
//            System.out.println("Numero de hijos de la tabla");
//            System.out.println(table.getChildCount());
//
//            for (int i = 0; i < eventos.length + 1; i++) {
//                //Al inicio solo tiene un hijo (una row) que es el encabezado
//                if (i > 0) {
//                    final int j = i;
//                    //Aqui crear tantas filas como eventos haya
//                    TableRow row1 = new TableRow(getActivity().getApplicationContext());
//                    TableRow row2 = new TableRow(getActivity().getApplicationContext());
//                    TextView ev1 = new TextView(getActivity().getApplicationContext());
//                    try {
//                        String nombreDisciplina = disciplinas[i].getString("nombre");
//                        ev1.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
//                                // set title
//                                alertDialogBuilder.setTitle("Event Info");
//                                // set dialog message
//                                try {
//                                    alertDialogBuilder
//                                            .setMessage(eventos[j].getString("Tipo"))
//                                            .setCancelable(false)
//                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                                public void onClick(DialogInterface dialog, int id) {
//                                                    // if this button is clicked, close
//                                                    // current activity
//                                                    ProfileActivity.this.getActivity().finish();
//                                                }
//                                            })
//                                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                                public void onClick(DialogInterface dialog, int id) {
//                                                    // if this button is clicked, just close
//                                                    // the dialog box and do nothing
//                                                    dialog.cancel();
//                                                }
//                                            });
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//                                // create alert dialog
//
//                                AlertDialog alertDialog = alertDialogBuilder.create();
//                                // show it
//                                alertDialog.show();
//
//                            }
//                        });
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    ev1.setText("Evento 1");
//                    ev1.setGravity(Gravity.CENTER);
//
//                    TextView hora1 = new TextView(getActivity().getApplicationContext());
//                    hora1.setText("18:00:10");
//                    hora1.setGravity(Gravity.CENTER);
//
//                    TextView lugar1 = new TextView(getActivity().getApplicationContext());
//                    lugar1.setText("Estadio Olimpico");
//                    lugar1.setGravity(Gravity.CENTER);
//
//                    row1.addView(ev1);
//                    row1.addView(hora1);
//                    row1.addView(lugar1);
//                    row1.setPadding(0, 25, 0, 0);
//
//                    TextView ev2 = new TextView(getActivity().getApplicationContext());
//                    ev2.setText("Evento 2");
//                    ev2.setGravity(Gravity.CENTER);
//
//                    TextView hora2 = new TextView(getActivity().getApplicationContext());
//                    hora2.setText("19:00:10");
//                    hora2.setGravity(Gravity.CENTER);
//
//                    TextView lugar2 = new TextView(getActivity().getApplicationContext());
//                    lugar2.setText("Estadio de las golondrinas");
//                    lugar2.setGravity(Gravity.CENTER);
//
//                    row2.addView(ev2);
//                    row2.addView(hora2);
//                    row2.addView(lugar2);
//                    row2.setPadding(0, 25, 0, 0);
//                    table.addView(row1, i);
//                    table.addView(row2, i + 1);
//                    break;
//                }
//            }//for
//        }//else
    }

}




