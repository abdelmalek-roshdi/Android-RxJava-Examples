package com.iti.myapplication.data.net;

import androidx.lifecycle.LiveData;

import com.iti.myapplication.data.model.MoviesResponse;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesApi {
  @GET("search/movie")
  Single<MoviesResponse> searchMovie(@Query("api_key") String apiKey, @Query("query") String query);
}
