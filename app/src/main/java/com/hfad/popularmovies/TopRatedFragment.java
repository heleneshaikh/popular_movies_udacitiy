package com.hfad.popularmovies;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopRatedFragment extends Fragment {

    private static final String ENDPOINT = "http://api.themoviedb.org/3/";
    private PosterAdapter adapter;
    private List<Movie> movieList;

    public TopRatedFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_top_rated, container, false);
        adapter = new PosterAdapter(getActivity(), movieList);
        recyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        getTopRatedMovies();

        return recyclerView;
    }

    private void getTopRatedMovies() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MoviesAPI api = retrofit.create(MoviesAPI.class);
        movieList = new ArrayList<>();
        api.getFeedTopRated().enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                movieList = response.body();
                adapter.setMovieList(movieList);
        }

        @Override
        public void onFailure (Call < List < Movie >> call, Throwable t){
            Toast toast = Toast.makeText(getActivity(), "unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    );

}

}
