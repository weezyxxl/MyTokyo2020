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
            String urlRequest = "http://localhost:8080/MyTokyo2020Server/CheckConnectivity";
            InputStream stream = null;
            JSONObject result = null;
            try {
                URL url = new URL(urlRequest);
                URLConnection connection = url.openConnection();
                HttpURLConnection httpConnection = (HttpURLConnection)connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();
                stream = httpConnection.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(stream));
                String aux = "";
                while((aux = buffer.readLine())!=null){
                    result = new JSONObject().getJSONObject(aux);
                }
            } catch (java.io.IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(JSONObject result){
            if(result != null) {
                Toast t = Toast.makeText(getApplicationContext(), "Conectado", Toast.LENGTH_LONG);
                t.show();
            }else{
                Toast t = Toast.makeText(getApplicationContext(), "Fallo", Toast.LENGTH_LONG);
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
