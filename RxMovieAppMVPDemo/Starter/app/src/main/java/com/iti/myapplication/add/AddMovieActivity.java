package com.iti.myapplication.add;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.iti.myapplication.R;
import com.iti.myapplication.model.LocalDataSource;
import com.iti.myapplication.model.Movie;
import com.iti.myapplication.search.SearchActivity;
import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.iti.myapplication.network.RetrofitClient.TMDB_IMAGEURL;
import static com.iti.myapplication.search.SearchActivity.EXTRA_POSTER_PATH;
import static com.iti.myapplication.search.SearchActivity.EXTRA_RELEASE_DATE;
import static com.iti.myapplication.search.SearchActivity.EXTRA_TITLE;
import static com.iti.myapplication.search.SearchActivity.SEARCH_QUERY;

public class AddMovieActivity extends AppCompatActivity implements AddMovieContract.ViewInterface {

  private EditText titleEditText;
  private EditText releaseDateEditText;
  private ImageView movieImageView;
  private LocalDataSource dataSource;
  public static final int SEARCH_MOVIE_ACTIVITY_REQUEST_CODE = 2;
  private AddMovieContract.PresenterInterface addMoviePresenter;
  private String posterPath;
  public static final String TitileKey = "title";
  public static final String PosterPathKey = "posterPath";
  public static final String ReleaseDateKey = "releaseDate";
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_movie);
    setupViews();
    setupDataSource();
    setupPresnnter();

  }

  private void setupDataSource() {
    if (dataSource == null)
    dataSource = new LocalDataSource(getApplication());
  }

  private void setupPresnnter() {
    addMoviePresenter = new AddMoviePresenter(dataSource,this);
  }

  private void setupViews() {
    titleEditText = findViewById(R.id.movie_title);
    releaseDateEditText = findViewById(R.id.movie_release_date);
    movieImageView = findViewById(R.id.movie_imageview);
  }

  @Override
  public void returnToMain() {
    finish();
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
    Toast.makeText(AddMovieActivity.this,message,Toast.LENGTH_SHORT).show();
  }

  public void goToSearchMovieActivity(View view) {
    startActivityForResult(new Intent(AddMovieActivity.this,SearchActivity.class).putExtra(SEARCH_QUERY,titleEditText.getText().toString()),SEARCH_MOVIE_ACTIVITY_REQUEST_CODE);
  }

  public void onClickAddMovie(View view) {


    if (!TextUtils.isEmpty(titleEditText.getText().toString())){
      addMoviePresenter.addMovie(titleEditText.getText().toString(),releaseDateEditText.getText().toString(),posterPath);
    displayMessage("movie added successfully");
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
