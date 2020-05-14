package com.iti.myapplication.data;

import com.iti.myapplication.data.model.Movie;
import com.iti.myapplication.data.model.MoviesResponse;

import java.util.List;

import androidx.lifecycle.LiveData;

import io.reactivex.Single;

public interface MovieRepository {

  Single<List<Movie>> getSavedMovies();

  void saveMovie(Movie movie);

  void deleteMovie(Movie movie);

  Single<MoviesResponse> searchMovies(String query);
}
