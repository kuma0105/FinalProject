package com.example.rajatkumar.finalproject;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.support.v7.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.example.rajatkumar.finalproject.MainActivity.ACTIVITY_NAME;
import static com.example.rajatkumar.finalproject.PatientDatabase.Name_Column;
import static com.example.rajatkumar.finalproject.PatientDatabase.TABLE_NAME;
import static com.example.rajatkumar.finalproject.PatientMain.database;
import static com.example.rajatkumar.finalproject.PatientMain.moveCursor;
import static com.example.rajatkumar.finalproject.PatientMain.notifyData;

public class PatientMain extends AppCompatActivity {
    protected LayoutInflater inflater;
    protected static ArrayList<String> patients = new ArrayList<String>();

    Button addPatient;
    Button xmlFetcher;
    ListView listView;
    static PatientDatabase database;
    static PatientAdapter pAdapter;
    static Cursor cursor;
    boolean isTablet;
    XML read;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        findViewById(R.id.progressBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_patients);
        Toolbar toolbar_patient = (Toolbar) findViewById(R.id.toolbar_patient);
        setSupportActionBar(toolbar_patient);

        setTitle("Patient List");
        addPatient = (Button) findViewById(R.id.addPatient);
        xmlFetcher = (Button) findViewById(R.id.button);
        listView = (ListView) findViewById(R.id.listOfPatients);
        read = new XML();
        pAdapter = new PatientAdapter(this);
        inflater = PatientMain.this.getLayoutInflater();
        listView.setAdapter(pAdapter);
        database = new PatientDatabase(this);

        db = database.getWritableDatabase();

        cursor = database.getData();

