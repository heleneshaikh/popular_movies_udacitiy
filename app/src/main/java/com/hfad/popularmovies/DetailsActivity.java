package com.hfad.popularmovies;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;


public class DetailsActivity extends Activity {
    static final String POSITION = "position";
    static final String FRAGMENT_TYPE = "fragment";
    private Movie movie;
    private int position;
    private Context context;

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
        TextView title = (TextView) findViewById(R.id.original_title);
        title.setText(movie.getOriginal_title());
        ImageView image = (ImageView) findViewById(R.id.details_imageView);
        Picasso.with(context)
                .load("https://image.tmdb.org/t/p/w185/" + movie.getPoster_path())
                .into(image);
        TextView year = (TextView) findViewById(R.id.release_year);
        year.setText(movie.getRelease_date().substring(0, 4));
        TextView vote = (TextView) findViewById(R.id.vote_average);
        vote.setText(Math.round(movie.getVote_average()) + "/10");
        TextView review = (TextView) findViewById(R.id.overview);
        review.setText(movie.getOverview());
        setActionBar();
    }

    private void setActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(movie.getOriginal_title());
    }
}

