package com.iti.myapplication.model;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Movie.class}, version = 1)
@TypeConverters(IntegerListTypeConverter.class)
abstract class LocalDatabase extends RoomDatabase {
    private static final String DB_NAME = "movie_database";
    private static LocalDatabase INSTANCE;

    public abstract MovieDao movieDao();

    public static LocalDatabase getInstance(Application application) {
        if (LocalDatabase.INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(application, LocalDatabase.class, DB_NAME)
                            .allowMainThreadQueries()
                            .build();
        }

        return INSTANCE;
    }
}
