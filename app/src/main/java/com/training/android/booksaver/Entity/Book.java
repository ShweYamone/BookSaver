package com.training.android.booksaver.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "book")
public class Book{

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "bookName")
    private String bookName;

    @ColumnInfo(name = "bookImg")
    private String bookImg;

    @ColumnInfo(name = "authorName")
    private String authorName;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "saved")
    private String saved;

    public Book(String bookName, String bookImg, String authorName, String category, String description, String saved) {
        this.bookName = bookName;
        this.bookImg = bookImg;
        this.authorName = authorName;
        this.category = category;
        this.description = description;
        this.saved = saved;
    }

    @Ignore
    public Book(int id, String bookName, String bookImg, String authorName, String category, String description, String saved) {
        this.id = id;
        this.bookName = bookName;
        this.bookImg = bookImg;
        this.authorName = authorName;
        this.category = category;
        this.description = description;
        this.saved = saved;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookImg() {
        return bookImg;
    }

    public void setBookImg(String bookImg) {
        this.bookImg = bookImg;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSaved() {
        return saved;
    }

    public void setSaved(String saved) {
        this.saved = saved;
    }
}

