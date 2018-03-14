package com.example.pop.myeconomizer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class saves_adapter extends ArrayAdapter<String> {
    private ArrayList<String> listOfSaves = null;

    saves_adapter(Context context, int resource, ArrayList<String> objects)
    {
        super(context, resource,objects);
        this.listOfSaves = objects;
    }
    @Override
    public int getCount(){return  this.listOfSaves.size();}

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView (int position , View convertView, @NonNull ViewGroup parent)
    {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_saves, null);
        TextView textSave = convertView.findViewById(R.id.save_name_txtbx);
        String item_save = this.listOfSaves.get(position);
        textSave.setText(item_save);
        return convertView;
    }

}
