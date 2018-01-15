package com.example.pop.myeconomizer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    public String getName() {return this.Name;}
    public double getCost() {return this.Cost;}
    public double getVolume() {return this.Volume;}

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
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putDouble("myDouble", 1.9);
        savedInstanceState.putInt("MyInt", 1);
        savedInstanceState.putString("MyString", "Welcome back to Android");
        super.onSaveInstanceState(savedInstanceState);
        Log.d("MainActiv","onSave");
    }

        @Override
        public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
            // This bundle has also been passed to onCreate.
            boolean myBoolean = savedInstanceState.getBoolean("MyBoolean");
            double myDouble = savedInstanceState.getDouble("myDouble");
            int myInt = savedInstanceState.getInt("MyInt");
            String myString = savedInstanceState.getString("MyString");
            Log.d("MainActiv","onRestore");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            newbutton = (Button) findViewById(R.id.new_bttn);
            listOfGoods = new ArrayList<>();
            gv = (GridView) findViewById(R.id.Goods_GridView);
            adapter = new GoodsListAdapter(this, R.layout.list_items, listOfGoods);
            gv.setAdapter(adapter);
            newbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                    startActivityForResult(intent, 1);
                }
            });
        Log.d("MainActiv","onCreate");
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
        Log.d("MainActiv","onActResult");
    }
}
