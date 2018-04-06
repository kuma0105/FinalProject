package com.example.rajatkumar.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import static com.example.rajatkumar.finalproject.MainActivity.ACTIVITY_NAME;

public class PatientMain extends Activity {
    Button addPatient;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_patients);


        addPatient = (Button) findViewById(R.id.addPatient);
        listView = (ListView) findViewById(R.id.listOfPatients);
        addPatient.setOnClickListener(e ->{
            Log.i(ACTIVITY_NAME, "User clicked Patient Form");
            Intent intent = new Intent(this, PatientForm.class);
            startActivity(intent);
        });
    }
}
