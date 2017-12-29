package com.dam.upm.mytokyo2020;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


// TODO: Rename and change types and number of parameters
    public class Fragmento_Eventos extends Fragment {

        ImageView bell;

        public Fragmento_Eventos() {
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            bell = (ImageView)getView().findViewById(R.id.notification);
            bell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*//Caso en el que la notificación este apagada
                    if(bell.getDrawable().equals(R.drawable.ic_notification_off)){
                        bell.setImageResource(R.drawable.ic_notification_on);
                    }//Caso en el que la notificacion esté encendida
                    if(bell.getDrawable().equals(R.drawable.ic_notification_on)){
                        bell.setImageResource(R.drawable.ic_notification_off);
                    }*/
                }
            });
            return inflater.inflate(R.layout.fragmento__eventos, container, false);
        }
}
