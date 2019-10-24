package com.training.android.booksaver.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.training.android.booksaver.DB.AppDatabase;
import com.training.android.booksaver.Entity.Book;
import com.training.android.booksaver.R;
import com.training.android.booksaver.activity.AddBookActivity;
import com.training.android.booksaver.adapter.BookAdapter;

import java.util.List;

public class FavouriteFragment extends Fragment {

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";


    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private List<Book> bookList;

    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_favourite, container, false);
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        //show books with recyclerview
        recyclerView = view.findViewById(R.id.recycler_view);

        int[] bookIds = AppDatabase.getAppDatabase(getActivity()).favouriteDAO().getBookIdsbyUserId(
                sharedpreferences.getInt("id", -100)
        );
        bookList = AppDatabase.getAppDatabase(getActivity()).bookDAO().getBooksByBookIds(bookIds);

        adapter = new BookAdapter(getActivity(), bookList);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this.getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onResume() {
        int[] bookIds = AppDatabase.getAppDatabase(getActivity()).favouriteDAO().getBookIdsbyUserId(
            sharedpreferences.getInt("id", -100)
        );
        bookList = AppDatabase.getAppDatabase(getActivity()).bookDAO().getBooksByBookIds(bookIds);
        adapter = new BookAdapter(getActivity(), bookList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        super.onResume();
    }

}
