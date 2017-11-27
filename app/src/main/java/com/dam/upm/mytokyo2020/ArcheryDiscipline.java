package com.dam.upm.mytokyo2020;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

public class ArcheryDiscipline extends AppCompatActivity {
    TabHost th;
    TextView disciplinaName;
    Bundle extras;

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

        tab1.setIndicator("Events");
        tab1.setContent(R.id.Eventos);

        tab2.setIndicator("News");
        tab2.setContent(R.id.Noticias);

        tab3.setIndicator("Results");
        tab3.setContent(R.id.Resultados);

        th.addTab(tab1);
        th.addTab(tab2);
        th.addTab(tab3);
    }
}
