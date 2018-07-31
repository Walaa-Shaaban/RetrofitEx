package com.example.walaa.retrofitapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class PrefConfig {

    private SharedPreferences sharedPreferences;
    private Context context;
    private ProgressDialog mProgress;

    PrefConfig(Context context) {
        this.context = context;
        sharedPreferences = this.context.getSharedPreferences(context.getString(R.string.pref_file), Context.MODE_PRIVATE);
        mProgress = new ProgressDialog(this.context);
    }


    //Login
    public void writeLoginStatus(boolean status) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_login_status), status);
        editor.commit();
    }

    public boolean readLoginStatus() {
        return sharedPreferences.getBoolean(context.getString(R.string.pref_login_status), false);
    }

    //Name
    public void writeName(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_user_name), username);
        editor.commit();
    }

    public String readName() {
        return sharedPreferences.getString(context.getString(R.string.pref_user_name), "user");
    }


    //IMage
    public void writeImageUrl(String imageUrl) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_image_url), imageUrl);
        editor.commit();
    }

    public String readImageUrl() {
        return sharedPreferences.getString(context.getString(R.string.pref_image_url), "");
    }


    //Auth
    public void writeAuth(String auth) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_auth), auth);
        editor.commit();
    }

    public String readAuth() {
        return sharedPreferences.getString(context.getString(R.string.pref_auth), "");
    }


    //Progress
    public void startProgress(String msg) {
        mProgress.setTitle(msg);
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.show();
    }

    public void stopProgress() {
        mProgress.dismiss();
    }


    //Toast
    public void DislayToast(String Message) {
        Toast.makeText(context, Message, Toast.LENGTH_SHORT).show();
    }
}