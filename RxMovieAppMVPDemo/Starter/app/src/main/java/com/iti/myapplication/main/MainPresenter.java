package com.iti.myapplication.main;


import com.iti.myapplication.model.LocalDataSource;
import com.iti.myapplication.model.Movie;

import java.util.HashSet;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements MainContract.PresenterInterface {
    private LocalDataSource localDataSource;
    private MainContract.ViewInterface view;
    private CompositeDisposable compositeDisposable;
    public MainPresenter(LocalDataSource dataSource, MainContract.ViewInterface view) {
        localDataSource = dataSource;
        this.view = view;
        compositeDisposable = new CompositeDisposable();


    }

    @Override
    public void getMyMoviesList() {
        if (view != null) {
             compositeDisposable.add(localDataSource.allMovies()
                     .subscribeOn(Schedulers.io())
                     .observeOn(AndroidSchedulers.mainThread())
                     .subscribe(list -> {
                         view.displayMovies(list);
                     }
                     ));

        }

    }

    @Override
    public void onDelete(HashSet<Movie> selectedMovies) {
        for (Movie movie : selectedMovies) {
            localDataSource.delete(movie);
        }
        getMyMoviesList();

    }

    @Override
    public void stop() {
        //release resources
        if (compositeDisposable.size() > 0 && !compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }
}
