package com.hfad.popularmovies;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hfad.popularmovies.adapters.ReviewAdapter;
import com.hfad.popularmovies.model.Review;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends Fragment {
    private ReviewAdapter adapter;
    private static final String ENDPOINT = "http://api.themoviedb.org/3/";
    private static final String API_KEY = "561825fba9c2d42683bcbbd5b12dbd1e";
    static final String ID = "id";
    static final String TITLE = "title";
    private Context context;
    int id;
    String movieTitle;
    public static ArrayList<Review> reviewList;
    public static final String LIST = "";

    public ReviewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_popular, container, false);
        adapter = new ReviewAdapter(getActivity(), reviewList);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        if (MainActivity.isDualPane) {
            Bundle bundle = this.getArguments();
            reviewList = bundle.getParcelableArrayList(LIST);
        } else {
            reviewList = getActivity().getIntent().getParcelableArrayListExtra(LIST);
        }
        adapter.setReviewList(reviewList);

        setActionBar();

        return recyclerView;
    }

    private void setActionBar() {
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(movieTitle);
    }
}



