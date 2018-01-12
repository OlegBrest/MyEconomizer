package com.example.pop.myeconomizer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GoodsListAdapter extends ArrayAdapter<goods_type>
{
    private ArrayList<goods_type> listOfGoods = null;

    public GoodsListAdapter(Context context, int resource , ArrayList<goods_type> objects)
    {
        super(context, resource,objects);
        listOfGoods = objects;
    }

    @Override
    public int getCount(){return  listOfGoods.size();}

    @Override
    public View getView (int position , View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_items,null);
        }
        TextView textName = (TextView) convertView.findViewById(R.id.txt_name);
        TextView textCost = (TextView) convertView.findViewById(R.id.txt_cost);
        TextView textVolume = (TextView) convertView.findViewById(R.id.txt_volume);
        TextView textPPI = (TextView) convertView.findViewById(R.id.txt_per_item);
        goods_type item_good = listOfGoods.get(position);
        textName.setText(item_good.getName().toString());
        textCost.setText(String.format("%f",item_good.getCost()));
        textVolume.setText(String.format("%f",item_good.getVolume()));
        textPPI.setText(String.format("%f",(item_good.getCost()/item_good.getVolume())));
        return convertView;
    }
}
