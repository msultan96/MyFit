package com.myfit.brownies.myfit;

/**
 * Created by essamyousry on 11/17/17.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;


public class CustomAdapter extends BaseAdapter {
    Calendar calendar;
    private Context context; //context
    private ChainedHashTable<Food> items; //data source of the list adapter
    ArrayList<Food> currentItems = null;

    //public constructor
    public CustomAdapter(Context context, ChainedHashTable<Food> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int i) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.list_item, parent, false);
        }

        // get current item to be displayed
        //ArrayList<Food> currentItems = items.dumpData1(position);

        // get the TextView for item name and item description
        TextView textViewItemName = (TextView)
                convertView.findViewById(R.id.name);
        TextView textViewItemDescription = (TextView)
                convertView.findViewById(R.id.calories);

        //sets the text for item name and item description from the current item object

        calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        textViewItemName.setText(items.dumpData1(day).getIName());
        textViewItemDescription.setText(items.dumpData1(day).getCalories());

        // returns the view for the current row
        return convertView;
    }
}
