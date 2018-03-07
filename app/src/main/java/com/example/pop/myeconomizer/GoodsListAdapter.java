package com.example.pop.myeconomizer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GoodsListAdapter extends ArrayAdapter<goods_type>
{
    private ArrayList<goods_type> listOfGoods = null;

    GoodsListAdapter(Context context, int resource, ArrayList<goods_type> objects)
    {
        super(context, resource,objects);
        this.listOfGoods = objects;
    }

    @Override
    public int getCount(){return  this.listOfGoods.size();}

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView (int position , View convertView, @NonNull ViewGroup parent)
    {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_items, null);
        TextView textName = convertView.findViewById(R.id.txt_name);
        TextView textShop = convertView.findViewById(R.id.txt_shop);
        TextView textCost = convertView.findViewById(R.id.txt_cost);
        TextView textVolume = convertView.findViewById(R.id.txt_volume);
        TextView textPPI = convertView.findViewById(R.id.txt_per_item);
        goods_type item_good = this.listOfGoods.get(position);
        textName.setText(item_good.getName());
        textShop.setText(item_good.getShop());
        textCost.setText(new DecimalFormat("#.#####").format(item_good.getCost()));
        textVolume.setText(new DecimalFormat("#.#####").format(item_good.getVolume()));
        textPPI.setText(new DecimalFormat("#.#####").format(item_good.getCost()/item_good.getVolume()));
        return convertView;
    }
}
