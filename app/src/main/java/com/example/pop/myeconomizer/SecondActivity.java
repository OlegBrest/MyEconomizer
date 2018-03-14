package com.example.pop.myeconomizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener{
private EditText Name_txt = null;
private EditText Shop_txt = null;
private EditText Cost_txt = null;
private EditText Volume_txt = null;
private Button addbutton = null;
private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        this.addbutton =  findViewById(R.id.ok_bttn);
        Name_txt = findViewById(R.id.txtbx_name);
        Shop_txt =  findViewById(R.id.txtbx_shop);
        Cost_txt =  findViewById(R.id.txtbx_cost);
        Volume_txt =  findViewById(R.id.txtbx_volume);
        Intent intent = getIntent();
        position = intent.getIntExtra("Position",-1);
        if (position!=-1)
        {
            Shop_txt.setText(intent.getStringExtra("Shop"));
            Name_txt.setText(intent.getStringExtra("Name"));
            Cost_txt.setText(String.valueOf(intent.getDoubleExtra("Cost",1)));
            Volume_txt.setText(String.valueOf(intent.getDoubleExtra("Volume",1)));
            addbutton.setText(R.string.edit);
        }
        this.addbutton.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("Name",Name_txt.getText().toString());
                data.putExtra("Shop",Shop_txt.getText().toString());
                data.putExtra("Cost",Double.parseDouble(Cost_txt.getText().toString()));
                data.putExtra("Volume",Double.parseDouble(Volume_txt.getText().toString()));
                data.putExtra("state","edit_add");
                if (position!=-1) data.putExtra("Position", position);
                setResult(RESULT_OK,data);
                finish();
            }
        });
        Log.d("SecondActiv","onCreate");
    }

    @Override
    public void onClick(View v) {

    }
}
