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

    @Query("SELECT * FROM book where id = :id")
    Book findByID(String id);

    @Query("SELECT * FROM book where authorName = :authorName")
    List<Book> findByAuthorName(String authorName);

    @Query("SELECT * FROM book where id = :userId")
    List<Book> getBooksByUserId(int userId);

    @Query("SELECT * FROM book where id IN (:bookIds)")
    List<Book> getBooksByBookIds(int[] bookIds);

    @Insert
    void insert(Book... book);

    @Delete
    void delete(Book... book);

    @Update
    void updateBook(Book... book);


}
