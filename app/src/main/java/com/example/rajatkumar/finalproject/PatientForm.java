package com.example.rajatkumar.finalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import static com.example.rajatkumar.finalproject.MainActivity.ACTIVITY_NAME;
import android.support.design.widget.Snackbar;

public class PatientForm extends Activity {
 ProgressBar pb;
    EditText name;
    EditText address;
    EditText phone;
    EditText birthday;
    EditText card;
    Button submit;
    CheckBox doctor;
    CheckBox dentist;
    CheckBox optometrsit;

      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_form);
         name= (EditText) findViewById(R.id.editTextName);
         address= (EditText) findViewById(R.id.editTextAddress);
         phone= (EditText) findViewById(R.id.editTextPhone);
         birthday= (EditText) findViewById(R.id.editTextBirthday);
         card= (EditText) findViewById(R.id.editTextCard);
         submit=(Button)findViewById(R.id.submit);
    doctor=(CheckBox)findViewById(R.id.doctorcheck);
    pb=(ProgressBar)findViewById(R.id.progressBar);
    doctor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                          @Override
                                          public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                                              if (check) {
                                                  AlertDialog.Builder builder = new AlertDialog.Builder(PatientForm.this);
// 2. Chain together various setter methods to set the dialog characteristics
                                                  builder.setMessage("You Selected the doctor") //Add a dialog message to strings.xml

                                                          .setTitle("Selection Made")
                                                          .setPositiveButton(" OK ", new DialogInterface.OnClickListener() {
                                                              public void onClick(DialogInterface dialog, int id) {
                                              }
                                                          })
                                              .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                  public void onClick(DialogInterface dialog, int id) {
                                                      // User cancelled the dialog
                                                  }


                                              })
                                                          .show();


                                              }
                                          }
    });
         submit.setOnClickListener(e ->{
             Log.i(ACTIVITY_NAME, "User clicked submit the Form");
          Toast toast = Toast.makeText(this, "submitted", Toast.LENGTH_SHORT);
           toast.show();
         });

    }
}
// Vaibhav jain is assigned to this activity patient intake form