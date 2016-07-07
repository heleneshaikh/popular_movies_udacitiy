package com.hfad.popularmovies;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
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
    static final String TITLE = "title";
    String movieTitle;
    public static ArrayList<Review> reviewList;
    public static final String LIST = "";

    public ReviewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_popular, container, false);
        if (MainActivity.isDualPane) {
            Bundle bundle = this.getArguments();
            reviewList = bundle.getParcelableArrayList(LIST);
        } else {
            reviewList = getActivity().getIntent().getParcelableArrayListExtra(LIST);
            movieTitle = getActivity().getIntent().getStringExtra(TITLE);
        }

        adapter = new ReviewAdapter(getActivity(), reviewList);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        if (reviewList.size() > 0) {
            adapter.setReviewList(reviewList);
        } else {
            if (MainActivity.isDualPane) {
                NoReviewsFragment noReviewsFragment = new NoReviewsFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.addToBackStack(null);
                transaction.replace(R.id.right_container, noReviewsFragment);
                transaction.commit();
            } else {
                Intent intent = new Intent(getActivity(), NoReviewsActivity.class);
                intent.putExtra(NoReviewsActivity.TITLE, movieTitle);
                startActivity(intent);
            }
        }
        setActionBar();
        return recyclerView;
    }

    private void setActionBar() {
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(movieTitle);
    }
}



