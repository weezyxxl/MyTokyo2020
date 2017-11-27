package com.dam.upm.mytokyo2020;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.support.v4.app.Fragment;

public class SportsActivity extends Fragment {

    @Override

        public View onCreateView(LayoutInflater inflater,
                ViewGroup container,
                Bundle savedInstanceState) {
        View view;

        view= inflater.inflate(R.layout.activity_sports, container, false);



        return view;
    }
        /*super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerSports);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Disciplinas, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);*/

}

