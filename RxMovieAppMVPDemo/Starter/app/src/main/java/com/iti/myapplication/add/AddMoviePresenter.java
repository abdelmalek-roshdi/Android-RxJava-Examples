package com.iti.myapplication.add;



import com.iti.myapplication.model.LocalDataSource;
import com.iti.myapplication.model.Movie;

public class AddMoviePresenter implements AddMovieContract.PresenterInterface {
    LocalDataSource localDataSource;
    AddMovieContract.ViewInterface view;

    public AddMoviePresenter(LocalDataSource dataSource,AddMovieContract.ViewInterface view) {
        this.localDataSource = dataSource;
        this.view = view;
    }

    @Override
    public void addMovie(String title, String releaseDate, String posterPath) {
        Movie movie = new Movie(title,posterPath,releaseDate);
        localDataSource.insert(movie);
        view.displayMessage("");
        view.returnToMain();
    }
}