        cursor = db.query(true, PatientDatabase.TABLE_NAME,
                new String[]{PatientDatabase.Name_Column, PatientDatabase.DATE_OF_BIRTH, PatientDatabase.ADDRESS, PatientDatabase.KEY_phone,
                        PatientDatabase.KEY_card, PatientDatabase.KEY_Description, PatientDatabase.KEY_PREVIOUS_SURGERIES, PatientDatabase.KEY_Allergies,
                        PatientDatabase.KEY_Benefits, PatientDatabase.KEY_Had_Braces, PatientDatabase.KEY_Glass_Bought, PatientDatabase.KEY_Glass_Store},
                PatientDatabase.Name_Column + " Not Null", null, null, null, null, null);


        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            patients.add(cursor.getString(cursor.getColumnIndex(Name_Column)));

            Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + cursor.getString(cursor.getColumnIndex(Name_Column)));
            cursor.moveToNext();
        }

        Log.i(ACTIVITY_NAME, "Cursor's  column count =" + cursor.getColumnCount());

        xmlFetcher.setOnClickListener(e -> {
            read = new XML();

            read.execute("https://torunski.ca/CST2335/PatientList.xml");

        });
        addPatient.setOnClickListener(e -> {
            Log.i(ACTIVITY_NAME, "User clicked Patient Form");
            Intent intent = new Intent(this, PatientForm.class);
            startActivity(intent);
        });

        listView.setAdapter(pAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle_message = new Bundle();
                bundle_message.putString("name", listView.getItemAtPosition(i).toString());
                bundle_message.putString("id", String.valueOf(pAdapter.getId(i)));
                bundle_message.putBoolean("isTablet", isTablet);
                if (isTablet) {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    PatientFragment mf = new PatientFragment();
                    mf.setArguments(bundle_message);
                    ft.replace(R.id.framelayout, mf);
                    ft.commit();
                } else {
                    Intent intent = new Intent(PatientMain.this, PatientDetails.class);
                    intent.putExtra("message", listView.getItemAtPosition(i).toString());
                    intent.putExtra("id", String.valueOf(l));
                    startActivityForResult(intent, 500);
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 600) {
            int indexToDelete = data.getIntExtra("position", 0);
            int id = (int) data.getLongExtra("id", 0);
            patients.remove(indexToDelete);
            db.delete(TABLE_NAME, Name_Column + " = " + id, null);
        }
    }

    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.patient_toolbar, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {
        switch (mi.getItemId()) {
            case R.id.action_patient_help:
                AlertDialog.Builder builder = new AlertDialog.Builder(PatientMain.this);
                builder.setTitle("Help");
                builder.setMessage("Version 1.0 by Vaibhav jain \n This Android Project add the patients and and show it in the list view " +
                        "And help to navigate between them.");
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                        .show();
                break;
        }
        return true;
    }


    public static void notifyData() {
        pAdapter.notifyDataSetChanged();
    }

    public static void moveCursor() {
        patients.clear();
        cursor = database.getData();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            patients.add(cursor.getString(cursor.getColumnIndex(Name_Column)));

            Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + cursor.getString(cursor.getColumnIndex(Name_Column)));
            cursor.moveToNext();
        }
        pAdapter.notifyDataSetChanged();
    }

    private class PatientAdapter extends ArrayAdapter<String> {

        public PatientAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return patients.size();
        }


        public String getItem(int position) {
            return String.valueOf(patients.get(position));
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View result = inflater.inflate(R.layout.listview_layout, null);
            TextView name = (TextView) result.findViewById(R.id.layout_text);
            name.setText(getItem(position));
            return result;

        }

        public long getId(int position) {
//            cursor.moveToPosition(position);
//            return cursor.getLong(cursor.getColumnIndex(PatientDatabase.Name_Column));
            return position;

        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
    class XML extends AsyncTask<String, Integer, ArrayList<String>> {
        String name = "";
        String DOB = "";
        String Address = "";
        String card = "";
        String phone = "";
        String DoctorType = "";
        String Description = "";
        String PREVIOUS_SURGERIES = "";
        String Allergies = "";
        String Glass_Bought = "";
        String Glass_Store = "";
        String Benefits = "";
        String Had_Braces = "";
        HttpURLConnection urlConnection;

        ArrayList<String> ar = new ArrayList<String>();

        @Override
        protected ArrayList<String> doInBackground(String... strings) {

            for (String siteUrl : strings) {
                try {

                    URL url = new URL(siteUrl);
                    String text = null;
                    String tagName = "";
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream iStream = urlConnection.getInputStream();

                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(false);
                    XmlPullParser xpp = factory.newPullParser();
                    xpp.setInput(iStream, "UTF-8");

                    int eventType = xpp.getEventType();
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        tagName = xpp.getName();
                        switch (eventType) {
                            case XmlPullParser.START_TAG:
                                if (xpp.getName().equals("Patient")) {
                                    DoctorType = xpp.getAttributeValue(null, "type");
                                    ar.add(DoctorType);
                                    publishProgress(25);
                                }
                                break;
                            case XmlPullParser.TEXT:
                                text = xpp.getText();
                                break;
                            case XmlPullParser.END_TAG:
                                if (tagName != null) {
                                    if (tagName.equals("Name")) {
                                        String s1;
                                        name = text;
                                        ar.add(name);

                                    }
                                    if (tagName.equals("Address")) {
                                        Address = text;
                                        publishProgress(35);
                                        ar.add(Address);
                                    }
                                    if (tagName.equals("Birthday")) {
                                        DOB = text;
                                        publishProgress(45);
                                        ar.add(DOB);
                                    }
                                    if (tagName.equals("PhoneNumber")) {
                                        phone = text;
                                        publishProgress(55);
                                        ar.add(phone);
                                    }
                                    if (tagName.equals("HealthCard")) {
                                        card = text;
                                        publishProgress(65);
                                        ar.add(card);
                                    }
                                    if (tagName.equals("Description")) {
                                        Description = text;
                                        publishProgress(75);
                                        ar.add(Description);
                                    }
                                    if (tagName.equals("PreviousSurgery")) {
                                        PREVIOUS_SURGERIES = text;
                                        publishProgress(85);
                                        ar.add(PREVIOUS_SURGERIES);
                                    }
                                    if (tagName.equals("Allergies")) {
                                        Allergies = text;
                                        publishProgress(95);
                                        ar.add(Allergies);
                                    }
                                    if (tagName.equals("GlassesBought")) {
                                        Glass_Bought = text;
                                        publishProgress(96);
                                        ar.add(Glass_Bought);
                                    }
                                    if (tagName.equals("GlassesStore")) {
                                        Glass_Store = text;
                                        publishProgress(98);
                                        ar.add(Glass_Store);
                                    }
                                    if (tagName.equals("Benefits")) {
                                        Benefits = text;
                                        publishProgress(99);
                                        ar.add(Benefits);
                                    }
                                    if (tagName.equals("HadBraces")) {
                                        Had_Braces = text;
                                        publishProgress(100);
                                        ar.add(Had_Braces);
                                    }
                                }
                                break;


                        }

//                    database.insertData(name,DOB,Address,card,phone,DoctorType,Description,PREVIOUS_SURGERIES,Allergies,Had_Braces,Benefits,Glass_Bought,Glass_Store);
                        eventType = xpp.next();
                    }

                } catch (Exception mfe) {
                    Log.e("Error", mfe.getMessage());
                }

            }

            return ar;
        }

        @Override
        public void onPostExecute(ArrayList<String> arl) {
            database.insertData(arl.get(0), arl.get(1), arl.get(2), arl.get(3), arl.get(4), arl.get(5), arl.get(6), arl.get(7), arl.get(8), "", "", "", "");
            database.insertData(arl.get(9), arl.get(10), arl.get(11), arl.get(12), arl.get(13), arl.get(14), arl.get(15), "", "", arl.get(16), arl.get(17), "", "");
            database.insertData(arl.get(18), arl.get(19), arl.get(20), arl.get(21), arl.get(22), arl.get(23), arl.get(24), "", "", "", "", arl.get(25), arl.get(26));
            moveCursor();
        }
    }
