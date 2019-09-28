package com.example.submission_3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenreTVResponse {

    @SerializedName("genres")
    @Expose
    private List<ListTVGenre> genreList;

    public List<ListTVGenre> getGenreList()
    {
        return genreList;
    }
    public void setGenreList(List<ListTVGenre> genreList)
    {
        this.genreList = genreList;
    }
}
