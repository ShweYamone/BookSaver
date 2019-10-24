package com.training.android.booksaver.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.training.android.booksaver.DB.AppDatabase;
import com.training.android.booksaver.Entity.User;
import com.training.android.booksaver.R;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";

    private EditText etName, etPassword;
    private Button btnLogin, btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);


        if(sharedpreferences.contains("id")) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        else {

            etName = findViewById(R.id.etName);
            etPassword = findViewById(R.id.etPassword);
            btnLogin = findViewById(R.id.btnLogin);
            btnRegister = findViewById(R.id.btnRegister);
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (TextUtils.isEmpty(etPassword.getText())) {
                        etPassword.setError("Password required");
                    }
                    if(TextUtils.isEmpty(etName.getText())) {
                        etName.setError("Username required");
                    }
                    if (!TextUtils.isEmpty(etPassword.getText()) && !TextUtils.isEmpty(etName.getText())) {

                        User user = AppDatabase.getAppDatabase(LoginActivity.this)
                                .userDAO()
                                .findByNameAndPwd(etName.getText().toString(), etPassword.getText().toString());

                        if(user != null) {

                            SharedPreferences.Editor editor = sharedpreferences.edit();

                            editor.putInt("id",user.getId());
                            editor.putString("name",user.getName());
                            editor.putString("photopath", user.getPhotoPath());
                            editor.commit();


                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("id", user.getId());
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Login failed!!!", Toast.LENGTH_SHORT).show();
                        }

                    }


                }
            });
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }
            });
            isWriteStoragePermissionGranted();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:
                Log.d(TAG, "External storage2");
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
                    //resume tasks needing this permission
                }else{
                }
                break;

            case 3:
                Log.d(TAG, "External storage1");
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
                    //resume tasks needing this permission
                }else{
                }
                break;
        }
    }

    public  boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted1");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked1");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted1");
            return true;
        }
    }

    public  boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted2");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted2");
            return true;
        }
    }
}
