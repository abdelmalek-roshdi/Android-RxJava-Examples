package com.iti.myapplication.viewmodel;

import com.iti.myapplication.data.MovieRepository;
import com.iti.myapplication.data.MovieRepositoryImpl;
import com.iti.myapplication.data.model.Movie;
import com.iti.myapplication.data.model.MoviesResponse;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.Single;

public class SearchViewModel extends ViewModel {
    MovieRepository movieRepository;

    LiveData<List<Movie>> listLiveData;

    public SearchViewModel() {
        movieRepository = new MovieRepositoryImpl();

    }

    public Single<MoviesResponse> searchMovies(String query){
        return movieRepository.searchMovies(query);
        //listLiveData =liveData;

    }

    public LiveData<List<Movie>> getListLiveData() {
        return listLiveData;

    }
}
