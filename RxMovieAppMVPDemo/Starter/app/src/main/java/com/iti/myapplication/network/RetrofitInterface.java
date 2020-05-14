package com.iti.myapplication.network;

import com.iti.myapplication.model.TmdbResponse;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {
  @GET("search/movie")
  Single<TmdbResponse> searchMovie(@Query("api_key") String apiKey, @Query("query") String query);
}
