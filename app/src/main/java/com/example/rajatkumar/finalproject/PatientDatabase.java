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
    public static final String DATABASE_NAME = "PatientDatabase";
    public static int VERSION_NUM = 1;
    public static final String TABLE_NAME = "PatientRecord";
    public static final String KEY_ID= "Patient_id";
    public static final String KEY_name = "Full_Name";
    public static final String KEY_DOB = "Date_of_Birth";
    public static final String KEY_Address = "Address";
    public static final String KEY_card = "HealthCard";
    public static final String KEY_phone = "Phone";
    public static final String KEY_DoctorType = "DoctorType";
    public PatientDatabase(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CHAT_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_name + " TEXT ,"+KEY_DOB+"Date,"+KEY_Address+"Text,"+KEY_card+"Integer,"
                +KEY_phone+"Integer,"+KEY_DoctorType+"Text"+");";

        db.execSQL(CREATE_CHAT_TABLE);
    }

    public void insertData(String name,String dob,String address,String card,String phone,String type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_name, name);
        contentValues.put(KEY_DOB, dob);
        contentValues.put(KEY_Address, address);
        contentValues.put(KEY_card, card);
        contentValues.put(KEY_phone, phone);
        contentValues.put(KEY_DoctorType, type);
        long insertResult = db.insert(TABLE_NAME, null, contentValues);
        Log.i("ChatDatabaseHelper", "insert data result: " + insertResult );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int l) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        Log.i("ChatDatabaseHelper", "onUpdate version " + i +" to new database version: " +  l );
        onCreate(db);

    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return cursor;
    }
}


