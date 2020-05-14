package com.iti.myapplication.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.iti.myapplication.R;
import com.iti.myapplication.data.model.Movie;
import com.iti.myapplication.view.adapters.MainAdapter;
import com.iti.myapplication.viewmodel.AddViewModel;
import com.iti.myapplication.viewmodel.MainViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

  private RecyclerView moviesRecyclerView;
  private MainAdapter adapter;
  private FloatingActionButton fab;
  private LinearLayout noMoviesLayout;
  private final String TAG = "MainActivity";
  private MainViewModel viewModel;
  private AddViewModel addViewModel;

  public static final int ADD_MOVIE_ACTIVITY_REQUEST_CODE = 1;

  private LinearLayout no_movies_layout;
  private RecyclerView movies_recyclerview;

  private MainAdapter mainAdapter;


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initViews();
    viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    addViewModel = ViewModelProviders.of(this).get(AddViewModel.class);

//    viewModel.getListMediatorLiveData().observe(this, new Observer<List<Movie>>() {
//      @Override
//      public void onChanged(List<Movie> movies) {
//        displayMovies(movies);
//      }
//    });

    viewModel.getListMediatorLiveData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( list -> displayMovies(list));

    addViewModel.getMutableMovieLiveData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( movie -> {
              mainAdapter.movieList.add(movie);
              mainAdapter.notifyItemInserted(mainAdapter.movieList.indexOf(movie));
            });


  }

  @Override
  protected void onResume() {
    super.onResume();
//    viewModel.getListMediatorLiveData()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe( list -> displayMovies(list));


  }

  private void initViews() {
    no_movies_layout = findViewById(R.id.no_movies_layout);
    movies_recyclerview = findViewById(R.id.movies_recyclerview);
    fab = findViewById(R.id.fab);

  }


  public void displayMovies(List<Movie> movieList) {
    if (movieList != null && movieList.size() > 0 ) {
      no_movies_layout.setVisibility(View.INVISIBLE);
      mainAdapter = new MainAdapter(MainActivity.this);
      movies_recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));
      mainAdapter.steMovies(movieList);
      movies_recyclerview.setAdapter(mainAdapter);
      movies_recyclerview.setVisibility(VISIBLE);

    } else {
      no_movies_layout.setVisibility(View.VISIBLE);
      movies_recyclerview.setVisibility(INVISIBLE);
    }
  }


  public void displayNoMovies() {
    showNoMovies();
  }


  public void displayMessage(String message) {
    showToast(message);
  }


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

    /*
      if (data != null && data.hasExtra(AddMovieActivity.TitileKey)) {

        // Movie movie = new Movie(data.getStringExtra(AddMovieActivity.TitileKey),data.getStringExtra(AddMovieActivity.PosterPathKey),data.getStringExtra(AddMovieActivity.ReleaseDateKey));
        //localDataSource.insert(movie);
        //Toast.makeText(MainActivity.this,"movie added successfully",Toast.LENGTH_SHORT).show();
        //presenter.
      } else {
        Toast.makeText(MainActivity.this, "movie added successfully", Toast.LENGTH_SHORT).show();
      }
     */

    }

  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.deleteMenuItem) {
     // presenter.onDelete(mainAdapter.movieHashSet);
      viewModel.deleteMovie(mainAdapter.movieHashSet);
//      viewModel.getListMediatorLiveData()
//              .subscribeOn(Schedulers.io())
//              .observeOn(AndroidSchedulers.mainThread())
//              .subscribe( list -> displayMovies(list));


    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onStart() {
    super.onStart();
    //presenter.getMyMoviesList();
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

