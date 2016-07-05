package com.hfad.popularmovies;


import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.hfad.popularmovies.Database.MovieDatabaseHelper;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment implements View.OnClickListener {
    private static final String ENDPOINT = "http://api.themoviedb.org/3/";
    private static final String API_KEY = "561825fba9c2d42683bcbbd5b12dbd1e";
    static final String POSITION = "position";
    static final String FRAGMENT_TYPE = "fragment";
    static final String MOVIE_ID = "movieId";
    private static final String TAG = "app";
    private Movie movie;
    private Context context;
    int id;
    MoviesAPI api;
    public static List<Trailer> trailerList;
    public static ArrayList<Review> reviewList;
    String movieTitle;
    int position;
    String fragmentType;
    Bundle bundle;
    int movieId;
    Retrofit retrofit;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final ScrollView scrollView = (ScrollView) inflater.inflate(R.layout.fragment_detail, container, false);

        if (MainActivity.isDualPane) {
            bundle = this.getArguments();
            fragmentType = bundle.getString(FRAGMENT_TYPE);
        } else {
            fragmentType = getActivity().getIntent().getStringExtra(FRAGMENT_TYPE);
        }

        if (fragmentType != null) {
            switch (fragmentType) {
                case "PopularFragment":
                    if (MainActivity.isDualPane) {
                        position = bundle.getInt(POSITION);
                    } else {
                        position = (int) getActivity().getIntent().getExtras().get(POSITION);
                    }
                    movie = PopularFragment.movieList.get(position);
                    id = movie.getId();
                    setData(scrollView);
                    break;

                case "TopRatedFragment":
                    if (MainActivity.isDualPane) {
                        position = bundle.getInt(POSITION);
                    } else {
                        position = (int) getActivity().getIntent().getExtras().get(POSITION);
                    }
                    movie = TopRatedFragment.movieList.get(position);
                    id = movie.getId();
                    setData(scrollView);
                    break;

                case "FavouriteListFragment":
                    if (MainActivity.isDualPane) {
                        Bundle bundle = this.getArguments();
                        movieId = bundle.getInt(MOVIE_ID);
                        id = movieId;
                    } else {
                        movieId = getActivity().getIntent().getExtras().getInt(MOVIE_ID);
                        id = movieId;
                    }
                    retrofitCall();
                    api.getMovies(movieId, API_KEY).enqueue(new Callback<Movie>() {
                        @Override
                        public void onResponse(Call<Movie> call, Response<Movie> response) {
                            movie = response.body();
                            setData(scrollView);
                        }

                        @Override
                        public void onFailure(Call<Movie> call, Throwable t) {
                            Toast toast = Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });
            }
        }

        ImageView imageView = (ImageView) scrollView.findViewById(R.id.iv_trailer);
        imageView.setOnClickListener(this);

        Button reviewButton = (Button) scrollView.findViewById(R.id.btn_reviews);
        reviewButton.setOnClickListener(this);

        Button favouriteButton = (Button) scrollView.findViewById(R.id.btn_favourite);
        favouriteButton.setOnClickListener(this);

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
        switch (v.getId()) {
            case R.id.iv_trailer:
                retrofitCall();
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
                        Toast toast = Toast.makeText(context, R.string.error, Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
                break;

            case R.id.btn_reviews:
                Log.v(TAG, Integer.toString(R.id.btn_reviews));
                retrofitCall();
                reviewList = new ArrayList<>();
                api.getReview(id, API_KEY).enqueue(new Callback<ReviewResult>() {
                    @Override
                    public void onResponse(Call<ReviewResult> call, Response<ReviewResult> response) {
                        ReviewResult reviewResult = response.body();
                        reviewList = (ArrayList<Review>) reviewResult.getResults();
                        if (MainActivity.isDualPane) {
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList(ReviewFragment.LIST, reviewList);
                            ReviewFragment reviewFragment = new ReviewFragment();
                            reviewFragment.setArguments(bundle);
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            transaction.replace(R.id.right_container, reviewFragment, "review_frag");
                            transaction.addToBackStack(null);
                            transaction.commit();
                        } else {
                            Intent intent = new Intent(getActivity(), ReviewsActivity.class);
                            intent.putParcelableArrayListExtra(ReviewsActivity.LIST, reviewList);
                            intent.putExtra(ReviewsActivity.TITLE, movieTitle);
                            intent.putExtra(NoReviewsActivity.TITLE, movieTitle);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<ReviewResult> call, Throwable t) {
                        Toast toast = Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
                break;

            case R.id.btn_favourite:
                Toast toast = Toast.makeText(getActivity(), R.string.add_fav, Toast.LENGTH_LONG);
                toast.show();
                SQLiteOpenHelper dbHelper = new MovieDatabaseHelper(getActivity());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("MOVIE_ID", movie.getId());
                contentValues.put("ORIGINAL_TITLE", movie.getOriginal_title());
                db.insert("MOVIE", null, contentValues);
                db.insertWithOnConflict("MOVIE", null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
                break;
        }
    }

    public void retrofitCall() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(MoviesAPI.class);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
}
