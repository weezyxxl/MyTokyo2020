package com.dam.upm.mytokyo2020;

import android.content.Intent;
import android.icu.util.Output;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public class Loading extends AppCompatActivity {

    //Primer argumento -> Params
    //Segundo argumento -> Progress
    //Tercer argumento -> Result
    private class CheckConnectivity extends AsyncTask<URL,Integer,JSONObject>{

        protected JSONObject doInBackground(URL... urls){
            boolean msgOk = false;
            boolean tipoOk = false;
            try {
                URL url = new URL("http://10.0.2.2:8080/MyTokyo2020Server/CheckConnectivity");
                URLConnection connection = url.openConnection();
                connection.setDoOutput(false);
                connection.setDoInput(true);

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = br.readLine();
                String msg = "";
                String tipo = "";
                while(response != null){
                    JSONObject json = new JSONObject(response);
                    msg = json.getString("msg");
                    tipo = json.getString("tipo");
                    response = br.readLine();
                }
                if(msg != "" && msg.equals("connected")) {
                    msgOk = true;
                }
                if(tipo != "" && tipo.equals("ok")){
                    tipoOk = true;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e){
                e.printStackTrace();
            }

            JSONObject jRespuesta = new JSONObject();
            try {
                if(msgOk && tipoOk) {
                    jRespuesta.put("res", "ok");
                }else{
                    jRespuesta.put("res","fail");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jRespuesta;
        }

        protected void onPostExecute(JSONObject result){
            if(result != null) {
                try {
                    String resS = result.getString("res");
                    if(resS!=null){
                        if(resS.equals("ok")){
                            Toast t = Toast.makeText(getApplicationContext(), "Conectado", Toast.LENGTH_LONG);
                            t.show();
                        }if(resS.equals("fail")){
                            Toast t = Toast.makeText(getApplicationContext(), "Fallo al Conectar", Toast.LENGTH_LONG);
                            t.show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                Toast t = Toast.makeText(getApplicationContext(), "Fallo JSONObject", Toast.LENGTH_LONG);
                t.show();
            }
        }
    }

    private ProgressBar spinner;
    private final int WAIT_TIME = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActionBar().hide();
        setContentView(R.layout.activity_loading);
        //spinner = (ProgressBar)findViewById(R.id.progressBar);
        //spinner.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new CheckConnectivity().execute();
                Intent mainIntent = new Intent(Loading.this,LoginActivity.class);
                startActivity(mainIntent);
                finish();
            }
        },WAIT_TIME);
    }
}
