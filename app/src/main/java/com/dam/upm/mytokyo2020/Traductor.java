package com.dam.upm.mytokyo2020;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//import com.google.api.client.util.IOUtils;
import org.apache.commons.io.IOUtils;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

public class Traductor extends Fragment implements OnInitListener {

    private TextToSpeech tts;

    public Traductor() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_traductor, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);


        tts = new TextToSpeech(getContext().getApplicationContext() , this);
        ((Button) getView().findViewById(R.id.bSpeak))
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        speakOut(((TextView) getView().findViewById(R.id.tvTranslatedText))
                                .getText().toString());
                    }
                });

        // Get the access token
// The key got from Azure portal, please see https://docs.microsoft.com/en-us/azure/cognitive-services/cognitive-services-apis-create-account

        ((Button) getView().findViewById(R.id.bTranslate))
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        class bgStuff extends AsyncTask<Void, Void, String> {

                            String translatedText ="";
                            protected String doInBackground(Void... params) {
                                //String key = "7a010fb3194b4259ac24fe3e4f38340a";
                                String authenticationUrl = "https://api.cognitive.microsoft.com/sts/v1.0/issueToken";
                                HttpsURLConnection authConn = null;
                                try {
                                    authConn = (HttpsURLConnection) new URL(authenticationUrl).openConnection();
                                    authConn.setRequestMethod("POST");
                                    authConn.setDoOutput(true);
                                    authConn.setRequestProperty("Ocp-Apim-Subscription-Key", "7a010fb3194b4259ac24fe3e4f38340a");
                                    IOUtils.write("", authConn.getOutputStream(), "UTF-8");
                                    String token = IOUtils.toString(authConn.getInputStream(), "UTF-8");
                                    System.out.println(token);

                                    // Using the access token to build the appid for the request url
                                    String appId = URLEncoder.encode("Bearer " + token, "UTF-8");
                                    //String text = URLEncoder.encode("happy birthday", "UTF-8");
                                    String from = "en";
                                    String to = "ja";
                                    String text1 = ((EditText) getView().findViewById(R.id.etUserText))
                                            .getText().toString();
                                    String translatorTextApiUrl = String.format("https://api.microsofttranslator.com/v2/http.svc/Translate?appid=%s&text=%s&from=%s&to=%s", appId, text1, from, to);
                                    HttpsURLConnection translateConn = (HttpsURLConnection) new URL(translatorTextApiUrl).openConnection();
                                    translateConn.setRequestMethod("GET");
                                    translateConn.setRequestProperty("Accept", "application/xml");
                                    translatedText = IOUtils.toString(translateConn.getInputStream(), "UTF-8");
                                    translatedText = cleanOUTPUT(translatedText);
                                    System.out.println( translatedText);



                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return translatedText;
                            }

                            @Override
                            protected void onPostExecute(String result) {
                                // TODO Auto-generated method stub
                                ((TextView) getView().findViewById(R.id.tvTranslatedText))
                                        .setText(translatedText);
                                super.onPostExecute(result);
                            }
                        }
                        new bgStuff().execute();
                    }
                });
    }
    public String cleanOUTPUT(String OUTPUT) {
        OUTPUT = OUTPUT.replaceAll("<string xmlns=\"http://schemas.microsoft.com/2003/10/Serialization/\">","");
        OUTPUT = OUTPUT.replaceAll("</string>", "");
        return OUTPUT;
    }
    public void onInit(int status) {
        // TODO Auto-generated method stub
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.JAPANESE);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {

                // speakOut("Ich");
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }
    private void speakOut(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
        /*
        tts = new TextToSpeech(getContext().getApplicationContext() , this);
        ((Button) getView().findViewById(R.id.bSpeak))
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        speakOut(((TextView) getView().findViewById(R.id.tvTranslatedText))
                                .getText().toString());
                    }
                });

        ((Button) getView().findViewById(R.id.bTranslate))
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub

                        class bgStuff extends AsyncTask<Void, Void, Void> {

                            String translatedText = "";

                            @Override
                            protected Void doInBackground(Void... params) {
                                // TODO Auto-generated method stub
                                try {
                                    String text = ((EditText) getView().findViewById(R.id.etUserText))
                                            .getText().toString();
                                    translatedText = translate(text);
                                } catch (Exception e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                    translatedText = e.toString();
                                }

                                return null;
                            }

                            @Override
                            protected void onPostExecute(Void result) {
                                // TODO Auto-generated method stub
                                ((TextView) getView().findViewById(R.id.tvTranslatedText))
                                        .setText(translatedText);
                                super.onPostExecute(result);
                            }

                        }

                        new bgStuff().execute();
                    }
                });
    }


    public String translate(String text) throws Exception {

        // Set the Client ID / Client Secret once per JVM. It is set statically
        // and applies to all services
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Translate.setClientId("3618aa25-cad0-4332-ba34-b3a508e0bfb5"); // Change this
        Translate.setClientSecret("8076825d8222429b91def8e7db630c94"); // change

        //String translatedText = "こんにちは私の名前はフアンです";
        String translatedText ="";
        translatedText = Translate.execute(text, Language.ENGLISH);

        return translatedText;
    }


    public void onInit(int status) {
        // TODO Auto-generated method stub
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.JAPANESE);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {

                // speakOut("Ich");
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    private void speakOut(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }


*/


}
