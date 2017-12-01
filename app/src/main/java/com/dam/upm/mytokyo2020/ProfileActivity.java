package com.dam.upm.mytokyo2020;

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

public class ProfileActivity extends Fragment {

    TableLayout table;
    //final Context context = this;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_profile, container, false);
    }


    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        TextView nombre_perfil = getView().findViewById(R.id.nombreFoto);
        nombre_perfil.setText("Jose Manuel Carral");

        TextView puntos = getView().findViewById(R.id.puntos);
        puntos.setText("500");

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




