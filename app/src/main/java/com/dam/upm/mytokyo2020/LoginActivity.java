package com.dam.upm.mytokyo2020;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class LoginActivity extends  AppCompatActivity {

    private static String nombre;

    private class LoginUser extends AsyncTask<String,Integer,JSONObject>{
        protected JSONObject doInBackground(String... params){
            boolean loginOk = false;
            try {
                URL url = new URL(ServerInfo.SERVER+"Login?email="+params[0]+"&password="+params[1]);
                URLConnection connection = url.openConnection();
                connection.setDoOutput(false);
                connection.setDoInput(true);

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = br.readLine();
                String msg2 = "";
                String tipo2 = "";
                while(response!=null){
                    JSONObject json = new JSONObject(response);
                    msg2 = json.getString("msg");
                    tipo2 = json.getString("tipo");
                    response = br.readLine();
                }
                if(msg2!="" && msg2.equals("user")){
                    if(tipo2!=""){
                        if(tipo2.equals("ok")){
                          loginOk = true;
                        }
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
                if (loginOk) {
                    jRespuesta.put("res", "ok");
                } else {
                    jRespuesta.put("res", "fail");
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
            return jRespuesta;
        }
    }

    private class GetUsuario extends AsyncTask<String,Integer,JSONObject>{
        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                URL url = new URL(ServerInfo.SERVER+"GetUser?email="+params[0]);
                URLConnection connection = url.openConnection();
                connection.setDoOutput(false);
                connection.setDoInput(true);

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = br.readLine();
                while(response!=null){
                    JSONObject json = new JSONObject(response);
                    nombre = json.getString("username");
                    response = br.readLine();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject jResponse = new JSONObject();
            try {
                jResponse.put("username",nombre);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jResponse;
        }
    }

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    public static boolean dentro = false;
    private static boolean continuar = false;
    private static boolean alertDialog = false;
    private Context context = this;
    public static final String myPreferences = "myPrefs";
    SharedPreferences sharedPreferences;

    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_login) Button _loginButton;
    @InjectView(R.id.link_signup) TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
        sharedPreferences = getSharedPreferences("myPrefs",Context.MODE_PRIVATE);

    }

    public void login() {
        Log.d(TAG, "Login");
        if (!validate()) {
            onLoginFailed();
            return;
        }
        _loginButton.setEnabled(false);
        final String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject result = new LoginUser().execute(email, password).get();
                    System.out.println("#####################");
                    System.out.println(result.toString());
                    if (result != null) {
                        if (result.get("res").equals("ok")) {
                            continuar = true;
                        }
                        if (result.get("res").equals("fail")) {
                            continuar = false;
                        }
                        if (continuar) {
                            //Llamo a getUser
                            try {
                                JSONObject result2 = new GetUsuario().execute(email).get();
                                if (result2 != null) {
                                    /*final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                                            R.style.Base_AppTheme);
                                    progressDialog.setIndeterminate(true);
                                    progressDialog.setMessage("Authenticating...");
                                    progressDialog.show();*/
                                    System.out.println("EN EL LOGIN INSERTANDO LAS SHAREDPREFERENCES");
                                    String uName = result2.getString("username");
                                    System.out.println("Username => " + uName);
                                    System.out.println("Email => " + email);
                                    sharedPreferences = getApplicationContext().getSharedPreferences("myPrefs",Context.MODE_PRIVATE);
                                    sharedPreferences.edit().putString("username",uName);
                                    sharedPreferences.edit().putString("email",email);
                                    sharedPreferences.edit().commit();
                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                    dentro = true;
                                    i.putExtra("username", uName);
                                    i.putExtra("email",email);
                                    i.putExtra("dentro", true);
                                    //progressDialog.cancel();
                                    startActivity(i);
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            alertDialog = true;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(alertDialog){
                    alertDialog = false;
                    //Sacar un Dialog
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    // set title
                    alertDialogBuilder.setTitle("Login Error");
                    // set dialog message
                    alertDialogBuilder
                            .setMessage("The username does not exists. Please consider SignUp.")
                            .setCancelable(false)
                            .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                    _loginButton.setEnabled(true);
                                }
                            });
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                }
            }
        });//fin Handler
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        _loginButton.setEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                dentro=true;
                System.out.println("dentro = " + dentro); //Esto hacerlo con SharedPreferences
                startActivity(i);
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        //moveTaskToBack(true);
        System.out.println("En back Pressed Login");
        super.onBackPressed();
        //finish();
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        dentro=true;
        System.out.println("dentro = " + dentro);
        startActivity(i);
        //finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }
        return valid;
    }
}
