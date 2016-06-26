package com.hfad.popularmovies;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ReviewsActivity extends Activity {
    static final String AUTHOR = "author";
    static final String REVIEW = "review";
    static final String TITLE = "title";
    //    static final String TOTAL_RESULTS = "title";
    TextView tvReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        String author = getIntent().getStringExtra(AUTHOR);
        String review = getIntent().getStringExtra(REVIEW);
        String movieTitle = getIntent().getStringExtra(TITLE);
//        int totalResults = (int)getIntent().getExtras().get(TOTAL_RESULTS);

        TextView tvAuthor = (TextView) findViewById(R.id.tv_author);
        tvAuthor.append(author);
        tvReview = (TextView) findViewById(R.id.tv_review);
        tvReview.setText(review);
        TextView reviewTitle = (TextView) findViewById(R.id.review_title);
        reviewTitle.setText(movieTitle);
    }
}
