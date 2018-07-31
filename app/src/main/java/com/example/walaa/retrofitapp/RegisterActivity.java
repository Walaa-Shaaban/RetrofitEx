package com.example.walaa.retrofitapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText mEmail, mUsername, mPassword;
    private Button mReg;
    private Context context;
    private PrefConfig prefConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mUsername = (EditText) findViewById(R.id.username);
        mReg = (Button) findViewById(R.id.reg);
        context = this.getApplicationContext();
        prefConfig = new PrefConfig(RegisterActivity.this);


        mReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(mEmail.getText().toString()) ||
                        !TextUtils.isEmpty(mPassword.getText().toString()) ||
                        !TextUtils.isEmpty(mUsername.getText().toString()) ||
                        Patterns.EMAIL_ADDRESS.matcher(mEmail.getText().toString()).matches()) {

                    new PrefConfig(RegisterActivity.this).startProgress("Please wait...");
                    Map<String, RequestBody> getData = new HashMap<>();
                    getData.put("email", createPartFromString(mEmail.getText().toString()));
                    getData.put("username", createPartFromString(mUsername.getText().toString()));
                    getData.put("password", createPartFromString(mPassword.getText().toString()));
                    getData.put("user_id", createPartFromString(Base64.encodeToString(
                            (mEmail.getText().toString() + ":" + mPassword.getText().toString()).getBytes(),
                            Base64.NO_WRAP)));
                    getData.put("image", createPartFromString("default"));

                    registerUser(getData);
                } else {
                    prefConfig.DislayToast("please check your form...");
                }

            }
        });
    }

    private RequestBody createPartFromString(String data) {
        return RequestBody.create(
                MultipartBody.FORM, data);

    }


    private void registerUser(Map<String, RequestBody> getData) {
        Call<User> call = SplashActivity.apiInterface.reg_user_map(
                getData


        );

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    prefConfig.DislayToast("Success ...");
                    prefConfig.writeName(mUsername.getText().toString());
                    prefConfig.writeAuth(
                            Base64.encodeToString(
                                    (mEmail.getText().toString() + ":" + mPassword.getText().toString()).getBytes(),
                                    Base64.NO_WRAP)
                    );

                    new PrefConfig(RegisterActivity.this).writeLoginStatus(true);
                    prefConfig.stopProgress();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
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
