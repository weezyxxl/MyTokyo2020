package com.dam.upm.mytokyo2020;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.URL;

public class BuyActivity extends AppCompatActivity {


    private SharedPreferences sharedPreferences;
    Button realizarPago;
    EditText date;
    EditText price;
    EditText cardNumber;

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

    private class BuyTicket extends AsyncTask<URL,Integer,JSONObject>{

        @Override
        protected JSONObject doInBackground(URL... urls) {
            return null;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        realizarPago = (Button)findViewById(R.id.pagar);
        date = (EditText)findViewById(R.id.date);
        price = (EditText)findViewById(R.id.price);
        cardNumber = (EditText)findViewById(R.id.cardNumber);

        realizarPago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(date.getText()!=null){
                    if(price.getText()!=null){
                        if(cardNumber.getText()!=null){
                           boolean res = validateCreditCardNumber(cardNumber.getText().toString());
                           if(res){

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
