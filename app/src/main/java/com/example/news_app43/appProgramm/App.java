package com.example.news_app43.appProgramm;

import android.app.Application;

import androidx.room.Room;

import com.example.news_app43.room.AppDataBase;

public class App extends Application {
    private static AppDataBase dataBase;

    @Override
    public void onCreate() {
        super.onCreate();
        dataBase = Room.databaseBuilder(this,AppDataBase.class,"mydatabase").allowMainThreadQueries()
                .build();
    }

    public static AppDataBase getDataBase() {
        return dataBase;
    }
}
