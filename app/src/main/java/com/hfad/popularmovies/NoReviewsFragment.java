package com.hfad.popularmovies;


import android.app.ActionBar;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NoReviewsFragment extends Fragment {
    static final String TITLE = "title";
    private static final String TAG = "app";
    String movieTitle;

    public NoReviewsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        movieTitle = getActivity().getIntent().getStringExtra(TITLE);

        setActionBar();

        return inflater.inflate(R.layout.fragment_no_reviews, container, false);
    }

    private void setActionBar() {
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(movieTitle);
    }
}
