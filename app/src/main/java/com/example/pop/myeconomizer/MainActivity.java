package com.example.pop.myeconomizer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
class goods_type
{
    private String Name;
    private double Cost;
    private double Volume;

    goods_type()
    {
        this.Name = "";
        this.Cost = 0.0;
        this.Volume = 0.0;
    }

    String getName() {return this.Name;}
    double getCost() {return this.Cost;}
    double getVolume() {return this.Volume;}

    void setName (String name) {this.Name =name;}
    void setCost (double cost) {this.Cost =cost;}
    void setVolume (double volume) {this.Volume = volume;}
}

public class MainActivity extends AppCompatActivity {


private Button newbutton = null;
private ListView lv = null;
private DBHelper dbHelper;

private ArrayAdapter<goods_type> adapter;
private ArrayList<goods_type> listOfGoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.listOfGoods = new ArrayList<>();

        if (savedInstanceState != null)
        {

            int count = savedInstanceState.getInt("Size");

            String [] Name_load ;
            double [] Cost_load ;
            double [] Volume_load ;
            Name_load = savedInstanceState.getStringArray("NameArray");
            Cost_load = savedInstanceState.getDoubleArray("CostArray");
            Volume_load = savedInstanceState.getDoubleArray("VolumeArray");
            for (int i = 0; i < count ; i++) {
                goods_type gt2load = new goods_type();
                gt2load.setName(Name_load != null ? Name_load[i] : null);
                gt2load.setCost(Cost_load != null ? Cost_load[i] : 0);
                gt2load.setVolume(Volume_load != null ? Volume_load[i] : 0);
                this.listOfGoods.add(gt2load);
            }
        }
        this.newbutton = findViewById(R.id.new_bttn);
        this.lv = findViewById(R.id.listview_goods);
        @SuppressLint("InflateParams") View viewHeader = getLayoutInflater().inflate(R.layout.header,null);
        this.lv.addHeaderView(viewHeader);
        this.adapter = new GoodsListAdapter(this, R.layout.list_items,this.listOfGoods);
        this.lv.setAdapter(this.adapter);
        this.newbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),SecondActivity.class);
                startActivityForResult(intent,1);
            }
        });
        dbHelper = new DBHelper(this);
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
        this.adapter.notifyDataSetChanged();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int count = this.listOfGoods.size();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_clear)
        {
            this.listOfGoods.clear();
            this.adapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }
}

