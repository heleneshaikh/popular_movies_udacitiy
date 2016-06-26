package com.hfad.popularmovies;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hfad.popularmovies.model.Movie;
import com.hfad.popularmovies.model.MoviesAPI;
import com.hfad.popularmovies.model.Review;
import com.hfad.popularmovies.model.ReviewResult;
import com.hfad.popularmovies.model.Trailer;
import com.hfad.popularmovies.model.TrailersResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DetailsActivity extends Activity {
    private static final String ENDPOINT = "http://api.themoviedb.org/3/";
    private static final String API_KEY = "561825fba9c2d42683bcbbd5b12dbd1e";
    static final String POSITION = "position";
    static final String FRAGMENT_TYPE = "fragment";
    private Movie movie;
    private int position;
    private Context context;
    int id;
    private final static String RESULT_TAG = "tag";
    public static List<Trailer> trailerList;
    public static List<Review> reviewList;
    String movieTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        position = (int) getIntent().getExtras().get(POSITION);
        String fragmentType = getIntent().getStringExtra(FRAGMENT_TYPE);
        if (fragmentType.equals("PopularFragment")) {
            movie = PopularFragment.movieList.get(position);
            id = movie.getId();
            setData();
        } else if (fragmentType.equals("TopRatedFragment")) {
            movie = TopRatedFragment.movieList.get(position);
            id = movie.getId();
            setData();
        }
    }

    public void setData() {
        TextView title = (TextView) findViewById(R.id.title);
        movieTitle = movie.getOriginal_title();
        title.setText(movieTitle);
        ImageView image = (ImageView) findViewById(R.id.iv_details);
        Picasso.with(context)
                .load("https://image.tmdb.org/t/p/w185/" + movie.getPoster_path())
                .into(image);
        TextView year = (TextView) findViewById(R.id.year);
        year.setText(movie.getRelease_date().substring(0, 4));
        TextView vote = (TextView) findViewById(R.id.vote);
        vote.setText(movie.getVote_average() + "/10");
        TextView overview = (TextView) findViewById(R.id.overview);
        overview.setText(movie.getOverview());

        setActionBar();
    }

    private void setActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(movie.getOriginal_title());
    }

    public void onClickViewTrailer(View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MoviesAPI api = retrofit.create(MoviesAPI.class);
        trailerList = new ArrayList<>();
        api.getTrailers(id, API_KEY).enqueue(new Callback<TrailersResult>() {
            @Override
            public void onResponse(Call<TrailersResult> call, Response<TrailersResult> response) {
                TrailersResult trailersResult = response.body();
                trailerList = trailersResult.getResults();
                String trailerKey = trailerList.iterator().next().getKey();
                String videoPath = "https://www.youtube.com/watch?v=" + trailerKey;

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoPath));
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<TrailersResult> call, Throwable t) {
                Toast toast = Toast.makeText(context, "an error occurred", Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    public void onClickAddFavourite(View view) {

    }
//http://api.themoviedb.org/3/movie/293660/reviews?api_key=561825fba9c2d42683bcbbd5b12dbd1e

    public void onClickSeeReviews(View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MoviesAPI api = retrofit.create(MoviesAPI.class);
        reviewList = new ArrayList<>();
        api.getReview(id, API_KEY).enqueue(new Callback<ReviewResult>() {
            @Override
            public void onResponse(Call<ReviewResult> call, Response<ReviewResult> response) {
                ReviewResult reviewResult = response.body();
//                int totalResults;
//                totalResults = Integer.parseInt(reviewResult.getTotal_results());
                reviewList = reviewResult.getResults();
                String review = reviewList.iterator().next().getContent();
                String author = reviewList.iterator().next().getAuthor();
                Intent intent = new Intent(DetailsActivity.this, ReviewsActivity.class);
                intent.putExtra(ReviewsActivity.REVIEW, review);
                intent.putExtra(ReviewsActivity.AUTHOR, author);
                intent.putExtra(ReviewsActivity.TITLE, movieTitle);
//                intent.putExtra(ReviewsActivity.TOTAL_RESULTS, totalResults);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<ReviewResult> call, Throwable t) {
                Toast toast = Toast.makeText(context, "an error occurred", Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}

