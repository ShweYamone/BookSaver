package com.training.android.booksaver.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.training.android.booksaver.DB.AppDatabase;
import com.training.android.booksaver.Entity.Book;
import com.training.android.booksaver.Entity.Favourite;
import com.training.android.booksaver.R;

import java.io.File;

public class BookDetailActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";

    public ImageView bookImg;
    public TextView bookName, authorName, category, description;
    public Button btnEdit, btnDelete;
    public ImageView ivFavourite;
    private Book book;
    private String bookId = "";

    private Boolean isFavourite = false;

    private ActionBar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        toolbar = getSupportActionBar();
        toolbar.setTitle("BookDetail");



        if(getIntent().getExtras().getString("id") != null) {
            bookId = getIntent().getExtras().getString("id");
        }
        bookImg = findViewById(R.id.ivBookImg);
        bookName = findViewById(R.id.tvBookName);
        authorName = findViewById(R.id.tvAuthorName);
        category = findViewById(R.id.tvCategory);
        description = findViewById(R.id.tvDescription);

        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        book = AppDatabase.getAppDatabase(BookDetailActivity.this).bookDAO().findByID(bookId);

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
                intent.putExtra("id", bookId +"");
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

        book = AppDatabase.getAppDatabase(BookDetailActivity.this).bookDAO().findByID(bookId);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favourite, menu);


        ivFavourite = (ImageView) menu.findItem(R.id.menu_favourite).getActionView();
        int count = AppDatabase.getAppDatabase(BookDetailActivity.this).favouriteDAO().getFavourite(
                sharedpreferences.getInt("id", -100),
                Integer.parseInt(bookId));
        if(count == 1) {
            Glide.with(BookDetailActivity.this)
                    .load(R.drawable.redheart)
                    .into(ivFavourite);
            isFavourite = true;
        }
        else {
            Glide.with(BookDetailActivity.this)
                    .load(R.drawable.whiteheart)
                    .into(ivFavourite);
        }

        ivFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFavourite) {

                    Glide.with(BookDetailActivity.this)
                            .load(R.drawable.whiteheart)
                            .into(ivFavourite);
                    isFavourite = false;

                    AppDatabase.getAppDatabase(BookDetailActivity.this).favouriteDAO().delete(
                            sharedpreferences.getInt("id", -100),
                            Integer.parseInt(bookId)
                    );

                }
                else {

                    Glide.with(BookDetailActivity.this)
                            .load(R.drawable.redheart)
                            .into(ivFavourite);

                    isFavourite = true;
                    AppDatabase.getAppDatabase(BookDetailActivity.this).favouriteDAO().insert(new Favourite(
                                    sharedpreferences.getInt("id", -100),
                                    Integer.parseInt(bookId)
                            )

                    );
                }
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

 }
