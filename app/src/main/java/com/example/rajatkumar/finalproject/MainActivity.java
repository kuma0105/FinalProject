package com.example.rajatkumar.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends Activity {
    protected static final String ACTIVITY_NAME = "MainActivity";


    Button buttonQuiz;
    Button buttonPatient;
    Button buttonMovie;
    Button buttonOCTranspo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonQuiz = (Button) findViewById(R.id.buttonQuiz);
        buttonPatient = (Button) findViewById(R.id.buttonPatient);
        buttonMovie = (Button) findViewById(R.id.buttonMovie);
        buttonOCTranspo = (Button) findViewById(R.id.buttonOCTranspo);

        buttonQuiz.setOnClickListener(e ->{
            Log.i(ACTIVITY_NAME, "User clicked Quiz Creater");
            Intent intent = new Intent(MainActivity.this, QuizCreater.class);
            startActivity(intent);
        });

        buttonPatient.setOnClickListener(e ->{
            Log.i(ACTIVITY_NAME, "User clicked Patient Form");
            Intent intent = new Intent(MainActivity.this, ProgrAndSnackBar.class);
            startActivity(intent);
        });

        buttonMovie.setOnClickListener(e ->{
            Log.i(ACTIVITY_NAME, "User clicked Movie Info");
            Intent intent = new Intent(MainActivity.this, MovieInfo.class);
            startActivity(intent);
        });

        buttonOCTranspo.setOnClickListener(e ->{
            Log.i(ACTIVITY_NAME, "User clicked OCTranspo");
            Intent intent = new Intent(MainActivity.this, OCTranspo.class);
            startActivity(intent);
        });
    }
}
