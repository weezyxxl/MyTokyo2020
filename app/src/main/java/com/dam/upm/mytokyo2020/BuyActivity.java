package com.dam.upm.mytokyo2020;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class BuyActivity extends AppCompatActivity {


    private SharedPreferences sharedPreferences;
    Button realizarPago;
    EditText date;
    EditText price;
    EditText cardNumber;
    String email;
    Context context;

    private static boolean validateCreditCardNumber(String str) {

        boolean result = false;

        int[] ints = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            ints[i] = Integer.parseInt(str.substring(i, i + 1));
        }
        for (int i = ints.length - 2; i >= 0; i = i - 2) {
            int j = ints[i];
            j = j * 2;
            if (j > 9) {
                j = j % 10 + 1;
            }
            ints[i] = j;
        }
        int sum = 0;
        for (int i = 0; i < ints.length; i++) {
            sum += ints[i];
        }
        if (sum % 10 == 0) {
            System.out.println(str + " is a valid credit card number");
            result = true;
        } else {
            System.out.println(str + " is an invalid credit card number");
            result = false;
        }
        return result;
    }

    private class BuyTicket extends AsyncTask<String,Integer,JSONObject>{

        private boolean comprado = false;
        private String msg = "";
        private String tipo = "";

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                URL url = new URL(ServerInfo.SERVER+"BuyTicket?email="+params[0]+"&idEvento="+params[1]);
                URLConnection connection = url.openConnection();
                connection.setDoOutput(false);
                connection.setDoInput(true);

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = br.readLine();
                while(response!=null){
                    JSONObject json = new JSONObject(response);
                    msg = json.getString("msg");
                    tipo = json.getString("tipo");
                    response = br.readLine();
                }
                if(msg != ""){
                    if(msg.equals("ok")){
                        comprado = true;
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject jRespuesta = new JSONObject();
            try {
                if(comprado){
                    jRespuesta.put("res","ok");
                }else{
                    switch (tipo){
                        case "nobought":
                            jRespuesta.put("res","nobought");
                            break;
                        case "yetbought":
                            jRespuesta.put("res","yetbought");
                            break;
                        case "noEvent":
                            jRespuesta.put("res","noEvent");
                            break;
                        default:
                            jRespuesta.put("res","error");
                            break;
                    }
                }}
            catch (JSONException e) {
                e.printStackTrace();
            }
            return jRespuesta;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            if(result!=null){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());
                try {
                    String resS = result.getString("res");
                    if(resS.equals("ok")){
                        alertDialogBuilder.setTitle("Purchase Result");
                        alertDialogBuilder.setMessage("Your purchase has been made correctly.\nAn email has been sent to you.\nCheck your email account for details.");
                        //alertDialogBuilder.setMessage("An email has been sent.");
                        //alertDialogBuilder.setMessage("Check your email account for details.");
                    }if(resS.equals("nobought")){
                        alertDialogBuilder.setTitle("Purchase Result");
                        alertDialogBuilder.setMessage("You have already purchase this event.\nIf you think you haven't already bought this event please contact\ndam2017g3@gmail.com for support.");
                        alertDialogBuilder.setCancelable(false);
                        alertDialogBuilder.setNeutralButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                    }if(resS.equals("yetbought")){
                        alertDialogBuilder.setTitle("Purchase Result");
                        alertDialogBuilder.setMessage("You have already purchase this event.\nIf you think you haven't already bought this event please contact\ndam2017g3@gmail.com for support.");
                        alertDialogBuilder.setCancelable(false);
                        alertDialogBuilder.setNeutralButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                    }if(resS.equals("noEvent")){
                        alertDialogBuilder.setTitle("Purchase Result");
                        alertDialogBuilder.setMessage("There is no event with that name.");
                        alertDialogBuilder.setCancelable(false);
                        alertDialogBuilder.setNeutralButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                    }if(resS.equals("error")){
                        alertDialogBuilder.setTitle("Purchase Result");
                        alertDialogBuilder.setMessage("Internal Server Error\nPlease contact dam2017g3@gmail.com for support.");
                        alertDialogBuilder.setCancelable(false);
                        alertDialogBuilder.setNeutralButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                    }else{
                        alertDialogBuilder.setTitle("Purchase Result");
                        alertDialogBuilder.setMessage("App Error\nPlease contact dam2017g3@gmail.com for support.");
                        alertDialogBuilder.setCancelable(false);
                        alertDialogBuilder.setNeutralButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                    }
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                Toast t = Toast.makeText(getApplicationContext(),"Fallo JSONObject",Toast.LENGTH_LONG);
                t.show();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        context = this.getApplicationContext();
        //sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences("myPrefs",context.MODE_PRIVATE);
        //sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        realizarPago = (Button)findViewById(R.id.pagar);
        date = (EditText)findViewById(R.id.date);
        price = (EditText)findViewById(R.id.price);
        cardNumber = (EditText)findViewById(R.id.cardNumber);
        email = sharedPreferences.getString("email","");

        realizarPago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(date.getText()!=null){
                    if(price.getText()!=null){
                        if(cardNumber.getText()!=null){
                            boolean res = validateCreditCardNumber(cardNumber.getText().toString());
                            if(res){
                                new BuyTicket().execute(email,"1");
                            }else{
                                Toast t = Toast.makeText(getApplicationContext(),"No valid format card",Toast.LENGTH_LONG);
                                t.show();
                            }
                        }else{
                            Toast t = Toast.makeText(getApplicationContext(),"No card",Toast.LENGTH_LONG);
                            t.show();
                        }
                    }
                }

            }
        });
    }
}
