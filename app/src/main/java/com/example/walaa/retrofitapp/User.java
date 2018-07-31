package com.example.walaa.retrofitapp;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("username")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("image")

    private String image;

    @SerializedName("password")
    private String password;

    @SerializedName("user_id")
    private String user_id;


    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public User() {

    }

    public String getUser_id() {
        return user_id;
    }


    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getImage() {
        return image;
    }
}
