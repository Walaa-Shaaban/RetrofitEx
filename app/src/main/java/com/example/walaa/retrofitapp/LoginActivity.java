package com.example.walaa.retrofitapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmail, mPassword;
    private Button mLogin;
    private TextView mCreateAccount;
    private PrefConfig prefConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mCreateAccount = (TextView) findViewById(R.id.txt_create);
        mEmail = (EditText) findViewById(R.id.login_email);
        mPassword = (EditText) findViewById(R.id.login_password);
        mLogin = (Button) findViewById(R.id.login_btn);
        prefConfig = new PrefConfig(LoginActivity.this);

        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                prefConfig.startProgress("please wait...");
                RequestBody email = RequestBody.create(MediaType.parse("text/plain"), mEmail.getText().toString());
                RequestBody password = RequestBody.create(MediaType.parse("text/plain"), mPassword.getText().toString());
                loginUser(email, password);


            }
        });
    }

    private void loginUser(RequestBody email, RequestBody password) {
        Call<User> call = SplashActivity.apiInterface.login_user(
                email,
                password
        );

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {
                    prefConfig.writeName(response.body().getName());
                    prefConfig.writeLoginStatus(true);
                    prefConfig.stopProgress();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    prefConfig.DislayToast("Error : " + response.errorBody().toString());
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                prefConfig.stopProgress();
                prefConfig.DislayToast("Error : " + t.getMessage().toString());

            }
        });

    }
}
