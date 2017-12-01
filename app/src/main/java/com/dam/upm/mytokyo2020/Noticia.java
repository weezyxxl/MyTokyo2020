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

    private String fecha;
    private String nombre;
    private int idDrawable;
    private int id;
    private URL url;

    public Noticia(int id, String fecha, String nombre, int idDrawable) {
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

    public static final List<Noticia> Noticias = new ArrayList<Noticia>();

    static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    static {
        Noticias.add(new Noticia(0,"26 NOV 2017", "SOFTBALL IS ‘BACK WHERE IT BELONGS’ ACCORDING TO CANADA COACH MARK SMITH",
                R.drawable.foto_noticia1));
        Noticias.add(new Noticia(1,"25 NOV 2017", "FANNING TARGETS OLYMPIC GAMES WITH SURFING READY TO TEAR UP TOKYO",
                R.drawable.foto_noticia2));
        Noticias.add(new Noticia(2,"24 NOV 2017", "AFRICA’S LONE MLB STAR SWINGING TO INSPIRE WHOLE CONTINENT IN TOKYO",
                R.drawable.foto_noticia3));
        Noticias.add(new Noticia(3,"24 NOV 2017", "SOFTBALL’S GREATEST OLYMPIAN BERG TARGETS GOLD FOR THE NEXT GENERATION",
                R.drawable.foto_noticia4));
        Noticias.add(new Noticia(4,"23 NOV 2017", "YOG STAR JADE JONES AIMING TO BECOME TAEKWONDO’S FIRST TRIPLE OLYMPIC CHAMPION",
                R.drawable.foto_noticia5));
        Noticias.add(new Noticia(5,"22 NOV 2017", "TEENAGE BMX FREESTYLER ROBERTS READY TO TAKE OLYMPIC STAGE BY STORM",
                R.drawable.foto_noticia6));
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


