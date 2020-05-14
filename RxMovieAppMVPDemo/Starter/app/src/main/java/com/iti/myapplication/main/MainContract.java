package com.iti.myapplication.main;

import com.iti.myapplication.model.Movie;

import java.util.HashSet;
import java.util.List;

interface MainContract {
    interface PresenterInterface {
        void getMyMoviesList();

        void onDelete(HashSet<Movie> selectedMovies);

        void stop();
    }

    interface ViewInterface {
        void displayMovies(List<Movie> movieList);

        void displayNoMovies();

        void displayMessage(String message);

        void displayError(String message);

    }
}
