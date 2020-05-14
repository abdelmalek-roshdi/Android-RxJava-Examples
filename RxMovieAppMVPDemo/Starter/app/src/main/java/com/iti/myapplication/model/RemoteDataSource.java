package com.iti.myapplication.model;

import com.iti.myapplication.network.RetrofitClient;

import io.reactivex.Single;
import retrofit2.Call;

public class RemoteDataSource {

  public Single<TmdbResponse> search(String query) {
    return RetrofitClient
        .getInstance()
        .getMoviesApi()
        .searchMovie(RetrofitClient.API_KEY, query);
  }
}
