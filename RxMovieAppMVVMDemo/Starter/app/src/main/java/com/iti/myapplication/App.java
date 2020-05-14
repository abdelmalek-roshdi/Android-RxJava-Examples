package com.iti.myapplication;

import android.app.Application;

import com.iti.myapplication.data.db.MovieDatabase;

public class App extends Application {
    public static MovieDatabase db ;
    @Override
    public void onCreate() {
        super.onCreate();
        db = MovieDatabase.getInstance(this);
    }
}
