package com.hfad.popularmovies.model;

import java.util.List;

/**
 * Created by heleneshaikh on 24/06/16.
 */
public class TrailersResult {
    private String id;
    private List<Trailer> results;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Trailer> getResults() {
        return results;
    }

    public void setResults(List<Trailer> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", results = " + results + "]";
    }
}
