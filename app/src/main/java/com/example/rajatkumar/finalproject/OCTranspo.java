package com.example.rajatkumar.finalproject;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import static com.example.rajatkumar.finalproject.MainActivity.ACTIVITY_NAME;

public class OCTranspo extends AppCompatActivity {
    public static ArrayList<String> stops = new ArrayList();
    static RoutesDatabase helper;
    boolean isTablet;
    static Cursor cursor;
    static SQLiteDatabase db;
    static Stopadapter stopadapter;
    ListView stops_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_octranspo);
        EditText enter_stop = (EditText) findViewById(R.id.stops_xml_edit);
        isTablet =((findViewById(R.id.frame))!=null);

        Button addButton = (Button) findViewById(R.id.stops_button);

        stops_view = (ListView) findViewById(R.id.stops_xml);
        stops_view.setBackgroundColor(Color.LTGRAY);
        stopadapter = new Stopadapter(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        helper = new RoutesDatabase(this);
        db = helper.getWritableDatabase();

        String query = "select Distinct * from " + RoutesDatabase.TABLE_NAME;
        cursor = db.rawQuery(query, null);
        moveCursor();




        Log.i(ACTIVITY_NAME, "Cursor's  column count =" + cursor.getColumnCount());

        stops_view.setAdapter(stopadapter);

        addButton.setOnClickListener((View e) -> {
            if (enter_stop.length() <= 0 || enter_stop.length() > 4) {
                Toast.makeText(getApplicationContext(), "Enter 4 digit Number", Toast.LENGTH_SHORT).show();
            } else {

                ContentValues newData = new ContentValues();

                newData.put(RoutesDatabase.KEY_ROUTES, enter_stop.getText().toString());

                db.insert(RoutesDatabase.TABLE_NAME, null, newData);


                String s;
                s = enter_stop.getText().toString();
                Intent intent = new Intent(OCTranspo.this, GetRouteSummaryForStop.class);
                intent.putExtra("RouteNo", s);
                startActivity(intent);


                moveCursor();
                enter_stop.setText("");

                Toast.makeText(getApplicationContext(), "Searching routes.......", Toast.LENGTH_SHORT).show();
            }

        });


        stops_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String s = stops_view.getItemAtPosition(position).toString();
                Log.i(ACTIVITY_NAME, "stop number =" + s);
                Intent intent = new Intent(OCTranspo.this, GetRouteSummaryForStop.class);
                intent.putExtra("RouteNo", s);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "getting routes.......", Toast.LENGTH_SHORT).show();

            }
        });


        stops_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundle_message = new Bundle();
                bundle_message.putString("stop",stops_view.getItemAtPosition(position).toString() );
                bundle_message.putString("id", String.valueOf(stopadapter.getItemId(position)) );
                bundle_message.putBoolean("isTablet", isTablet);
                if(isTablet){
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    StopsFragment mf = new StopsFragment();
                    mf.setArguments(bundle_message);
                    ft.replace(R.id.frame, mf);
                    ft.commit();
                }
                else{
                    Intent intent = new Intent(OCTranspo.this, StopsDetails.class);
                    intent.putExtra("stop", stops_view.getItemAtPosition(position).toString());
                    intent.putExtra("id",String.valueOf(stopadapter.getItemId(position) ));
                    startActivityForResult(intent, 500);
                }
                return false;

            }


        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ( resultCode == 600) {
            Bundle extras = data.getExtras();
            String comingID = (String) extras.get("sendingID");
            db.delete(RoutesDatabase.TABLE_NAME,RoutesDatabase.KEY_ID +"="+comingID,null);
            stops.clear();
            moveCursor();
        }

    }

    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.toolbar_oc_transpo, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {
        switch (mi.getItemId()) {

            case R.id.action_one:
                AlertDialog.Builder builder = new AlertDialog.Builder(OCTranspo.this);
                builder.setTitle(R.string.Help_title);
                builder.setMessage(R.string.help_dialog);
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }

        return true;

    }

    private class Stopadapter extends ArrayAdapter<String> {

        public Stopadapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return stops.size();
        }

        public String getItem(int i) {
            return stops.get(i);
        }

        public long getItemId(int i) {

            cursor.moveToPosition(i);
            return cursor.getLong(cursor.getColumnIndex(RoutesDatabase.KEY_ID));
//            return i;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater inflater = OCTranspo.this.getLayoutInflater();
            View show_view = view;
            if (view == null)
                show_view = inflater.inflate(R.layout.activity_oc_stops, viewGroup, false);
            TextView tv = (TextView) show_view.findViewById(R.id.STOPS_VIEW);  //look at setContentView in onCreate
            tv.setText(getItem(i));
            return show_view;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
    public static void moveCursor() {

        stops.clear();
        String query = "select Distinct * from " + RoutesDatabase.TABLE_NAME;
        cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            if (!stops.contains(cursor.getString(cursor.getColumnIndex(RoutesDatabase.KEY_ROUTES)))) {
                stops.add(cursor.getString(cursor.getColumnIndex(RoutesDatabase.KEY_ROUTES)));
            }
            stopadapter.notifyDataSetChanged();
            cursor.moveToNext();
        }
        stopadapter.notifyDataSetChanged();

    }
}









