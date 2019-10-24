
package com.training.android.booksaver.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.training.android.booksaver.DB.AppDatabase;
import com.training.android.booksaver.Entity.Book;
import com.training.android.booksaver.R;

import java.io.File;
import java.net.URI;


public class AddBookActivity extends AppCompatActivity {

    private static final String TAG = "AddBookActivity";

    public static final int GALLERY_REQUEST_CODE = 1;

    private ImageView ivBookPhoto;
    private EditText etBookName, etAuthorName, etDescription;
    private Button btnSave;

    private Spinner spCategory;
    private String[] categories = {"drama", "comedy", "romance", "comic", "thriller"};
    private String bookFilePath = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        ivBookPhoto = findViewById(R.id.ivBookPhoto);

        etBookName = findViewById(R.id.etBookName);
        etAuthorName = findViewById(R.id.etAuthorName);
        etDescription = findViewById(R.id.etDescription);
        btnSave = findViewById(R.id.btnSave);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddBookActivity.this, android.R.layout.simple_dropdown_item_1line, categories);
        spCategory = findViewById(R.id.spCategory);
        spCategory.setAdapter(arrayAdapter);

        //Update Book
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            Book book = AppDatabase.getAppDatabase(AddBookActivity.this).bookDAO().findByID(bundle.getString("id"));
            etBookName.setText(book.getBookName());
            etAuthorName.setText(book.getAuthorName());
            etDescription.setText(book.getDescription());

            Glide.with(this)
                    .load(book.getBookImg())
                    .into(ivBookPhoto);

            if(book.getCategory().equals("drama")) spCategory.setSelection(0);
            else if(book.getCategory().equals("comedy")) spCategory.setSelection(1);
            else if(book.getCategory().equals("romance")) spCategory.setSelection(2);
            else if(book.getCategory().equals("comic")) spCategory.setSelection(3);
            else spCategory.setSelection(4);
            final String saved = book.getSaved();
            final int id = book.getId();
            btnSave.setText("Update");
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AppDatabase.getAppDatabase(AddBookActivity.this).bookDAO().updateBook(
                            new Book(
                                    id,
                            etBookName.getText().toString(),
                            bookFilePath,
                            etAuthorName.getText().toString(),
                            spCategory.getSelectedItem().toString(),
                            etDescription.getText().toString(),
                            saved)

                    );
                    finish();
                    //startActivity(new Intent(AddBookActivity.this, BookDetailActivity.class));
                }
            });

        } else {//ADD new book
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.i("BookFilePath", bookFilePath);
                    AppDatabase.getAppDatabase(AddBookActivity.this).bookDAO().insert(new Book(
                            etBookName.getText().toString(),
                            bookFilePath,
                            etAuthorName.getText().toString(),
                            spCategory.getSelectedItem().toString(),
                            etDescription.getText().toString(),
                            "false"
                    ));
                    finish();
                    //startActivity(new Intent(AddBookActivity.this, MainActivity.class));

                }
            });
        }

        ivBookPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                pickFromGallery();
                Log.i("Book", bookFilePath);
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

                    bookFilePath = cursor.getString(columnIndex);
                    cursor.close();

                    ivBookPhoto.setImageBitmap(BitmapFactory.decodeFile(bookFilePath));
                    break;
            }
    }
}
