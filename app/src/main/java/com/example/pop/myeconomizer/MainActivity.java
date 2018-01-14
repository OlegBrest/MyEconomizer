package com.example.pop.myeconomizer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
class goods_type
{
    private String Name;
    private double Cost;
    private double Volume;

    public goods_type()
    {
        this.Name = "";
        this.Cost = 0.0;
        this.Volume = 0.0;
    }

    public String getName() {return Name;}
    public double getCost() {return Cost;}
    public double getVolume() {return Volume;}

    public void setName (String name) {this.Name =name;}
    public void setCost (double cost) {this.Cost =cost;}
    public void setVolume (double volume) {this.Volume = volume;}
}

public class MainActivity extends AppCompatActivity {



private Button newbutton = null;
private GridView gv = null;

private ArrayAdapter<goods_type> adapter;
private ArrayList<goods_type> goods;
private ArrayList<goods_type> listOfGoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newbutton = (Button) findViewById(R.id.new_bttn);
        listOfGoods = new ArrayList<>();
        gv = (GridView) findViewById(R.id.Goods_GridView);
        adapter = new GoodsListAdapter(this, R.layout.list_items,listOfGoods);
        gv.setAdapter(adapter);
        newbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),SecondActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }
    @Override
    protected void onActivityResult (int requestCode, int resultCode , Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        goods_type gt = new goods_type();
        gt.setName(data.getStringExtra("Name"));
        gt.setCost(data.getDoubleExtra("Cost",1));
        gt.setVolume(data.getDoubleExtra("Volume",1));

        listOfGoods.add (gt);
        adapter.notifyDataSetChanged();

        int count = this.gv.getChildCount();
        for (int i = 0; i < count; i++)
        {
            View cell = this.gv.getChildAt(i);
            TextView cellText = (TextView) cell.findViewById(R.id.txt_name);
            cellText.setTextColor(0);
        }

    }
}
