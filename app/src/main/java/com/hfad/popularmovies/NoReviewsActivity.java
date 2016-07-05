package com.hfad.popularmovies;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class NoReviewsActivity extends Activity {
    static final String TITLE = "title";
    private static final String TAG = "app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_reviews);

        NoReviewsFragment noReviewsFragment = new NoReviewsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack(null);
        transaction.replace(R.id.no_reviews_container, noReviewsFragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.startActivity(new Intent(NoReviewsActivity.this, MainActivity.class));
    }


}
