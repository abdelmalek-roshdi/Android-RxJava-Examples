package com.iti.myapplication.add;

interface AddMovieContract {
  interface PresenterInterface {
    void addMovie(String title, String releaseDate, String posterPath);
  }

  interface ViewInterface {
    void returnToMain();

    void displayMessage(String message);

    void displayError(String message);
  }
}
