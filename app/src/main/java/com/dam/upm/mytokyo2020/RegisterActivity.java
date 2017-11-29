package com.dam.upm.mytokyo2020;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RegisterActivity extends AppCompatActivity {

    private class RegisterUser extends AsyncTask<String,Integer,JSONObject>{
        @Override
        protected JSONObject doInBackground(String... params) {
            boolean msgInserted = false;
            boolean tipoOk = false;
            try {
                URL url = new URL("http://10.0.2.2:8080/MyTokyo2020Server/SignUp?email="+params[0]+"&username="+params[1]+"&password="+params[2]);
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
                if(msg != ""){
                    if(msg.equals("inserted")){
                        msgInserted = true;
                    }
                }
                if(tipo != ""){
                    if(tipo.equals("ok")){
                        tipoOk = true;
                    }
                }
            }catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject jRespuesta = new JSONObject();
            try {
                if (msgInserted && tipoOk) {
                    jRespuesta.put("res", "ok");
                } else {
                    jRespuesta.put("res", "fail");
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
            return jRespuesta;
        }

        protected void onPostExecute(JSONObject result){
            if(result != null){
                try {
                    String resS = result.getString("res");
                    if(resS!=null){
                        if(resS.equals("ok")){
                            Toast t = Toast.makeText(getApplicationContext(),"Registrado", Toast.LENGTH_LONG);
                            t.show();
                        }if(resS.equals("fail")){
                            Toast t = Toast.makeText(getApplicationContext(),"Fallo registro", Toast.LENGTH_LONG);
                            t.show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                Toast t = Toast.makeText(getApplicationContext(),"Fallo JSONObject",Toast.LENGTH_LONG);
                t.show();
            }
        }
    }


    private static final String TAG = "SignupActivity";

    @InjectView(R.id.input_name) EditText _nameText;
    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_signup) Button _signupButton;
    @InjectView(R.id.link_login) TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String name = _nameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

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

    public void signup() {
        if(validate()){
            String name = _nameText.getText().toString();
            String email = _emailText.getText().toString();
            String password = _passwordText.getText().toString();
            new RegisterUser().execute(email,name,password);
        }else{
            Toast t = Toast.makeText(getApplicationContext(),"Fallo de registro validacion",Toast.LENGTH_LONG);
            t.show();
        }
    }


    public void onSignupSuccess() {
        finish();
    }

    public void onSignupFailed() {

    }


}
