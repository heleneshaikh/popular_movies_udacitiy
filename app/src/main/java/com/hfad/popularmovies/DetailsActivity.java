package com.hfad.popularmovies;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class DetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setId(R.id.detail_frag_container);
        setContentView(frameLayout, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        if (savedInstanceState == null) {
            DetailFragment detailFragment = new DetailFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.addToBackStack(null);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.add(R.id.detail_frag_container, detailFragment);
            transaction.commit();
        }
    }

    public void onClickAddFavourite(View view) {

    }


//    public void onClickSeeReviews(View view) {
//        if (MainActivity.isDualPane) {
//            ReviewFragment reviewFragment = new ReviewFragment();
//            reviewFragment.onClickSeeReviews(view);
//        } else {
//
//        }
//        detailFragment.onClickSeeReviews(view);
//    }
}

