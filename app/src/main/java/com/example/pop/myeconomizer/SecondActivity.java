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
private EditText Cost_txt = null;
private EditText Volume_txt = null;
private Button addbutton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        addbutton = (Button) findViewById(R.id.ok_bttn);
        Name_txt = (EditText) findViewById(R.id.txtbx_name);
        Cost_txt = (EditText) findViewById(R.id.txtbx_cost);
        Volume_txt = (EditText) findViewById(R.id.txtbx_volume);
        addbutton.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("Name",Name_txt.getText().toString());
                data.putExtra("Cost",Double.parseDouble(Cost_txt.getText().toString()));
                data.putExtra("Volume",Double.parseDouble(Volume_txt.getText().toString()));
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
