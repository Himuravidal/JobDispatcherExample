package com.adachersoft.jobdispatcherexample;

import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by adacher on 01-08-17.
 */

public class MyJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters job) {

        StatFs statFs = new StatFs(Environment.getRootDirectory().getPath());

        Log.i("MY DEVICE SPACE SERVICE",
                "Free space is " +
                        (statFs.getAvailableBlocksLong() *
                                statFs.getBlockSizeLong() /
                                1024) +
                        " Kb"
        );

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
