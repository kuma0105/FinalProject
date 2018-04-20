package com.example.rajatkumar.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

/**
 * Created by Vaibhav jain on 2018-04-20.
 */

public class ProgrAndSnackBar extends AppCompatActivity {

    int progressStatus;
    Handler handler = new Handler();
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_snack);
        progressBar = (ProgressBar) findViewById(R.id.progressBar) ;
        new Thread(new Runnable() {
            public void run() {

                while (progressStatus < 100) {
                    progressStatus += 5;
                    // Update the progress bar and display the current value in the text view

                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                       /* textView.setText("Loading "+progressStatus+"/"+progressBar.getMax());*/
                        }
                    });
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Intent intent = new Intent(ProgrAndSnackBar.this, PatientMain.class);
                startActivity(intent);
            }
        }).start();
        int snackTime = Snackbar.LENGTH_LONG;
        String message = "Connecting to Patients ...";

        Snackbar snackbar = Snackbar.make(findViewById(R.id.layout_coordinate),message,snackTime);
        snackbar.show();
    }

}
