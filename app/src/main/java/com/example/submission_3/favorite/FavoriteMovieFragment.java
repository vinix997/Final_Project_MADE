package com.example.submission_3.favorite;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.submission_3.ListMovieGenre;
import com.example.submission_3.R;
import com.example.submission_3.adapter.MoviesAdapter;
import com.example.submission_3.moviespackage.Movie;
import com.example.submission_3.viewmodel.MyViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMovieFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView mRecyclerView;
    private MoviesAdapter adapter;
    private MyViewModel myViewModel;
    private ImageView imgError;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Movie> listmovie = new ArrayList<>();
    private List<ListMovieGenre> listgenre = new ArrayList<>();
    private List<ListMovieGenre> listGenres;
    private String genre;
    private Observer<List<ListMovieGenre>> getGenres = new Observer<List<ListMovieGenre>>() {
        @Override
        public void onChanged(@Nullable List<ListMovieGenre> listMovieGenres) {
            listGenres = listMovieGenres;
        }
    };


    private Observer<List<Movie>> getFavMovies = new Observer<List<Movie>>() {
        @Override
        public void onChanged(@Nullable List<Movie> movies) {
            if (movies.size() > 0) {
                adapter = new MoviesAdapter(getContext(), movies, listGenres);
                mRecyclerView.setAdapter(adapter);
                imgError.setVisibility(View.GONE);
            } else {
                adapter = new MoviesAdapter(getContext(),listmovie, listgenre);
                mRecyclerView.setAdapter(adapter);
                imgError.setVisibility(View.VISIBLE);
            }

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movies, container, false);
        mRecyclerView = v.findViewById(R.id.rv_movies);
        swipeRefreshLayout = v.findViewById(R.id.sr_layout);
        imgError = v.findViewById(R.id.error_img);
        swipeRefreshLayout.setOnRefreshListener(this);
        loadGenre();
        loadFavoriteData();
        return v;
    }

    private void loadFavoriteData() {
        myViewModel = ViewModelProviders.of(getActivity()).get(MyViewModel.class);
        myViewModel.getFavList(getActivity()).observe(getActivity(), getFavMovies);
    }


    private void loadGenre()
    {
        myViewModel = ViewModelProviders.of(getActivity()).get(MyViewModel.class);
        myViewModel.getGenreList().observe(getActivity() ,getGenres);
    }
    @Override
    public void onResume() {
        super.onResume();
        loadGenre();
        loadFavoriteData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadGenre();
        loadFavoriteData();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        loadGenre();
       loadFavoriteData();
    }
}
