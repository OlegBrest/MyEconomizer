package com.example.pop.myeconomizer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper  extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MyeconomizerDb";
    private static final String TABLE_ECONOMIZER = "MyEconomizer";

    private static final String KEY_ID = "_id";
    private static final String KEY_SAVE_NAME = "save_name";
    private static final String KEY_ITEM_NAME = "item_name";
    private static final String KEY_ITEM_COST = "item_cost";
    private static final String KEY_ITEM_VOLUME = "item_volume";
    private static final String KEY_ITEM_PPI = "item_price_per_item";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_ECONOMIZER + "(" + KEY_ID
                + " integer primary key," + KEY_SAVE_NAME + " text," + KEY_ITEM_NAME + " text,"  + KEY_ITEM_COST + " real," + KEY_ITEM_VOLUME + " real," + KEY_ITEM_PPI + " real" + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_ECONOMIZER);

        onCreate(db);

    }
}