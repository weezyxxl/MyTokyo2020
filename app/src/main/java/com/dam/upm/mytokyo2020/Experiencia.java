package com.dam.upm.mytokyo2020;

/**
 * Created by Alfonso on 05/01/2018.
 */

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Alfonso on 24/11/2017.
 */

public class Experiencia {

    private String fecha;
    private String nombre;
    private int idDrawable;
    private int id;
    private URL url;

    public Experiencia(int id, String fecha, String nombre, int idDrawable) {
        this.id=id;
        this.fecha= fecha;
        this.nombre = nombre;
        this.idDrawable = idDrawable;

        try {
            this.url = new URL("https://www.olympic.org/news/make-2020-tokyo-model-for-sustainable-games");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static final List<Experiencia> Experiencias = new ArrayList<Experiencia>();

    static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    static {
        Experiencias.add(new Experiencia(0,"SHINJUKU NIGHTLIFE", "Shinjuku & Northwest Tokyo",
                R.drawable.experiencia_1));
        Experiencias.add(new Experiencia(1,"TSUKIJI MARKET", "Ginza & Tsukiji",
                R.drawable.experiencia_2));
        Experiencias.add(new Experiencia(2,"TOKYO CITYSCAPE", "Shinjuku & Northwest Tokyo",
                R.drawable.experiencia_3));
        Experiencias.add(new Experiencia(3,"SHOPPING IN HARAJUKU", "Harajuku & Aoyama",
                R.drawable.experiencia_4));

        /*Noticias.add(new com.dam.upm.mytokyo2020.Noticia(2017-05-23, "AUSTRALIA’S MR BASEBALL HAS SET HIS SIGHTS ON MORE OLYMPIC GLORY",
                R.drawable.rosca));*/

    }

    public String getFecha() {
        return fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdDrawable() {
        return idDrawable;
    }

    public int getId() {
        return id;
    }



    @Override
    public String toString() {
        return   nombre +
                "\n" + df.format(fecha);
    }
}
