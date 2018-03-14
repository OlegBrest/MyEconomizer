package com.example.pop.myeconomizer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
class goods_type
{
    private String Name;
    private String Shop;
    private double Cost;
    private double Volume;
    private double PPI;

    goods_type()
    {
        this.Name = "";
        this.Shop = "";
        this.Cost = 0.0;
        this.Volume = 0.0;
        this.PPI = 0;
    }

    String getName() {return this.Name;}
    String getShop() {return this.Shop;}
    double getCost() {return this.Cost;}
    double getVolume() {return this.Volume;}
    double getPPI() {return this.PPI;}

    void setName (String name) {this.Name =name;}
    void setShop (String shop) {this.Shop =shop;}
    void setCost (double cost) {this.Cost =cost;}
    void setVolume (double volume) {this.Volume = volume;}
    void setPPI (double ppi) {this.PPI = ppi;}
}

public class MainActivity extends AppCompatActivity {


private Button newbutton = null;
private SwipeMenuListView lv = null;
private DBHelper dbHelper;
private SQLiteDatabase database;
private TextView save_name_txtbx = null;
private Button save_name_bttn = null;
private Button cancel_save_bttn = null;

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
            String [] Shop_load ;
            double [] Cost_load ;
            double [] Volume_load ;
            Name_load = savedInstanceState.getStringArray("NameArray");
            Shop_load = savedInstanceState.getStringArray("ShopArray");
            Cost_load = savedInstanceState.getDoubleArray("CostArray");
            Volume_load = savedInstanceState.getDoubleArray("VolumeArray");
            for (int i = 0; i < count ; i++) {
                goods_type gt2load = new goods_type();
                gt2load.setName(Name_load != null ? Name_load[i] : null);
                gt2load.setShop(Shop_load != null ? Shop_load[i] : null);
                gt2load.setCost(Cost_load != null ? Cost_load[i] : 0);
                gt2load.setVolume(Volume_load != null ? Volume_load[i] : 0);
                if ((Volume_load == null) || (Volume_load[i] == 0)) {
                    gt2load.setPPI(0);
                }
                else {
                    gt2load.setPPI(Cost_load[i]/Volume_load[i]);
                }
                this.listOfGoods.add(gt2load);
            }
        }
        this.newbutton = findViewById(R.id.new_bttn);
        this.lv = findViewById(R.id.listview_goods);
        this.lv.setMenuCreator(creator);
        this.save_name_txtbx = findViewById(R.id.save_name_txtbx);
        this.save_name_bttn = findViewById(R.id.save_name_bttn);
        this.cancel_save_bttn = findViewById(R.id.cancel_name_bttn);

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
        this.dbHelper = new DBHelper(this);
        //this.database = this.dbHelper.getWritableDatabase();

        this.save_name_bttn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_name_txtbx.setVisibility(View.INVISIBLE);
                save_name_bttn.setVisibility(View.INVISIBLE);
                cancel_save_bttn.setVisibility(View.INVISIBLE);
                lv.setVisibility(View.VISIBLE);
                newbutton.setVisibility(View.VISIBLE);
                database = dbHelper.getWritableDatabase();
                dbHelper.SaveArrLst(database,save_name_txtbx.getText().toString(), listOfGoods);
            }
        });

        this.cancel_save_bttn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_name_txtbx.setVisibility(View.INVISIBLE);
                save_name_bttn.setVisibility(View.INVISIBLE);
                cancel_save_bttn.setVisibility(View.INVISIBLE);
                lv.setVisibility(View.VISIBLE);
                newbutton.setVisibility(View.VISIBLE);
            }
        });

        this.lv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // edit
                        goods_type selected_pos = listOfGoods.get(position);
                        Intent intent= new Intent(getApplicationContext(),SecondActivity.class);
                        intent.putExtra("Shop",selected_pos.getShop());
                        intent.putExtra("Name",selected_pos.getName());
                        intent.putExtra("Cost",selected_pos.getCost());
                        intent.putExtra("Volume",selected_pos.getVolume());
                        intent.putExtra("Position",position);
                        startActivityForResult(intent,1 );
                        break;
                    case 1:
                        // delete
                        listOfGoods.remove(position);
                        adapter.notifyDataSetChanged();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }


    SwipeMenuCreator creator = new SwipeMenuCreator() {

        @Override
        public void create(SwipeMenu menu) {
            // create "open" item
            SwipeMenuItem editItem = new SwipeMenuItem(
                    getApplicationContext());
            // set item background
            editItem.setBackground(new ColorDrawable(Color.rgb(0xa0, 0xa0,
                    0xaE)));
            // set item width
            editItem.setWidth(170);
            // set item title
            editItem.setTitle("Edit");
            //editItem.setIcon(R.drawable.ic_edit_item);
            // set item title fontsize
            editItem.setTitleSize(18);
            // set item title font color
            editItem.setTitleColor(Color.WHITE);
            // add to menu
            menu.addMenuItem(editItem);

            // create "delete" item
            SwipeMenuItem deleteItem = new SwipeMenuItem(
                    getApplicationContext());
            // set item background
            deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                    0x3F, 0x25)));
            // set item width
            deleteItem.setWidth(170);
            // set a icon
            deleteItem.setIcon(R.drawable.ic_delete_item);
            // add to menu
            menu.addMenuItem(deleteItem);
        }
    };




    @Override
    protected void onActivityResult (int requestCode, int resultCode , Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        Log.d("MA","onActivityResult");
        if (data != null) {
            String state = "";
            state = data.getStringExtra("state");
            Log.d("MA","state = " + state);
            if (state.equals("load"))
            {
                String save_name = data.getStringExtra("Save_Name");
                this.database = dbHelper.getWritableDatabase();
                dbHelper.LoadArrLst(database,save_name, this.listOfGoods);
                Log.d("MA","Loaded = " + save_name);
            }
            else {
                goods_type gt = new goods_type();
                int position = data.getIntExtra("Position", -1);
                gt.setName(data.getStringExtra("Name"));
                gt.setShop(data.getStringExtra("Shop"));
                gt.setCost(data.getDoubleExtra("Cost", 1));
                gt.setVolume(data.getDoubleExtra("Volume", 1));
                gt.setPPI(data.getDoubleExtra("Cost", 0) / data.getDoubleExtra("Volume", 1));


                if (position == -1) {
                    this.listOfGoods.add(gt);
                } else {
                    this.listOfGoods.set(position, gt);
                }
            }
            this.adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int count = this.listOfGoods.size();
        String [] Name_save = new String[count];
        String [] Shop_save = new String[count];
        double [] Cost_save = new double[count];
        double [] Volume_save = new double[count];
        double [] PPI_save = new double[count];
        for (int i = 0; i < count ; i++)
        {
            Name_save[i] = this.listOfGoods.get(i).getName();
            Shop_save[i] = this.listOfGoods.get(i).getShop();
            Cost_save[i] = this.listOfGoods.get(i).getCost();
            Volume_save[i] = this.listOfGoods.get(i).getVolume();
            PPI_save[i] = this.listOfGoods.get(i).getPPI();
        }
        outState.putStringArray("NameArray",Name_save);
        outState.putStringArray("ShopArray",Shop_save);
        outState.putDoubleArray("CostArray",Cost_save);
        outState.putDoubleArray("VolumeArray",Volume_save);
        outState.putDoubleArray("PPIArray",PPI_save);
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

        if (id == R.id.menu_save)
        {
            this.save_name_bttn.setVisibility(View.VISIBLE);
            this.save_name_txtbx.setVisibility(View.VISIBLE);
            this.cancel_save_bttn.setVisibility(View.VISIBLE);
            this.lv.setVisibility(View.INVISIBLE);
            this.newbutton.setVisibility(View.INVISIBLE);
        }

        if (id == R.id.menu_load)
        {
            Intent intent= new Intent(getApplicationContext(),saves_activity.class);
            startActivityForResult(intent,1);

        }
        return super.onOptionsItemSelected(item);
    }
}

