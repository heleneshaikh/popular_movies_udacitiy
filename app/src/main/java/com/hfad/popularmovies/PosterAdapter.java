package com.hfad.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.ViewHolder> {

    private Context context;
    private List<Movie> movieList;

    public void setMovieList(List<Movie> movieList) {
        this.movieList.clear();
        this.movieList.addAll(movieList);
        notifyDataSetChanged();
    }

    public PosterAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = new ArrayList<>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.iv_cardview);
        }
    }

    @Override
    public PosterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.poster_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PosterAdapter.ViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        Picasso.with(context)
                .load("https://image.tmdb.org/t/p/w185/" + movie.getPoster_path())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return (movieList.isEmpty() ? 0 : movieList.size());
    }


}
