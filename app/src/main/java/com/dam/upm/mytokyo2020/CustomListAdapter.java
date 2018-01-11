package com.dam.upm.mytokyo2020;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final Integer[] imgid;
    private final String[] listviewShortDescription;

    public CustomListAdapter(Activity context, String[] itemname, Integer[] imgid, String[] listviewShortDescription) {
        super(context, R.layout.list_hotels, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
        this.listviewShortDescription=listviewShortDescription;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_hotels, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView txtDescription = (TextView) rowView.findViewById(R.id.item2);

        txtTitle.setText(itemname[position]);
        imageView.setImageResource(imgid[position]);
        txtDescription.setText(listviewShortDescription[position]);
        return rowView;

    };
}
