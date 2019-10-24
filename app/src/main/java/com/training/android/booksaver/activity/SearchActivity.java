package com.training.android.booksaver.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;

import com.training.android.booksaver.DB.AppDatabase;
import com.training.android.booksaver.Entity.Book;
import com.training.android.booksaver.R;
import com.training.android.booksaver.adapter.BookAdapter;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private List<Book> bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        recyclerView = findViewById(R.id.recycler_view);

        bookList = AppDatabase.getAppDatabase(this).bookDAO().findByAuthorName("");

        adapter = new BookAdapter(this, bookList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_searchview).getActionView();
        searchView.setIconified(false);
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                bookList = AppDatabase.getAppDatabase(SearchActivity.this).bookDAO().getAll();
                adapter = new BookAdapter(SearchActivity.this, bookList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();


                //   Toast.makeText(this,query,Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
