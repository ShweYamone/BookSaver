package com.training.android.booksaver.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.training.android.booksaver.R;

public class ProfileActivity extends AppCompatActivity {


    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";

    private ImageView ivUserPhoto;
    private TextView tvUserName;
    private Button btnEdit, btnLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);


        ivUserPhoto = findViewById(R.id.ivUserPhoto);
        String photoPath = sharedpreferences.getString("photopath", "");
        if (!photoPath.equals("")) {
            ivUserPhoto.setImageBitmap(BitmapFactory.decodeFile(photoPath));
        } else {

        }



        tvUserName = findViewById(R.id.tvUserName);
        tvUserName.setText(sharedpreferences.getString("name", ""));

        btnEdit = findViewById(R.id.btnEdit);
        btnLogout = findViewById(R.id.btnLogOut);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, UserEditActivity.class);
                startActivity(intent);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();

                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        Log.i("RESUME", "works");
        tvUserName = findViewById(R.id.tvUserName);

        ivUserPhoto = findViewById(R.id.ivUserPhoto);
        tvUserName.setText(sharedpreferences.getString("name", ""));
        Log.i("RESUME", sharedpreferences.getString("photopath", ""));
        ivUserPhoto.setImageBitmap(BitmapFactory.decodeFile(sharedpreferences.getString("photopath", "")));

        super.onResume();
    }
}
