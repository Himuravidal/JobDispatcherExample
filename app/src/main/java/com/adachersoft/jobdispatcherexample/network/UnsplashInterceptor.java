package com.adachersoft.jobdispatcherexample.network;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cutiko on 03-08-17.
 */

public class UnsplashInterceptor {

    private static final String BASE_URL = "https://api.unsplash.com/";

    public Requests get() {
         /*This is very common in gets cause increase the response time wait and add headers and does retrys*/
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();

                Request request = originalRequest.newBuilder()
                    /*Common headers*/
                        .header("Authorization", "Client-ID 670dca916f140cd78e74829bd67751e011c9b5b94c17c8ce042d3bfd2bba2a2e")
                        .build();

                Response response = chain.proceed(request);
            
            /*If the request fail then you get 3 retrys*/
                int retryCount = 0;
                while (!response.isSuccessful() && retryCount < 3) {
                    retryCount++;
                    response = chain.proceed(request);
                }

                return response;
            }
        });

        OkHttpClient client = httpClient.build();

        Retrofit interceptor = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        Requests request = interceptor.create(Requests.class);

        return request;
    }

}
