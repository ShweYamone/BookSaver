package com.training.android.booksaver.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.training.android.booksaver.DB.AppDatabase;
import com.training.android.booksaver.Entity.Book;
import com.training.android.booksaver.R;

import java.io.File;

public class BookDetailActivity extends AppCompatActivity {
    public ImageView bookImg;
    public TextView bookName, authorName, category, description;
    public Button btnEdit, btnDelete;
    private Book book;
    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        if(getIntent().getExtras().getString("id") != null) {
            id = getIntent().getExtras().getString("id");
        }
        bookImg = findViewById(R.id.ivBookImg);
        bookName = findViewById(R.id.tvBookName);
        authorName = findViewById(R.id.tvAuthorName);
        category = findViewById(R.id.tvCategory);
        description = findViewById(R.id.tvDescription);

        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        book = AppDatabase.getAppDatabase(BookDetailActivity.this).bookDAO().findByID(id);
        Log.i("Book", book.getBookName());
        Log.i("Book1", book.getAuthorName());
        bookName.setText(book.getBookName());
        authorName.setText(book.getAuthorName());
        category.setText(book.getCategory());
        description.setText(book.getDescription());

        if(book.getBookImg().equals("")) {
            Glide.with(this).load(R.drawable.bookcover).into(bookImg);
        }
        else {

            bookImg.setImageBitmap(BitmapFactory.decodeFile(book.getBookImg()));
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookDetailActivity.this, AddBookActivity.class);
                intent.putExtra("id", id +"");
                startActivity(intent);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BookDetailActivity.this);
                builder.setTitle("Delete");
                builder.setMessage("Are you sure to delete?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppDatabase.getAppDatabase(BookDetailActivity.this).bookDAO().delete(book);
                        finish();
                    //    startActivity(new Intent(BookDetailActivity.this, MainActivity.class));
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

    }

    @Override
    protected void onResume() {

        book = AppDatabase.getAppDatabase(BookDetailActivity.this).bookDAO().findByID(id);
        Log.i("Book", book.getBookName());
        Log.i("Book1", book.getAuthorName());
        bookName.setText(book.getBookName());
        authorName.setText(book.getAuthorName());
        category.setText(book.getCategory());
        description.setText(book.getDescription());

        if(book.getBookImg().equals("")) {
            Glide.with(this).load(R.drawable.bookcover).into(bookImg);
        }
        else {
            Log.i("BookFilePath", book.getBookImg());
            Uri uri = Uri.fromFile(new File("content://com.miui.gallery.open/raw/%2Fstorage%2Femulated%2F0%2FDCIM%2FCamera%2FIMG_20191023_125854.jpg"));
            Glide.with(this).load(new File(book.getBookImg())).into(bookImg);
            // bookImg.setImageURI(uri);
        }
        super.onResume();
    }
}
