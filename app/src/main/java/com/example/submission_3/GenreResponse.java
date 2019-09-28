package com.example.submission_3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenreResponse {
    @SerializedName("genres")
    @Expose
    private List<ListMovieGenre> genres;

    public List<ListMovieGenre> getGenres()
    {
        return genres;
    }




    public void setGenres(List<ListMovieGenre> genres)
    {
        this.genres = genres;
    }


}
