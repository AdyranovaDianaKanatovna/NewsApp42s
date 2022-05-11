package com.example.news_app43.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.news_app43.models.Article;

import java.util.List;

@Dao
public interface ArticleDao {
    @Query("SELECT * FROM article")
    List<Article> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void Insert(Article article);

    @Query("SELECT * FROM article ORDER BY text ASC")
    List<Article> sort();

    @Query("SELECT * FROM article WHERE text LIKE '%' || :search || '%'")
    List<Article> getSearch(String search);

    @Delete
    void Delete(Article article);

}
