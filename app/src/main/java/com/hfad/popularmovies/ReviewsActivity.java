package com.hfad.popularmovies;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class ReviewsActivity extends Activity {
    public static final String LIST = "";
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
