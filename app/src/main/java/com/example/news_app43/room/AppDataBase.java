package com.example.news_app43.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.news_app43.models.Article;

@Database(entities = {Article.class},version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract ArticleDao articleDao();
}
