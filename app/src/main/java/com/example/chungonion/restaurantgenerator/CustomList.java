package com.example.chungonion.restaurantgenerator;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] restaurants;

    public CustomList(Activity context, String[] restaurants) {
        super(context, R.layout.list_single, restaurants);
        this.context = context;
        this.restaurants=restaurants;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_single, null, true);
        TextView txtContent = (TextView) rowView.findViewById(R.id.txt); //Initialise
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img); //Initialise
        txtContent.setText(restaurants[position]);
        imageView.setImageResource(android.R.drawable.ic_delete);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return rowView;
    }
}
