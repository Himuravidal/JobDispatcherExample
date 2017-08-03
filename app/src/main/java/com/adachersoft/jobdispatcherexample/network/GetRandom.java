package com.adachersoft.jobdispatcherexample.network;

import android.os.AsyncTask;
import android.util.Log;

import com.adachersoft.jobdispatcherexample.models.Unsplash;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by cutiko on 03-08-17.
 */

public class GetRandom extends AsyncTask<Void, Void, Integer> {

    @Override
    protected Integer doInBackground(Void... params) {
        Requests requests = new UnsplashInterceptor().get();
        Call<Unsplash> call = requests.getRandom();

        try {
            Response<Unsplash> response = call.execute();
            int code = response.code();
            Log.d("CODE", String.valueOf(code));

            if (200 == code & response.isSuccessful()) {
                Unsplash unsplash = response.body();
                Log.d("URL", unsplash.getUrls().getRegular());
            }

            return code;
        } catch (IOException e) {
            e.printStackTrace();
            return 777;
        }
    }

}
