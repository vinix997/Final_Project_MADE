package com.example.submission_3.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.submission_3.BuildConfig;
import com.example.submission_3.GenreTVResponse;
import com.example.submission_3.ListMovieGenre;
import com.example.submission_3.GenreResponse;
import com.example.submission_3.ListTVGenre;
import com.example.submission_3.retrofitservice.RetrofitService;
import com.example.submission_3.api.ApiMovie;
import com.example.submission_3.api.ApiTV;
import com.example.submission_3.moviespackage.Movie;
import com.example.submission_3.moviespackage.MovieResponse;
import com.example.submission_3.room.DBRoom;
import com.example.submission_3.tvshowpackage.TVShow;
import com.example.submission_3.tvshowpackage.TVShowResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyViewModel extends ViewModel {
    public static final String API_KEY = BuildConfig.TMDB_API_KEY;
    public static final String LANGUAGE = "en-US";
    private MutableLiveData<List<ListMovieGenre>> listGenres = new MutableLiveData<>();
    private MutableLiveData<List<ListTVGenre>> listTvGenres = new MutableLiveData<>();
    private MutableLiveData<List<Movie>> listMovies = new MutableLiveData<>();
    private MutableLiveData<List<TVShow>> listTvs = new MutableLiveData<>();
    public static String getCurrDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public LiveData<List<TVShow>> getTvs(Context context) {
        loadTvs(context);
        return listTvs;
    }

    public LiveData<List<Movie>> getMovies(Context context) {

        loadMovies(context);

        return listMovies;
    }

    public LiveData<List<Movie>> getSearch(String newText) {
        searchMovie(newText);
        return listMovies;
    }

    public LiveData<List<TVShow>> getSearchTv(String newText) {
        searchTv(newText);
        return listTvs;
    }

    public LiveData<List<ListMovieGenre>> getGenreList()
    {
        generateGenreList();
        return listGenres;
    }
    public LiveData<List<ListTVGenre>> getTVGenre()
    {
        generateGenreTV();
        return listTvGenres;
    }
    private void generateGenreList()
    {
        final ApiMovie apiInterface = RetrofitService.createService(ApiMovie.class);
        Call<GenreResponse> call = apiInterface.getGenreList(API_KEY,LANGUAGE);
        call.enqueue(new Callback<GenreResponse>() {
            @Override
            public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                listGenres.setValue(response.body().getGenres());
            }

            @Override
            public void onFailure(Call<GenreResponse> call, Throwable t) {

            }
        });
    }
    private void generateGenreTV()
    {
        final ApiTV apiInterface = RetrofitService.createService(ApiTV.class);
        Call<GenreTVResponse> call = apiInterface.getListGenre(API_KEY);
        call.enqueue(new Callback<GenreTVResponse>() {
            @Override
            public void onResponse(@NonNull Call<GenreTVResponse> call, Response<GenreTVResponse> response) {

                    listTvGenres.setValue(response.body().getGenreList());

            }

            @Override
            public void onFailure(Call<GenreTVResponse> call, Throwable t) {

            }
        });
    }
    private void loadMovies(final Context context) {
        final ApiMovie apiInterface = RetrofitService.createService(ApiMovie.class);
        Call<MovieResponse> call = apiInterface.getTopRatedMovies(API_KEY,LANGUAGE);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                listMovies.setValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(context, "Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchMovie(final String newText) {

        final ApiMovie apiInterface = RetrofitService.createService(ApiMovie.class);

        Call<MovieResponse> call = apiInterface.getListSearchMovie(newText, API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                listMovies.setValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    private void searchTv(final String newText) {

        ApiTV apiInterface = RetrofitService.createService(ApiTV.class);
        Call<TVShowResponse> call = apiInterface.getListSearchTvs(newText, API_KEY);
        call.enqueue(new Callback<TVShowResponse>() {
            @Override
            public void onResponse(Call<TVShowResponse> call, Response<TVShowResponse> response) {
                listTvs.setValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<TVShowResponse> call, Throwable t) {
            }
        });
    }

    public LiveData<List<Movie>> getFavList(Context context) {
        MutableLiveData<List<Movie>> movieData = new MutableLiveData<>();
        List<Movie> test;
        test = DBRoom.getInstance(context.getApplicationContext()).movieDao().getFavouriteMovieList();
        movieData.setValue(test);
        return movieData;
    }

    public LiveData<List<TVShow>> getFavListTv(Context context) {
        MutableLiveData<List<TVShow>> tvData = new MutableLiveData<>();
        List<TVShow> test;
        test = DBRoom.getInstance(context.getApplicationContext()).tvDao().getFavouriteTvList();
        tvData.setValue(test);
        return tvData;
    }



    private void loadTvs(final Context context) {
        ApiTV apiInterface = RetrofitService.createService(ApiTV.class);
        Call<TVShowResponse> call = apiInterface.getTopRatedTvShow(API_KEY,LANGUAGE);
        call.enqueue(new Callback<TVShowResponse>() {
            @Override
            public void onResponse(Call<TVShowResponse> call, Response<TVShowResponse> response) {
                listTvs.setValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<TVShowResponse> call, Throwable t) {
            }
        });
    }


}
