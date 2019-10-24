package com.training.android.booksaver.adapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.training.android.booksaver.activity.BookDetailActivity;
import com.training.android.booksaver.Entity.Book;
import com.training.android.booksaver.R;

import java.io.File;
import java.net.URI;
import java.util.List;


public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {

private Context mContext;
private List<Book> booksList;

    private static final String TAG = "BookAdapter";

public class MyViewHolder extends RecyclerView.ViewHolder {
    public ImageView bookImg, saved;
    public TextView bookName;
    Book book;

    public MyViewHolder(View view) {
        super(view);
        bookImg = view.findViewById(R.id.ivBook);
        bookName = view.findViewById(R.id.tvBookName);
        saved = view.findViewById(R.id.ivSaved);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BookDetailActivity.class);
                intent.putExtra("id", book.getId()+"");
                mContext.startActivity(intent);
         }
        });

    }

    public void bindView(Book book){

        this.book = book;
        bookName.setText(book.getBookName());

        File image = new File(book.getBookImg());
        Log.e(TAG, "bindView: "+ book.getBookImg()  );
        if (image.exists()) {
            Log.e(TAG, "bindView: exist" );
            bookImg.setImageBitmap(BitmapFactory.decodeFile(book.getBookImg()));
        }else {
            Log.e(TAG, "bindView: do not exist" );
        }


    }
}

    public BookAdapter(Context mContext, List<Book> booksList) {
        this.mContext = mContext;
        this.booksList = booksList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_book_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Book book = booksList.get(position);
        holder.bindView(book);
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }
}

