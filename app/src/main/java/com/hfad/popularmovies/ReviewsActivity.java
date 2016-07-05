package com.hfad.popularmovies;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
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
    public static final String LIST = "";
    public static ArrayList<Review> reviewList;
    final static String TITLE = "title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setId(R.id.review_frag_container);
        setContentView(frameLayout, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ReviewFragment reviewFragment = new ReviewFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.review_frag_container, reviewFragment);
        transaction.commit();

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        String title = getIntent().getStringExtra(TITLE);
        actionBar.setTitle(title);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
