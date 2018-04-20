package com.example.rajatkumar.finalproject;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class StopsFragment extends Fragment {
    TextView tv_message;
    TextView tv_id;
    Button delete_button;
    SQLiteDatabase db;
    OCTranspo oc;
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        oc = new OCTranspo();
        db = OCTranspo.helper.getWritableDatabase();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View page;
        page = inflater.inflate(R.layout.activity_stops_phone_fragment, container, false);


        tv_message = page.findViewById(R.id.stops_here);
        tv_id = page.findViewById(R.id.stops_id_here);
        delete_button = page.findViewById(R.id.Delete_stops_button);

        Bundle b = this.getArguments();


        tv_message.setText(b.getString("stop"));
        Log.i("stop selected",b.getString("stop") );
        tv_id.setText(b.getString("id"));
        Log.i("stop ID ",b.getString("id") );

        delete_button.setOnClickListener(e->{

            if(b.getBoolean("isTablet")){
                db.delete(RoutesDatabase.TABLE_NAME,RoutesDatabase.KEY_ID +"="+b.getString("id"),null);
                OCTranspo.stops.clear();
                OCTranspo.moveCursor();

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.remove(this);
                ft.commit();
            }
            else{
                Intent resultIntent = new Intent();
                resultIntent.putExtra("sendingID",b.getString("id") );
                getActivity().setResult(600, resultIntent);
                getActivity().finish();}
        });
        return page;
    }
}