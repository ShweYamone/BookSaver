package com.training.android.booksaver.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.training.android.booksaver.Entity.Book;

import java.util.List;

@Dao
public interface BookDAO {

    @Query("SELECT * FROM book")
    List<Book> getAll();

    @Query("SELECT * FROM book where category LIKE :category")
    List<Book> getByCategory(String category);

    @Query("SELECT * FROM book where saved = :saved")
    List<Book> getBySaved(String saved);

    @Query("SELECT * FROM book where id = :id")
    Book findByID(String id);

    @Query("SELECT COUNT(*) from book")
    int countBooks();

    @Insert
    void insert(Book book);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Book... books);

    @Delete
    void delete(Book book);

    @Update
    public void updateBook(Book book);


}
