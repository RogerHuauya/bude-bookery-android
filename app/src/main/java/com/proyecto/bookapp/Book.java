package com.proyecto.bookapp;

import com.google.gson.annotations.SerializedName;

public class Book {
    @SerializedName("_id")
    private String id;

    @SerializedName("author")
    private String author;

    @SerializedName("title")
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }
}
