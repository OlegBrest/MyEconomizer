package com.example.pop.myeconomizer;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class saves_activity extends AppCompatActivity implements View.OnClickListener {
    private ListView saves_lv = null;
    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> saves;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saves_layout);
        this.saves = new ArrayList<>();
        this.saves_lv = findViewById(R.id.saves_names_listview);
        this.adapter = new saves_adapter(this, R.layout.list_saves, this.saves);
        this.saves_lv.setAdapter(this.adapter);
        this.dbHelper = new DBHelper(this);
        this.database = this.dbHelper.getWritableDatabase();

        Cursor DBreader =  database.query("MyEconomizer", new String[] {"save_name"},null,null,"save_name",null,null);

        if (DBreader != null) {
            if (DBreader.moveToFirst()) {
                String str;
                do {
                    str = DBreader.getString(DBreader.getColumnIndex("save_name"));
                        saves.add( str);
                } while (DBreader.moveToNext());
            }
            DBreader.close();
            this.database.close();
        }
        this.adapter.notifyDataSetChanged();
        this.saves_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str_to_load = ""+saves_lv.getItemAtPosition(position);
                Intent data = new Intent();
                data.putExtra("Save_Name",str_to_load);
                data.putExtra("state","load");
                setResult(RESULT_OK,data);
                finish();
            }
        });
    }


    @Override
    public void onClick(View v)
    {

    }
}

