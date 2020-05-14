package com.iti.myapplication.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.myapplication.R;

import com.iti.myapplication.data.model.Movie;
import com.iti.myapplication.view.activities.SearchActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchMoviesHolder> {

  private List<Movie> movieList;
  private Context context;
  private SearchActivity.RecyclerItemListener listener;

  public SearchAdapter(
          List<Movie> movieList, Context context, SearchActivity.RecyclerItemListener listener) {
    this.movieList = movieList;
    this.context = context;
    this.listener = listener;
  }

  @NonNull
  @Override
  public SearchMoviesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.item_movie_details, parent, false);

    final SearchMoviesHolder viewHolder = new SearchMoviesHolder(view);
    view.setOnClickListener(
            v -> listener.onItemClick(v, viewHolder.getAdapterPosition()));
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull SearchMoviesHolder holder, int position) {
    holder.titleTextView.setText(movieList.get(position).getTitle());
    holder.releaseDateTextView.setText(movieList.get(position).getReleaseYearFromDate());
    holder.overviewTextView.setText(movieList.get(position).getOverview());

    if (movieList.get(position).getPosterPath() != null) {
      Picasso.get()
              .load("https://image.tmdb.org/t/p/w500/" + movieList.get(position).getPosterPath())
              .into(holder.movieImageView);
    }
  }

  @Override
  public int getItemCount() {
    return movieList.size();
  }

  public Movie getItemAtPosition(int pos) {
    return movieList.get(pos);
  }

  class SearchMoviesHolder extends RecyclerView.ViewHolder {
    private TextView titleTextView;
    private TextView overviewTextView;
    private TextView releaseDateTextView;
    private ImageView movieImageView;

    SearchMoviesHolder(@NonNull View itemView) {
      super(itemView);

      titleTextView = itemView.findViewById(R.id.title_textview);
      overviewTextView = itemView.findViewById(R.id.overview_overview);
      releaseDateTextView = itemView.findViewById(R.id.release_date_textview);
      movieImageView = itemView.findViewById(R.id.movie_imageview);
      itemView.setOnClickListener(
              new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  listener.onItemClick(v, getAdapterPosition());
                }
              });
    }
  }
}
