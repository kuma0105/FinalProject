package com.example.rajatkumar.finalproject;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class StopsDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stops_details);
        Bundle b = getIntent().getExtras();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        StopsFragment mf = new StopsFragment();
        mf.setArguments(b);
        ft.replace(R.id.Layout,mf);
        ft.commit();
    }
}
