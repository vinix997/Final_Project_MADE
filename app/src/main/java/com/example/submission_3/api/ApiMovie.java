package com.example.submission_3.api;

import com.example.submission_3.moviespackage.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiMovie {
    @GET("movie/upcoming")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey,@Query("language") String language);

    @GET("search/movie/")
    Call<MovieResponse> getListSearchMovie(@Query("query") String query, @Query("api_key") String apiKey);

    @GET("discover/movie")
    Call<MovieResponse> getUpcomingDate(@Query("api_key") String apiKey,@Query("language") String language
            , @Query("primary_release_date.gte") String currDate
            , @Query("primary_release_date.lte") String currDate2);


}

