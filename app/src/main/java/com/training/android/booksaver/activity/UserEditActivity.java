package com.training.android.booksaver.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.training.android.booksaver.DB.AppDatabase;
import com.training.android.booksaver.Entity.User;
import com.training.android.booksaver.R;

import java.io.File;

public class UserEditActivity extends AppCompatActivity {


    public static final int GALLERY_REQUEST_CODE = 0;

    SharedPreferences sharedpreferences;

    private EditText etUserName, etPassword, etRepassword;
    private Button btnUpdate, btnChoosePhoto;
    private ImageView ivUserPhoto;
    private String photoPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        sharedpreferences = getSharedPreferences(LoginActivity.mypreference,
                Context.MODE_PRIVATE);
        final int id = sharedpreferences.getInt("id", -100);
        String name = sharedpreferences.getString("name", "");

        ivUserPhoto = findViewById(R.id.ivUserPhoto);

        photoPath = sharedpreferences.getString("photopath", "");
        if(!photoPath.equals("")) {
            ivUserPhoto.setImageBitmap(BitmapFactory.decodeFile(photoPath));
        }
        else {

        }
        etUserName = findViewById(R.id.etUserName);
        etUserName.setText(name);

        etPassword = findViewById(R.id.etPassword);

        etRepassword = findViewById(R.id.etRePassword);

        btnChoosePhoto = findViewById(R.id.btnChoosePhoto);
        btnChoosePhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                pickFromGallery();
            }
        });

        btnUpdate = findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDatabase.getAppDatabase(UserEditActivity.this).userDAO().update(
                        new User(
                                id,
                                etUserName.getText().toString(),
                                etPassword.getText().toString(),
                                photoPath
                        )
                );

                SharedPreferences.Editor editor = sharedpreferences.edit();
                //  editor.putInt("id",user.getId());
                editor.putString("name",etUserName.getText().toString());
                editor.putString("photopath", photoPath
                );
                editor.commit();
                finish();

            }
        });
    }
    private void pickFromGallery() {
        //Create an Intent with action as ACTION_PICK
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode,int resultCode,Intent data){
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST_CODE:
                    Log.i("REQUEST", "Works");
                    //data.getData return the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    // Get the cursor
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    //Get the column index of MediaStore.Images.Media.DATA
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    //Gets the String value in the column
                    photoPath = cursor.getString(columnIndex);
                    cursor.close();
                    ivUserPhoto.setImageURI(data.getData());

                    break;
            }
    }


}
