package com.hfad.popularmovies;


import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.hfad.popularmovies.adapters.ReviewAdapter;
import com.hfad.popularmovies.model.Movie;
import com.hfad.popularmovies.model.MoviesAPI;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment implements View.OnClickListener{
    private static final String ENDPOINT = "http://api.themoviedb.org/3/";
    private static final String API_KEY = "561825fba9c2d42683bcbbd5b12dbd1e";
    static final String POSITION = "position";
    static final String FRAGMENT_TYPE = "fragment";
    private Movie movie;
    private Context context;
    int id;
    public static List<Trailer> trailerList;
    String movieTitle;
    int position;
    String fragmentType;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ScrollView scrollView = (ScrollView) inflater.inflate(R.layout.fragment_detail, container, false);
        if (MainActivity.isDualPane) {
            Bundle bundle = this.getArguments();
            position = bundle.getInt(POSITION);
            fragmentType = bundle.getString(FRAGMENT_TYPE);
        } else {
            position = (int)getActivity().getIntent().getExtras().get(POSITION);
            fragmentType = getActivity().getIntent().getStringExtra(FRAGMENT_TYPE);
        }

        if (fragmentType != null) {
            if (fragmentType.equals("PopularFragment")) {
                movie = PopularFragment.movieList.get(position);
                id = movie.getId();
                setData(scrollView);
            } else if (fragmentType.equals("TopRatedFragment")) {
                movie = TopRatedFragment.movieList.get(position);
                id = movie.getId();
                setData(scrollView);
            }
        }

        ImageView imageView = (ImageView) scrollView.findViewById(R.id.iv_trailer);
        imageView.setOnClickListener(this);

        return scrollView;
    }

    private void setData(ScrollView scrollView) {
        TextView title = (TextView) scrollView.findViewById(R.id.title);
        movieTitle = movie.getOriginal_title();
        title.setText(movieTitle);
        ImageView image = (ImageView) scrollView.findViewById(R.id.iv_details);
        Picasso.with(context)
                .load("https://image.tmdb.org/t/p/w185/" + movie.getPoster_path())
                .into(image);
        TextView year = (TextView) scrollView.findViewById(R.id.year);
        year.setText(movie.getRelease_date().substring(0, 4));
        TextView vote = (TextView) scrollView.findViewById(R.id.vote);
        double averageVote = Math.round(movie.getVote_average() * 10) / 10d;
        vote.setText(Double.toString(averageVote));
        vote.append("/10");
        TextView overview = (TextView) scrollView.findViewById(R.id.overview);
        overview.setText(movie.getOverview());

        setActionBar();
    }

    private void setActionBar() {
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(movie.getOriginal_title());
    }


    @Override
    public void onClick(View v) {
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

//    public void onClickAddFavourite(View view) {
//
//    }

//    public void onClickSeeReviews(View view) {
//        Intent intent = new Intent(DetailsActivity.this, ReviewsActivity.class);
//        intent.putExtra(ReviewsActivity.TITLE, movieTitle);
//        intent.putExtra(ReviewsActivity.ID, id);
//        startActivity(intent);
//    }

}
