package com.adachersoft.jobdispatcherexample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

public class MainActivity extends AppCompatActivity {

    private FirebaseJobDispatcher dispatcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(MainActivity.this));



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Job myJob = dispatcher.newJobBuilder()
                        .setService(GeoJob.class) // the JobService that will be called
                        .setTag("GEO_TAG")        // uniquely identifies the job
                        .setRecurring(false)
                        .setLifetime(Lifetime.FOREVER)
                        .setTrigger(Trigger.executionWindow(5, 30))
                       /* .setConstraints(
                                Constraint.ON_UNMETERED_NETWORK
                        )*/
                        .build();

                dispatcher.mustSchedule(myJob);


            }
        });
    }

}
