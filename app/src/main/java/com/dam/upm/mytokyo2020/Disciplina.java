package com.dam.upm.mytokyo2020;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

public class Disciplina extends AppCompatActivity {

    TabHost th;
    TextView disciplinaName;
    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplina);

        disciplinaName = (TextView)findViewById(R.id.disciplinaTitle);
        extras = getIntent().getExtras();
        if(extras != null){
            disciplinaName.setText(extras.getString("nombre"));
        }

        th = (TabHost)findViewById(R.id.tabHost);

        th.setup();

        TabHost.TabSpec tab1 = th.newTabSpec("tab1");
        TabHost.TabSpec tab2 = th.newTabSpec("tab2");
        TabHost.TabSpec tab3 = th.newTabSpec("tab3");
        TabHost.TabSpec tab4 = th.newTabSpec("tab4");

        tab1.setIndicator("Eventos");
        tab1.setContent(R.id.Eventos);
        tab2.setIndicator("Atletas");
        tab2.setContent(R.id.Atletas);
        tab3.setIndicator("Noticias");
        tab3.setContent(R.id.Noticias);
        tab4.setIndicator("Resultados");
        tab4.setContent(R.id.Resultados);

        th.addTab(tab1);
        th.addTab(tab2);
        th.addTab(tab3);
        th.addTab(tab4);
    }
}
