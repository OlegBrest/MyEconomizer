package com.example.pop.myeconomizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.listOfGoods = new ArrayList<>();
        if (savedInstanceState != null)
        {

            int count = savedInstanceState.getInt("Size");
            goods_type gt2load = new goods_type();
            String [] Name_load = new String[count];
            double [] Cost_load = new double[count];
            double [] Volume_load = new double[count];
            Name_load = savedInstanceState.getStringArray("NameArray");
            Cost_load = savedInstanceState.getDoubleArray("CostArray");
            Volume_load = savedInstanceState.getDoubleArray("VolumeArray");
            for (int i = 0; i < count ; i++) {
                gt2load.setName(Name_load[i]);
                gt2load.setCost(Cost_load[i]);
                gt2load.setVolume(Volume_load[i]);
                this.listOfGoods.add(gt2load);
            }
        }
        newbutton = (Button) findViewById(R.id.new_bttn);
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

        this.listOfGoods.add (gt);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int count = this.listOfGoods.toArray().length;
        String [] Name_save = new String[count];
        double [] Cost_save = new double[count];
        double [] Volume_save = new double[count];

        for (int i = 0; i < count ; i++)
        {
            Name_save[i] = this.listOfGoods.get(i).getName();
            Cost_save[i] = this.listOfGoods.get(i).getCost();
            Volume_save[i] = this.listOfGoods.get(i).getVolume();
        }
        outState.putStringArray("NameArray",Name_save);
        outState.putDoubleArray("CostArray",Cost_save);
        outState.putDoubleArray("VolumeArray",Volume_save);
        outState.putInt("Size",count);
    }



}
