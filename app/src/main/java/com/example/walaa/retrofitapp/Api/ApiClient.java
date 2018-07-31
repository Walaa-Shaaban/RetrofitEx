package com.example.walaa.retrofitapp.Api;


import com.example.walaa.retrofitapp.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static String BASE_URL = "https://retrofit-walaashaaban.c9users.io/retrofit/";
    public static Retrofit retrofit = null;

    public static Retrofit getRetrofit() {

        if (retrofit == null) {
            //********************************

            //create okhttp client
            OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            if (BuildConfig.DEBUG) {
                okhttpClientBuilder.addInterceptor(logging);
            }


            //Build retrofit

            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okhttpClientBuilder.build())
                    .build();

        }
        return retrofit;
    }

}
