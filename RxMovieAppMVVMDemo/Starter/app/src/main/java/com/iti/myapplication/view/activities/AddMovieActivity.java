package com.iti.myapplication.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.iti.myapplication.R;
import com.iti.myapplication.data.model.Movie;
import com.iti.myapplication.viewmodel.AddViewModel;
import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import static com.iti.myapplication.view.activities.SearchActivity.EXTRA_POSTER_PATH;
import static com.iti.myapplication.view.activities.SearchActivity.EXTRA_RELEASE_DATE;
import static com.iti.myapplication.view.activities.SearchActivity.EXTRA_TITLE;
import static com.iti.myapplication.view.activities.SearchActivity.SEARCH_QUERY;

public class AddMovieActivity extends AppCompatActivity {

  private EditText titleEditText;
  private EditText releaseDateEditText;
  private ImageView movieImageView;
  private AddViewModel viewModel;
  public static final int SEARCH_MOVIE_ACTIVITY_REQUEST_CODE = 2;
  String posterPath;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_movie);
    setupViews();
    viewModel = ViewModelProviders.of(this).get(AddViewModel.class);
   /*
    viewModel.getMutableMovieLiveData().observe(this, new Observer<Movie>() {
      @Override
      public void onChanged(Movie movie) {
       // titleEditText.setText(movie.getTitle());
       // releaseDateEditText.setText(movie.getReleaseDate());
        Picasso.get().load("https://image.tmdb.org/t/p/w500/"+movie.getPosterPath()).into(movieImageView);
        //posterPath ="https://image.tmdb.org/t/p/w500/"+data.getStringExtra(EXTRA_POSTER_PATH);
      }
    });
    */

  }


  private void setupViews() {
    titleEditText = findViewById(R.id.movie_title);
    releaseDateEditText = findViewById(R.id.movie_release_date);
    movieImageView = findViewById(R.id.movie_imageview);
  }

  public void returnToMain() {
    finish();
  }

  public void displayMessage(String message) {
    showToast(message);
  }

  public void displayError(String message) {
    showToast(message);
  }

  void showToast(String message){
    Toast.makeText(AddMovieActivity.this,message,Toast.LENGTH_SHORT).show();
  }

  public void goToSearchMovieActivity(View view) {
    if (!TextUtils.isEmpty(titleEditText.getText().toString())) {
      startActivityForResult(new Intent(AddMovieActivity.this, SearchActivity.class).putExtra(SEARCH_QUERY, titleEditText.getText().toString()), SEARCH_MOVIE_ACTIVITY_REQUEST_CODE);
    }else {displayError("please enter a movie name to search for");}
  }

  public void onClickAddMovie(View view) {


    if (!TextUtils.isEmpty(titleEditText.getText().toString())){
      Movie movie = new Movie(titleEditText.getText().toString(),posterPath,releaseDateEditText.getText().toString());
      viewModel.addMovie(movie);
      displayMessage("movie added successfully");
      returnToMain();
    }else {displayError("movie not added");}

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == SEARCH_MOVIE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
      titleEditText.setText(data.getStringExtra(EXTRA_TITLE));
      releaseDateEditText.setText(data.getStringExtra(EXTRA_RELEASE_DATE));
      Picasso.get().load("https://image.tmdb.org/t/p/w500/"+data.getStringExtra(EXTRA_POSTER_PATH)).into(movieImageView);
      posterPath ="https://image.tmdb.org/t/p/w500/"+data.getStringExtra(EXTRA_POSTER_PATH);
    }

  }
}
