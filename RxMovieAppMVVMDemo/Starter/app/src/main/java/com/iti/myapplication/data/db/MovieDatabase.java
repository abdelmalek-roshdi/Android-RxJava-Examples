package com.iti.myapplication.data.db;

import android.app.Application;

import com.iti.myapplication.data.model.Movie;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(
    entities = {Movie.class},
    version = 1)
@TypeConverters(GenreIdConverter.class)
public abstract class MovieDatabase extends RoomDatabase {
  private static final String DB_NAME = "movie_database";
  private static MovieDatabase INSTANCE;

  public abstract MovieDao movieDao();

  public static MovieDatabase getInstance(Application application) {
    if (MovieDatabase.INSTANCE == null) {
      INSTANCE =
          Room.databaseBuilder(application, MovieDatabase.class, DB_NAME)
              .allowMainThreadQueries()
              .build();
    }

    return INSTANCE;
  }
}
