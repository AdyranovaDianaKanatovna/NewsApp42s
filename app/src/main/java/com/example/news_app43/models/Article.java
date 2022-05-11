package com.example.news_app43.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity
public class Article implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String text;
    private long date;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Article(String text, long date) {
        this.text = text;
        this.date = date;
    }
}
