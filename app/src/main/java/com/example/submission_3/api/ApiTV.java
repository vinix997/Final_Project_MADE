package com.example.submission_3.api;

import com.example.submission_3.tvshowpackage.TVShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiTV {
    @GET("tv/popular")
    Call<TVShowResponse> getTopRatedTvShow(@Query("api_key") String apiKey,@Query("language") String language);

    @GET("search/tv")
    Call<TVShowResponse> getListSearchTvs(@Query("query") String query, @Query("api_key") String apiKey);
}
