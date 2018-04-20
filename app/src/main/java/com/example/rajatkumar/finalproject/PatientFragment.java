package com.example.rajatkumar.finalproject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static com.example.rajatkumar.finalproject.PatientDatabase.Name_Column;
import static com.example.rajatkumar.finalproject.PatientDatabase.TABLE_NAME;

/**
 * Created by Vaibhav jain on 2018-04-16.
 */

public class PatientFragment extends Fragment {
    Context context;
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

    TextView type;
    TextView nameAns;
    TextView dobAns;
    TextView addrAns;
    TextView phoneAns;
    TextView cardAns;
    TextView descAns;
    TextView allerAns;
    TextView prevSAns;
    TextView benefits;
    TextView had_braces;
    TextView gstore;
    TextView gbought;
    Button delete_button;
    SQLiteDatabase db;
 public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle getInfo = getArguments();
      name=getInfo.getString("Name");
     DOB=getInfo.getString("Birthday");
     Address=getInfo.getString("Address");
     phone=getInfo.getString("Phone");
     card=getInfo.getString("Health Card");
     DoctorType=getInfo.getString("Type of Doctor");
     Description=getInfo.getString("Description");
     PREVIOUS_SURGERIES=getInfo.getString("Any Surgeries");
     Allergies=getInfo.getString("Allergies");
     Glass_Bought=getInfo.getString("Glass Bought");
     Glass_Store=getInfo.getString("Glass Store");
     Benefits=getInfo.getString("Benefits");
     Had_Braces=getInfo.getString("Had_braces");

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View page;
        page = inflater.inflate(R.layout.patient_xml, container, false);

        db=PatientMain.database.getWritableDatabase();

        type=page.findViewById(R.id.typeAns);
        nameAns = page.findViewById(R.id.nameAns);
        addrAns = page.findViewById(R.id.addAns);
        dobAns=page.findViewById(R.id.birthAns);
        descAns=page.findViewById(R.id.descriptAns);
        phoneAns=page.findViewById(R.id.numAns);
        cardAns=page.findViewById(R.id.hCardAns);
        allerAns=page.findViewById(R.id.Ans2);
        prevSAns=page.findViewById(R.id.Ans1);
        benefits=page.findViewById(R.id.beneAns);
        had_braces=page.findViewById(R.id.hbraceAns);
        gstore=page.findViewById(R.id.storeAns);
        gbought=page.findViewById(R.id.gbAns);
        delete_button = page.findViewById(R.id.delButton);
         type.setText(DoctorType);
        nameAns.setText(name);
        addrAns.setText(Address);
        dobAns.setText(DOB);
        descAns.setText(Description);
        phoneAns.setText(phone);
        cardAns.setText(card);
        allerAns.setText(Allergies);
        prevSAns.setText(PREVIOUS_SURGERIES);
        benefits.setText(Benefits);
        had_braces.setText(Had_Braces);
        gstore.setText(Glass_Store);
        gbought.setText(Glass_Bought);

        Bundle b = this.getArguments();

        delete_button.setOnClickListener(e->{

            if(!b.getBoolean("isTablet")){
                db.delete(PatientDatabase.TABLE_NAME,PatientDatabase.Name_Column +"="+b.getString("Name"),null);
                PatientMain.patients.clear();
                PatientMain.cursor = db.query(true, PatientDatabase.TABLE_NAME,
                        new String[] { PatientDatabase.Name_Column, PatientDatabase.DATE_OF_BIRTH, PatientDatabase.ADDRESS, PatientDatabase.KEY_phone,
                                PatientDatabase.KEY_card, PatientDatabase.KEY_Description, PatientDatabase.KEY_PREVIOUS_SURGERIES, PatientDatabase.KEY_Allergies,
                                PatientDatabase.KEY_Benefits, PatientDatabase.KEY_Had_Braces, PatientDatabase.KEY_Glass_Bought,PatientDatabase.KEY_Glass_Store},
                        PatientDatabase.Name_Column + " Not Null" , null, null, null, null, null);

                PatientMain.cursor.moveToFirst();

                while(!PatientMain.cursor.isAfterLast() ) {
                    PatientMain.patients.add(PatientMain.cursor.getString(PatientMain.cursor.getColumnIndex(PatientDatabase.Name_Column)));
                    PatientMain.cursor.moveToNext();
                }
                PatientMain.notifyData();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.remove(this);
                ft.commit();
            }
            else{
                Intent resultIntent = new Intent();
                resultIntent.putExtra("Name",b.getString("Name") );
                getActivity().setResult(600, resultIntent);
                getActivity().finish();}
        });
        return page;
    }



}