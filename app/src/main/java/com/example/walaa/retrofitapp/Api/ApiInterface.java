package com.example.walaa.retrofitapp.Api;


import com.example.walaa.retrofitapp.User;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("register.php")
    Call<User> register_user(

            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password,
            @Field("image") String image
    );

    @Multipart
    @POST("register.php")
    Call<User> reg_user_map(
            @PartMap Map<String, RequestBody> data_map


    );


    @Multipart
    @POST("login.php")
    Call<User> login_user(
            @Part("email") RequestBody email,
            @Part("password") RequestBody password
    );

    @FormUrlEncoded
    @POST("upload_image.php")
    Call<User> upload_image(
            @Field("image") String image,
            @Field("user_id") String user_id
    );

    @Multipart
    @POST("getImage.php")
    Call<User> download_image(
            @Part("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("update_username.php")
    Call<User> update_username(
            @Field("user_id") String user_id,
            @Field("username") String username
    );

    @POST("getContent.php")
    Call<List<User>> getContentRecycle();
}
