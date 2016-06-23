package com.hfad.popularmovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesAPI {

    @GET("movie/top_rated")
    Call<QueryResult> getFeedTopRated(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<QueryResult> getFeedPopular(@Query("api_key") String apiKey);

}
