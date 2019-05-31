package com.hugof.filmsurfer;

import android.graphics.Movie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movies {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("vote_average")
    @Expose
    private String vote_average;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("poster_path")
    @Expose
    private String poster_path;

    @SerializedName("genre_ids")
    @Expose
    private List<Integer> genreIds;
    @SerializedName("release_date")
    @Expose
    private String release_date;




    public String getId() {
        return id;
    }

    public String getOverview() {
        return overview;
    }

    public String getVote_average() {
        return vote_average;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }
    public List<Integer> getGenreIds() {
        return genreIds;
    }
    public String getRelease_date() {
        return release_date;
    }


}
