package com.example.rajatkumar.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by khushpreetsingh on 2018-03-28.
 */

public class RoutesDatabase extends SQLiteOpenHelper {
    public static final int VERSION_NUM = 1;
    public static final String DATABASE_NAME= "rout.db";
    public static final String TABLE_NAME= "routes";
    public final static String KEY_ID="id_key";
    public final static String KEY_ROUTES="routes";

    public RoutesDatabase (Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "CREATE TABLE " + TABLE_NAME + " ( "+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +KEY_ROUTES+" text);" );
        Log.i("RoutesDatabase", "Calling onCreate");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);  //make a new database
        Log.i("RoutesDatabase", "Calling onUpgrade, oldVersion=" + oldVer + " newVersion=" + newVer);
    }
}
