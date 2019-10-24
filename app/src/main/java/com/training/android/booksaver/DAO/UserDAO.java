package com.training.android.booksaver.DAO;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.training.android.booksaver.Entity.Book;
import com.training.android.booksaver.Entity.User;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM user where id = :id")
    User findByID(String id);

    @Query("SELECT * FROM user where name = :name and password = :password")
    User findByNameAndPwd(String name, String password);

    @Query("SELECT COUNT(*) from user")
    int countUsers();

    @Insert
    void insert(User user);

    @Update
    void update(User user);
}
