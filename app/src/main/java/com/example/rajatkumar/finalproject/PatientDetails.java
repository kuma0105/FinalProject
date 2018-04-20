package com.example.rajatkumar.finalproject;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class PatientDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);
        Bundle b = getIntent().getExtras();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        PatientFragment mf = new PatientFragment();
        mf.setArguments(b);
        ft.replace(R.id.emtFrame,mf);
        ft.commit();
    }
}
