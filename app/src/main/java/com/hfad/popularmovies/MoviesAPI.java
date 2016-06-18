package com.hfad.popularmovies;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;

public interface MoviesAPI {

    @GET("movie/top_rated?api_key=XXXXXXXXXXXXXXX")
    Call<List<Movie>> getFeedTopRated();

    @GET("movie/popular?api_key=XXXXXXXXXXXXXXX")
    Call<List<Movie>> getFeedPopular();

}
