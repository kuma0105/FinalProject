package com.example.rajatkumar.finalproject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.rajatkumar.finalproject.MainActivity.ACTIVITY_NAME;

public class OCTranspo extends Activity {
ArrayList<String> stops = new ArrayList();
    Cursor cursor;
     Snackbar snackbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_octranspo);
        EditText enter_stop = (EditText) findViewById(R.id.stops_xml_edit);
        Button addButton = (Button) findViewById(R.id.stops_button);
        ListView stops_view = (ListView) findViewById(R.id.stops_xml);
        Stopadapter stopadapter = new Stopadapter(this);
        stops_view.setAdapter(stopadapter);

        RoutesDatabase myOpener = new RoutesDatabase(this);
        SQLiteDatabase db = myOpener.getWritableDatabase();

       Cursor cursor= db.query(true, RoutesDatabase.TABLE_NAME,
               new String[] { RoutesDatabase.KEY_ID, RoutesDatabase.KEY_ROUTES},
               RoutesDatabase.KEY_ROUTES + " Not Null" , null, null, null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast() ) {

            stops.add(cursor.getString(cursor.getColumnIndex(RoutesDatabase.KEY_ROUTES)));
            stopadapter.notifyDataSetChanged(); //this restarts the process of getCount() & getView()

            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(RoutesDatabase.KEY_ROUTES)));
            cursor.moveToNext();



        }
        Log.i(ACTIVITY_NAME, "Cursor's  column count =" + cursor.getColumnCount() );

        stops_view.setAdapter(stopadapter);

        //stops_view.setOnItemClickListener(this::onItemClick);
        addButton.setOnClickListener((View e) -> {

            stops.add(enter_stop.getText().toString());


            ContentValues newData = new ContentValues();

            newData.put(RoutesDatabase.KEY_ROUTES, enter_stop.getText().toString());

            db.insert(RoutesDatabase.TABLE_NAME, null, newData);

            stopadapter.notifyDataSetChanged(); //this restarts the process of getCount() & getView()
            enter_stop.setText("");
            stopadapter.notifyDataSetChanged(); //this restarts the process of getCount() & getView()
            enter_stop.setText("");
            //CharSequence text  = "Searching.....";
//            snackbar.make((findViewById(R.id.stops_button)), " snack bar", Snackbar.LENGTH_SHORT);
//            snackbar.show();
           // Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();

        });


        stops_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

              String s =  stops_view.getItemAtPosition(position).toString();
//

                Log.i(ACTIVITY_NAME, "stop number =" +s );

                Intent intent = new Intent(OCTranspo.this, OCTranspoRoutes.class);
               // intent.putExtra("CountryName", stops_view.getItemAtPosition(position).toString());

                intent.putExtra("key",s);

                startActivity(intent);
            }
        });

    }
    private class Stopadapter extends ArrayAdapter<String> {

             public Stopadapter(Context ctx){
                                 super(ctx, 0);

                             }



           public int getCount() {
                return stops.size();
            }


            public String getItem(int i) {
                return stops.get(i);
            }


            public long getItemId(int i) {
                return i;
            }


            public View getView(int i, View view, ViewGroup viewGroup) {

                LayoutInflater inflater = OCTranspo.this.getLayoutInflater();
                View show_view = view;
                if(view == null)
                    show_view = inflater.inflate(R.layout.activity_oc_stops, viewGroup, false);

                TextView tv = (TextView) show_view.findViewById(R.id.STOPS_VIEW);  //look at setContentView in onCreate
               // Button rowButton = (Button)show_view.findViewById(R.id.stops_button);
                // if(oldView == null)
                tv.setText( getItem(i));
                return show_view;
            }

    }
    }









