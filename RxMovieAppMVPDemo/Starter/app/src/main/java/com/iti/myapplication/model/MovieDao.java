package com.iti.myapplication.model;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import io.reactivex.Single;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MovieDao {

  @Query("SELECT * FROM movie_table")
  Single<List<Movie>> getAll();

  @Insert(onConflict = REPLACE)
  void insert(Movie movie);

  @Query("DELETE FROM movie_table WHERE id = :id")
  void delete(Integer id);

  @Query("DELETE FROM movie_table")
  void deleteAll();

  @Update
  void update(Movie movie);
}
