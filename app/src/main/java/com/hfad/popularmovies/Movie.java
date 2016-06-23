package com.hfad.popularmovies;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by heleneshaikh on 15/06/16.
 */
public class Movie {
    private double vote_average;
    private int id;
    private String overview;
    private String original_title;
    private String release_date;
    private String poster_path;
    private String popularity;

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", vote_average='" + vote_average + '\'' +
                ", overview='" + overview + '\'' +
                ", original_title='" + original_title + '\'' +
                ", release_date='" + release_date + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", popularity='" + popularity + '\'' +
                '}';
    }
}
