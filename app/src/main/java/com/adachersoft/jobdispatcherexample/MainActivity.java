package com.adachersoft.jobdispatcherexample;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

public class MainActivity extends AppCompatActivity {

    private static final int RC_GEO = 343;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        &&
                        ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    setGeoJob();
                } else {
                    String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
                    requestPermissions(permissions, RC_GEO);
                }
            }
        });

        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(MainActivity.this));

                Job myJob = dispatcher.newJobBuilder()
                        .setService(AsyncJob.class)
                        .setTag("ASYNC_TAG")
                        .setRecurring(true)
                        .setLifetime(Lifetime.FOREVER)
                        .setTrigger(Trigger.executionWindow(0, 60))
                        .setConstraints(
                                Constraint.ON_UNMETERED_NETWORK
                        )
                        .build();

                dispatcher.mustSchedule(myJob);

                Toast.makeText(MainActivity.this, "ASYNC JOB SET", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (RC_GEO == requestCode) {
            setGeoJob();
        }
    }

    private void setGeoJob(){
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(MainActivity.this));

        Job myJob = dispatcher.newJobBuilder()
                .setService(GeoJob.class) // the JobService that will be called
                .setTag("GEO_TAG")        // uniquely identifies the job
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(5, 30))
                       /* .setConstraints(
                                Constraint.ON_UNMETERED_NETWORK
                        )*/
                .build();

        dispatcher.mustSchedule(myJob);
    }
}
