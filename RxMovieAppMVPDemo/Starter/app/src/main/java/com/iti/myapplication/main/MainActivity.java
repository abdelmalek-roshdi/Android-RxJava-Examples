package com.iti.myapplication.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.iti.myapplication.R;
import com.iti.myapplication.add.AddMovieActivity;
import com.iti.myapplication.model.LocalDataSource;
import com.iti.myapplication.model.Movie;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity implements MainContract.ViewInterface {
    private LinearLayout no_movies_layout;
    private RecyclerView movies_recyclerview;
    private FloatingActionButton fab;
    private MainAdapter mainAdapter;
    private MainContract.PresenterInterface presenter;
    private LocalDataSource localDataSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setupDataSource();
        setupPresenter();


    }

    private void setupDataSource() {
        if (localDataSource == null)
            localDataSource = new LocalDataSource(getApplication());
    }

    private void setupPresenter() {
        presenter = new MainPresenter(localDataSource, this);
        presenter.getMyMoviesList();
    }

    private void initViews() {
        no_movies_layout = findViewById(R.id.no_movies_layout);
        movies_recyclerview = findViewById(R.id.movies_recyclerview);
        fab = findViewById(R.id.fab);

    }

    @Override
    public void displayMovies(List<Movie> movieList) {
        if (movieList.size() > 0) {
            no_movies_layout.setVisibility(View.INVISIBLE);
            mainAdapter = new MainAdapter(MainActivity.this, movieList);
            movies_recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            movies_recyclerview.setAdapter(mainAdapter);
            movies_recyclerview.setVisibility(VISIBLE);

        } else {
            no_movies_layout.setVisibility(View.VISIBLE);
            movies_recyclerview.setVisibility(INVISIBLE);
        }
    }

    @Override
    public void displayNoMovies() {
        showNoMovies();
    }

    @Override
    public void displayMessage(String message) {
        showToast(message);
    }

    @Override
    public void displayError(String message) {
        showToast(message);
    }

    void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    void showNoMovies() {
        no_movies_layout.setVisibility(View.VISIBLE);
        movies_recyclerview.setVisibility(INVISIBLE);
    }

    void showMovies() {
        no_movies_layout.setVisibility(INVISIBLE);
        movies_recyclerview.setVisibility(VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AddMovieActivity.SEARCH_MOVIE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            if (data != null && data.hasExtra(AddMovieActivity.TitileKey)) {

                // Movie movie = new Movie(data.getStringExtra(AddMovieActivity.TitileKey),data.getStringExtra(AddMovieActivity.PosterPathKey),data.getStringExtra(AddMovieActivity.ReleaseDateKey));
                //localDataSource.insert(movie);
                //Toast.makeText(MainActivity.this,"movie added successfully",Toast.LENGTH_SHORT).show();
                //presenter.
            } else {
                Toast.makeText(MainActivity.this, "movie added successfully", Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.deleteMenuItem) {
            presenter.onDelete(mainAdapter.movieHashSet);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.getMyMoviesList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.stop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    public void goToAddMovieActivity(View view) {
        startActivity(new Intent(MainActivity.this, AddMovieActivity.class));
    }
}
