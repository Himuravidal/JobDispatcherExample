package com.adachersoft.jobdispatcherexample;

import android.location.Location;
import android.os.Looper;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * Created by adacher on 02-08-17.
 */

public class GeoJob extends JobService {


    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    public boolean onStartJob(JobParameters job) {

        Log.d("IS THIS HAPPENING", "here");
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null) {
                    Log.d("LATITUDE", String.valueOf(location.getLatitude()));
                    Log.d("LONGITUDE", String.valueOf(location.getLongitude()));
                } else {

                    Log.d("Fallo", "Fallo o no hay location");
                }
            }
        });

         if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            Log.d("THREAD", Thread.currentThread().getName());
        } else {
             Log.d("THREAD", Thread.currentThread().getName() + "No es main");
        }



        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
