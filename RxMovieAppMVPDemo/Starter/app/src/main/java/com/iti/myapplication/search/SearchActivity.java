package com.iti.myapplication.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.myapplication.R;
import com.iti.myapplication.model.Movie;
import com.iti.myapplication.model.RemoteDataSource;
import com.iti.myapplication.model.TmdbResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class SearchActivity extends AppCompatActivity implements SearchContract.ViewInterface {
    public static final String SEARCH_QUERY = "searchQuery";
    public static final String EXTRA_TITLE = "SearchActivity.TITLE_REPLY";
    public static final String EXTRA_RELEASE_DATE = "SearchActivity.RELEASE_DATE_REPLY";
    public static final String EXTRA_POSTER_PATH = "SearchActivity.POSTER_PATH_REPLY";

    private final String TAG = "SearchActivity";

    private RecyclerView searchResultsRecyclerView;
    private SearchAdapter adapter;
    private TextView noMoviesTextView;
    private ProgressBar progressBar;
    private String query;
    private SearchPresenter searchPresenter;
    private RemoteDataSource dataSource = new RemoteDataSource();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);
        Intent intent = getIntent();
        query = intent.getStringExtra(SEARCH_QUERY);

        setupViews();
        setupPresenter();
        getSearchResults(query);

    }

    private void setupPresenter() {
        searchPresenter = new SearchPresenter(this,dataSource);
    }

    private void setupViews() {
        searchResultsRecyclerView = findViewById(R.id.search_results_recyclerview);
        noMoviesTextView = findViewById(R.id.no_movies_textview);
        progressBar = findViewById(R.id.progress_bar);
        searchResultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void getSearchResults(final String query) {
        searchPresenter.getSearchResults(query);
    }


    @Override
    public void displayResult(TmdbResponse tmdbResponse) {
        progressBar.setVisibility(INVISIBLE);

        if (tmdbResponse.getTotalResults() == null || tmdbResponse.getTotalResults() == 0) {
            searchResultsRecyclerView.setVisibility(INVISIBLE);
            noMoviesTextView.setVisibility(VISIBLE);
        } else {
            adapter = new SearchAdapter(tmdbResponse.getResults(), this, itemListener);
            searchResultsRecyclerView.setAdapter(adapter);

            searchResultsRecyclerView.setVisibility(VISIBLE);
            noMoviesTextView.setVisibility(INVISIBLE);
        }
    }

    @Override
    public void displayMessage(String message) {
        showToast(message);
    }

    @Override
    public void displayError(String message) {
        showToast(message);
    }
    void showToast(String message){
        Toast.makeText(SearchActivity.this,message,Toast.LENGTH_SHORT).show();
    }

    private RecyclerItemListener itemListener =
            new SearchActivity.RecyclerItemListener() {
                public void onItemClick(View view, int position) {
                    Movie movie = adapter.getItemAtPosition(position);
                    Intent replyIntent = new Intent();
                    replyIntent.putExtra(EXTRA_TITLE, movie.getTitle());
                    replyIntent.putExtra(EXTRA_RELEASE_DATE, movie.getReleaseYearFromDate());
                    replyIntent.putExtra(EXTRA_POSTER_PATH, movie.getPosterPath());
                    setResult(RESULT_OK, replyIntent);

                    finish();
                }
            };

    interface RecyclerItemListener {
        void onItemClick(View v, int position);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        searchPresenter.stop();
    }
}
