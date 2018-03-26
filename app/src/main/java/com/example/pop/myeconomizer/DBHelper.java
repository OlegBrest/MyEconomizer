package com.example.pop.myeconomizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class DBHelper  extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MyeconomizerDb";
    private static final String TABLE_ECONOMIZER = "MyEconomizer";

    private static final String KEY_ID = "_id";
    private static final String KEY_SAVE_NAME = "save_name";
    private static final String KEY_ITEM_SHOP = "item_shop";
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
                + " integer primary key," + KEY_SAVE_NAME + " text,"  + KEY_ITEM_SHOP + " text," + KEY_ITEM_NAME + " text,"  + KEY_ITEM_COST + " real," + KEY_ITEM_VOLUME + " real," + KEY_ITEM_PPI + " real" + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_ECONOMIZER);

        onCreate(db);

    }

    public void SaveArrLst (SQLiteDatabase db,String Save_Name,ArrayList <goods_type> alist)
    {
        int count = alist.size();
        this.DeleteSave(db,Save_Name);
        if (!db.isOpen()) db.beginTransaction();
        for (int i = 0; i < count ; i++)
        {
            ContentValues cv = new ContentValues();
            goods_type dt_row = alist.get(i);
            //cv.put(KEY_ID,i);
            cv.put(KEY_SAVE_NAME,Save_Name);
            cv.put(KEY_ITEM_SHOP,dt_row.getShop());
            cv.put(KEY_ITEM_NAME,dt_row.getName());
            cv.put(KEY_ITEM_COST,dt_row.getCost());
            cv.put(KEY_ITEM_VOLUME,dt_row.getVolume());
            cv.put(KEY_ITEM_PPI,dt_row.getPPI());
            db.insert(TABLE_ECONOMIZER,null,cv);
        }
        db.close();
    }

    public void DeleteSave(SQLiteDatabase db,String Save_Name)
    {
        if (!db.isOpen()) db.beginTransaction();
        db.execSQL("delete from " + TABLE_ECONOMIZER + " where " + KEY_SAVE_NAME + "='"+Save_Name+"'");
        db.close();
    }

    public void LoadArrLst (SQLiteDatabase db,String Save_Name,ArrayList <goods_type> alist)
    {
        Cursor DBreader =  db.query("MyEconomizer", new String[]{KEY_ITEM_NAME,KEY_ITEM_SHOP,KEY_ITEM_COST,KEY_ITEM_VOLUME,KEY_ITEM_PPI},KEY_SAVE_NAME + "='" +Save_Name + "'" ,null,null,null,null);

        if (DBreader != null) {
            if (DBreader.moveToFirst()) {
                do {
                    String Name_load = DBreader.getString(DBreader.getColumnIndex(KEY_ITEM_NAME));
                    String Shop_load = DBreader.getString(DBreader.getColumnIndex(KEY_ITEM_SHOP));
                    double Cost_load = DBreader.getDouble(DBreader.getColumnIndex(KEY_ITEM_COST));
                    double Volume_load = DBreader.getDouble(DBreader.getColumnIndex(KEY_ITEM_VOLUME));
                    double PPI_load = DBreader.getDouble(DBreader.getColumnIndex(KEY_ITEM_PPI));
                    goods_type gt2load = new goods_type();
                    gt2load.setName(Name_load);
                    gt2load.setShop(Shop_load);
                    gt2load.setCost(Cost_load);
                    gt2load.setVolume( Volume_load );
                    gt2load.setPPI(PPI_load);
                    alist.add(gt2load);
                } while (DBreader.moveToNext());
            }
            DBreader.close();
            db.close();
        }
    }

}