package com.hfad.popularmovies;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    static final String POSITION = "position";
    List<Movie> movieList;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View view = getView();
//        int position = (int) getActivity().getIntent().getExtras().get(POSITION);
//        Movie movie = movieList.get(position);
//        if (view != null) {
//            TextView original_title = (TextView) view.findViewById(R.id.original_title);
//            original_title.setText(movie.getOriginal_title());
//            ImageView details_image = (ImageView) view.findViewById(R.id.details_imageView);
//            //details_image.setImageURI("https://image.tmdb.org/t/p/w185/" + movie.getPoster_path());
//            TextView release_year = (TextView) view.findViewById(R.id.release_year);
//            release_year.setText(movie.getRelease_date());
//            TextView vote_average = (TextView) view.findViewById(R.id.vote_average);
//            vote_average.setText(movie.getVote_average());
//            TextView overview = (TextView) view.findViewById(R.id.overview);
//            overview.setText(movie.getOverview());
//        }
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

}
