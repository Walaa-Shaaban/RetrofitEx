package com.example.walaa.retrofitapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    public static PrefConfig prefConfig;
    private TextView txtUsername;
    private ImageView mImgProfile;
    private Button mChangeUsername, mLogout, mAllUser;
    private static final int GALLERY_PICK = 1;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        prefConfig = new PrefConfig(this);
        context = this.getApplicationContext();
        txtUsername = (TextView) findViewById(R.id.username);
        mImgProfile = (ImageView) findViewById(R.id.img_profile);
        mChangeUsername = (Button) findViewById(R.id.btn_change_username);
        mLogout = (Button) findViewById(R.id.btn_logout);
        mAllUser = (Button) findViewById(R.id.btn_all_user);
        txtUsername.setText(prefConfig.readName());


        mChangeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ChangeUsernameActivity.class));
            }
        });


        mAllUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UsersActivity.class));
            }
        });

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefConfig.writeName("");
                prefConfig.writeAuth("");
                prefConfig.writeLoginStatus(false);
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });


        prefConfig = new PrefConfig(MainActivity.this);
        prefConfig.DislayToast(prefConfig.readAuth());

        downloadImage();

        mImgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_PICK);

            }
        });


    }


    private void downloadImage() {
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), prefConfig.readAuth());
        Call<User> downloadImage = SplashActivity.apiInterface.
                download_image(user_id);
        downloadImage.enqueue(new retrofit2.Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.body().getImage() != "default") {
                        Picasso.with(MainActivity.this)
                                .load("https://retrofit-walaashaaban.c9users.io/retrofit/" + response.body().getImage())
                                .error(R.drawable.profile)
                                .into(mImgProfile);
                    }
                } else {
                    Log.e(response.errorBody().toString(), "***");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(t.getMessage().toString(), "*****");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setAspectRatio(5, 5)
                    .start(MainActivity.this);

        }


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();


                //compress Image
                File thumb_path = new File(resultUri.getPath());

                Bitmap thumb_image = null;
                try {
                    thumb_image = new
                            Compressor(context)
                            .setMaxWidth(200)
                            .setMaxHeight(200)
                            .setQuality(75)
                            .compressToBitmap(thumb_path);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumb_image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                final byte[] thumb_byte = baos.toByteArray();


                String image = getStringImage(thumb_image);
                Call<User> upload_Image = SplashActivity.apiInterface.upload_image(image, prefConfig.readAuth());
                upload_Image.enqueue(new retrofit2.Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            downloadImage();

                        } else {
                            prefConfig.DislayToast(response.errorBody().toString() + "&&&&");
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        prefConfig.DislayToast(t.getMessage());
                    }
                });


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 30, baos);                      //compress from 100 to 30 for images taken by camera
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

}
