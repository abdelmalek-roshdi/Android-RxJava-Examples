package com.iti.myapplication.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
  public static final String API_KEY = "9ba2a10e20c15ef6fb276c33692ba9e8";
  private static final String TMDB_BASE_URL = "http://api.themoviedb.org/3/";
  public static final String TMDB_IMAGEURL = "https://image.tmdb.org/t/p/w500/";
  private static RetrofitInterface moviesApi;
  private static RetrofitClient INSTANCE;

  public RetrofitInterface getMoviesApi() {
    return moviesApi;
  }

  private RetrofitClient() {}

  public static RetrofitClient getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new RetrofitClient();
      moviesApi =
          new Retrofit.Builder()
              .baseUrl(TMDB_BASE_URL)
              .addConverterFactory(GsonConverterFactory.create())
               .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
              .build()
              .create(RetrofitInterface.class);
    }
    return INSTANCE;
  }
}
