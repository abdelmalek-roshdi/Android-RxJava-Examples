package com.iti.myapplication.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.iti.myapplication.R;

import com.iti.myapplication.data.model.LocalDataSource;
import com.iti.myapplication.data.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.List;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;



public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MoviesHolder> {
  Context context;
  public List<Movie> movieList;
  LayoutInflater inflater;
  public HashSet<Movie> movieHashSet;
  public MainAdapter(Context context ) {
    this.context = context;
    this.movieList = movieList;
    inflater = LayoutInflater.from(context);
    movieHashSet = new HashSet<>();
  }

  @NonNull
  @Override
  public MoviesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = inflater.inflate(R.layout.item_movie_main,parent,false);
    MoviesHolder moviesHolder = new MoviesHolder(view);
    return moviesHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull MoviesHolder holder, int position) {
    holder.title_textview.setText(movieList.get(position).getTitle());
    holder.release_date_textview.setText(movieList.get(position).getReleaseDate());
    holder.checkbox.setChecked(false);
    if (movieList.get(position).getPosterPath()!=null&&!movieList.get(position).getPosterPath().endsWith(".jpg")){
      holder.movie_imageview.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_local_movies_gray));
    }else {
      Picasso.get().load(movieList.get(position).getPosterPath()).into(holder.movie_imageview);
    }
    holder.checkbox.setOnCheckedChangeListener((compoundButton, b) -> {
      if (b){
        movieHashSet.add(movieList.get(position));
      }else {
        movieHashSet.remove(movieList.get(position));
      }
    });
  }

  public void steMovies(List<Movie> movies){
    movieList = movies;
    notifyDataSetChanged();
  }


  @Override
  public int getItemCount() {
    return movieList.size();
  }


  class MoviesHolder extends RecyclerView.ViewHolder {
    ImageView movie_imageview;
    TextView title_textview,release_date_textview;
    CheckBox checkbox;

    public MoviesHolder(@NonNull View itemView) {
      super(itemView);
      movie_imageview = itemView.findViewById(R.id.movie_imageview);
      title_textview = itemView.findViewById(R.id.title_textview);
      release_date_textview = itemView.findViewById(R.id.release_date_textview);
      checkbox = itemView.findViewById(R.id.checkbox);
    }
  }

}
