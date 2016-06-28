package com.hfad.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.hfad.popularmovies.adapters.ReviewAdapter;
import com.hfad.popularmovies.model.MoviesAPI;
import com.hfad.popularmovies.model.Review;
import com.hfad.popularmovies.model.ReviewResult;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewsActivity extends Activity {
    private ReviewAdapter adapter;
    private static final String ENDPOINT = "http://api.themoviedb.org/3/";
    private static final String API_KEY = "561825fba9c2d42683bcbbd5b12dbd1e";
    static final String ID = "id";
    private Context context;
    int id;
    public static ArrayList<Review> reviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.review_recycler);
        adapter = new ReviewAdapter(this, reviewList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        id = getIntent().getExtras().getInt(ID);

        getReviews();
    }

    public void getReviews() {
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
                reviewList = (ArrayList<Review>) reviewResult.getResults();
                adapter.setReviewList(reviewList);
            }

            @Override
            public void onFailure(Call<ReviewResult> call, Throwable t) {
                Toast toast2 = Toast.makeText(context, "An error occurred", Toast.LENGTH_LONG);
                toast2.show();
            }
        });
    }
}
