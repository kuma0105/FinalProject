package com.example.rajatkumar.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Vaibhav jain on 2018-03-23.
 */

public class PatientDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "PatientDatabase.db";
    public static int VERSION_NUM = 7;
    public static final String TABLE_NAME = "PatientRecord";
    public static final String Name_Column = "Full_Name";
    public static final String DATE_OF_BIRTH = "Date_of_Birth";
    public static final String ADDRESS = "ADDRESS";
    public static final String KEY_card = "card";
    public static final String KEY_phone = "phone";
    public static final String KEY_Doctor_Type = "Doctor_Type";
    public static final String KEY_Description = "Description";
    public static final String KEY_PREVIOUS_SURGERIES = "PREVIOUS_SURGERIES";
    public static final String KEY_Allergies = "Allergies";
    public static final String KEY_Glass_Bought = "Glass_Bought";
    public static final String KEY_Glass_Store = "Glass_Store";
    public static final String KEY_Benefits = "Benefits";
    public static final String KEY_Had_Braces = "Had_Braces";


    public PatientDatabase(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CHAT_TABLE = "CREATE TABLE " + TABLE_NAME + " ( _id  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Name_Column + " text, "+ DATE_OF_BIRTH +" Date, "+ ADDRESS +" Text, "+KEY_card+" Integer, "
                +KEY_phone+" Integer, "+KEY_Doctor_Type+" Text, "+KEY_Description+" Text, "+KEY_PREVIOUS_SURGERIES+" Text, "+
                KEY_Allergies+" Text, "+KEY_Benefits+" Text, "+KEY_Had_Braces+" Text, "+KEY_Glass_Bought+" Text, "+KEY_Glass_Store+" Text "+ ");";
        db.execSQL(CREATE_CHAT_TABLE);
    }

    public void insertData(String type,String name,String address,String dob,String phone,String card,String description, String previousSurgeries, String allergies,String glasses_bought, String glasses_store, String benefits, String braces){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Name_Column, name);
        contentValues.put(DATE_OF_BIRTH, dob);
        contentValues.put(ADDRESS, address);
        contentValues.put(KEY_card, card);
        contentValues.put(KEY_phone, phone);
        contentValues.put(KEY_Doctor_Type, type);
        contentValues.put(KEY_Description,description);
        contentValues.put(KEY_PREVIOUS_SURGERIES, previousSurgeries);
        contentValues.put(KEY_Allergies, allergies);
        contentValues.put(KEY_Benefits, benefits);
        contentValues.put(KEY_Had_Braces, braces);
        contentValues.put(KEY_Glass_Bought, glasses_bought);
        contentValues.put(KEY_Glass_Store, glasses_store);
        db.insert(TABLE_NAME, null, contentValues);
PatientMain.notifyData();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int l) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        Log.i("ChatDatabaseHelper", "onUpdate version " + i +" to new database version: " +  l );
        onCreate(db);

    }
    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer) // newVer < oldVer
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME ); //delete any existing data
        onCreate(db);  //make a new database
    }

    public void onOpen(SQLiteDatabase db) //always gets called
    {
        Log.i("DATABASE", "Database opened");
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return cursor;
    }
}


