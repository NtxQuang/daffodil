package com.example.readnews.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.readnews.News;

import java.util.List;

@Dao
public interface NewsDao {
    @Query("select * from News")
    List<News> getAll();

    @Query("select * from News where isFavorited like :isFavorite")
    List<News> getFavNews(String isFavorite);

    @Insert
    void insert(News...news);

    @Delete
    void delete(News news);

    @Update
    void update(News news);
}


