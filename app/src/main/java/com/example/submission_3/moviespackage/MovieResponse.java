package com.example.submission_3.moviespackage;

import com.example.submission_3.ListMovieGenre;
import com.google.gson.annotations.SerializedName;

import java.util.List;

//Response
public class MovieResponse {
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<Movie> results;

    @SerializedName("genres")
    private List<ListMovieGenre> genresList;

    public List<Movie> getResults() {
        return results;
    }




}


