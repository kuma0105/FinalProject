package com.example.rajatkumar.finalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.rajatkumar.finalproject.MainActivity.ACTIVITY_NAME;
import android.support.design.widget.Snackbar;

public class PatientForm extends Activity {

    EditText name;
    EditText address;
    EditText phone;
    EditText birthday;
    EditText card;
    Button submit;
    String type;
   RadioGroup rg;
   RadioButton rb;
    EditText description;
    String benefits;
    String braces;
    String glasses_bought;
    String glasses_store;
    String previousSurgeries;
    String allergies;
PatientDatabase data;
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
         description=(EditText)findViewById(R.id.descAns);
    rg=(RadioGroup) findViewById(R.id.selectedId);

 data=new PatientDatabase(this);


         submit.setOnClickListener(e ->{
             int i = rg.getCheckedRadioButtonId();
             rb=findViewById(i);
             type=rb.getText().toString();
             String PatientName=name.getText().toString();
             String PatientAddress=address.getText().toString();
             String PatientPhone=phone.getText().toString();
             String PatientBirthday=birthday.getText().toString();
             String PatientCard=card.getText().toString();
             String descript=description.getText().toString();
             Log.i(ACTIVITY_NAME, "User clicked submit the Form");
             LayoutInflater inflater = getLayoutInflater();
             View view = inflater.inflate(R.layout.alert_box_details, null);

             EditText editText1 = (EditText) view.findViewById(R.id.dialogueAns1);
             EditText editText2 = (EditText) view.findViewById(R.id.dialogueAns2);
             TextView textView1=(TextView) view.findViewById(R.id.dialogue1);
             TextView textView2=(TextView) view.findViewById(R.id.dialogue2);


        if(type.equals("Doctor")){
            textView1.setText("Previous Surgeries");
            textView2.setText("Allergies");

            AlertDialog.Builder builder = new AlertDialog.Builder(PatientForm.this);
            builder = new AlertDialog.Builder(PatientForm.this);
            builder.setTitle("We need details of Following fields ");
            builder.setView(view);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                 previousSurgeries  = editText1.getText().toString();
                    allergies  = editText1.getText().toString();
                    data.insertData(PatientName,PatientBirthday,PatientAddress,PatientCard,PatientPhone,type,descript,previousSurgeries,allergies,"Not Provided","Not Provided","Not Provided","Not Provided");

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                    Intent resultIntent = new Intent(  );
                    resultIntent.putExtra("Response", "Here is my response");
                }
            });
            // Create the AlertDialog
            builder.show();
        }
             if(type.equals("Dentist")){
                 textView1.setText("Benefits");

                 textView2.setText("Hard Braces");
                 AlertDialog.Builder builder = new AlertDialog.Builder(PatientForm.this);
                 builder = new AlertDialog.Builder(PatientForm.this);
                 builder.setTitle("We need details of Following fields ");
                 builder.setView(view);
                 builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int id) {
                         // User clicked OK button
                         benefits  = editText1.getText().toString();
                         braces  = editText1.getText().toString();
                         data.insertData(PatientName,PatientBirthday,PatientAddress,PatientCard,PatientPhone,type,descript,"Not Provided","Not provided",braces,benefits,"Not Provided","Not Provided");

                     }
                 });
                 builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int id) {
                         // User cancelled the dialog
                         Intent resultIntent = new Intent(  );
                         resultIntent.putExtra("Response", "Here is my response");
                     }
                 });
                 // Create the AlertDialog
                 builder.show();
             }
             if(type.equals("Optometrist")){
                 textView1.setText("Glasses Bought");
                 textView2.setText("Glasses Store");

                 AlertDialog.Builder builder = new AlertDialog.Builder(PatientForm.this);
                 builder = new AlertDialog.Builder(PatientForm.this);
                 builder.setTitle("We need details of Following fields ");
                 builder.setView(view);
                 builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int id) {
                         // User clicked OK button
                         glasses_bought  = editText1.getText().toString();
                         glasses_store  = editText1.getText().toString();
                         data.insertData(PatientName,PatientBirthday,PatientAddress,PatientCard,PatientPhone,type,descript,"Not Provided","Not Provided","Not Provided","Not Provided",glasses_bought,glasses_store);

                     }
                 });
                 builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int id) {
                         // User cancelled the dialog
                         Intent resultIntent = new Intent(  );
                         resultIntent.putExtra("Response", "Here is my response");
                     }
                 });
                 // Create the AlertDialog
                 builder.show();
             }

          Toast toast = Toast.makeText(this, "submitted", Toast.LENGTH_SHORT);
           toast.show();

         });

    }
}
// Vaibhav jain is assigned to this activity patient intake form