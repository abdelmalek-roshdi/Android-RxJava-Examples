package com.iti.myapplication.viewmodel;

import com.iti.myapplication.data.MovieRepository;
import com.iti.myapplication.data.MovieRepositoryImpl;
import com.iti.myapplication.data.model.Movie;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.Emitter;
import io.reactivex.Observable;
import io.reactivex.Single;

public class AddViewModel extends ViewModel {
    Observable<Movie> mutableMovieLiveData ;
    Emitter<Movie> movieEmitter;
    MovieRepository movieRepository;
    public AddViewModel() {
        movieRepository = new MovieRepositoryImpl();
        mutableMovieLiveData = Observable.create( emitter -> {movieEmitter = emitter; });
    }
    public void addMovie(Movie movie){
        movieRepository.saveMovie(movie);
       // if (movieEmitter != null) {
           // movieEmitter.onNext(movie);
        //}
    }

    public Observable<Movie> getMutableMovieLiveData() {
        return mutableMovieLiveData;
    }
}
