package com.astrosei.lokaverkefni;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by astrosei on 01/04/2017.
 * Used to create the listView for CategoriesActivity
 */

public class CategoriesAdapter extends BaseAdapter {

    private Context context;
    private String [] categories;
    private int[] images;


    public CategoriesAdapter(Context context, String[] categories, int[] images){

        this.context = context;
        this.categories = categories;
        this.images = images;
    }

    @Override
    public int getCount() {
        return categories.length;
    }

    @Override
    public String getItem(int position) {
        return categories[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final String categories = (String)getItem(position);

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.categories_item_layout, null);
        }
        TextView listCatagories = (TextView)convertView.findViewById(R.id.textView_category);
        listCatagories.setText(categories);

        ImageView listImages = (ImageView)convertView.findViewById(R.id.imageView_category);
        listImages.setImageResource(images[position]);
        return  convertView;
    }
}
