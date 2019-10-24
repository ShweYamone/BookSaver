package com.training.android.booksaver.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

public class RegisterActivity extends AppCompatActivity {

    public static final int GALLERY_REQUEST_CODE = 0;
    public static final int CHOOSE_PIC_REQUEST_CODE = 1;
    public static final int MEDIA_TYPE_IMAGE = 2;

    private EditText etUserName, etPassword, etRepassword;
    private Button btnChoosePhoto, btnRegister;
    private ImageView ivUserPhoto;
    private String photoPath = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ivUserPhoto = findViewById(R.id.ivUserPhoto);
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        etRepassword = findViewById(R.id.etRePassword);
        btnChoosePhoto = findViewById(R.id.btnChoosePhoto);


        btnChoosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGallery();
            }
        });

        btnRegister = findViewById(R.id.btnRegister);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = true;
                if(TextUtils.isEmpty(etUserName.getText())) {
                    flag = false;
                    etUserName.setError("Password required");
                }
                if(TextUtils.isEmpty(etPassword.getText())) {
                    flag = false;
                    etPassword.setError("Username required");
                }
                else if(!etPassword.getText().toString().equals(etRepassword.getText().toString())){
                    flag = false;
                    etRepassword.setError("Password mismatch");
                }
                if(flag) {
                    AppDatabase.getAppDatabase(RegisterActivity.this).userDAO().insert(
                            new User(etUserName.getText().toString(),
                                    etPassword.getText().toString(), photoPath)

                    );
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                }

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



                    ivUserPhoto.setImageBitmap(BitmapFactory.decodeFile(photoPath));
                    break;
            }
    }

}
