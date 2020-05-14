package com.iti.myapplication.model;

import android.app.Application;

import java.util.List;

import io.reactivex.Single;

public class LocalDataSource {

  private final MovieDao movieDao;

  public LocalDataSource(Application application) {
    LocalDatabase db = LocalDatabase.getInstance(application);
    this.movieDao = db.movieDao();
  }

  public Single<List<Movie>> allMovies() {
    return movieDao.getAll();
  }

  public void insert(Movie movie) {
    movieDao.insert(movie);
  }

  public void delete(Movie movie) {
    movieDao.delete(movie.getId());
  }

  public void update(Movie movie) {
    movieDao.update(movie);
  }
}
