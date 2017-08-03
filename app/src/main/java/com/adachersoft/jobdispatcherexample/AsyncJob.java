package com.adachersoft.jobdispatcherexample;


import android.util.Log;

import com.adachersoft.jobdispatcherexample.network.GetRandom;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by cutiko on 03-08-17.
 */

public class AsyncJob extends JobService {

    private static final String TAG = "ASYNC_JOB";

    @Override
    public boolean onStartJob(JobParameters params) {

        Log.d(TAG, "onStartJob: start");

        new GetRandom().execute();

        Log.d(TAG, "onStartJob: fin");

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
