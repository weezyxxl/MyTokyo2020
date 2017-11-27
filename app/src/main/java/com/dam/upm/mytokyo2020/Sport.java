package com.dam.upm.mytokyo2020;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alfonso on 26/11/2017.
 */

public class Sport {

    private String nombre;
    private int idDrawable;
    private int id;
    private URL url;

    public Sport(int id, String nombre, int idDrawable) {
        this.id=id;
        this.nombre = nombre;
        this.idDrawable = idDrawable;

        try {
            this.url = new URL("https://www.olympic.org/news/make-2020-tokyo-model-for-sustainable-games");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static final List<Sport> SPORTS = new ArrayList<Sport>();

    static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    static {
        SPORTS.add(new Sport(0,"Archery",
                R.drawable.arqueria));
        SPORTS.add(new Sport(1,"Athletics",
                R.drawable.atletismo));
        SPORTS.add(new Sport(2,"Basketball",
                R.drawable.basketball));
        SPORTS.add(new Sport(3,"Football",
                R.drawable.football));
        SPORTS.add(new Sport(4,"Tennis",
                R.drawable.tennis));


        /*Noticias.add(new com.dam.upm.mytokyo2020.Noticia(2017-05-23, "AUSTRALIA’S MR BASEBALL HAS SET HIS SIGHTS ON MORE OLYMPIC GLORY",
                R.drawable.rosca));*/

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
}
