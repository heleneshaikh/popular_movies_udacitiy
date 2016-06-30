package com.hfad.popularmovies;

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
public class ReviewFragment extends Fragment implements View.OnClickListener{
    private ReviewAdapter adapter;
    private static final String ENDPOINT = "http://api.themoviedb.org/3/";
    private static final String API_KEY = "561825fba9c2d42683bcbbd5b12dbd1e";
    static final String ID = "id";
    static final String TITLE = "title";
    private Context context;
    int id;
    String movieTitle;
    public static ArrayList<Review> reviewList;
    private static final String TAG = "app";

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

        return recyclerView;
    }

    @Override
    public void onClick(View v) {

    }

//    public void onClickSeeReviews(View view) {
//
//    }

}
