package com.iti.myapplication.data;

import android.util.Log;

import com.iti.myapplication.App;
import com.iti.myapplication.data.db.MovieDao;
import com.iti.myapplication.data.model.Movie;
import com.iti.myapplication.data.model.MoviesResponse;
import com.iti.myapplication.data.net.RetrofitClient;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.iti.myapplication.App.db;

public class MovieRepositoryImpl implements MovieRepository {
  public static final String API_KEY = "9ba2a10e20c15ef6fb276c33692ba9e8";
  private RetrofitClient retrofitClient;
  private MutableLiveData<List<Movie>> allMovies = new MutableLiveData<>();

  public MovieRepositoryImpl() {
    retrofitClient = RetrofitClient.getInstance();
  }

  @Override
  public Single<List<Movie>> getSavedMovies() {
    return db.movieDao().getAll();
  }

  @Override
  public void saveMovie(Movie movie) {
    db.movieDao().insert(movie);
  }

  @Override
  public void deleteMovie(Movie movie) {
    db.movieDao().delete(movie.getId());
  }

  @Override
  public Single<MoviesResponse> searchMovies(String query) {

    return retrofitClient.getMoviesApi().searchMovie(API_KEY, query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());

  }
}
