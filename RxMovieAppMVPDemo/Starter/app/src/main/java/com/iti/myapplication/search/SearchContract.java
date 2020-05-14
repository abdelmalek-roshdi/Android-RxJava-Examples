package com.iti.myapplication.search;

import com.iti.myapplication.model.TmdbResponse;

interface SearchContract {
    interface PresenterInterface {

        void getSearchResults(String query);

        void stop();
    }

    interface ViewInterface {
        void displayResult(TmdbResponse tmdbResponse);

        void displayMessage(String message);

        void displayError(String message);
    }
}
