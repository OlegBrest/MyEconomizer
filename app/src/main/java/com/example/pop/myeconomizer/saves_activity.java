package com.example.pop.myeconomizer;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;


public class saves_activity extends AppCompatActivity implements View.OnClickListener {
    private SwipeMenuListView saves_lv = null;
    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> saves;
    private TableRow tabrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saves_layout);
        this.saves = new ArrayList<>();
        this.saves_lv = findViewById(R.id.saves_names_listview);
        this.tabrow = findViewById(R.id.header_tabrow);
        this.saves_lv.setMenuCreator(creator);
        this.adapter = new saves_adapter(this, R.layout.list_saves, this.saves);
        this.saves_lv.setAdapter(this.adapter);
        this.dbHelper = new DBHelper(this);
        this.database = this.dbHelper.getWritableDatabase();

        Cursor DBreader = this.database.query("MyEconomizer", new String[]{"save_name"}, null, null, "save_name", null, null);

        if (DBreader != null) {
            if (DBreader.moveToFirst()) {
                String str;
                do {
                    str = DBreader.getString(DBreader.getColumnIndex("save_name"));
                    saves.add(str);
                } while (DBreader.moveToNext());
            }
            DBreader.close();
            this.database.close();
        }
        this.adapter.notifyDataSetChanged();
        this.saves_lv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // load
                        String str_to_load = "" + saves_lv.getItemAtPosition(position);
                        Intent data = new Intent();
                        data.putExtra("Save_Name", str_to_load);
                        data.putExtra("state", "load");
                        setResult(RESULT_OK, data);
                        finish();
                        break;
                    case 1:
                        // delete
                        String str_to_delete= "" + saves_lv.getItemAtPosition(position);
                        database = dbHelper.getWritableDatabase();
                        dbHelper.DeleteSave(database,str_to_delete,true);
                        saves.remove(position);
                        adapter.notifyDataSetChanged();
                        break;
                }
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
            editItem.setTitle("Load");
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
    public void onClick(View v)
    {
       Log.d("135","Saves on click header") ;
    }
}

