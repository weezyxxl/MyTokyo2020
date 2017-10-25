package com.dam.upm.mytokyo2020;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by Angel on 25/10/2017.
 */

public class ImageAdapter extends BaseAdapter {

    private int id;
    private Context mContext;

    public ImageAdapter(Context c, int id) {
        this.mContext = c;
        this.id = id;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if (view == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) view;
        }
        if(this.id == 1){
            imageView.setImageResource(imagenes_1[position]);
        }
        if(this.id == 2){
            imageView.setImageResource(imagenes_2[position]);
        }
        if(this.id == 3){
            imageView.setImageResource(imagenes_3[position]);
        }
        return imageView;

    }

    //referencia a las imagenes
    private Integer[]imagenes_1 = {
            R.drawable.ic_launcher_archery_web,R.drawable.ic_launcher_atletics_web,
            R.drawable.ic_launcher_basket_web,R.drawable.ic_launcher_beachvolley_web};
    private Integer[]imagenes_2 = {
            R.drawable.ic_launcher_archery_web,R.drawable.ic_launcher_atletics_web,
            R.drawable.ic_launcher_basket_web,R.drawable.ic_launcher_beachvolley_web};
    private Integer[]imagenes_3 = {
            R.drawable.ic_launcher_archery_web,R.drawable.ic_launcher_atletics_web,
            R.drawable.ic_launcher_basket_web,R.drawable.ic_launcher_beachvolley_web};
}
