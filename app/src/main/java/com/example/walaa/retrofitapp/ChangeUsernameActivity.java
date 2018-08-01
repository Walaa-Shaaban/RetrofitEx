package com.example.walaa.retrofitapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeUsernameActivity extends AppCompatActivity {

    private EditText mUsername;
    private Button mSave;
    private PrefConfig prefConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_username);

        prefConfig = new PrefConfig(ChangeUsernameActivity.this);
        mUsername = (EditText) findViewById(R.id.change_username);
        mSave = (Button) findViewById(R.id.btn_change);
        mUsername.setText(prefConfig.readName());

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                prefConfig.startProgress("please wait ...");
                Call<User> updateUsername = SplashActivity.apiInterface.update_username(
                        prefConfig.readAuth(),
                        mUsername.getText().toString()
                );

                //Error not change username

                updateUsername.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()){
                            prefConfig.stopProgress();
                            prefConfig.writeName(mUsername.getText().toString());
                            startActivity(new Intent(ChangeUsernameActivity.this, MainActivity.class));

                        }


                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        prefConfig.DislayToast(t.getMessage() + "uuuu");

                    }
                });

            }
        });


    }
}
