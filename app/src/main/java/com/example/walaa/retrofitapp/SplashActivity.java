package com.example.walaa.retrofitapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.walaa.retrofitapp.Api.ApiClient;
import com.example.walaa.retrofitapp.Api.ApiInterface;

public class SplashActivity extends AppCompatActivity {

    public static PrefConfig prefConfig;
    public static ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        prefConfig = new PrefConfig(this);
        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(3000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    if (prefConfig.readLoginStatus()) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();

                    } else {

                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                    }


                }
            }
        };
        timerThread.start();


    }
}
