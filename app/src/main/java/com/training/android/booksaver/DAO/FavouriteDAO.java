package com.training.android.booksaver.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.training.android.booksaver.Entity.Favourite;

@Dao
public interface FavouriteDAO {

    @Query("SELECT COUNT(*) from favourite where userId = :userId AND bookId = :bookId")
    int getFavourite(int userId, int bookId);

    @Query("SELECT bookId from favourite where userId = :userId")
    int[] getBookIdsbyUserId(int userId);

    @Insert
    void insert(Favourite... favourite);

    @Query("DELETE FROM favourite WHERE userId = :userId AND bookId = :bookId")
    void delete(int userId, int bookId);
}
