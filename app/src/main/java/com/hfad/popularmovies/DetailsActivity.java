package com.hfad.popularmovies;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class DetailsActivity extends Activity {
    static final String POSITION = "position";
    static final String FRAGMENT_TYPE = "fragment";
    private Movie movie;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        position = (int) getIntent().getExtras().get(POSITION);
        String fragmentType = getIntent().getStringExtra(FRAGMENT_TYPE);
        if (fragmentType.equals("PopularFragment")) {
            movie = PopularFragment.movieList.get(position);
            setData();
        } else if (fragmentType.equals("TopRatedFragment")) {
            movie = TopRatedFragment.movieList.get(position);
            setData();
        }
    }

    public void setData() {
        TextView original_title = (TextView) findViewById(R.id.original_title);
        original_title.setText(movie.getOriginal_title());
        ImageView details_image = (ImageView) findViewById(R.id.details_imageView);
        //details_image.setImageURI("https://image.tmdb.org/t/p/w185/" + movie.getPoster_path());
        TextView release_year = (TextView) findViewById(R.id.release_year);
        release_year.setText(movie.getRelease_date());
        TextView vote_average = (TextView) findViewById(R.id.vote_average);
        vote_average.setText(movie.getVote_average());
        TextView overview = (TextView) findViewById(R.id.overview);
        overview.setText(movie.getOverview());
    }
}

