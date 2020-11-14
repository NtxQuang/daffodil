package com.example.readnews.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.readnews.News;

@Database(entities = {News.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract NewsDao newsDao();

    private static AppDataBase appDataBase;
    public static AppDataBase getInstance(Context context){
        if (appDataBase== null){
            appDataBase= Room.databaseBuilder(context,AppDataBase.class,"QuanLiTinTuc").allowMainThreadQueries().build();
        }
        return appDataBase;
    }
}
