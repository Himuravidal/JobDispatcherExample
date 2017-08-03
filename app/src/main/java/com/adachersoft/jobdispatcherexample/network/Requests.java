package com.adachersoft.jobdispatcherexample.network;

import com.adachersoft.jobdispatcherexample.models.Unsplash;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by cutiko on 03-08-17.
 */

public interface Requests {

    @GET("photos/random")
    Call<Unsplash> getRandom();

}
