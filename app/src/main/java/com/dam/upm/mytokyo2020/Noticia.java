package com.dam.upm.mytokyo2020;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Alfonso on 24/11/2017.
 */

public class Noticia {

    private Date fecha;
    private String nombre;
    private int idDrawable;
    private URL url;

    public Noticia(Date fecha, String nombre, int idDrawable) {
        this.fecha= fecha;
        this.nombre = nombre;
        this.idDrawable = idDrawable;
        try {
            this.url = new URL("https://www.olympic.org/news/make-2020-tokyo-model-for-sustainable-games");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static final List<Noticia> Noticias = new ArrayList<Noticia>();


    static {
        Noticias.add(new Noticia(new Date(), "MAKE 2020 TOKYO MODEL FOR SUSTAINABLE GAMES",
                R.mipmap.foto1));
        /*Noticias.add(new com.dam.upm.mytokyo2020.Noticia(2017-05-23, "AUSTRALIA’S MR BASEBALL HAS SET HIS SIGHTS ON MORE OLYMPIC GLORY",
                R.drawable.rosca));*/

    }

    public Date getFecha() {
        return fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdDrawable() {
        return idDrawable;
    }

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    public String toString() {
        return   nombre +
                "\n" + df.format(fecha);
    }
}


